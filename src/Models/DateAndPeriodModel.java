/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Helpers.StringHelper;
import java.time.LocalDate;

/**
 *
 * @author tibor.wekerle
 */
public class DateAndPeriodModel
{
    private LocalDate date;
    private LocalTimeRangeModel timeRange;
    
    public LocalDate getDate() 
    {
        return date;
    }
    
    public DateAndPeriodModel(LocalDate date, LocalTimeRangeModel period)
    {
        this.date=date;
        this.timeRange=period;
    }
    
    public LocalTimeRangeModel getTimeRange() 
    {
        return timeRange;
    }
    
    public String getDayAndTimeString()
    {
        if(date!=null && timeRange!=null)
        {
            return StringHelper.getConverter().toString(date)+" "+timeRange.toString();
        }
        else if(timeRange!=null)
        {
            return timeRange.toString();
        }
        else if(date!=null)
        {
            return StringHelper.getConverter().toString(date);
        }
        return "";            
    }  
}
