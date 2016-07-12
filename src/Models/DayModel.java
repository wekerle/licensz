/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    
    //timeRange -> room -> session
    private HashMap<Integer,HashMap<Integer,Integer>> roomTimeMap=new HashMap<Integer,HashMap<Integer,Integer>>();
    private LocalTimeRangeModel totalPeriod;
    
    private LocalDate day=LocalDate.now();

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }
    
    public void addRoom(RoomModel room)
    {
        this.rooms.add(room);
    }
    
    public void removeRoom(RoomModel room)
    {
        this.rooms.remove(room);
    }
    //todo ellenorizni hogy ne tevodjon egymasra
    public void addTimeRange(LocalTimeRangeModel timeRange)
    {
        this.times.add(timeRange);
    }
    
    public void removeTimeRange(LocalTimeRangeModel timeRange)
    {
        this.times.remove(timeRange);
    }
    
    public RoomModel getRoomModelById(int id)
    {
        for(RoomModel room : rooms)
        {
            if(room.getId()==id)
            {
                return room;
            }
        }
        return null;
    }
    
    public LocalTimeRangeModel getTimeRangeModelById(int id)
    {
        for(LocalTimeRangeModel timeRange : times)
        {
            if(timeRange.getId()==id)
            {
                return timeRange;
            }
        }
        return null;
    }
    
    public void addSession(SessionModel session,LocalTimeRangeModel time,RoomModel room)
    {
        LocalTimeRangeModel test=getTimeRangeModelById(time.getId());
        RoomModel test2=getRoomModelById(room.getId());
        
        if(test== null || test2==null)
        {
            throw new IllegalArgumentException("The room or period is null");
        }
        
        HashMap<Integer,Integer> roomTime =roomTimeMap.get(time.getId());
        
        if(roomTime==null)
        {
            roomTime=new HashMap<Integer,Integer>();
            roomTimeMap.put(time.getId(), roomTime);
        }
        
        Integer roomId= roomTime.get(room.getId());
        
        if(roomId==null)
        {
            roomTime.put(room.getId(), session.getId());
        }else
        {
           throw new IllegalArgumentException("session already exist at that place and time");
        }   
    }
    
    public int getSessionIdByRoomTime(int timeId,int roomId)
    {
        HashMap<Integer,Integer> roomTime =roomTimeMap.get(timeId);
        Integer sessionId=null;
        
        if(roomTime!=null)
        {
            sessionId=roomTime.get(roomId);
        }
       if(sessionId==null)
       {
           return 0;
       }else
       {
           return sessionId;
       }
    }
    
}
