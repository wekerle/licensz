/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagment;

import Models.AplicationModel;
import Models.DayModel;
import Models.LectureModel;
import Models.LocalTimeRangeModel;
import Models.RoomModel;
import Models.SessionModel;
import Models.TopicModel;
import java.util.ArrayList;
/**
 *
 * @author tibor.wekerle
 */
public class DataManager 
{
    
    private AplicationModel aplicationModel=null;
    
    // <editor-fold desc="private region" defaultstate="collapsed">
    
    private void deleteEmptyDays() 
    {        
        ArrayList<DayModel> days=aplicationModel.getDays();
        for(int i=days.size()-1;i>=0;i--)
        {
            if(days.get(i).getNumberOfSessions()==0)
            {
                days.remove(i);
            }
        }
    }
    
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
                for(LectureModel lecture:session.getLectures())
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
    
    private LectureModel getLectureById(int lectureId)
    {
        for(TopicModel topic :aplicationModel.getTopics())
        {
            for(SessionModel session:topic.getSessions())
            {
                for(LectureModel lecture:session.getLectures())
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
    
    private void addLectureToSession(SessionModel session,LectureModel lecture)
    {
        session.getLectures().add(lecture);
    }
    
    private void removeLectureFromSession(SessionModel session,LectureModel lecture)
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
    
    private void deleteEmptyTopics()
    {
        for(int i=aplicationModel.getTopics().size()-1;i>=0;i--)
        {
            TopicModel topic=aplicationModel.getTopics().get(i);
            if(topic.getSessions().isEmpty())
            {
                aplicationModel.getTopics().remove(topic);
            }
        }
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
        
        RoomModel room=destinationDay.getRoomBySessionId(destinationSessionId);
        LocalTimeRangeModel time=destinationDay.getTimeRangeBySessionId(destinationSessionId);
        
        destinationDay.addNewTimeBreak();
        sourceDay.removeSession(sourceSessionId);
        destinationDay.shiftDown(time.getId(), room.getId());
        destinationDay.addSession(session, time, room);
        
        sourceDay.cleanUpUselessBreaks();
        destinationDay.cleanUpUselessBreaks();
        
        sourceDay.deleteEmptyRoom();
        deleteEmptyTopics();
        
        sourceDay.calculateTimesAccordingToLecturesDuration(aplicationModel.getShortLectureDuration(),aplicationModel.getLongLectureDuration());
        destinationDay.calculateTimesAccordingToLecturesDuration(aplicationModel.getShortLectureDuration(),aplicationModel.getLongLectureDuration());
        
        deleteEmptyDays();
        
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
        
        RoomModel room=destinationDay.getRoomBySessionId(destinationSessionId);
        LocalTimeRangeModel time=destinationDay.getTimeRangeBySessionId(destinationSessionId);
        
        destinationDay.addNewTimeBreak();  
        LocalTimeRangeModel nextTime=destinationDay.getNextTimeByCurrentTimeAndRoom(time.getId());        
        sourceDay.removeSession(sourceSessionId);
        
        destinationDay.shiftDown(nextTime.getId(), room.getId());
        destinationDay.addSession(session, nextTime, room);
        
        sourceDay.cleanUpUselessBreaks();
        destinationDay.cleanUpUselessBreaks();
        
        sourceDay.deleteEmptyRoom();
        deleteEmptyTopics();
        
        sourceDay.calculateTimesAccordingToLecturesDuration(aplicationModel.getShortLectureDuration(),aplicationModel.getLongLectureDuration());
        destinationDay.calculateTimesAccordingToLecturesDuration(aplicationModel.getShortLectureDuration(),aplicationModel.getLongLectureDuration());
        deleteEmptyDays();
        
    }
    
    public boolean checkIfLectureExistInSession(int sessionId,int lectureId)
    {               
        for(TopicModel topic :aplicationModel.getTopics())
        {
            for(SessionModel session:topic.getSessions())
            {
                if(session.getId()==sessionId)
                {
                   for (LectureModel lecture:session.getLectures())
                   {
                       if(lecture.getId()==lectureId)
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
            
            LectureModel lecture=getLectureById(lectureId);
        
            removeLectureFromSession(sourceSession, lecture);
            addLectureToSession(destinationSession, lecture);
            
            DayModel sourceDay=getDayBySessionId(sourceSession.getId());
            DayModel destinationDay=getDayBySessionId(destinationSession.getId());
            
            destinationDay.calculateTimesAccordingToLecturesDuration(aplicationModel.getShortLectureDuration(),aplicationModel.getLongLectureDuration());
            
            if(sourceDay!=destinationDay)
            {
                sourceDay.calculateTimesAccordingToLecturesDuration(aplicationModel.getShortLectureDuration(),aplicationModel.getLongLectureDuration());
            }
        };
    } 

}
