/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Helpers.StringHelper;
import java.io.Serializable;
import java.time.LocalTime;
import licenta.IdGenerator;

/**
 *
 * @author Ronaldo
 */
public class LocalTimeRangeModel implements Serializable
{
    private int id;
    private LocalTime startTime;
    private LocalTime endTime;

    public int getId() 
    {
        return id;
    }

    public LocalTimeRangeModel(LocalTime startTime, LocalTime endTime) 
    {
        this.startTime = startTime;
        this.endTime = endTime;
        id=IdGenerator.getNewId();
    }
    
    public LocalTimeRangeModel(int hour, int minute,int durationMinute) 
    {
        
        this.startTime = LocalTime.of(hour, minute);
        this.endTime = startTime.plusMinutes(durationMinute);
        id=IdGenerator.getNewId();
    }
    
    public LocalTimeRangeModel(LocalTime startTime,int durationMinute) 
    {
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(durationMinute);
        id=IdGenerator.getNewId();
    }

    public LocalTime getStartTime() 
    {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) 
    {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() 
    {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) 
    {
        this.endTime = endTime;
    }
    
    @Override
    public String toString()
    {
        if(startTime!=null && endTime!=null)
        {
            return startTime.format(StringHelper.timeFormatter)+"-"+endTime.format(StringHelper.timeFormatter);
        }
        return "";
    }
}
