/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;
import licentav2.IdGenerator;

/**
 *
 * @author Ronaldo
 */
public class TopicModel implements Serializable{
    private String title;
    private ArrayList<SessionModel> sessions=new ArrayList<SessionModel>();
    private int id;
    
    public TopicModel()
    {
         id=new IdGenerator().getNewId();
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

    public ArrayList<SessionModel> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<SessionModel> sessions) {
        this.sessions = sessions;
    }
}
