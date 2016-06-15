/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.DateTimeStringConverter;
import javafx.util.converter.LocalDateStringConverter;

/**
 *
 * @author tibor.wekerle
 */
public class DayEditor extends VBox {
    private Text text=new Text();
    private DatePicker datePicker=new DatePicker();
    private final String pattern = "yyyy-MMM-dd";
    
    public DayEditor()
    {
        this.getChildren().add(text);
        
        text.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                                        
                    StringConverter converter = new StringConverter<LocalDate>() {
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
                        @Override
                        public String toString(LocalDate date) {
                            if (date != null) {
                                return dateFormatter.format(date);
                            } else {
                                return "";
                            }
                        }
                        @Override
                        public LocalDate fromString(String string) {
                            if (string != null && !string.isEmpty()) {
                                return LocalDate.parse(string, dateFormatter);
                            } else {
                                return null;
                            }
                        }
                    }; 
                    datePicker.setConverter(converter);
                  
                    String dateString=converter.toString(datePicker.getValue());
                   // LocalDate dateLocalDate =(LocalDate) converter.fromString(dateString);
                    
                   // datePicker.setValue(dateLocalDate);
                    text.setText(dateString);    
                    
                    DayEditor.this.getChildren().remove(text);
                    DayEditor.this.getChildren().add(datePicker);
                    
                    datePicker.requestFocus();                                        
                }else
                {
                    // azert csinaltam hogy veszitse el a texteditor a focust
                    text.requestFocus();
                }
            }else{
                text.requestFocus();
            }
                    mouseEvent.consume();
        }

        });
        
        datePicker.focusedProperty().addListener(new ChangeListener<Boolean>()
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
                    text.setText(datePicker.getValue().toString());
                    DayEditor.this.getChildren().remove(datePicker);
                    DayEditor.this.getChildren().add(text);
                }
            }
        });
    }
    
    public DayEditor(String text)
    {
        this();
        this.setText(text);
    }
   
    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
        
        StringConverter converter = new StringConverter<LocalDate>() {
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
                        @Override
                        public String toString(LocalDate date) {
                            if (date != null) {
                                return dateFormatter.format(date);
                            } else {
                                return "";
                            }
                        }
                        @Override
                        public LocalDate fromString(String string) {
                            if (string != null && !string.isEmpty()) {
                                return LocalDate.parse(string, dateFormatter);
                            } else {
                                return null;
                            }
                        }
                    }; 
                    datePicker.setConverter(converter);
                    
                    LocalDate dateLocalDate =(LocalDate) converter.fromString(text);
                    
                    datePicker.setValue(dateLocalDate);
    }
    
    public void setFont(Font font) {                       
        this.text.setFont(font);
    }
}
