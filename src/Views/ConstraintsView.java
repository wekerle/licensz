/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.AplicationModel;
import Models.ConstraintModel;
import Models.LocalTimeRangeModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author tibor.wekerle
 */
public class ConstraintsView extends ScrollPane
{
    AplicationModel aplicationModel=null;
    private GridPane table=new GridPane();
         
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
            
            textName.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                    new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent event) 
                        {
                            DayEditor dayEditor=new DayEditor();
                            dayEditor.setDay(LocalDate.now());
                            HourEditor hourEditor=new HourEditor(new LocalTimeRangeModel(0, 0, 0));
                            
                            VBox editorContent=new VBox();
                            
                            editorContent.getChildren().addAll(dayEditor,hourEditor);
                            
                            Dialog dialog = new Dialog<>();
                            dialog.setHeaderText("Select the date and the hour:");
                            dialog.getDialogPane().setPrefSize(200, 150);

                            dialog.getDialogPane().setContent(editorContent);

                            ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                            ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                            dialog.getDialogPane().getButtonTypes().add(buttonCancel);

                            Optional<ButtonType> result = dialog.showAndWait();

                            if ((result.isPresent()) && (result.get() == buttonTypeOk)) 
                            {
                               
                            }
                        }                       
                    }
            );
            
            Text restriction=new Text("2017-Jul-12");
            table.add(textName, 0, i);
            table.add(restriction, 1, i);
            i++;
        }
        /*ToggleButton tg=new ToggleButton();
        ImageView ii =new ImageView("file://C:\\Users\\Ronaldo\\Desktop\\licenszGit3\\src\\about.png");
        tg.setGraphic(ii);
        
       table.add(tg, 2,0 );*/
        this.setContent(table);
        
    }
}
