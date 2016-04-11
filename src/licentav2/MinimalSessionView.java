/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Ronaldo
 */
public class MinimalSessionView {
    private VBox contentNode=new VBox();
    private TextEditor titleView=new TextEditor();
    private VBox containerNode=new VBox();
    
    public MinimalSessionView(String title)
    {
        titleView.setText(title);        
        containerNode.getChildren().add(titleView);
        containerNode.getChildren().add(contentNode);
        containerNode.setPadding(new Insets(10.0));
        
        titleView.setFont(Font.font("TimesNewRoman",18));
    }
    
    public VBox getContainerNode() {
        return containerNode;
    }
}
