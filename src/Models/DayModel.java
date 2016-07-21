/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import licenta.IdGenerator;

/**
 *
 * @author Ronaldo
 */
public class DayModel implements Serializable
{
    // <editor-fold desc="private region" defaultstate="collapsed">
    private int id;
    private ArrayList<RoomModel> rooms=new ArrayList<RoomModel>();
    private ArrayList<LocalTimeRangeModel> times=new ArrayList<LocalTimeRangeModel>();
    
    //timeRange -> room -> session
    private HashMap<Integer,HashMap<Integer,SessionModel>> roomTimeMap=new HashMap<Integer,HashMap<Integer,SessionModel>>();
    private LocalTimeRangeModel totalPeriod;
    
    private LocalDate day=LocalDate.now();
    
    private RoomModel getRoomModelById(int id)
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
    
    private LocalTimeRangeModel getTimeRangeModelById(int id)
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
    
    //</editor-fold>
    
    public DayModel()
    {
        this.id=IdGenerator.getNewId();
    }
    
    public LocalDate getDay() 
    {
        return day;
    }

    public int getId() 
    {
        return id;
    }
        
    public void setDay(LocalDate day) 
    {
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
        
    public void addSession(SessionModel session,LocalTimeRangeModel time,RoomModel room)
    {
        LocalTimeRangeModel timeModel=getTimeRangeModelById(time.getId());
        RoomModel roomModel=getRoomModelById(room.getId());
        
        if(timeModel== null || roomModel==null)
        {
            throw new IllegalArgumentException("The room or period is null");
        }
        
        HashMap<Integer,SessionModel> roomSessionMap =roomTimeMap.get(time.getId());
        
        if(roomSessionMap==null)
        {
            roomSessionMap=new HashMap<Integer,SessionModel>();
            roomTimeMap.put(time.getId(), roomSessionMap);
        }
        
        SessionModel sessionModel= roomSessionMap.get(room.getId());
        
        if(sessionModel==null)
        {
            roomSessionMap.put(room.getId(), session);
        }else
        {
           throw new IllegalArgumentException("session already exist at that place and time");
        }   
    }
    
    public SessionModel getSessionModelTimeRoom(int timeId,int roomId)
    {
        HashMap<Integer,SessionModel> roomTime =roomTimeMap.get(timeId);
        SessionModel session=null;
        
        if(roomTime!=null)
        {
            session=roomTime.get(roomId);
        }
           return session;
    }
    
    public int getRoomNumberCount()
    {
        return rooms.size();
    }
    
    public int getTimesNumberCount()
    {
        return times.size();
    }

    public ArrayList<RoomModel> getRooms()
    {
        return rooms;
    }

    public ArrayList<LocalTimeRangeModel> getTimes() 
    {
        return times;
    }
    
}
