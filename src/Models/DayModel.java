/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private LocalTimeRangeModel totalPeriod;
    private LocalDate day;
    private int numberOfSessionsPerDay;
    
    //timeRange -> room -> session
    private HashMap<Integer,HashMap<Integer,SessionModel>> roomTimeMap=new HashMap<Integer,HashMap<Integer,SessionModel>>();    
       
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
    
    public void addNewTimeBreak()
    {
        LocalTimeRangeModel time=new LocalTimeRangeModel(times.get(times.size()-1).getEndTime(), 15);
        times.add(time);
        
        LocalTimeRangeModel breakTime=new LocalTimeRangeModel(times.get(times.size()-1).getEndTime(), 15);
        times.add(breakTime);
        
        SessionModel session=new SessionModel();
        session.makeBreak("Time break");
        
        for(RoomModel room:rooms)
        {          
            addSession(session,breakTime,room);
        }
    }
    
    public LocalTimeRangeModel getTotalPeriod()
    {
        return totalPeriod;
    }

    public void setTotalPeriod(LocalTimeRangeModel totalPeriod) 
    {
        this.totalPeriod = totalPeriod;
    }

    public int getNumberOfSessionsPerDay() 
    {
        return numberOfSessionsPerDay;
    }

    public void setNumberOfSessionsPerDay(int numberOfSessionsPerDay) 
    {
        this.numberOfSessionsPerDay = numberOfSessionsPerDay;
    }

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
        if(session==null)
        {
            return;
        }
        
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
    
    public SessionModel removeSessionByTimeRoom(int timeId,int roomId)
    {
        HashMap<Integer,SessionModel> roomTime =roomTimeMap.get(timeId);
        SessionModel session=null;
        
        if(roomTime!=null && roomTime.size()>0)
        {
            session=roomTime.get(roomId);
            if(session!=null && !session.isBreak())
            {
                roomTime.remove(roomId);
                return session;
            }            
        }
        
        return null;
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
    
    public boolean removeSession(int sessionId)
    {
        for(Map.Entry<Integer, HashMap<Integer, SessionModel>> timeRoomSession:roomTimeMap.entrySet())
        {
            for(Map.Entry<Integer, SessionModel> roomSession: timeRoomSession.getValue().entrySet())
            {
                boolean contain=roomSession.getValue().getId()==sessionId;
                if(contain && !roomSession.getValue().isBreak())
                {
                    timeRoomSession.getValue().remove(roomSession.getKey());
                    shiftUp(timeRoomSession.getKey(), roomSession.getKey());
                    return contain;
                }
            }
        }
        return false;
    }
    
    public boolean containsSession(int sessionId)
    {
        for(Map.Entry<Integer, HashMap<Integer, SessionModel>> timeRoomSession:roomTimeMap.entrySet())
        {
            for(Map.Entry<Integer, SessionModel> roomSession: timeRoomSession.getValue().entrySet())
            {
                boolean contain=roomSession.getValue().getId()==sessionId;
                if(contain)
                {
                    return contain;
                }
            }
        }
        return false;
    }
    
    public LocalTimeRangeModel getTimeRangeBySessionId(int sessionId)
    {
        for(Map.Entry<Integer, HashMap<Integer, SessionModel>> timeRoomSession:roomTimeMap.entrySet())
        {
            for(Map.Entry<Integer, SessionModel> roomSession: timeRoomSession.getValue().entrySet())
            {
                if(roomSession.getValue().getId()==sessionId)
                {
                    return getTimeRangeModelById(timeRoomSession.getKey());
                }
            }
        }
        return null;
    }
    
    public void shiftDown(int timeId,int roomId)
    {      
        int index=0;
       
        for(int i=0;i<times.size();i++)
        {
            if(timeId==times.get(i).getId())
            {
               index=i;
               break;
            }
           
        }
        
        for(int i=times.size()-3;i>=index;i--)
        {
            LocalTimeRangeModel time=times.get(i);
            LocalTimeRangeModel timeNext=times.get(i+1);
            
            //the above if is neccesary to mange to skip the time break dureing the shifting procces
            SessionModel nextTimeSession=getSessionModelTimeRoom(timeNext.getId(),roomId);
            
            if(nextTimeSession!=null && nextTimeSession.isBreak())
            {
                timeNext=times.get(i+2);
            }
            SessionModel session=removeSessionByTimeRoom(time.getId(),roomId);
            addSession(session, timeNext, getRoomModelById(roomId));
             
        }
    }
    
    public void shiftUp(int timeId,int roomId)
    {
        int index=0;
        for(int i=0;i<times.size();i++)
        {
            if(timeId==times.get(i).getId())
            {
               index=i;
               break;
            }
           
        }
        
        LocalTimeRangeModel lastTime=times.get(times.size()-1);
        
        for(int i=index;i<times.size()-1;i++)
        {
            LocalTimeRangeModel time=times.get(i);
            LocalTimeRangeModel timeNext=times.get(i+1);
            if(timeNext.compareTo(lastTime)!=0)
            {
                //the above if is neccesary to mange to skip the time break dureing the shifting procces
                SessionModel nextTimeSession=getSessionModelTimeRoom(timeNext.getId(),roomId);
                if(nextTimeSession!=null && nextTimeSession.isBreak())
                {
                    timeNext=times.get(i+2);
                }
                SessionModel session=removeSessionByTimeRoom(timeNext.getId(),roomId);
                addSession(session, time, getRoomModelById(roomId));
            }        
        }
    }
    
    public LocalTimeRangeModel getNextTimeByCurrentTimeAndRoom(int currentTimeId)
    {
        for(int i=0;i<times.size()-2;i++)
        {
            if(times.get(i).getId()==currentTimeId)
            {
                return times.get(i+2);
            }
        }
        return null;
    }
    
    public void cleanUpUselessBreaks()
    {
        int index=times.size();
        for(int i=times.size()-2;i>=0;i--)
        {
            LocalTimeRangeModel currentTime=times.get(i);
            
            boolean deleteRow=true;
            for(RoomModel room:rooms)
            {
                SessionModel session=getSessionModelTimeRoom(currentTime.getId(), room.getId());
                
                if(session!=null && !session.isBreak())
                {
                    deleteRow=false;
                    break;
                } 
            }
            
            if(deleteRow)
            {
                index=i;
            }
            else
            {
                break;
            }
        }
        
        for(int i=times.size()-2;i>=index;i--)
        {
            LocalTimeRangeModel currentTime=times.get(i);
            roomTimeMap.remove(currentTime.getId());
            times.remove(i);
        }
    }
    
    public void deleteEmptyRoom()
    {      
        for(int i=rooms.size()-1;i>=0;i--)
        {
            RoomModel currentRoom=rooms.get(i);
            
            boolean deleteColumn=true;
            for(LocalTimeRangeModel time:times)
            {
                SessionModel session=getSessionModelTimeRoom(time.getId(), currentRoom.getId());
                
                if(session!=null && !session.isBreak())
                {
                    deleteColumn=false;
                    break;
                } 
            }
            
            if(deleteColumn)
            {
                for(Map.Entry<Integer, HashMap<Integer, SessionModel>> timeRoomSession:roomTimeMap.entrySet())
                {
                    if(timeRoomSession.getKey()==currentRoom.getId())
                    {
                        timeRoomSession.getValue().remove(currentRoom.getId());
                    }
                }
                rooms.remove(currentRoom);
            }
        }     
    }   

    public SessionModel getSessionById(int id) {
        for(Map.Entry<Integer, HashMap<Integer, SessionModel>> timeRoomSession:roomTimeMap.entrySet())
        {
            for(Map.Entry<Integer, SessionModel> roomSession: timeRoomSession.getValue().entrySet())
            {
                if(roomSession.getValue().getId()==id)
                {
                    return roomSession.getValue();
                }
            }
        }
        return null;
    }
}
