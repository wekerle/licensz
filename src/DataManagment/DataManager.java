/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagment;

import Models.AplicationModel;
import Models.LectureWithDetailsModel;
import Models.SessionModel;
import Models.TopicModel;
import java.util.ArrayList;

/**
 *
 * @author tibor.wekerle
 */
public class DataManager {
    
    private AplicationModel aplicationModel=null;
    
    // <editor-fold desc="private region" defaultstate="collapsed">
    
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
    
    public void addSessionToTopic(TopicModel topicModel,SessionModel session, int position)
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
    
    public void removeSessionFromTopic(TopicModel topicModel, SessionModel session)
    {     
        for(TopicModel topic :aplicationModel.getTopics())
        {
            if(topic.getId()==topicModel.getId())
            {
                topic.getSessions().remove(session);
            }
        }
    }
    
    public void moveSession2BeforeSession1(SessionModel s1,SessionModel s2)
    {               
        TopicModel t1=getTopicIdBySessionId(s1.getId());
        TopicModel t2=getTopicIdBySessionId(s2.getId());
        int session1Position=getSessionPositionIdBySessionId(s1.getId());
        
        removeSessionFromTopic(t2, s2);
        addSessionToTopic(t1, s2, session1Position);
        
    }
    
    public void moveSession2AfterSession1(SessionModel s1,SessionModel s2)
    {               
        TopicModel t1=getTopicIdBySessionId(s1.getId());
        TopicModel t2=getTopicIdBySessionId(s2.getId());
        int session1Position=getSessionPositionIdBySessionId(s1.getId());
        
        removeSessionFromTopic(t2, s2);
        addSessionToTopic(t1, s2, session1Position+1);
        
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
        if(!checkIfLectureExistInSession(sessionId,lectureId)){
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
}
