/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adaptor;

import Models.LectureWithDetailsModel;
import Models.TopicModel;
import Models.SessionModel;
import java.util.ArrayList;
import Views.LectureView;
import Views.MinimalSessionView;
import Views.TopicView;
import Views.SessionView;

/**
 *
 * @author Ronaldo
 */
public class Converter {
    
    public LectureView lectureToDragLecture(LectureWithDetailsModel lectureWithDetails)
    {
       return new LectureView(lectureWithDetails.getTitle(),lectureWithDetails.getAuthors(),lectureWithDetails.getId());
    }
    
    public SessionView sessionToSessionView(SessionModel session)
    {
        SessionView sessionView=new SessionView(session.getTitle(),session.getChairs(),session.getId());
        for(LectureWithDetailsModel lecture : session.getLectures())
        {
            sessionView.addDragLecture(lectureToDragLecture(lecture));
        }
       return sessionView;
    }
    
    public MinimalSessionView sessionToMinimalSessionView(SessionModel session)
    {
        MinimalSessionView minimalSessionView=new MinimalSessionView(session.getTitle(),session.getId());
        return minimalSessionView;
    }
    
    public TopicView topicToTopicView(TopicModel topic)
    {
        TopicView topicView=new TopicView(topic.getTitle(),topic.getId());
        for(SessionModel session : topic.getSessions())
        {
            topicView.addSessionView(sessionToSessionView(session));
        }
       return topicView;
    }
    
    public ArrayList<SessionView> sessionListToSessionViewList(ArrayList<SessionModel> sessions)
    {
        ArrayList<SessionView> sessionViewList=new ArrayList<SessionView>();
        
        for(SessionModel session : sessions)
        {
            sessionViewList.add(sessionToSessionView(session));
        }
       return sessionViewList;
    }
    
    public ArrayList<TopicView> topicListToTopicViewList(ArrayList<TopicModel> topics)
    {
        ArrayList<TopicView> topicViewList=new ArrayList<TopicView>();
        
        for(TopicModel topic : topics)
        {
            topicViewList.add(topicToTopicView(topic));
        }
       return topicViewList;
    }
}
