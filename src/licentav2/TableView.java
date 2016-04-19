/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import Adaptor.Converter;
import Models.AplicationModel;
import Models.Topic;
import Models.Session;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ronaldo
 */
public class TableView extends GridPane{
    private AplicationModel am=null;
    
    public TableView(AplicationModel am)
    {
        super();
        this.am=am;
        populateContent(am.getTopics());
        
        this.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {               
                if (event.getGestureSource() != this &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }

              //  event.consume();
            }
        });
        
        this.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            /* the drag-and-drop gesture entered the target */
            /* show to the user that it is an actual gesture target */
                 if (event.getGestureSource() != this &&
                         event.getDragboard().hasString()) {
                    // contentNode.setStyle("-fx-background-color:white");
                 }

                // event.consume();
            }
        });
        
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    
                    
                   success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);

              //  event.consume();
             }
        });
    }
    
    private void populateContent(ArrayList<Topic> topics)
    {
        int colNum=1;
        int maxRow=0;
        Converter c=new Converter();
        for(Topic p : topics)
        {
            int rowNum=1;
            if(maxRow<p.getSessions().size())
            {
                maxRow=p.getSessions().size();
            }
            for(Session s : p.getSessions())
            {
                MinimalSessionView sw=c.sessionToMinimalSessionView(s);
                this.add(sw.getContainerNode(),colNum,rowNum);
                rowNum++;
            }
            colNum++;
        }
        
        for(int i=0;i<topics.size();i++)
        {
            this.add(new TextEditor("Sala"+i), i+1, 0);
        }
        
         for(int i=0;i<maxRow; i++)
        {
            this.add(new TextEditor("10:00-10:00"), 0, i+1);
        }
    }
}
