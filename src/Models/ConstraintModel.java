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
    private String teacherName;
    private ArrayList<DateAndPeriodModel> dateAndPeriods=new ArrayList<DateAndPeriodModel>();
    
    public String getTeacherName() 
    {
        return teacherName;
    }

    public void setTeacherName(String teacherName) 
    {
        this.teacherName = teacherName;
    }
    
    public void addDateAndPeriod(DateAndPeriodModel dateAndPeriod)
    {
        this.dateAndPeriods.add(dateAndPeriod);
    }
    
    public ArrayList<DateAndPeriodModel> getDateAndPeriod()
    {
        return this.dateAndPeriods;
    }
}
