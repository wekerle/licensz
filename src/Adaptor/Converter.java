/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adaptor;

import Models.LectureModel;
import Models.TopicModel;
import Models.SessionModel;
import java.util.ArrayList;
import Views.LectureView;
import Views.MinimalSessionView;
import Views.TopicView;
import Views.SessionView;
import Listener.LectureDragEventListener;
import Listener.SessionDragEventListener;
import Listener.TextChangeEventListener;
import Models.DayModel;
import Views.TableView;

/**
 *
 * @author Ronaldo
 */
public class Converter 
{
    
    public LectureView lectureToLectureView(LectureModel model)
    {
       return new LectureView(model);
    }
    
    public SessionView sessionToSessionView(SessionModel session,LectureDragEventListener lectureDragEvent)
    {
        SessionView sessionView=new SessionView(session);
        sessionView.setLectureDragEventListener(lectureDragEvent);
        
        for(LectureModel lecture : session.getLectures())
        {
            sessionView.addLectureView(lectureToLectureView(lecture));           
        }
       return sessionView;
    }
    
    public MinimalSessionView sessionToMinimalSessionView(SessionModel session)
    {
        MinimalSessionView minimalSessionView=new MinimalSessionView(session);
        return minimalSessionView;
    }
    
    public TopicView topicToTopicView(TopicModel topic,LectureDragEventListener lectureDragEvent)
    {
        TopicView topicView=new TopicView(topic);
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
    
    public TableView dayModelToTableView(DayModel day,SessionDragEventListener sessionDragEvent)
    {
       TableView tableView=new TableView(day);
       tableView.setSessionDragEventListener(sessionDragEvent);
       
       return tableView;
    }

    public ArrayList<TableView> dayModelListToTableViewList(ArrayList<DayModel> days, SessionDragEventListener sessionDragEvent) 
    {
        ArrayList<TableView> tableViews=new ArrayList<TableView>();
        
        for(DayModel day : days)
        {
            tableViews.add(dayModelToTableView(day,sessionDragEvent));
        }
        
        return tableViews;
    }
    
}
