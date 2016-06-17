/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.Enums;
import Listener.TextChangeEventListener;
import Models.AplicationModel;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author tibor.wekerle
 */
public class ParalelSessionPerDayView extends GridPane implements TextChangeEventListener  {
     private TextEditor titleView=new TextEditor();
     private Text text=new Text();
     private AplicationModel aplicationModel;
     
     public ParalelSessionPerDayView(AplicationModel aplicationModel)
     {
         text.setText("Insert the number of paralel sessions per day:");
         titleView.setText(Integer.toString(aplicationModel.getMaxNumberSessionPerDay()));
         titleView.setTextChangeEventListener(this);
         this.aplicationModel=aplicationModel;
         SetupView();
     }
     
     private void SetupView()
    {
        GridPane grid = new GridPane();   
        grid.getStyleClass().add("grid");

        grid.add(titleView, 1, 0);
        
        text.setFont(Font.font("TimesNewRoman", FontWeight.BOLD, 20));
        titleView.setFont(Font.font("TimesNewRoman", FontWeight.BOLD, 20));
        grid.getChildren().add(text);
        
        this.getChildren().add(grid);                                       
    }

    @Override
    public void modifyText(Enums.TextType type, Enums.TextCategory category, int id, String newValue) {
        aplicationModel.setMaxNumberSessionPerDay(Integer.parseInt(newValue));
    }
}
