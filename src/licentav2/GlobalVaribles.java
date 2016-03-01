/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import javafx.animation.Timeline;

/**
 *
 * @author Ronaldo
 */
public final class GlobalVaribles {
    private static int lectureNumber=0;
    private static  Dictionary dragLectureList=new Hashtable();
    private static  ArrayList<DragLecture> selectedDragLectures=new ArrayList<DragLecture>();
    private static Timeline scrolltimeline = new Timeline();

    public static int getLectureNumber() {
        return lectureNumber;
    }

    public static void setLectureNumber(int aLectureNumber) {
        lectureNumber = aLectureNumber;
    }
    
    public static void addElementToDictionary(int lectureNumber, DragLecture dl) {
        dragLectureList.put(lectureNumber, dl);
    }
    
    public static DragLecture getElementByNumber(int lectureNumber) {
        return (DragLecture)dragLectureList.get(lectureNumber);
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
    
    public static Timeline getScrollTimeline() {
      return  scrolltimeline;
    }
    
    public static void stopScrollTimeline() {
      scrolltimeline.stop();
    }
    
    public static void playScrollTimeline() {
      scrolltimeline.play();
    }
    
}
