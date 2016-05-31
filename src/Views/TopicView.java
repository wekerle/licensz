/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.TopicModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import licentav2.LectureDragEventListener;
import licentav2.TextChangeObserver;

/**
 *
 * @author Ronaldo
 */
public class TopicView implements LectureDragEventListener {
    private VBox contentNode=new VBox();
    private TextEditor titleView=new TextEditor();
    private VBox containerNode=new VBox();
    private int id;
    private TopicModel model;
    private LectureDragEventListener lectureDragEvent;

    public void setLectureDragEvent(LectureDragEventListener lectureDragEvent) {
        this.lectureDragEvent = lectureDragEvent;
    }
    
    public TopicView(TopicModel model)
    {
        this.model=model;
        this.id=model.getId();
        titleView.setText(model.getTitle());
        containerNode.getChildren().add(titleView);
        containerNode.getChildren().add(contentNode);
        
        titleView.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,32));
        titleView.setAlignment(Pos.CENTER);
        containerNode.setPadding(new Insets(16));
        
        titleView.setTextChangeObserver(new TextChangeObserver() {
            @Override
            public void notifyTextChange() {
                model.setTitle(titleView.getText());
            }
        });
    }
    
     public VBox getContainerNode() {
        return containerNode;
    }
     public void addSessionView(SessionView sessionView) {
        this.contentNode.getChildren().add(sessionView.getContainerNode());
    }

    @Override
    public void notify(int sessionid, int lectureId) {
        if(lectureDragEvent!=null)
        {
            lectureDragEvent.notify(sessionid, lectureId);
        }
    }
}
