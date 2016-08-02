/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.StringHelper;
import Models.DayModel;
import Models.LocalTimeRangeModel;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Ronaldo
 */
public class TableSettingsView extends VBox
{
    private DayEditor dayEditor=new DayEditor();
    private HourEditor period=null;
    private TextEditor numberOfSessionsPerDay=new TextEditor();
    private DayModel dayModel=null;
    private GridPane grid=new GridPane();
    
    public TableSettingsView(DayModel dayModel)
    {
       this.dayModel=dayModel; 
       populateContent(this.dayModel);
    }
    
    public void populateContent(DayModel dayModel)
    {   
        Text textDate=new Text("Date:");
        textDate.setFont(StringHelper.font16Bold);
        grid.add(textDate, 0, 0);
        
        dayEditor.setDay(dayModel.getDay());
        grid.add(dayEditor, 1, 0);
        dayEditor.setFont(StringHelper.font16);
        
        Text textPeriod=new Text("Period:");
        textPeriod.setFont(StringHelper.font16Bold);
        grid.add(textPeriod, 0, 1);
        
        period=new HourEditor(dayModel.getTotalPeriod());
        period.setFont(StringHelper.font16);
        grid.add(period, 1, 1);
        
        Text textSessions=new Text("Number of paralel sessions per day:");
        textSessions.setFont(StringHelper.font16Bold);
        grid.add(textSessions, 0, 2);
        
        numberOfSessionsPerDay.setText(Integer.toString(dayModel.getNumberOfSessionsPerDay()));
        numberOfSessionsPerDay.setFont(StringHelper.font16);
        grid.add(numberOfSessionsPerDay, 1, 2);
        
        this.getChildren().add(grid);
    }
}
