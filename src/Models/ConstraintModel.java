/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.time.LocalDate;

/**
 *
 * @author tibor.wekerle
 */
public class ConstraintModel {
    private String teacherName;
    private LocalDate date;
    private LocalTimeRangeModel timeRange;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTimeRangeModel getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(LocalTimeRangeModel timeRange) {
        this.timeRange = timeRange;
    }
    
}
