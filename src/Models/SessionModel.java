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
public class SessionModel {
    private String title;
    private ArrayList<LectureWithDetailsModel> LectureWithDetails=new  ArrayList<LectureWithDetailsModel>();
    private int id;
    
    public SessionModel()
    {
         id=GlobalVaribles.getSessionNumber();
         GlobalVaribles.addElementToSessionAndNumberMap(id, this);
         GlobalVaribles.setSessionNumber(GlobalVaribles.getSessionNumber()+1);
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
}
