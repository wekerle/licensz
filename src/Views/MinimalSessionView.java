/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.Enums;
import Helpers.StringHelper;
import Listener.TextChangeEventListener;
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
public class MinimalSessionView extends VBox implements TextChangeEventListener
{
    private VBox contentNode=new VBox();
    private TextEditor titleView=new TextEditor();
    private int sessionId=0;
    private TextChangeEventListener textChangeEvent;
    
    public void setTextChangeEventListener(TextChangeEventListener textChangeEvent) 
    {
        this.textChangeEvent = textChangeEvent;
    }
    
    public MinimalSessionView(String title,int id)
    {
        this.sessionId=id;
        titleView.setText(title);  
        this.getChildren().add(titleView);
        this.getChildren().add(contentNode);
        this.setPadding(new Insets(10.0));
                
        titleView.setFont(StringHelper.font18);
          
        this.setOnDragDetected(new EventHandler<MouseEvent>()
        {
             public void handle(MouseEvent event)
             {
                
                 Dragboard db =MinimalSessionView.this.startDragAndDrop(TransferMode.MOVE);                
                 ClipboardContent cc =new ClipboardContent();    
                                  
                 cc.putString(Integer.toString(sessionId));
                 db.setContent(cc);
                 
             }
         });                      
    }
    
    public VBox getContainerNode() 
    {
        return this;
    }
    
    public int getSessionId()
    {
        return this.sessionId;
    }

    @Override
    public void modifyText(Enums.TextType type, Enums.TextCategory category, int id, String newValue) {
        textChangeEvent.modifyText(Enums.TextType.TITLE, Enums.TextCategory.SESSION, sessionId, newValue);
    }  
}
