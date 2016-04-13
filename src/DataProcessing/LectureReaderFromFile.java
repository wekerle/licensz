/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProcessing;

import Models.KeyWord;
import Models.Session;
import Models.Topic;
import Models.Lecture;
import Models.LectureWithDetails;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Ronaldo
 */
public class LectureReaderFromFile implements LectureReader{

    private ArrayList<File> listOfFiles = new ArrayList<File>();
    private LectureWithDetails currentLecture=null;
    private int index=-1;
        
    private ArrayList<File> getAllFilesFromDir(String path)
    {
        ArrayList<File> files=new ArrayList<File>();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
          if (listOfFiles[i].isFile()) {
            //System.out.println("File " + listOfFiles[i].getName());
            files.add(listOfFiles[i]);
          } 
        }
        return files;
    }
    public LectureReaderFromFile(String pathToFiles) {
               
        ArrayList<File> files = getAllFilesFromDir(pathToFiles);
             
        for (File f:files)
        {
            listOfFiles.add(f);
        }        
    }
    
    @Override
    public boolean readNext() {
        index++;
        if(index>=listOfFiles.size())
        {
            return false;
        }
        currentLecture=readLectureDetailsFromFiles(listOfFiles.get(index));
        return true;
    }

    @Override
    public LectureWithDetails getCurrent() {
       return currentLecture;
    }

    private LectureWithDetails readLectureDetailsFromFiles(File file) {
        
        //roszul neveztem el a fileokat mindehol 
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            ArrayList<String> keyWords=new ArrayList<String>();
            ArrayList<String> authors=new ArrayList<String>();
            ArrayList<String> generatedKeyWords=new ArrayList<String>();
            ArrayList<KeyWord> listOfKeyWords=new ArrayList<KeyWord>();
            String title="";
            String topic="";
            String abstarct="";          
            int pageNr=0;
            String type="";          
            
            while (line != null) {          
                title=line;
                line = br.readLine();
                
                authors=new ArrayList<String>(Arrays.asList(line.split(",")));
                line = br.readLine();
                
                pageNr=Integer.parseInt(line);
                line = br.readLine();
                                 
                type=line;
                line = br.readLine();
                
                keyWords=new ArrayList<String>(Arrays.asList(line.split(",")));
                line = br.readLine();
                
                generatedKeyWords=new ArrayList<String>(Arrays.asList(line.split(",")));
                for (String s : generatedKeyWords)
                {
                    String[] keywordSplit= s.split("\\s*[\\(\\)]");
                    KeyWord kw=new KeyWord(keywordSplit[0],Integer.parseInt(keywordSplit[1]));
                    listOfKeyWords.add(kw);
                }
                line = br.readLine();
                
                topic=line;
                line = br.readLine();
                
                abstarct=line;
                line = br.readLine();
            }
           return new LectureWithDetails(title, authors, pageNr,type, keyWords,  listOfKeyWords,topic,abstarct);
        } catch(Exception e) {
           // br.close();
            }
        return null;
       
    }
}
