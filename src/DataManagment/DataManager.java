/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagment;

import Models.AplicationModel;
import Models.DayModel;
import Models.LectureWithDetailsModel;
import Models.LocalTimeRangeModel;
import Models.RoomModel;
import Models.SessionModel;
import Models.TopicModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author tibor.wekerle
 */
public class DataManager 
{
    
    private AplicationModel aplicationModel=null;
    
    // <editor-fold desc="private region" defaultstate="collapsed">
    
    private DayModel getDayBySessionId(int sessionId) 
    {
        for(DayModel day : aplicationModel.getDays())
        {
            boolean result=day.containsSession(sessionId);
            if(result==true)
            {
                return day;
            }
        }
        return null;
    }
        
    private RoomModel getRoomByTopicId(int topicId) 
    {
        for(DayModel day : aplicationModel.getDays())
        {
           for(RoomModel room : day.getRooms())
           {
               if(room.getTopicId()==topicId)
               {
                   return room;
               }
           }
        }
        return null;
    }
    
    private TopicModel getTopicIdBySessionId(int sessionId)
    {
        for(TopicModel topic :aplicationModel.getTopics())
        {
            for(SessionModel session:topic.getSessions())
            {
                if(session.getId()==sessionId)
                {
                    return topic;
                }
            }
        }
        return null;
    }
    
    private TopicModel getTopicIdById(int topicId)
    {
        for(TopicModel topic :aplicationModel.getTopics())
        {
            if(topic.getId()==topicId)
            {
                 return topic;
            }
        }
        return null;
    }
    
    private SessionModel getSessionById(int sessionId)
    {
        for(TopicModel topic :aplicationModel.getTopics())
        {
            for(SessionModel session:topic.getSessions())
            {
                if(session.getId()==sessionId)
                {
                    return session;
                }
            }
        }
        return null;
    }
    
    private SessionModel getSessionByLectureId(int lectureId)
    {
        for(TopicModel topic :aplicationModel.getTopics())
        {
            for(SessionModel session:topic.getSessions())
            {
                for(LectureWithDetailsModel lecture:session.getLectures())
                {
                    if(lecture.getId()==lectureId)
                    {
                        return session;
                    }
                }                
            }
        }
        return null;
    }
    
    private LectureWithDetailsModel getLectureById(int lectureId)
    {
        for(TopicModel topic :aplicationModel.getTopics())
        {
            for(SessionModel session:topic.getSessions())
            {
                for(LectureWithDetailsModel lecture:session.getLectures())
                {
                    if(lecture.getId()==lectureId)
                    {
                        return lecture;
                    }
                }                
            }
        }
        return null;
    }
    
    private void addLectureToSession(SessionModel session,LectureWithDetailsModel lecture)
    {
        session.getLectures().add(lecture);
    }
    
    private void removeLectureFromSession(SessionModel session,LectureWithDetailsModel lecture)
    {
        session.getLectures().remove(lecture);
    }
    
    private int getSessionPositionIdBySessionId(int sessionId)
    {
        for(TopicModel topic :aplicationModel.getTopics())
        {
            for(SessionModel session:topic.getSessions())
            {
                if(session.getId()==sessionId)
                {
                   return topic.getSessions().indexOf(session);
                }
            }
        }
        return 0;
    }
        
    //</editor-fold>
    
    public DataManager(AplicationModel aplicationModel)
    {
        this.aplicationModel=aplicationModel;
    }
    
    public void addSessionToTopicBySessionId(TopicModel topicModel,SessionModel session, int position)
    {                      
        for(TopicModel topic :aplicationModel.getTopics())
        {
            if(topic.getId()==topicModel.getId())
            {
                if(topic.getSessions().size()<=position)
                {
                    topic.getSessions().add(session);
                }else
                {
                    topic.getSessions().add(position, session);
                }
            }
        }       
    }
    
    public void removeSessionFromTopicBySessionId(TopicModel topicModel, int sessionId)
    {   
        SessionModel session=getSessionById(sessionId);
        for(TopicModel topic :aplicationModel.getTopics())
        {
            if(topic.getId()==topicModel.getId())
            {
                topic.getSessions().remove(session);
            }
        }
    }
    
    public void moveDestinationSessionBeforeSourceSession(int destinationSessionId,int sourceSessionId)
    {               
        TopicModel destinationTopic=getTopicIdBySessionId(destinationSessionId);
        TopicModel sourceTopic=getTopicIdBySessionId(sourceSessionId);
        int session1Position=getSessionPositionIdBySessionId(destinationSessionId);
        SessionModel session=getSessionById(sourceSessionId);
        
        removeSessionFromTopicBySessionId(sourceTopic, sourceSessionId);
        addSessionToTopicBySessionId(destinationTopic, session, session1Position);
        
        DayModel sourceDay=getDayBySessionId(sourceSessionId);
        DayModel destinationDay=getDayBySessionId(destinationSessionId);
        
        RoomModel room=getRoomByTopicId(destinationTopic.getId());
        LocalTimeRangeModel time=destinationDay.getTimeRangeBySessionId(destinationSessionId);
        
        sourceDay.removeSession(sourceSessionId);
        destinationDay.shiftDown(time.getId(), room.getId());
        destinationDay.addSession(session, time, room);
        
        sourceDay.cleanUpUselessBreaks();
        destinationDay.cleanUpUselessBreaks();
        
    }
    
