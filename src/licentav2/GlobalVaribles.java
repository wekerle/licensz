/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import Models.SessionModel;
import Models.TopicModel;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import javafx.animation.Timeline;

/**
 *
 * @author Ronaldo
 */
public final class GlobalVaribles {
    private static int lectureNumber=0;
    private static int sessionNumber=0;
    private static int topicNumber=0;
    
    private static HashMap<Integer,SessionModel> sessionAndNumberMap=new HashMap<Integer,SessionModel>();        
    private static  HashMap<Integer,DragLecture> dragLectureAndNumberMap=new HashMap<Integer,DragLecture>();
    private static  HashMap<Integer,TopicModel> topicAndNumberMap=new HashMap<Integer,TopicModel>();
    
    private static  ArrayList<DragLecture> selectedDragLectures=new ArrayList<DragLecture>();
            
    public static int getLectureNumber() {
        return lectureNumber;
    }
    
    public static int getSessionNumber() {
        return sessionNumber;
    }
    
    public static int getTopicNumber() {
        return topicNumber;
    }

    public static void setLectureNumber(int ln) {
        lectureNumber = ln;
    }
    
    public static void setSessionNumber(int sn) {
        sessionNumber = sn;
    }
    
    public static void setTopicNumber(int tn) {
        topicNumber = tn;
    }
        
    public static void addElementToDragLectureAndNumberMap(int lectureNumber, DragLecture dl) {
        dragLectureAndNumberMap.put(lectureNumber, dl);
    }
    
    public static void addElementToSessionAndNumberMap(int sessionNumber, SessionModel s) {
        sessionAndNumberMap.put(sessionNumber, s);
    }
    
    public static void addElementToTopicAndNumberMap(int sessionNumber, TopicModel t) {
        topicAndNumberMap.put(sessionNumber, t);
    }
    
    public static DragLecture getDragLectureByNumber(int lectureNumber) {
        return dragLectureAndNumberMap.get(lectureNumber);
    }
    
    public static DragLecture getTopicByNumber(int lectureNumber) {
        return dragLectureAndNumberMap.get(lectureNumber);
    }
    
    public static SessionModel getSessionByNumber(int sessionNumber) {
        return sessionAndNumberMap.get(sessionNumber);
    }
    
    public static void removeAllSelected() {
        for(DragLecture dl:selectedDragLectures)
        {
            dl.getNode().setStyle("-fx-background-color:inherit");
        }
        selectedDragLectures.removeAll(selectedDragLectures);
        
    }
    
    public static void addSelected(DragLecture dragLecture) {
        dragLecture.getNode().setStyle("-fx-background-color:#d6c9c9");
        selectedDragLectures.add(dragLecture);
    }
    
    public static boolean isSelected(DragLecture dragLecture) {
      return  selectedDragLectures.contains(dragLecture);
    }
    
    public static ArrayList<DragLecture> getAllSelected() {
      return  selectedDragLectures;
    }   
}
