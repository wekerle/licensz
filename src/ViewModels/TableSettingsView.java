/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Helpers.StringHelper;
import Listener.DayChangeEventListener;
import Listener.HourChangeEventListener;
import Listener.TextChangeEventListener;
import Models.DayModel;
import Models.LocalTimeRangeModel;
import java.time.LocalDate;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Ronaldo
 */
public class TableSettingsView extends VBox
{
    private DayEditor dayEditor=null;
    private HourEditor period=null;
    private TextEditor numberOfSessionsPerRoom=new TextEditor();
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
        
        dayEditor=new DayEditor(dayModel.getDay());
        grid.add(dayEditor, 1, 0);
        dayEditor.setFont(StringHelper.font16);
        
        dayEditor.setDayChangeEventListener(new DayChangeEventListener() 
        {
            @Override
            public void modifyDate(LocalDate localdate) 
            {
                dayModel.setDay(localdate);
            }
        });
        
        Text textPeriod=new Text("Period:");
        textPeriod.setFont(StringHelper.font16Bold);
        grid.add(textPeriod, 0, 1);
        
        period=new HourEditor(dayModel.getTotalPeriod());
        period.setFont(StringHelper.font16);
        grid.add(period, 1, 1);
        
        period.setHourChangeEventListener(new HourChangeEventListener() 
        {
            @Override
            public void modifyHour(LocalTimeRangeModel timeRange) 
            {
                dayModel.setTotalPeriod(timeRange);
            }
        });
        
        Text textSessions=new Text("Number of sessions per room:");
        textSessions.setFont(StringHelper.font16Bold);
        grid.add(textSessions, 0, 2);
        
        numberOfSessionsPerRoom.setText(Integer.toString(dayModel.getNumberOfSessionsPerRoom()));
        numberOfSessionsPerRoom.setIsNumeric(true);
        numberOfSessionsPerRoom.setFont(StringHelper.font16);
        grid.add(numberOfSessionsPerRoom, 1, 2);
        
        numberOfSessionsPerRoom.setTextChangeEventListener(new TextChangeEventListener() 
        {
            @Override
            public void modifyText(String newValue) 
            {
                dayModel.setNumberOfSessionsPerDay(Integer.parseInt(newValue));                
            }
        });
                
        this.getChildren().add(grid);
    }
}
