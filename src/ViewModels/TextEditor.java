/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Listener.TextChangeEventListener;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 *
 * @author Ronaldo
 */
public class TextEditor extends VBox 
{
    private TextField textField=new TextField();
    private Text text=new Text();
    private TextChangeEventListener textChangeListener;
    private boolean isNumeric=false;

    public void setTextChangeEventListener(TextChangeEventListener textChangeListener)
    {
        this.textChangeListener=textChangeListener;
    }
    
    public void setIsNumeric(boolean numeric)
    {
        this.isNumeric=numeric;
    }   
    
    public TextEditor()
    {
        this.getChildren().add(text);
        
        text.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
                {
                    if(mouseEvent.getClickCount() == 2){
                        textField.setText(text.getText());                  
                        TextEditor.this.getChildren().remove(text);
                        TextEditor.this.getChildren().add(textField);
                        textField.requestFocus();                                        
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
        
        textField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {                                    
                if (newPropertyValue)
                {
                    
                }
                else
                {
                    if(isNumeric)
                    {
                        try
                        {
                            Integer.parseInt(textField.getText());
                            
                            text.setText(textField.getText());
                            TextEditor.this.getChildren().remove(textField);
                            TextEditor.this.getChildren().add(text);
                            if(textChangeListener!=null)
                            {
                                textChangeListener.modifyText(text.getText());
                            }
                        }catch(Exception e)
                        {
                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("Please insert a valid number");

                            Optional<ButtonType> result = alert.showAndWait();

                            if ((result.isPresent()) && (result.get() == ButtonType.OK))
                            {
                               textField.requestFocus();
                            }
                        }
                    }else
                    {
                        text.setText(textField.getText());
                        TextEditor.this.getChildren().remove(textField);
                        TextEditor.this.getChildren().add(text);
                        if(textChangeListener!=null)
                        {
                            textChangeListener.modifyText(text.getText());
                        }
                    }                                      
                }
            }
        });
    }
    
    public TextEditor(String text)
    {
        this();
        this.setText(text);
    }

    public String getText() 
    {
        return text.getText();
    }

    public void setText(String text)
    {
        this.text.setText(text);
        this.textField.setText(text);
    }

    public void setFont(Font font) 
    {
        this.text.setFont(font);
        this.textField.setFont(font);
    }
    
}
