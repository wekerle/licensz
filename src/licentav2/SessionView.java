/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


/**
 *
 * @author Ronaldo
 */
public class SessionView {
    private VBox contentNode=new VBox();
    //private Text titleView=new Text();
    private TextEditor titleView=new TextEditor();
    private VBox containerNode=new VBox();
    
    public SessionView(String title)
    {
        titleView.setText(title);
        containerNode.getChildren().add(titleView);
        containerNode.getChildren().add(contentNode);
        
        titleView.setFont(new Font(18));
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
        // ====== ez akkor tortenik amikor kihuzza az elemtet a sessionview-on kivulre,
        //==== en aszem ez nem kell nekem a proiectbe
        /*contentNode.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                contentNode.setStyle("-fx-background-color:blue");

                event.consume();
            }
        });*/
        
        contentNode.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {

                    DragLecture dl= GlobalVaribles.getElementByNumber(Integer.parseInt(db.getString()));
                    addDragLecture(dl);
                    GlobalVaribles.getAllSelected();
                    
                    for(DragLecture dragLec: GlobalVaribles.getAllSelected())
                    {
                        addDragLecture(dragLec);
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
        
        contentNode.setOnScroll(new EventHandler() {
            @Override
            public void handle(Event event) {
                System.out.println("scroll dwon");
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
    }

    
    public VBox getContainerNode() {
        return containerNode;
    }
     public void addDragLecture(DragLecture dl) {
        this.contentNode.getChildren().add(dl.getNode());
    }
     
}
