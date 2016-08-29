/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.StringHelper;
import Listener.TextChangeEventListener;
import Models.SessionModel;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
/**
 *
 * @author Ronaldo
 */
public class MinimalSessionView extends VBox
{
    private VBox contentNode=new VBox();
    private TextEditor titleView=new TextEditor();
    private int sessionId=0;
    private SessionModel sessionModel;
        
    public MinimalSessionView(SessionModel session)
    {
        sessionModel=session;
        this.sessionId=sessionModel.getId();
        titleView.setText(sessionModel.getTitle());  
        this.getChildren().add(titleView);
        this.getChildren().add(contentNode);
        this.setPadding(new Insets(10.0));
                
        titleView.setFont(StringHelper.font18);
        titleView.setTextChangeEventListener(new TextChangeEventListener() 
        {
            @Override
            public void modifyText(String newValue) 
            {
                sessionModel.setTitle(newValue);
            }
        });
          
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
    
    public SessionModel getSessionModel()
    {
        return sessionModel;
    }
}
