/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Helpers.StringHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import Listener.LectureDragEventListener;
import Listener.TextChangeEventListener;
import Models.TopicModel;

/**
 *
 * @author Ronaldo
 */
public class TopicView implements LectureDragEventListener 
{
    private VBox contentNode=new VBox();
    private TextEditor titleView=new TextEditor();
    private VBox containerNode=new VBox();
    private LectureDragEventListener lectureDragEvent;
    private TopicModel topicModel;

    public void setLectureDragEvent(LectureDragEventListener lectureDragEvent) 
    {
        this.lectureDragEvent = lectureDragEvent;
    }
    
    public TopicView(TopicModel topic)
    {
        this.topicModel=topic;
        titleView.setText(topic.getTitle());
        
        titleView.setTextChangeEventListener(new TextChangeEventListener() 
        {
             @Override
             public void modifyText(String newValue) 
             {
                 topicModel.setTitle(newValue);
             }
         });
        
        containerNode.getChildren().add(titleView);
        containerNode.getChildren().add(contentNode);
        
        titleView.setFont(StringHelper.font32Bold);
        titleView.setAlignment(Pos.CENTER);
        containerNode.setPadding(new Insets(16)); 
        
    }
    
    public VBox getContainerNode()
    {
        return containerNode;
    }
    public void addSessionView(SessionView sessionView)
    {
        this.contentNode.getChildren().add(sessionView.getContainerNode());
    }

    @Override
    public void notify(int sessionid, int lectureId) 
    {
        if(lectureDragEvent!=null)
        {
            lectureDragEvent.notify(sessionid, lectureId);
        }
    }
}
