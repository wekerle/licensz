/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.Enums;
import Helpers.StringHelper;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import licenta.GlobalVaribles;
import Listener.LectureDragEventListener;
import Listener.TextChangeEventListener;
import Models.SessionModel;


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
    private int sessionId;
    private LectureDragEventListener lectureDragEvent;
    private TextChangeEventListener textChangeEvent;
    private SessionModel sessionModel;

    public void setLectureDragEventListener(LectureDragEventListener lectureDragEvent) 
    {
        this.lectureDragEvent = lectureDragEvent;
    }
            
    public SessionView(SessionModel session)
    {
        this.sessionModel=session;
        this.sessionId=sessionModel.getId();
        titleView.setText(sessionModel.getTitle());
        chairView.setText(StringHelper.createListSeparateComma(sessionModel.getChairs()));
        
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
                ArrayList<String> chairs=StringHelper.createArralyListFromListSeparateComma(newValue);
                sessionModel.setChairs(chairs);
            }
         });
        
        containerNode.getChildren().add(titleView);
        containerNode.getChildren().add(chairView);
        
        containerNode.getChildren().add(contentNode);
        
        titleView.setFont(StringHelper.font18Bold);
        chairView.setFont(StringHelper.font18);
        
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
                        
                        LectureView dl= GlobalVaribles.getDragLectureByNumber(Integer.parseInt(db.getString()));
                        addLectureView(dl);
                        
                        for(LectureView dragLec: GlobalVaribles.getAllSelected())
                        {
                            lectureDragEvent.notify(sessionId, dragLec.getLectureNumber());
                            addLectureView(dragLec);
                        }
                    }                                                          
                    GlobalVaribles.removeAllSelected();
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
        this.contentNode.getChildren().add(lectureView.getNode());
    }             

    public int getId() 
    {
        return this.sessionId;
    }
}
