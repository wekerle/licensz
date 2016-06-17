/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author tibor.wekerle
 */
public class TimePicker extends GridPane{
    private Spinner<Integer> hourSpinner = new Spinner<>(0, 23, 1);
    private Spinner<Integer> minutSpinner = new Spinner<>(0, 59, 1);
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
    
    public TimePicker(){
        SetupView();
    }
    public void setTimePickerValue(int hour,int minutes){
        //this.hourSpinner.Va
    }
        
}
