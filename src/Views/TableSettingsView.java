/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.DayModel;
import Models.LocalTimeRangeModel;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ronaldo
 */
public class TableSettingsView extends VBox
{
    private DayEditor dayEditor=new DayEditor();
    private HourEditor period= new HourEditor(new LocalTimeRangeModel(8, 0, 0));
    private TextEditor numberOfSessionsPerDay=new TextEditor();
    private DayModel dayModel=null;
    
    public TableSettingsView(DayModel dayModel)
    {
       this.dayModel=dayModel; 
       populateContent(this.dayModel);
    }
    
    public void populateContent(DayModel dayModel)
    {   
        this.getChildren().add(dayEditor);
        this.getChildren().add(period);
        this.getChildren().add(numberOfSessionsPerDay);
    }
}
