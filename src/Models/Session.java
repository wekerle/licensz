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
public class Session {
    private String title;
    private ArrayList<LectureWithDetails> LectureWithDetails=new  ArrayList<LectureWithDetails>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<LectureWithDetails> getLectures() {
        return LectureWithDetails;
    }
}
