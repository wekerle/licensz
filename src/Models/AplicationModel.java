/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ronaldo
 */
public class AplicationModel implements Serializable 
{
    private ArrayList<TopicModel> topics=new ArrayList<TopicModel>();
    private ArrayList<DayModel> days=new ArrayList<DayModel>();
    private ArrayList<ConstraintModel> constraints=new ArrayList<ConstraintModel>();
    private String pathToThesaurus=null;
    private boolean hasModification=true;

    public String getPathToThesaurus() 
    {
        return pathToThesaurus;
    }

    public void setPathToThesaurus(String pathToThesaurus) 
    {
        this.pathToThesaurus = pathToThesaurus;
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
