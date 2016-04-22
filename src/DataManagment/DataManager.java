/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagment;

import Models.AplicationModel;
import Models.Session;
import Models.Topic;
import licentav2.GlobalVaribles;

/**
 *
 * @author tibor.wekerle
 */
public class DataManager {
    
    // <editor-fold desc="private region" defaultstate="collapsed">
    
    private Topic GetTopicIdBySessionId(AplicationModel am,int sessionId)
    {
        for(Topic t :am.getTopics())
        {
            for(Session s:t.getSessions())
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
    
    public AplicationModel addSessionToTopic(AplicationModel am, Topic topic,Session s, int position)
    {               
        for(Topic t :am.getTopics())
        {
            if(t.getTitle()==topic.getTitle())
            {
                t.getSessions().add(position, s);
            }
        }
        
        return am;
    }
    
    public AplicationModel removeSessionFromTopic(AplicationModel am, Topic topic, Session session)
    {     
        for(Topic t :am.getTopics())
        {
            if(t.getTitle()==topic.getTitle())
            {
                t.getSessions().remove(session);
            }
        }
        
        return am;
    }
    
    public AplicationModel moveSession(AplicationModel am,  Session s1,Session s2)
    {               
        Topic t1=GetTopicIdBySessionId(am, s1.getId());
        Topic t2=GetTopicIdBySessionId(am, s2.getId());
        
        am=addSessionToTopic(am, t1, s2, 0);
        am=removeSessionFromTopic(am, t2, s2);
        
        return am;
    }
    
}
