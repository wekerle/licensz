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
    private boolean hasModification=true;
        
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
}