    public void moveDestinationSessionAfterSourceSession(int destinationSessionId,int sourceSessionId)
    {               
        TopicModel destinationTopic=getTopicIdBySessionId(destinationSessionId);
        TopicModel sourceTopic=getTopicIdBySessionId(sourceSessionId);
        int session1Position=getSessionPositionIdBySessionId(destinationSessionId);
        SessionModel session=getSessionById(sourceSessionId);
        
        removeSessionFromTopicBySessionId(sourceTopic, sourceSessionId);
        addSessionToTopicBySessionId(destinationTopic, session, session1Position+1);
        
        DayModel sourceDay=getDayBySessionId(sourceSessionId);
        DayModel destinationDay=getDayBySessionId(destinationSessionId);
        
        RoomModel room=getRoomByTopicId(destinationTopic.getId());
        LocalTimeRangeModel time=destinationDay.getTimeRangeBySessionId(destinationSessionId);
        
        LocalTimeRangeModel nextTime=destinationDay.getNextTimeByCurrentTimeAndRoom(time.getId());
        
        sourceDay.removeSession(sourceSessionId);
        destinationDay.shiftDown(nextTime.getId(), room.getId());
        destinationDay.addSession(session, nextTime, room);
        
        sourceDay.cleanUpUselessBreaks();
        destinationDay.cleanUpUselessBreaks();
        
    }
    
    public boolean checkIfLectureExistInSession(int sessionId,int lectureId)
    {               
        for(TopicModel topic :aplicationModel.getTopics())
        {
            for(SessionModel session:topic.getSessions())
            {
                if(session.getId()==sessionId)
                {
                   for (LectureWithDetailsModel lecture:session.getLectures())
                   {
                       if(lecture.getPageNr()==lectureId)
                       {
                           return true;
                       }
                   }
                }
            }
        }
        return false;
    }
    
    public SessionModel getSessionBySessionId(int sessionId)
    {               
        for(TopicModel topic :aplicationModel.getTopics())
        {
            for(SessionModel session:topic.getSessions())
            {
                if(session.getId()==sessionId)
                {
                   return session;
                }
            }
        }
        return null;
    }
    
    public void moveLectureToSession(int sessionId,int lectureId)
    {               
        if(!checkIfLectureExistInSession(sessionId,lectureId))
        {
            SessionModel destinationSession=getSessionById(sessionId);
            SessionModel sourceSession=getSessionByLectureId(lectureId);
            
            LectureWithDetailsModel lecture=getLectureById(lectureId);
        
            this.removeLectureFromSession(sourceSession, lecture);
            this.addLectureToSession(destinationSession, lecture);

        };
    } 
    
    public void changeLectureTitleByLectureId(int lectureId,String newTitle)
    {               
        LectureWithDetailsModel lecture=getLectureById(lectureId);
        lecture.setTitle(newTitle);
    }
    
    public void changeSessionTitleBySessionId(int sessionId,String newTitle)
    {               
        SessionModel session=getSessionById(sessionId);
        session.setTitle(newTitle);
    }
    
    public void changeTopicTitleByTopicId(int topicId,String newTitle)
    {               
        TopicModel topic=getTopicIdById(topicId);
        topic.setTitle(newTitle);
    }
    
    public void setSessionChairsBySessionId(int sessionId,ArrayList<String> chairs)
    {               
        SessionModel session=getSessionBySessionId(sessionId);
        session.setChairs(chairs);
    }
    
    public void setLectureAuthorsByLectureId(int lectureId,ArrayList<String> authors)
    {               
        LectureWithDetailsModel lecture=getLectureById(lectureId);
        lecture.setAuthors(authors);
    }

    public void updateDay(int dayId, LocalDate localdate)
    {
        for(DayModel day:this.aplicationModel.getDays())
        {
            if(day.getId()==dayId)
            {
                day.setDay(localdate);
            }
        }
    }

    public void updateHour(int periodId, LocalTimeRangeModel timeRange)
    {
        for(DayModel day:this.aplicationModel.getDays())
        {
            for(LocalTimeRangeModel time:day.getTimes())
            {
                if(time.getId()==periodId)
                {
                    time.setStartTime(timeRange.getStartTime());
                    time.setEndTime(timeRange.getEndTime());
                }
            }
        }
    }

    public void updateTimeBreakTitle(int id, String newValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateRoomName(int id, String newValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
