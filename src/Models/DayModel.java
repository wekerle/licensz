/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ronaldo
 */
public class DayModel {
    private int id;
    private ArrayList<RoomModel> rooms=new ArrayList<RoomModel>();
    private ArrayList<LocalTimeRangeModel> times=new ArrayList<LocalTimeRangeModel>();
    private HashMap<Integer,HashMap<Integer,Integer>> roomTimeMap=new HashMap<Integer,HashMap<Integer,Integer>>();
    
    
}
