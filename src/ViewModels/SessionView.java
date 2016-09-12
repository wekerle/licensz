/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Helpers.StringHelper;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import Listener.LectureDragEventListener;
import Listener.TextChangeEventListener;
import Models.SessionModel;
import Views.SummaryView;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;


/**
 *
 * @author Ronaldo
 */
public class SessionView 
{
    private VBox contentNode=new VBox();
    private TextEditor titleView=new TextEditor();
    private VBox containerNode=new VBox();
    private TextEditor chairView=new TextEditor();
    private TextEditor coChairView=new TextEditor();
    private int sessionId;
    private LectureDragEventListener lectureDragEvent;
    private SessionModel sessionModel;

        // <editor-fold desc="static region" defaultstate="collapsed">
    protected static HashMap<Integer,LectureView> dragLectureAndNumberMap=new HashMap<Integer,LectureView>();
    protected static  ArrayList<LectureView> selectedDragLectures=new ArrayList<LectureView>();
    
    public static LectureView getDragLectureByNumber(int lectureNumber) 
    {
        return dragLectureAndNumberMap.get(lectureNumber);
    }
    
    public static void removeAllSelected() 
    {
        for(LectureView dl:selectedDragLectures)
        {
            dl.getNode().setStyle("-fx-background-color:inherit");
        }
        selectedDragLectures.removeAll(selectedDragLectures);
        
    }
    
    public static void addSelected(LectureView dragLecture) 
    {
        dragLecture.getNode().setStyle("-fx-background-color:#d6c9c9");
        selectedDragLectures.add(dragLecture);
    }
    
    public static boolean isSelected(LectureView dragLecture) 
    {
      return  selectedDragLectures.contains(dragLecture);
    }
         //</editor-fold>
    
    public void setLectureDragEventListener(LectureDragEventListener lectureDragEvent) 
    {
        this.lectureDragEvent = lectureDragEvent;
    }
            
    public SessionView(SessionModel session)
    {
        this.sessionModel=session;
        this.sessionId=sessionModel.getId();
        titleView.setText(sessionModel.getTitle());
        chairView.setText(sessionModel.getChair());
        coChairView.setText(sessionModel.getCoChair());
        
        titleView.setTextChangeEventListener(new TextChangeEventListener() 
        {
            @Override
            public void modifyText(String newValue) 
            {
                sessionModel.setTitle(newValue);
            }
         });
        
        chairView.setTextChangeEventListener(new TextChangeEventListener() 
        {
            @Override
            public void modifyText(String newValue) 
            {
                sessionModel.setChair(newValue);
            }
         });
        
        coChairView.setTextChangeEventListener(new TextChangeEventListener() 
        {
            @Override
            public void modifyText(String newValue) 
            {
                sessionModel.setCoChair(newValue);
            }
         });
        
        containerNode.getChildren().add(titleView);
        containerNode.getChildren().add(chairView);
        containerNode.getChildren().add(coChairView);
        
        containerNode.getChildren().add(contentNode);
        
        titleView.setFont(StringHelper.font18Bold);
        chairView.setFont(StringHelper.font18);
        coChairView.setFont(StringHelper.font18);
        
        containerNode.setPadding(new Insets(10));
                
        contentNode.setOnDragOver(new EventHandler<DragEvent>() 
        {
            public void handle(DragEvent event) 
            {               
                if (event.getGestureSource() != contentNode && event.getDragboard().hasString()) 
                {
                    event.acceptTransferModes(TransferMode.MOVE);
                    
                    if(!SessionView.this.containerNode.getStyleClass().contains("backGroundWhite"))
                    {
                        SessionView.this.containerNode.getStyleClass().add("backGroundWhite");
                    }
                }
            }
        });
        
        contentNode.setOnDragExited(new EventHandler<DragEvent>() 
        {
            public void handle(DragEvent event) {
                 if (event.getGestureSource() != this && event.getDragboard().hasString()) 
                 {
                    SessionView.this.containerNode.getStyleClass().remove("backGroundWhite");
                 }

                 event.consume();
            }
        });
        
        contentNode.setOnDragDropped(new EventHandler<DragEvent>() 
        {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false; 
                if (db.hasString()) 
                {                                   
                    int lectureId=Integer.parseInt(db.getString());
                    int sessionId=SessionView.this.sessionId;
                    
                    if(SessionView.this.lectureDragEvent!=null){
                        lectureDragEvent.notify(sessionId, lectureId);
                        
                        LectureView dl= SessionView.getDragLectureByNumber(Integer.parseInt(db.getString()));
                        addLectureView(dl);
                        
                        for(LectureView dragLec: SessionView.selectedDragLectures)
                        {
                            lectureDragEvent.notify(sessionId, dragLec.getLectureNumber());
                            addLectureView(dragLec);
                        }
                    }                                                          
                    SessionView.removeAllSelected();
                   success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);

              //  event.consume();
             }
        });        
    }
    
    public VBox getContainerNode() 
    {
        return containerNode;
    }
    
    public void addLectureView(LectureView lectureView) 
    {
        if(!this.contentNode.getChildren().contains(lectureView.getNode()))
        {
            this.contentNode.getChildren().add(lectureView.getNode());
        }
    }             

    public int getId() 
    {
        return this.sessionId;
    }
}
