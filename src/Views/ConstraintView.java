/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.ConstraintModel;
import java.util.ArrayList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author tibor.wekerle
 */
public class ConstraintView extends ScrollPane{
    
    private GridPane table=new GridPane();
         
    public ConstraintView()
    {
        super();         
    }
    
    public void populateContent(ArrayList<ConstraintModel> constraints)
    {   
   
        this.getChildren().add(table);
    }
}
