/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Ronaldo
 */
public class LocalTimeRange {
    private LocalTime startTime;
    private LocalTime endTime;

    public LocalTimeRange(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public LocalTimeRange(int hour, int minute,int durationMinute) {
        
        this.startTime = LocalTime.of(hour, minute);
        this.endTime = startTime.plusMinutes(durationMinute);
    }
    
    public LocalTimeRange(LocalTime startTime,int durationMinute) {
        
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(durationMinute);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    @Override
    public String toString(){
        return startTime.getHour()+":"+startTime.getMinute()+"-"+endTime.getHour()+":"+endTime.getMinute();
    }
}
