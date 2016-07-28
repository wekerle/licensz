/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

import java.time.LocalDate;

/**
 *
 * @author tibor.wekerle
 */
public interface DayChangeEventListener 
{
    void modifyDate(int dayId,LocalDate localdate);
}
