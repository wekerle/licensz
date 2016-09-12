/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import licenta.IdGenerator;

/**
 *
 * @author Ronaldo
 */
public class SessionModel implements Serializable
{
    private String title;
   // private ArrayList<String> chairs=new  ArrayList<String>();
    private String chair;
    private String coChair;
    
    private ArrayList<LectureModel> lectures=new  ArrayList<LectureModel>();
    private int id;
    
    public SessionModel()
    {
         id=IdGenerator.getNewId();
         chair="chair"+IdGenerator.getNewId();
         coChair="co-chair"+IdGenerator.getNewId();
    }
    
    public void makeBreak(String title)
    {
        this.lectures=null;
        this.chair=null;
        this.coChair=null;
        this.title=title;
    }
    
    public boolean isBreak()
    {
        return lectures == null && chair==null && coChair==null;
    }
    
    public String getTitle() 
    {
        return title;
    }
    
    public int getId() 
    {
        return id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public ArrayList<LectureModel> getLectures() 
    {
        return lectures;
    }

    public String getChair() 
    {
        return chair;
    }
    
    public String getCoChair() 
    {
        return coChair;
    }

    public void setChair(String chair) 
    {
        this.chair = chair;
    }
    
    public void setCoChair(String coChair) 
    {
        this.coChair = coChair;
    }

    public void calculatedPeriodForLectures(int shortLectureDuration,int longLectureDuration,LocalTimeRangeModel sessionPeriod) 
    {
        LocalTime startTime=sessionPeriod.getStartTime();
        int duration=0;
        for(LectureModel lecture:lectures)
        {
            if(lecture.getType().compareTo("S")==0)
            {
                duration=shortLectureDuration;
            }
            if(lecture.getType().compareTo("F")==0)
            {
                duration=longLectureDuration;
            }
            LocalTimeRangeModel lecturePeriod=new LocalTimeRangeModel(startTime,duration);
            lecture.setPeriod(lecturePeriod);
            
            startTime=lecturePeriod.getEndTime();
        }
    }
    
}
