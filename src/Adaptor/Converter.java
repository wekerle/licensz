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
import Observer.LectureDragEventListener;
import Observer.SessionTitleTextChangeListener;

/**
 *
 * @author Ronaldo
 */
public class Converter {
    
    public LectureView lectureToLectureView(LectureWithDetailsModel model)
    {
       return new LectureView(model.getTitle(),model.getAuthors(),model.getId());
    }
    
    public SessionView sessionToSessionView(SessionModel session,LectureDragEventListener lectureDragEvent)
    {
        SessionView sessionView=new SessionView(session.getTitle(),session.getChairs(),session.getId());
        sessionView.setLectureDragEvent(lectureDragEvent);

        for(LectureWithDetailsModel lecture : session.getLectures())
        {
            sessionView.addLectureView(lectureToLectureView(lecture));           
        }
       return sessionView;
    }
    
    public MinimalSessionView sessionToMinimalSessionView(SessionModel session)
    {
        MinimalSessionView minimalSessionView=new MinimalSessionView(session.getTitle(),session.getId());
        return minimalSessionView;
    }
    
    public TopicView topicToTopicView(TopicModel topic,LectureDragEventListener lectureDragEvent)
    {
        TopicView topicView=new TopicView(topic.getTitle(),topic.getId());
        topicView.setLectureDragEvent(lectureDragEvent);
        for(SessionModel session : topic.getSessions())
        {
            topicView.addSessionView(sessionToSessionView(session,topicView));
        }
       return topicView;
    }
    
    public ArrayList<TopicView> topicListToTopicViewList(ArrayList<TopicModel> topics,LectureDragEventListener lectureDragEvent)
    {
        ArrayList<TopicView> topicViewList=new ArrayList<TopicView>();
        
        for(TopicModel topic : topics)
        {
            topicViewList.add(topicToTopicView(topic,lectureDragEvent));
        }
       return topicViewList;
    }
}
