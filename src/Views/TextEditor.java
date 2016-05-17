/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import licentav2.TextChangeObserver;

/**
 *
 * @author Ronaldo
 */
public class TextEditor extends VBox {
    private TextField textField=new TextField();
    private Text text=new Text();
    private TextChangeObserver textChangeObserver;

    public void setTextChangeObserver(TextChangeObserver textChangeObserver) {
        this.textChangeObserver = textChangeObserver;
    }

    public TextEditor()
    {
        this.getChildren().add(text);
        
        text.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    textField.setText(text.getText());
                    TextEditor.this.getChildren().remove(text);
                    TextEditor.this.getChildren().add(textField);
                    textField.requestFocus();                                        
                }
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
                    System.out.println("Textfield on focus");
                }
                else
                {
                    text.setText(textField.getText());
                    TextEditor.this.getChildren().remove(textField);
                    TextEditor.this.getChildren().add(text);
                    if(textChangeObserver!=null)
                    {
                        textChangeObserver.notifyTextChange();
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

    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
        this.textField.setText(text);
    }

    public void setFont(Font font) {
        this.text.setFont(font);
        this.textField.setFont(font);
    }
    
}
