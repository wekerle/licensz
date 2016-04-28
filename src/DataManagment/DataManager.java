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
        
    //</editor-fold>
    
    public AplicationModel addSessionToTopic(AplicationModel am, TopicModel topic,SessionModel s, int position)
    {               
        for(TopicModel t :am.getTopics())
        {
            if(t.getTitle()==topic.getTitle())
            {
                t.getSessions().add(position, s);
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
    
    public AplicationModel moveSession(AplicationModel am,  SessionModel s1,SessionModel s2)
    {               
        TopicModel t1=GetTopicIdBySessionId(am, s1.getId());
        TopicModel t2=GetTopicIdBySessionId(am, s2.getId());
        
        am=addSessionToTopic(am, t1, s2, 0);
        am=removeSessionFromTopic(am, t2, s2);
        
        return am;
    }
    
}
