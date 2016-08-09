/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.StringHelper;
import Models.AplicationModel;
import Models.ConstraintModel;
import Models.DateAndPeriodModel;
import Models.LocalTimeRangeModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
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
            textName.setFont(StringHelper.font16Bold);
                                 
            Image imageAdd=new Image("/Icons/add3.png");
            Button buttonAdd=new Button();
            buttonAdd.setGraphic(new ImageView(imageAdd));
            
            table.add(buttonAdd,0,i);           
            table.add(textName, 1, i);
            
            GridPane constraintGrid=new GridPane();
            int j=0;                           
            for(DateAndPeriodModel dateAndPeriod:constraint.getDatesAndPeriods())
            {
                Text restriction=new Text();
                restriction.setFont(StringHelper.font16);
                restriction.setText(dateAndPeriod.getDayAndTimeString());
                
                Image imageDelete=new Image("/Icons/delete3.png");
                Button buttonDelete=new Button();
                buttonDelete.setGraphic(new ImageView(imageDelete));

                buttonDelete.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new EventHandler<MouseEvent>()
                        {
                            @Override
                            public void handle(MouseEvent event) {
                                constraint.deleteDateAndPeriod(dateAndPeriod);
                                constraintGrid.getChildren().remove(restriction);
                                constraintGrid.getChildren().remove(buttonDelete);
                            }
                        });
                
                constraintGrid.add(restriction, 0, j);
                constraintGrid.add(buttonDelete,1,j);
                j++;
               // table.add(restriction, 2, i);
               // table.add(buttonDelete, 3, i);
               // i++;
            }           
            table.add(constraintGrid, 2, i);
            
            final int jj = j;
                                 
            buttonAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                    new EventHandler<MouseEvent>()
                    {
                        int ypos = -1;
                        @Override
                        public void handle(MouseEvent event) 
                        {
                            if (ypos == -1) ypos = jj;
                            GridPane dialogContent=new GridPane();                           
                            
                            Text textDate=new Text("Date:");
                            textDate.setFont(StringHelper.font16Bold);
                            dialogContent.add(textDate, 0, 0);
                                
                            DayEditor dayEditor=new DayEditor();
                            dayEditor.setDay(LocalDate.now());
                            dayEditor.setFont(StringHelper.font16);
                            dialogContent.add(dayEditor, 1, 0);

                            Text textPeriod=new Text("Period:");
                            textPeriod.setFont(StringHelper.font16Bold);
                            dialogContent.add(textPeriod, 0, 1);

                            HourEditor hourEditor=new HourEditor(new LocalTimeRangeModel(8,0,360));
                            hourEditor.setFont(StringHelper.font16);
                            dialogContent.add(hourEditor, 1, 1);
                                           
                            Dialog dialog = new Dialog<>();
                            dialog.setHeaderText("Select the date and the hour:");
                            dialog.getDialogPane().setPrefSize(300, 225);

                            dialog.getDialogPane().setContent(dialogContent);

                            ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                            ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                            dialog.getDialogPane().getButtonTypes().add(buttonCancel);

                            Optional<ButtonType> result = dialog.showAndWait();

                            if ((result.isPresent()) && (result.get() == buttonTypeOk)) 
                            {
                                DateAndPeriodModel dayPeriod=new DateAndPeriodModel(dayEditor.getDay(), hourEditor.getTimeRange());
                                constraint.addDateAndPeriod(dayPeriod);
                                
                                Text restriction=new Text();
                                restriction.setFont(StringHelper.font16);
                                restriction.setText(dayPeriod.getDayAndTimeString());
                                constraintGrid.add(restriction, 0,ypos);
                                
                                Image imageDelete=new Image("/Icons/delete3.png");
                                Button buttonDelete=new Button();
                                buttonDelete.setGraphic(new ImageView(imageDelete));
                                
                                constraintGrid.add(buttonDelete, 1,ypos++);

                                buttonDelete.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                        new EventHandler<MouseEvent>()
                                        {
                                            @Override
                                            public void handle(MouseEvent event) {
                                                constraint.deleteDateAndPeriod(dayPeriod);
                                                constraintGrid.getChildren().remove(restriction);
                                                constraintGrid.getChildren().remove(buttonDelete);
                                            }
                                        });
                            }
                        }                       
                    }
            );             
            i++;
        }
   
        
        
        this.setContent(table);
        
    }
}
