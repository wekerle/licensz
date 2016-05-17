/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.util.ArrayList;
import java.util.StringJoiner;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import licentav2.GlobalVaribles;


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
    
    private String createListSeparateComma(ArrayList<String> chairs)
    {
        //ez csak java 8 -al megy
        StringJoiner stringJoiner =new StringJoiner(", ");
        
        for(String chair : chairs)
        {
            stringJoiner.add(chair);
        }
        return stringJoiner.toString();
    }
    
    public SessionView(String title,ArrayList<String> chairs,int id)
    {
        titleView.setText(title);
        chairView.setText(createListSeparateComma(chairs));
        this.id=id;
        
        containerNode.getChildren().add(titleView);
        containerNode.getChildren().add(chairView);
        
        containerNode.getChildren().add(contentNode);
        
        titleView.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,18));
        chairView.setFont(new Font(18));
        
        containerNode.setPadding(new Insets(10));
        contentNode.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {               
                if (event.getGestureSource() != contentNode &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }

              //  event.consume();
            }
        });
        contentNode.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            /* the drag-and-drop gesture entered the target */
            /* show to the user that it is an actual gesture target */
                 if (event.getGestureSource() != contentNode &&
                         event.getDragboard().hasString()) {
                    // contentNode.setStyle("-fx-background-color:white");
                 }

                // event.consume();
            }
        });
        contentNode.setOnDragDetected(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("ASDGASDG");
                
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
