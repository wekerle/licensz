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
    private ArrayList<Session> sessions=new ArrayList<Session>();
    private ArrayList<Part> parts=new ArrayList<Part>();
    
    public void setSessions(ArrayList<Session> sessions)
    {
        this.sessions=sessions;
    }
    
    public void setParts(ArrayList<Part> parts)
    {
        this.parts=parts;
    }

    public ArrayList<Session> getSessions() {
        return this.sessions;
    }
    
    public ArrayList<Part> getParts() {
        return this.parts;
    }
    
}
