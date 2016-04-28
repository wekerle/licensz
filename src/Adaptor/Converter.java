/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adaptor;

import Models.LectureModel;
import Models.LectureWithDetailsModel;
import Models.TopicModel;
import Models.SessionModel;
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
    
    public DragLecture lectureToDragLecture(LectureWithDetailsModel l)
    {
       return new DragLecture(l.getTitle(),l.getAuthors());
    }
    
    public SessionView sessionToSessionView(SessionModel s)
    {
        SessionView sw=new SessionView(s.getTitle());
        for(LectureWithDetailsModel l : s.getLectures())
        {
            sw.addDragLecture(lectureToDragLecture(l));
        }
       return sw;
    }
    
    public MinimalSessionView sessionToMinimalSessionView(SessionModel s)
    {
        MinimalSessionView msw=new MinimalSessionView(s.getTitle(),s.getId());
        return msw;
    }
    
    public TopicView topicToTopicView(TopicModel p)
    {
        TopicView pw=new TopicView(p.getTitle());
        for(SessionModel s : p.getSessions())
        {
            pw.addSessionView(sessionToSessionView(s));
        }
       return pw;
    }
    
    public ArrayList<SessionView> sessionListToSessionViewList(ArrayList<SessionModel> sessions)
    {
        ArrayList<SessionView> swl=new ArrayList<SessionView>();
        
        for(SessionModel s : sessions)
        {
            swl.add(sessionToSessionView(s));
        }
       return swl;
    }
    
    public ArrayList<TopicView> topicListToTopicViewList(ArrayList<TopicModel> topics)
    {
        ArrayList<TopicView> pwl=new ArrayList<TopicView>();
        
        for(TopicModel p : topics)
        {
            pwl.add(topicToTopicView(p));
        }
       return pwl;
    }
}
