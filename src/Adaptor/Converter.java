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
import Listener.LectureDragEventListener;
import Listener.TextChangeEventListener;
import Models.ConstraintModel;
import Models.DayModel;
import Views.ConstraintView;
import Views.TableView;

/**
 *
 * @author Ronaldo
 */
public class Converter 
{
    
    public LectureView lectureToLectureView(LectureWithDetailsModel model)
    {
       return new LectureView(model.getTitle(),model.getAuthors(),model.getId());
    }
    
    public SessionView sessionToSessionView(SessionModel session,LectureDragEventListener lectureDragEvent,TextChangeEventListener textChangeEvent)
    {
        SessionView sessionView=new SessionView(session.getTitle(),session.getChairs(),session.getId());
        sessionView.setLectureDragEvent(lectureDragEvent);
        
        sessionView.setTextChangeEvent(textChangeEvent);
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
    
    public TopicView topicToTopicView(TopicModel topic,LectureDragEventListener lectureDragEvent, TextChangeEventListener textChangeEvent)
    {
        TopicView topicView=new TopicView(topic.getTitle(),topic.getId());
        topicView.setLectureDragEvent(lectureDragEvent);
        topicView.setTextChangeEvent(textChangeEvent);
        
        for(SessionModel session : topic.getSessions())
        {
            topicView.addSessionView(sessionToSessionView(session,topicView,textChangeEvent));
        }
       return topicView;
    }
    
    public ArrayList<TopicView> topicListToTopicViewList(ArrayList<TopicModel> topics,LectureDragEventListener lectureDragEvent,TextChangeEventListener textChangeEvent)
    {
        ArrayList<TopicView> topicViewList=new ArrayList<TopicView>();
        
        for(TopicModel topic : topics)
        {
            topicViewList.add(topicToTopicView(topic,lectureDragEvent,textChangeEvent));
        }
       return topicViewList;
    }
    
    public TableView dayModelToTableView(DayModel day)
    {
       TableView tableView=new TableView();
       tableView.setTableId(day.getId());
       tableView.populateContent(day);
       
       return tableView;
    }

    public ArrayList<TableView> dayModelListToTableViewList(ArrayList<DayModel> days, TextChangeEventListener textChangeEvent) 
    {
        ArrayList<TableView> tableViews= new ArrayList<TableView>();
        
        for(DayModel day : days)
        {
            tableViews.add(dayModelToTableView(day));
        }
        
        return tableViews;
    }
    
    public ArrayList<ConstraintView> constraintModelListToConstraintViewList(ArrayList<ConstraintModel> constraints) 
    {
        ArrayList<ConstraintView> tableViews= new ArrayList<ConstraintView>();
        
        for(ConstraintModel constarint : constraints)
        {
            tableViews.add(constarintModelToConstarintView(constarint));
        }
        
        return tableViews;
    }

    public ConstraintView constarintModelToConstarintView(ConstraintModel constarint) 
    {
        ConstraintView constraintView=new ConstraintView();
        
        return constraintView;
    }
}
