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
import Models.TermModel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

/**
 *
 * @author Ronaldo
 */
public class DataCollector{
    private ArrayList<Lecture> lectures =new ArrayList<Lecture>();
    
    public ArrayList<Lecture> getLectures()
    {
       return lectures;
    }
    
    private ArrayList<LectureWithDetails>  getLecturesFromfiles()
    {
        ArrayList<LectureWithDetails> lectures=new ArrayList<LectureWithDetails>();
        // laptop
        //LectureReaderFromFile lrff=new LectureReaderFromFile("C:\\Users\\Ronaldo\\Desktop\\licensGit\\licensz\\data\\generated");
        
        //munkaba
        LectureReaderFromFile lrff=new LectureReaderFromFile("C:\\Users\\tibor.wekerle\\Desktop\\licenszeGit\\licensz\\data\\generated");
        
        while(lrff.readNext())
        {
            LectureWithDetails lecture = lrff.getCurrent();
            lectures.add(lecture);
        }
        return lectures;
    }
    
    private void groupByKeyWord(ArrayList<LectureWithDetails> lectures)
    {
        HashMap<String,ArrayList<LectureWithDetails>> map=new HashMap();
        HashMap<String,ArrayList<LectureWithDetails>> map2=new HashMap();
        for(LectureWithDetails lwd :lectures)
        {                      
            for(KeyWord kw:lwd.getGeneratedKeyWords())
            {
                 if(!map.containsKey(kw.getKeyWord()))
                 {
                     map.put(kw.getKeyWord(), new ArrayList<LectureWithDetails>());
                 }
                 map.get(kw.getKeyWord()).add(lwd);
            }
           
        }
        
        HashSet<LectureWithDetails> hs=new HashSet();
        for(Entry<String,ArrayList<LectureWithDetails>> entry : map.entrySet())
        {
            if(entry.getValue().size()>1)
            {
                map2.put(entry.getKey(), entry.getValue());
                hs.addAll(entry.getValue());
            }
        }
               
        int x=0;
        
    }
    
    public ArrayList<Session> getSessions()
    {
        ArrayList<Session> sessions =new ArrayList<Session>();        
        //ArrayList<Lecture> lectures=getLectures();
        ArrayList<LectureWithDetails> lectures=getLecturesFromfiles();
        groupByKeyWord(lectures);  
        
        Session s1= new Session();
        s1.setTitle("Inteligent System 1");
        
        Session s2= new Session();
        s2.setTitle("Inteligent System 2");
        
        Session s3= new Session();
        s3.setTitle("Inteligent System 3");
        
        Session s4= new Session();
        s4.setTitle("Inteligent System 4");
        
        Session s5= new Session();
        s5.setTitle("Inteligent System 5");
        
        Session s6= new Session();
        s6.setTitle("Inteligent System 6");
        
        Session s7= new Session();
        s7.setTitle("Inteligent System 7");
        
        Session s8= new Session();
        s8.setTitle("Inteligent System 8");
        
        Session s9= new Session();
        s9.setTitle("Inteligent System 9");
        
        int i=0;
        for (LectureWithDetails l : lectures)
        {
            if(i<4)
            {
                s1.getLectures().add(l);
            }
            else if(i<8)
            {
                s2.getLectures().add(l);
            }else if(i<12)
            {
                s3.getLectures().add(l);
            }else if(i<16)
            {
                s4.getLectures().add(l);
            }else if(i<20)
            {
                s5.getLectures().add(l);
            }else if(i<24)
            {
                s6.getLectures().add(l);
            }else if(i<28)
            {
                s7.getLectures().add(l);
            }
            else if(i<32)
            {
                s8.getLectures().add(l);
            }
            else if(i<36)
            {
                s9.getLectures().add(l);
            }
            i++;
        }
        sessions.add(s1);
        sessions.add(s2);
        sessions.add(s3);
        sessions.add(s4);
        sessions.add(s5);       
        sessions.add(s6);
        sessions.add(s7);
        sessions.add(s8);
        sessions.add(s9);
        
        return sessions;
    }
    public ArrayList<Part> getParts()
    {
        HashMap<String,TermModel> terms=getTermModelsFromfile();
        ArrayList<Part> parts =new ArrayList<Part>();        
       // ArrayList<Session> sessions=getSessions();
       
       ArrayList<String> topics=new ArrayList<String>();       
       ArrayList<LectureWithDetails> lectures=getLecturesFromfiles();
       
       for(LectureWithDetails lwd:lectures)
       {
           if(!topics.contains(lwd.getTopic()))
           {
               topics.add(lwd.getTopic());
           }
       }
       
       for(String topic:topics)
       {
           Part p= new Part();
           p.setTitle(topic);
           parts.add(p);
       }
       
       for(Part p :parts)
       {
           ArrayList<Session> sessions=new ArrayList<Session>();
           Session s=new Session();
           int i=1;
           s.setTitle(p.getTitle()+" "+i);
           
           for(LectureWithDetails lwd:lectures)
            {                
                if(lwd.getTopic().equals(p.getTitle()))
                {                                                        
                    if(s.getLectures().size()<4){
                        s.getLectures().add(lwd);
                    }else
                    {
                        sessions.add(s);
                        s=new Session();
                        i++;
                        s.setTitle(p.getTitle()+" "+i);
                    }
                }
            }
           sessions.add(s);
           
           p.setSessions(sessions);
       }
       
       /* Part p1= new Part();
        Part p2= new Part();
        Part p3= new Part();
        int i=0;
         for (Session s : sessions)
        {
            if(i<4)
            {
                 p1.getSessions().add(s);
            }else if(i<7)
            {
                 p2.getSessions().add(s);
            }else
            {
                 p3.getSessions().add(s);
            }
            i++;
           
        }
        
         p1.setTitle("Part I");
         p2.setTitle("Part II");
         p3.setTitle("Part III");
         
         parts.add(p1);
         parts.add(p2);
         parts.add(p3);*/
         
        return parts;
    }

    private HashMap<String,TermModel>  getTermModelsFromfile()
    {
        HashMap<String,TermModel> terms=new HashMap<String,TermModel>();
        // laptop
        //File file = new File("");
        
        //munkaba
        File file = new File("C:\\Users\\tibor.wekerle\\Desktop\\licenszeGit\\licensz\\data\\ieee_thesaurus_2013.txt");
        terms=new TermReaderFromFile().readTermsFromFile(file);
        
        return terms;
    }    
}
