/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagment;

import Models.AplicationModel;
import Models.SessionModel;
import Models.TopicModel;
import licentav2.GlobalVaribles;

/**
 *
 * @author tibor.wekerle
 */
public class DataManager {
    
    // <editor-fold desc="private region" defaultstate="collapsed">
    
    private TopicModel GetTopicIdBySessionId(AplicationModel am,int sessionId)
    {
        for(TopicModel t :am.getTopics())
        {
            for(SessionModel s:t.getSessions())
            {
                if(s.getId()==sessionId)
                {
                    return t;
                }
            }
        }
        return null;
    }
    
    private int GetSessionPositionIdBySessionId(AplicationModel am,int sessionId)
    {
        for(TopicModel t :am.getTopics())
        {
            for(SessionModel s:t.getSessions())
            {
                if(s.getId()==sessionId)
                {
                   return t.getSessions().indexOf(s);
                }
            }
        }
        return 0;
    }
        
    //</editor-fold>
    
    public AplicationModel addSessionToTopic(AplicationModel am, TopicModel topic,SessionModel s, int position)
    {               
        for(TopicModel t :am.getTopics())
        {
            if(t.getTitle()==topic.getTitle())
            {
                if(t.getSessions().size()<=position)
                {
                    t.getSessions().add(s);
                }else
                {
                    t.getSessions().add(position, s);
                }
            }
        }
        
        return am;
    }
    
    public AplicationModel removeSessionFromTopic(AplicationModel am, TopicModel topic, SessionModel session)
    {     
        for(TopicModel t :am.getTopics())
        {
            if(t.getTitle()==topic.getTitle())
            {
                t.getSessions().remove(session);
            }
        }
        
        return am;
    }
    
    public AplicationModel moveSession2BeforeSession1(AplicationModel am,  SessionModel s1,SessionModel s2)
    {               
        TopicModel t1=GetTopicIdBySessionId(am, s1.getId());
        TopicModel t2=GetTopicIdBySessionId(am, s2.getId());
        int session1Position=GetSessionPositionIdBySessionId(am, s1.getId());
        
        am=removeSessionFromTopic(am, t2, s2);
        am=addSessionToTopic(am, t1, s2, session1Position);
        
        return am;
    }
    
    public AplicationModel moveSession2AfterSession1(AplicationModel am,  SessionModel s1,SessionModel s2)
    {               
        TopicModel t1=GetTopicIdBySessionId(am, s1.getId());
        TopicModel t2=GetTopicIdBySessionId(am, s2.getId());
        int session1Position=GetSessionPositionIdBySessionId(am, s1.getId());
        
        am=removeSessionFromTopic(am, t2, s2);
        am=addSessionToTopic(am, t1, s2, session1Position+1);
        
        return am;
    }
    
}
