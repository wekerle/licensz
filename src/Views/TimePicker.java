/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.time.LocalTime;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;

/**
 *
 * @author tibor.wekerle
 */
public class TimePicker extends GridPane
{
    private Spinner<Integer> hourSpinner;
    private Spinner<Integer> minutSpinner;
    private GridPane grid = new GridPane();
    private void SetupView()
    {
        hourSpinner.setMaxWidth(55.00);
        minutSpinner.setMaxWidth(55.00);
        hourSpinner.setEditable(true);
        minutSpinner.setEditable(true);
                
        grid.add(new Label("Time:"), 0, 0);
        grid.add(hourSpinner, 1, 0);
        grid.add(minutSpinner, 2, 0);
        this.getChildren().add(grid);
    }
    
    public TimePicker(LocalTime time)
    {      
        hourSpinner=new Spinner<>(0, 23,time.getHour(),1);
        hourSpinner=new Spinner<>(0, 23, time.getHour(),1);
        minutSpinner=new Spinner<>(0, 59, time.getMinute(),5);
        SetupView();
    }
    
    public LocalTime getValue()
    {
        return LocalTime.of(hourSpinner.getValue(),minutSpinner.getValue());
    }
}
