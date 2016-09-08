/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author tibor.wekerle
 */
public class ConstraintModel implements Serializable
{
    private String authorName;
    private ArrayList<DateAndPeriodModel> datesAndPeriods=new ArrayList<DateAndPeriodModel>();
    
    public String getAuthorName() 
    {
        return authorName;
    }

    public void setAuthorName(String teacherName) 
    {
        this.authorName = teacherName;
    }
    
    public void addDateAndPeriod(DateAndPeriodModel dateAndPeriod)
    {
        this.datesAndPeriods.add(dateAndPeriod);
    }
    
    public void deleteDateAndPeriod(DateAndPeriodModel dateAndPeriod)
    {
        this.datesAndPeriods.remove(dateAndPeriod);
    }
    
    public ArrayList<DateAndPeriodModel> getDatesAndPeriods()
    {
        return this.datesAndPeriods;
    }
}
