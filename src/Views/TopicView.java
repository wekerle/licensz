/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Ronaldo
 */
public class TopicView {
    private VBox contentNode=new VBox();
    private TextEditor titleView=new TextEditor();
    private VBox containerNode=new VBox();
    private int id;
    
    public TopicView(String title,int id)
    {
        this.id=id;
        titleView.setText(title);
        containerNode.getChildren().add(titleView);
        containerNode.getChildren().add(contentNode);
        
        titleView.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,32));
        titleView.setAlignment(Pos.CENTER);
        containerNode.setPadding(new Insets(16));
    }
    
     public VBox getContainerNode() {
        return containerNode;
    }
     public void addSessionView(SessionView sessionView) {
        this.contentNode.getChildren().add(sessionView.getContainerNode());
    }
}
