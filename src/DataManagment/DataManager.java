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

/**
 *
 * @author tibor.wekerle
 */
public class DataManager {
    
    // <editor-fold desc="private region" defaultstate="collapsed">
    
    private TopicModel getTopicIdBySessionId(AplicationModel aplicationModel,int sessionId)
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
    
    private int getSessionPositionIdBySessionId(AplicationModel aplicationModel,int sessionId)
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
    
    public AplicationModel addSessionToTopic(AplicationModel aplicationModel, TopicModel topicModel,SessionModel session, int position)
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
        
        return aplicationModel;
    }
    
    public AplicationModel removeSessionFromTopic(AplicationModel aplicationModel, TopicModel topicModel, SessionModel session)
    {     
        for(TopicModel topic :aplicationModel.getTopics())
        {
            if(topic.getId()==topicModel.getId())
            {
                topic.getSessions().remove(session);
            }
        }
        
        return aplicationModel;
    }
    
    public AplicationModel moveSession2BeforeSession1(AplicationModel am,  SessionModel s1,SessionModel s2)
    {               
        TopicModel t1=getTopicIdBySessionId(am, s1.getId());
        TopicModel t2=getTopicIdBySessionId(am, s2.getId());
        int session1Position=getSessionPositionIdBySessionId(am, s1.getId());
        
        am=removeSessionFromTopic(am, t2, s2);
        am=addSessionToTopic(am, t1, s2, session1Position);
        
        return am;
    }
    
    public AplicationModel moveSession2AfterSession1(AplicationModel am,  SessionModel s1,SessionModel s2)
    {               
        TopicModel t1=getTopicIdBySessionId(am, s1.getId());
        TopicModel t2=getTopicIdBySessionId(am, s2.getId());
        int session1Position=getSessionPositionIdBySessionId(am, s1.getId());
        
        am=removeSessionFromTopic(am, t2, s2);
        am=addSessionToTopic(am, t1, s2, session1Position+1);
        
        return am;
    }
    
    public boolean checkIfLectureExistInSession(AplicationModel am,  int sessionId,int lectureId)
    {               
        for(TopicModel topic :am.getTopics())
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
    
    public SessionModel getSessionBySessionId(AplicationModel am,  int sessionId)
    {               
        for(TopicModel topic :am.getTopics())
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
    
    public void moveLectureToSession(AplicationModel am,int sessionId,int lectureId)
    {               
        int x=0;
    }
    
}
