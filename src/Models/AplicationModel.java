/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;

/**
 *
 * @author Ronaldo
 */
public class AplicationModel {
    private ArrayList<TopicModel> topics=new ArrayList<TopicModel>();
        
    public void setTopics(ArrayList<TopicModel> topics)
    {
        this.topics=topics;
    }
    
    public ArrayList<TopicModel> getTopics() {
        return this.topics;
    }
    
}
