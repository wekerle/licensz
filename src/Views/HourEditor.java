/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.LocalTimeRangeModel;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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
                                        
                    dialog.show();
                    
                    ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                    dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);

                    Optional<ButtonType> result = dialog.showAndWait();

                    if ((result.isPresent()) && (result.get() == buttonTypeOk)) 
                    {
                       
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
}
