/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import Views.LectureView;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ronaldo
 */
public final class GlobalVaribles {
    private static int lectureNumber=1;
    private static int sessionNumber=1;
    private static int topicNumber=1;
   
    private static HashMap<Integer,LectureView> dragLectureAndNumberMap=new HashMap<Integer,LectureView>();
    
    private static  ArrayList<LectureView> selectedDragLectures=new ArrayList<LectureView>();
            
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
        
    public static void addElementToDragLectureAndNumberMap(int lectureNumber, LectureView lectureView) {
        dragLectureAndNumberMap.put(lectureNumber, lectureView);
    }
            
    public static LectureView getDragLectureByNumber(int lectureNumber) {
        return dragLectureAndNumberMap.get(lectureNumber);
    }
            
    public static void removeAllSelected() {
        for(LectureView dl:selectedDragLectures)
        {
            dl.getNode().setStyle("-fx-background-color:inherit");
        }
        selectedDragLectures.removeAll(selectedDragLectures);
        
    }
    
    public static void addSelected(LectureView dragLecture) {
        dragLecture.getNode().setStyle("-fx-background-color:#d6c9c9");
        selectedDragLectures.add(dragLecture);
    }
    
    public static boolean isSelected(LectureView dragLecture) {
      return  selectedDragLectures.contains(dragLecture);
    }
    
    public static ArrayList<LectureView> getAllSelected() {
      return  selectedDragLectures;
    }   
       
}
