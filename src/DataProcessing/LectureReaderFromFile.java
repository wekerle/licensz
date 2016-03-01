/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProcessing;

import Models.KeyWord;
import Models.Session;
import Models.Part;
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

    private ArrayList<File> listOfFilesIdTitleAuthors = new ArrayList<File>();
    private ArrayList<File> listOfFilesKeywordsAbtrsct = new ArrayList<File>();
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
    public LectureReaderFromFile(String idTitleAuthors,String abstractKeywords) {
               
        ArrayList<File> files1 = getAllFilesFromDir(idTitleAuthors);
        ArrayList<File> files2 = getAllFilesFromDir(abstractKeywords);
        
        
        for (File f1:files1)
        {
            File fileToRemove=null;
            for(File f2:files2)
            {
                String[] nameSplit1=f1.getName().split("_");
                String[] nameSplit2=f2.getName().split("_");
                if(nameSplit1[nameSplit1.length-1].compareTo(nameSplit2[nameSplit2.length-1])==0)
                {
                    listOfFilesIdTitleAuthors.add(f1);
                    listOfFilesKeywordsAbtrsct.add(f2);
                    fileToRemove=f2;
                }
            }
            // cerejem ki a listakat hogy lehgyenek lancolt listak
            files2.remove(fileToRemove);
        }        
    }
    
    @Override
    public boolean readNext() {
        index++;
        if(index>=listOfFilesIdTitleAuthors.size())
        {
            return false;
        }
        currentLecture=readLectureDetailsFromFiles(listOfFilesIdTitleAuthors.get(index), listOfFilesKeywordsAbtrsct.get(index));
        return true;
    }

    @Override
    public LectureWithDetails getCurrent() {
       return currentLecture;
    }

    private LectureWithDetails readLectureDetailsFromFiles(File titleAuthorsAbstract, File idKeywords) {
        
        //roszul neveztem el a fileokat mindehol 
        try {
            BufferedReader br = new BufferedReader(new FileReader(idKeywords));
            String line = br.readLine();
            
             BufferedReader br2 = new BufferedReader(new FileReader(titleAuthorsAbstract));
            String line2 = br2.readLine();
            
            int id=0;
            ArrayList<String> keyWords=new ArrayList<String>();
            ArrayList<String> authors=new ArrayList<String>();
            ArrayList<String> generatedKeyWords=new ArrayList<String>();
            ArrayList<KeyWord> listOfKeyWords=new ArrayList<KeyWord>();
            String title="";
            String abstarct="";          
            
            while (line != null) {
                id=Integer.parseInt(line);               
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
                
                //azert tettem ide 2 redline-ot mert most szandekosan at akarok szokni
                //azzon a soron ahol irja azt hogy Topic
                //majd amikor arra szukseg lesz majd azt is be kell olvassam
                line = br.readLine();
            }
            
            while (line2 != null) {
                title=line2;   
                // ide pedig azert tettem 2 redline-ot mert a tanar ugy adta a bementi fajlot hogy ott van meg egy ures sor
                line2 = br2.readLine();
                line2 = br2.readLine();
                
                
                authors=new ArrayList<String>(Arrays.asList(line2.split(",")));
                
                //ide is pont az az ok mint az elobinnel
                line2 = br2.readLine();
                 line2 = br2.readLine();
                abstarct=line2;
                line2 = br2.readLine();
            }
           return new LectureWithDetails(id, title, authors,  abstarct,  keyWords,  listOfKeyWords);
        } catch(Exception e) {
           // br.close();
            }
        return null;
       
    }
}
