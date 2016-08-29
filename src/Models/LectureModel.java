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
public class LectureModel implements Serializable
{
    private int pageNr;
    private String title;
    private String topic;
    private String type;
    private ArrayList<String> authors;
    private String abstarct;
    private ArrayList<String> keyWords;
    private LocalTimeRangeModel period;
    private ArrayList<KeyWordModel> generatedKeyWords;
    private int id;

    public LocalTimeRangeModel getPeriod() {
        return period;
    }

    public void setPeriod(LocalTimeRangeModel period) {
        this.period = period;
    }
       
    public int getPageNr() 
    {
        return pageNr;
    }
    
    public int getId() 
    {
        return id;
    }

    public String getTopic() 
    {
        return topic;
    }

    public String getType() 
    {
        return type;
    }

    public String getTitle() 
    {
        return title;
    }

    public ArrayList<String> getAuthors() 
    {
        return authors;
    }

    public String getAbstarct() 
    {
        return abstarct;
    }

    public ArrayList<String> getKeyWords() 
    {
        return keyWords;
    }

    public ArrayList<KeyWordModel> getGeneratedKeyWords() 
    {
        return generatedKeyWords;
    }
    public LectureModel(String title, ArrayList<String> authors, int pageNr, String type, ArrayList<String> keyWords, ArrayList<KeyWordModel> generatedKeyWords,String topic,String abstarct) 
    {
        this.pageNr = pageNr;
        this.title = title;
        this.authors = authors;
        this.abstarct = abstarct;
        this.keyWords = keyWords;
        this.generatedKeyWords = generatedKeyWords;
        this.type=type;
        this.topic=topic;
        
        id=IdGenerator.getNewId();
    }   

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public void setAuthors(ArrayList<String> authors) 
    {
        this.authors = authors;
    }
}
