/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ronaldo
 */
public class AplicationModel implements Serializable 
{
    private ArrayList<TopicModel> topics=new ArrayList<TopicModel>();
    private ArrayList<DayModel> days=new ArrayList<DayModel>();
    private ArrayList<ConstraintModel> constraints=new ArrayList<ConstraintModel>();
    private HashMap<String,String> teacherAffiliationMap=null;
    private String pathToThesaurus=null;
    private String pathToTecherAffiliation=null;
    private int shortLectureDuration=15;
    private int longLectureDuration=20;
    private boolean hasModification=false;

    public HashMap<String, String> getTeacherAffiliationMap() 
    {
        return teacherAffiliationMap;
    }

    public void setTeacherAffiliationMap(HashMap<String, String> teacherAffiliationMap) 
    {
        this.teacherAffiliationMap = teacherAffiliationMap;
    }

    public int getShortLectureDuration() {
        return shortLectureDuration;
    }

    public void setShortLectureDuration(int shortLectureDuration) {
        this.shortLectureDuration = shortLectureDuration;
    }

    public int getLongLectureDuration() {
        return longLectureDuration;
    }

    public void setLongLectureDuration(int longLectureDuration) {
        this.longLectureDuration = longLectureDuration;
    }
  
    public String getPathToThesaurus() 
    {
        return pathToThesaurus;
    }

    public void setPathToThesaurus(String pathToThesaurus) 
    {
        this.pathToThesaurus = pathToThesaurus;
    }
    
    public String getPathToTecherAffiliation() 
    {
        return pathToTecherAffiliation;
    }

    public void setPathToTecherAffiliation(String pathToTecherAffiliation) 
    {
        this.pathToTecherAffiliation = pathToTecherAffiliation;
    }
            
    public void setTopics(ArrayList<TopicModel> topics)
    {
        this.topics=topics;
    }
    
    public ArrayList<TopicModel> getTopics() 
    {
        return this.topics;
    }

    public boolean hasModification() 
    {
        return hasModification;
    }

    public void setHasModification(boolean hasModification) 
    {
        this.hasModification = hasModification;
    }

    public ArrayList<DayModel> getDays() 
    {
        return days;
    }

    public void setDays(ArrayList<DayModel> days) 
    {
        this.days = days;
    }

    public ArrayList<ConstraintModel> getConstraints() {
        return constraints;
    }

    public void setConstraints(ArrayList<ConstraintModel> constraints) {
        this.constraints = constraints;
    }
    
}
