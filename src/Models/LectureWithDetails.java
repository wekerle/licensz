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
public class LectureWithDetails{
    private int id;
    private String title;
    private ArrayList<String> authors;
    private String abstarct;
    private ArrayList<String> keyWords;
    private ArrayList<KeyWord> generatedKeyWords;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getAbstarct() {
        return abstarct;
    }

    public ArrayList<String> getKeyWords() {
        return keyWords;
    }

    public ArrayList<KeyWord> getGeneratedKeyWords() {
        return generatedKeyWords;
    }

    public LectureWithDetails(int id, String title, ArrayList<String> authors, String abstarct, ArrayList<String> keyWords, ArrayList<KeyWord> generatedKeyWords) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.abstarct = abstarct;
        this.keyWords = keyWords;
        this.generatedKeyWords = generatedKeyWords;
    }



    
}
