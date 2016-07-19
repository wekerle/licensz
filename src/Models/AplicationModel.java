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
public class AplicationModel implements Serializable {
    private ArrayList<TopicModel> topics=new ArrayList<TopicModel>();
    private ArrayList<DayModel> days=new ArrayList<DayModel>();
    private boolean hasModification=true;
    private int maxNumberSessionPerDay=5;
    private int deafultBreakDuration=10;

    public int getMaxNumberSessionPerDay() {
        return maxNumberSessionPerDay;
    }

    public void setMaxNumberSessionPerDay(int maxNumberSessionPerDay) {
        this.maxNumberSessionPerDay = maxNumberSessionPerDay;
    }
        
    public void setTopics(ArrayList<TopicModel> topics)
    {
        this.topics=topics;
    }
    
    public ArrayList<TopicModel> getTopics() {
        return this.topics;
    }

    public boolean hasModification() {
        return hasModification;
    }

    public void setHasModification(boolean hasModification) {
        this.hasModification = hasModification;
    }

    public ArrayList<DayModel> getDays() {
        return days;
    }

    public void setDays(ArrayList<DayModel> days) {
        this.days = days;
    }

    public int getDeafultBreakDuration() {
        return deafultBreakDuration;
    }

    public void setDeafultBreakDuration(int deafultBreakDuration) {
        this.deafultBreakDuration = deafultBreakDuration;
    }  
}
