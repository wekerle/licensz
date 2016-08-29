/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Helpers.StringHelper;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import licenta.IdGenerator;

/**
 *
 * @author Ronaldo
 */
public class LocalTimeRangeModel implements Serializable,Comparable<LocalTimeRangeModel>
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

    @Override
    public int compareTo(LocalTimeRangeModel time) 
    {
        if(time.startTime==this.startTime && time.endTime==this.endTime)
        {
            return 0;
        }
        return -1;
    }
    
    public int getDurationMinutes()
    {
        long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
        return (int)minutes;
    }
    
    public boolean contains(LocalTime time)
    {
        if((time.isAfter(startTime) || time.compareTo(startTime)==0) && time.isBefore(endTime))
        {
            return true;
        }
        return false;
    }
    
    public boolean contains(LocalTimeRangeModel time)
    {
        if(contains(time.startTime) && contains(time.endTime))
        {
            return true;
        }
        return false;
    }
    
    public boolean intersects(LocalTimeRangeModel time)
    {
        if(time.contains(this) || this.contains(time) || time.contains(endTime) || time.contains(startTime))
        {
            return true;
        }
        return false;
    }
}
