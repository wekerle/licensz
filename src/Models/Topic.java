/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import licentav2.GlobalVaribles;

/**
 *
 * @author Ronaldo
 */
public class Topic {
    private String title;
    private ArrayList<Session> sessions=new ArrayList<Session>();
    private int id;
    
    public Topic()
    {
         id=GlobalVaribles.getTopicNumber();
         GlobalVaribles.addElementToTopicAndNumberMap(id, this);
         GlobalVaribles.setSessionNumber(GlobalVaribles.getTopicNumber()+1);
    }
    
    public String getTitle() {
        return title;
    }
    
    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }
}
