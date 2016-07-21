/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;
import licenta.IdGenerator;

/**
 *
 * @author Ronaldo
 */
public class SessionModel implements Serializable{
    private String title;
    private ArrayList<String> chairs=new  ArrayList<String>();
    
    private ArrayList<LectureWithDetailsModel> LectureWithDetails=new  ArrayList<LectureWithDetailsModel>();
    private int id;
    
    public SessionModel()
    {
         id=IdGenerator.getNewId();
         chairs.add("chair1");
         chairs.add("chair2");
    }
    
    public void makeBreak(String title)
    {
        this.LectureWithDetails=null;
        this.chairs=null;
        this.title=title;
    }
    
    public boolean isBreak()
    {
        return LectureWithDetails == null && chairs==null;
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

    public ArrayList<LectureWithDetailsModel> getLectures() {
        return LectureWithDetails;
    }

    public ArrayList<String> getChairs() {
        return chairs;
    }

    public void setChairs(ArrayList<String> chairs) {
        this.chairs = chairs;
    }

    public void setLectureWithDetails(ArrayList<LectureWithDetailsModel> LectureWithDetails) {
        this.LectureWithDetails = LectureWithDetails;
    }
    
}
