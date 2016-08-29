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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author tibor.wekerle
 */
public class ConstraintsView extends ScrollPane
{
    AplicationModel aplicationModel=null;
    private GridPane table=new GridPane();
    double scrollValue;
    
     private ScrollBar getVerticalScrollbar() 
     {
        ScrollBar result = null;
        for (Node n : this.lookupAll(".scroll-bar")) 
        {
            if (n instanceof ScrollBar) 
            {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) 
                {
                    result = bar;
                }
            }
        }        
        return result;
    }
    
    private void addContraintDateAndPeriodToGrid(DateAndPeriodModel dateAndPeriod,ConstraintModel constraint, GridPane constraintGrid,int rowNumber )
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
                        ConstraintsView.this.scrollValue=ConstraintsView.this.getVvalue();
                        constraint.deleteDateAndPeriod(dateAndPeriod);
                       // constraintGrid.getChildren().remove(restriction);
                       // constraintGrid.getChildren().remove(buttonDelete);
                        
                        populateContent(aplicationModel.getConstraints());
                        ConstraintsView.this.layout();
                        ConstraintsView.this.layoutChildren();
                        ConstraintsView.setTimeout(new Thread()
                        {
                            public void run()
                            {
                                ConstraintsView.this.getVerticalScrollbar().setValue(scrollValue);
                            }
                        },10);
                       // ConstraintsView.this.getVerticalScrollbar().setValue(scrollValue);
                    }
                });

        constraintGrid.add(restriction, 0, rowNumber);
        constraintGrid.add(buttonDelete,1,rowNumber);
    }
    
    public static void setTimeout(Runnable runnable, int delay){
    new Thread(() -> {
        try {
            Thread.sleep(delay);
            runnable.run();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }).start();
}
    public ConstraintsView(AplicationModel aplicationModel)
    {   
        super();     
        this.aplicationModel=aplicationModel;
        
        populateContent(aplicationModel.getConstraints());
       // this.setVvalue(scrollValue);    
    }
    
    public void populateContent(ArrayList<ConstraintModel> constraints)
    {   
        
        this.table.getChildren().clear();
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
                addContraintDateAndPeriodToGrid(dateAndPeriod, constraint, constraintGrid, j);
                j++;
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
                                addContraintDateAndPeriodToGrid(dayPeriod, constraint, constraintGrid, ypos);
                                ypos++;
                            }
                        }                       
                    }
            );             
            i++;
        }
        
        this.setContent(table);   
        /*this.setVvalue(scrollValue);
        
        if(getVerticalScrollbar()!=null)
        {
            getVerticalScrollbar().setValue(scrollValue);
        }*/
    }
}
