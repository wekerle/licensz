/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author Ronaldo
 */
public class MinimalSessionView extends VBox {
    private VBox contentNode=new VBox();
    private TextEditor titleView=new TextEditor();
    private int sessionId=0;
    
    public MinimalSessionView(String title,int id)
    {
        this.sessionId=id;
        titleView.setText(title);  
        this.getChildren().add(titleView);
        this.getChildren().add(contentNode);
        this.setPadding(new Insets(10.0));
                
        titleView.setFont(Font.font("TimesNewRoman",18));

        /*titleView.setTextChangeObserver(new TextChangeObserver() {
            @Override
            public void notifyTextChange() {
                model.setTitle(titleView.getText());
            }
        });*/        
        
        this.setOnDragDetected(new EventHandler<MouseEvent>(){
             public void handle(MouseEvent event)
             {
                
                 Dragboard db =MinimalSessionView.this.startDragAndDrop(TransferMode.MOVE);                
                 ClipboardContent cc =new ClipboardContent();    
                                  
                 cc.putString(Integer.toString(sessionId));
                 db.setContent(cc);

             }
         });                      
    }
    
    public VBox getContainerNode() {
        return this;
    }
    
    public int getSessionId(){
        return this.sessionId;
    }
    
}
