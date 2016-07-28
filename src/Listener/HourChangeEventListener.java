/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

import Models.LocalTimeRangeModel;

/**
 *
 * @author tibor.wekerle
 */
public interface HourChangeEventListener 
{
    void modifyHour(int periodId,LocalTimeRangeModel timeRange);
}
