/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adaptor;

import Models.Lecture;
import Models.LectureWithDetails;
import Models.Topic;
import Models.Session;
import java.util.ArrayList;
import licentav2.DragLecture;
import licentav2.MinimalSessionView;
import licentav2.TopicView;
import licentav2.SessionView;

/**
 *
 * @author Ronaldo
 */
public class Converter {
    
    public DragLecture lectureToDragLecture(LectureWithDetails l)
    {
       return new DragLecture(l.getTitle(),l.getAuthors());
    }
    
    public SessionView sessionToSessionView(Session s)
    {
        SessionView sw=new SessionView(s.getTitle());
        for(LectureWithDetails l : s.getLectures())
        {
            sw.addDragLecture(lectureToDragLecture(l));
        }
       return sw;
    }
    
    public MinimalSessionView sessionToMinimalSessionView(Session s)
    {
        MinimalSessionView msw=new MinimalSessionView(s.getTitle());
        return msw;
    }
    
    public TopicView topicToTopicView(Topic p)
    {
        TopicView pw=new TopicView(p.getTitle());
        for(Session s : p.getSessions())
        {
            pw.addSessionView(sessionToSessionView(s));
        }
       return pw;
    }
    
    public ArrayList<SessionView> sessionListToSessionViewList(ArrayList<Session> sessions)
    {
        ArrayList<SessionView> swl=new ArrayList<SessionView>();
        
        for(Session s : sessions)
        {
            swl.add(sessionToSessionView(s));
        }
       return swl;
    }
    
    public ArrayList<TopicView> topicListToTopicViewList(ArrayList<Topic> topics)
    {
        ArrayList<TopicView> pwl=new ArrayList<TopicView>();
        
        for(Topic p : topics)
        {
            pwl.add(topicToTopicView(p));
        }
       return pwl;
    }
}
