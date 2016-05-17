/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.StringHelper;
import Models.SessionModel;
import java.util.ArrayList;
import java.util.StringJoiner;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import licentav2.GlobalVaribles;
import licentav2.TextChangeObserver;


/**
 *
 * @author Ronaldo
 */
public class SessionView {
    private VBox contentNode=new VBox();
    private TextEditor titleView=new TextEditor();
    private VBox containerNode=new VBox();
    private TextEditor chairView=new TextEditor();
    private int id;
    private StringHelper stringHelper=new StringHelper();
    private SessionModel model;
    
    public SessionView(SessionModel model)
    {
        this.model=model;
        titleView.setText(model.getTitle());
        chairView.setText(stringHelper.createListSeparateComma(model.getChairs()));
        this.id=model.getId();
        
        containerNode.getChildren().add(titleView);
        containerNode.getChildren().add(chairView);
        
        containerNode.getChildren().add(contentNode);
        
        titleView.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,18));
        chairView.setFont(new Font(18));
        
        containerNode.setPadding(new Insets(10));
        
        titleView.setTextChangeObserver(new TextChangeObserver() {
            @Override
            public void notifyTextChange() {
                model.setTitle(titleView.getText());
            }
        });
        
        chairView.setTextChangeObserver(new TextChangeObserver(){
             
             @Override
             public void notifyTextChange() {
                 String text=SessionView.this.chairView.getText();
                 if(text.contains(";")){
                     SessionView.this.chairView.setText(stringHelper.createListSeparateComma(SessionView.this.model.getChairs()));
                     Alert alert=new Alert(Alert.AlertType.ERROR, "The authors name's must be separate with comma");
                     alert.showAndWait();
                 } else {
                    SessionView.this.model.setChairs(stringHelper.createArralyListFromListSeparateComma(SessionView.this.chairView.getText()));
                 }              
             }
         });
        
        contentNode.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {               
                if (event.getGestureSource() != contentNode &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                    
                    if(!SessionView.this.containerNode.getStyleClass().contains("backGroundWhite"))
                    {
                        SessionView.this.containerNode.getStyleClass().add("backGroundWhite");
                    }
                }
            }
        });
        
        contentNode.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                 if (event.getGestureSource() != this &&
                         event.getDragboard().hasString()) {

                         SessionView.this.containerNode.getStyleClass().remove("backGroundWhite");

                 }

                 event.consume();
            }
        });
        
        contentNode.setOnDragDetected(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("ASDGASDG");

            }
        });
        contentNode.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    
                    boolean exist=false;
                    LectureView dl= GlobalVaribles.getDragLectureByNumber(Integer.parseInt(db.getString()));
                    for(LectureView dragLec: GlobalVaribles.getAllSelected())
                    {
                        // comparam  numerele
                        if(dragLec.isEqual(dl)){
                            exist = true;
                        }
                    }
                    if(!exist)
                    {
                        addLectureView(dl);
                    }
                                        
                    for(LectureView dragLec: GlobalVaribles.getAllSelected())
                    {
                        addLectureView(dragLec);
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
    
    public VBox getContainerNode() {
        return containerNode;
    }
     public void addLectureView(LectureView dl) {
        this.contentNode.getChildren().add(dl.getNode());
    }             

    public int getId() {
        return id;
    }
}
