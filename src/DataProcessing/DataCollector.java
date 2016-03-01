/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProcessing;

import Models.Session;
import Models.Part;
import Models.Lecture;
import Models.LectureWithDetails;
import java.util.ArrayList;

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
        
        LectureReaderFromFile lrff=new LectureReaderFromFile("C:\\Users\\Ronaldo\\Documents\\NetBeansProjects\\licentav2\\data\\abstracts","C:\\Users\\Ronaldo\\Documents\\NetBeansProjects\\licentav2\\data\\keywords");
        while(lrff.readNext())
        {
            LectureWithDetails lecture = lrff.getCurrent();
            lectures.add(lecture);
        }
        return lectures;
    }
    
    public ArrayList<Session> getSessions()
    {
        ArrayList<Session> sessions =new ArrayList<Session>();        
        //ArrayList<Lecture> lectures=getLectures();
        ArrayList<LectureWithDetails> lectures=getLecturesFromfiles();
        
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
        ArrayList<Part> parts =new ArrayList<Part>();        
        ArrayList<Session> sessions=getSessions();
        
        Part p1= new Part();
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
         parts.add(p3);
         
        return parts;
    }    
}
