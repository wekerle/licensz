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
    private ArrayList<Topic> topics=new ArrayList<Topic>();
        
    public void setTopics(ArrayList<Topic> topics)
    {
        this.topics=topics;
    }
    
    public ArrayList<Topic> getTopics() {
        return this.topics;
    }
    
}
