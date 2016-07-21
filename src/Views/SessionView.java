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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import licenta.GlobalVaribles;
import Listener.LectureDragEventListener;
import Listener.TextChangeEventListener;


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

    public void setLectureDragEvent(LectureDragEventListener lectureDragEvent) 
    {
        this.lectureDragEvent = lectureDragEvent;
    }
    
    public void setTextChangeEvent(TextChangeEventListener textChangeEvent) 
    {
        this.textChangeEvent = textChangeEvent;
    }
        
    public SessionView(String title,ArrayList<String> chairs,int id)
    {
        this.sessionId=id;
        titleView.setText(title);
        chairView.setText(StringHelper.createListSeparateComma(chairs));
        
        titleView.setTextChangeEventListener(new TextChangeEventListener() 
        {

             @Override
             public void modifyText(Enums.TextType type, Enums.TextCategory category, int id, String newValue) 
             {
                 if(type==Enums.TextType.NOTHING)
                 {
                     type=Enums.TextType.TITLE;
                 }
                 
                 if(category==Enums.TextCategory.NOTHING)
                 {
                     category=Enums.TextCategory.SESSION;
                 }
                 
                 if(id==0)
                 {
                     id=SessionView.this.sessionId;
                 }
                 SessionView.this.textChangeEvent.modifyText(type, category, id, newValue);
             }
         });
        
        chairView.setTextChangeEventListener(new TextChangeEventListener() 
        {
             @Override
             public void modifyText(Enums.TextType type, Enums.TextCategory category, int id, String newValue) 
             {
                 if(type==Enums.TextType.NOTHING)
                 {
                     type=Enums.TextType.CHAIRS;
                 }
                 
                 if(category==Enums.TextCategory.NOTHING)
                 {
                     category=Enums.TextCategory.SESSION;
                 }
                 
                 if(id==0)
                 {
                     id=SessionView.this.sessionId;
                 }
                 SessionView.this.textChangeEvent.modifyText(type, category, id, newValue);
             }
         });
        
        containerNode.getChildren().add(titleView);
        containerNode.getChildren().add(chairView);
        
        containerNode.getChildren().add(contentNode);
        
        titleView.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,18));
        chairView.setFont(new Font(18));
        
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
        lectureView.setTextChange(textChangeEvent);
        this.contentNode.getChildren().add(lectureView.getNode());
    }             

    public int getId() 
    {
        return this.sessionId;
    }
}
