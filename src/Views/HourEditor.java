/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.Enums;
import Listener.TextChangeEventListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
/**
 *
 * @author Ronaldo
 */
public class HourEditor extends VBox {
    private TimePicker timePicker=new TimePicker();
    private Text text=new Text();
    
    public HourEditor()
    {
        this.getChildren().add(text);
        
        text.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){ 
                    
                    Dialog dialog = new Dialog<>();
                    dialog.setTitle("Set the time");
                    dialog.setHeaderText("Set the hour and minute");
                    dialog.getDialogPane().setPrefSize(200, 150);
                    
                    dialog.getDialogPane().setContent(timePicker);
                    
                    dialog.show();
                    
                    ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                    dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);

                    timePicker.requestFocus();                                        
                }else
                {
                    text.requestFocus();
                }
            }else{
                text.requestFocus();
            }
        }
        });
        
        timePicker.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {                                    
                if (newPropertyValue)
                {
                    System.out.println("Textfield on focus");
                }
                else
                {
                    //text.setText(textField.getText());
                    //TextEditor.this.getChildren().remove(textField);
                   // TextEditor.this.getChildren().add(text);
                   // textChangeListener.modifyText(Enums.TextType.NOTHING, Enums.TextCategory.NOTHING, 0, text.getText());
                }
            }
        });
    }
    
    public HourEditor(String text)
    {
        this();
        this.setText(text);
    }

    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
        //this.textField.setText(text);
    }

    public void setFont(Font font) {
        this.text.setFont(font);
        //this.textField.setFont(font);
    }
    
}
