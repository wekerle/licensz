/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.StringHelper;
import Models.AplicationModel;
import Models.ConstraintModel;
import java.util.ArrayList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author tibor.wekerle
 */
public class ConstraintsView extends ScrollPane
{
    AplicationModel aplicationModel=null;
    private GridPane table=new GridPane();
    private Text text=new Text("Ronaldo");
         
    public ConstraintsView(AplicationModel aplicationModel)
    {   
        super();     
        this.aplicationModel=aplicationModel;
        
        populateContent(aplicationModel.getConstraints());
    }
    
    public void populateContent(ArrayList<ConstraintModel> constraints)
    {   
        int i=0;
        for(ConstraintModel constraint : constraints)
        {
            Text textName=new Text(constraint.getTeacherName());
            Text restriction=new Text("2017-12-12");
            table.add(textName, 0, i);
            table.add(restriction, 1, i);
            i++;
        }
        ToggleButton tg=new ToggleButton();
        ImageView ii =new ImageView("file://C:\\Users\\Ronaldo\\Desktop\\licenszGit3\\src\\about.png");
        tg.setGraphic(ii);
        
       table.add(tg, 2,0 );
        this.setContent(table);
        
    }
}
