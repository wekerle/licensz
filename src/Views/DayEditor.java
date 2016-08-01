/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.StringHelper;
import Listener.DayChangeEventListener;
import java.time.LocalDate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author tibor.wekerle
 */
public class DayEditor extends VBox 
{
    private Text text=new Text();
    private DatePicker datePicker=new DatePicker();
    private DayChangeEventListener dayChangeListener;
    
    public void setDayChangeEventListener(DayChangeEventListener dayChangeListener)
    {
        this.dayChangeListener=dayChangeListener;
    }
    
    public DayEditor()
    {
        this.getChildren().add(text);
        
        text.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
            {
                if(mouseEvent.getClickCount() == 2)
                {
                                        
                    datePicker.setConverter(StringHelper.getConverter());
                  
                    String dateString=StringHelper.getConverter().toString(datePicker.getValue());
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
                if (!newPropertyValue)
                {
                    String dateString=StringHelper.getConverter().toString(datePicker.getValue());
                    text.setText(dateString); 
                    if(dayChangeListener!=null)
                    {
                        dayChangeListener.modifyDate(0, datePicker.getValue());
                    }
                    
                    DayEditor.this.getChildren().remove(datePicker);
                    DayEditor.this.getChildren().add(text);
                }
            }
        });
    }
    
    public void setDay(LocalDate dateLocalDate)
    {
        this.text.setText(StringHelper.getConverter().toString(dateLocalDate));
        datePicker.setValue(dateLocalDate);
    }
    
    public LocalDate getDay()
    {
        return datePicker.getValue();
    }
    
    public void setFont(Font font) {                       
        this.text.setFont(font);
    }
}
