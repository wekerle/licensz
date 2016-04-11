/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
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
public class TextEditor extends VBox {
    private TextField tf=new TextField();
    private Text text=new Text();

    public TextEditor()
    {
        this.getChildren().add(text);
        
        text.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    //System.out.println("Double clicked");
                    tf.setText(text.getText());
                    TextEditor.this.getChildren().remove(text);
                    TextEditor.this.getChildren().add(tf);
                    tf.requestFocus();
                }
            }
        }
        });
        
        tf.focusedProperty().addListener(new ChangeListener<Boolean>()
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
                    text.setText(tf.getText());
                    TextEditor.this.getChildren().remove(tf);
                    TextEditor.this.getChildren().add(text);
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
        this.tf.setText(text);
    }

    void setFont(Font font) {
        this.text.setFont(font);
        this.tf.setFont(font);
    }
    
    
}
