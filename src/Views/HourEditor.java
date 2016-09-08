/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Listener.HourChangeEventListener;
import Models.LocalTimeRangeModel;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 *
 * @author Ronaldo
 */
public class HourEditor extends VBox 
{
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private Text text=new Text();
    private HourChangeEventListener hourChangeListener;
    
    public void setHourChangeEventListener(HourChangeEventListener hourChangeListener)
    {
        this.hourChangeListener=hourChangeListener;
    }
    
    public HourEditor(LocalTimeRangeModel time)
    {
        startTimePicker=new TimePicker(time.getStartTime());
        endTimePicker=new TimePicker(time.getEndTime());
        
        text.setText(time.toString());
        this.getChildren().add(text);
        
        text.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
            {
                if(mouseEvent.getClickCount() == 2)
                { 
                    
                    Dialog dialog = new Dialog<>();
                    dialog.setTitle("Set the time");
                    dialog.setHeaderText("Set the hour and minute");
                    dialog.getDialogPane().setPrefSize(200, 150);
                    
                    VBox dialogContent=new VBox();
                    dialogContent.getChildren().add(startTimePicker);
                    dialogContent.getChildren().add(endTimePicker);
                    dialog.getDialogPane().setContent(dialogContent);
                                                            
                    ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                    dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
                    
                    while(true)
                    {
                        Optional<ButtonType> result = dialog.showAndWait();
                        
                        if ((result.isPresent()) && (result.get() == buttonTypeOk)) 
                        {
                            if(startTimePicker.getValue().compareTo(endTimePicker.getValue())>=0)
                            {
                                Alert alert=new Alert(AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setContentText("The end time must be greater then the start time");
                                alert.showAndWait();
                            }
                            else
                            {
                                LocalTimeRangeModel time=new LocalTimeRangeModel(startTimePicker.getValue(), endTimePicker.getValue());
                                text.setText(time.toString());

                                if(hourChangeListener!=null)                           
                                {                         
                                    hourChangeListener.modifyHour(time);
                                }
                                break;
                            }                                                                   
                        }
                        else
                        {
                            break;
                        }
                    }                                                                                                    
                }else
                {
                    text.requestFocus();
                }
            }else
            {
                text.requestFocus();
            }
        }
        });
    }    

    public void setFont(Font font) 
    {
        this.text.setFont(font);
    }
    
    public LocalTimeRangeModel getTimeRange()
    {
       return new LocalTimeRangeModel(startTimePicker.getValue(), endTimePicker.getValue());
    }
}
