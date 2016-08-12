/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adaptor;

import Listener.DayChangeEventListener;
import Listener.HourChangeEventListener;
import Models.LectureWithDetailsModel;
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
    
    public LectureView lectureToLectureView(LectureWithDetailsModel model)
    {
       return new LectureView(model.getTitle(),model.getAuthors(),model.getId());
    }
    
    public SessionView sessionToSessionView(SessionModel session,LectureDragEventListener lectureDragEvent,TextChangeEventListener textChangeEvent)
    {
        SessionView sessionView=new SessionView(session.getTitle(),session.getChairs(),session.getId());
        sessionView.setLectureDragEventListener(lectureDragEvent);
        
        sessionView.setTextChangeEventListener(textChangeEvent);
        for(LectureWithDetailsModel lecture : session.getLectures())
        {
            sessionView.addLectureView(lectureToLectureView(lecture));           
        }
       return sessionView;
    }
    
    public MinimalSessionView sessionToMinimalSessionView(SessionModel session,TextChangeEventListener textChangeEvent)
    {
        MinimalSessionView minimalSessionView=new MinimalSessionView(session.getTitle(),session.getId(),session.isBreak(),textChangeEvent);
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
    
    public TableView dayModelToTableView(DayModel day,SessionDragEventListener sessionDragEvent,DayChangeEventListener dayChangeListener,HourChangeEventListener hourChangeListener,TextChangeEventListener textChangeListener)
    {
       TableView tableView=new TableView();
       tableView.setTableId(day.getId());
       tableView.setDay(day.getDay());
       tableView.setDayChangeEventListener(dayChangeListener);
       tableView.setTextChangeEventListener(textChangeListener);
       tableView.setSessionDragEventListener(sessionDragEvent);
       tableView.setHourChangeEventListener(hourChangeListener);
       tableView.populateContent(day);
       
       return tableView;
    }

    public ArrayList<TableView> dayModelListToTableViewList(ArrayList<DayModel> days, SessionDragEventListener sessionDragEvent,DayChangeEventListener dayChangeListener,HourChangeEventListener hourChangeListener, TextChangeEventListener textChangeListener) 
    {
        ArrayList<TableView> tableViews= new ArrayList<TableView>();
        
        for(DayModel day : days)
        {
            tableViews.add(dayModelToTableView(day,sessionDragEvent,dayChangeListener,hourChangeListener,textChangeListener));
        }
        
        return tableViews;
    }
    
}
