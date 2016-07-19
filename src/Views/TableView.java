/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Adaptor.Converter;
import DataManagment.DataManager;
import Helpers.Enums;
import Listener.SessionDragEventListener;
import Models.DayModel;
import Models.LocalTimeRangeModel;
import Models.RoomModel;
import Models.TopicModel;
import Models.SessionModel;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Ronaldo
 */
public class TableView extends VBox implements SessionDragEventListener{
    private DataManager dataManager=null;
    private DayEditor day=new DayEditor();
    private GridPane table=new GridPane();
    private SessionDragEventListener sessionDragEvent;
    private int tableId=0;
        
    public void setSessionDragEventListener(SessionDragEventListener sessionDragEvent)
    {
        this.sessionDragEvent=sessionDragEvent;
    }    
    
    public TableView()
    {
        super();         
    }
    
    public void populateContent(DayModel day)
    {   
      /*  int i=0;
        int j=0;
        for(RoomModel room : day.getRooms())
        {
             TextEditor textEditor=new TextEditor(room.getName());
             
             TableCellView roomTableCellView=new TableCellView(this,i+1, 0);
             roomTableCellView.setContentNode(textEditor);
             
            // textEditor.getStyleClass().add("tableCellSala");
             textEditor.setAlignment(Pos.CENTER);
             textEditor.setStyle("-fx-text-fill:white;");
             //tableCellView.getStyleClass().add("tableCellSala");
             
            this.table.add(roomTableCellView, i+1, j);
            
            HourEditor hourEditor=null;
            for(LocalTimeRangeModel timeRange : day.getTimes())
            {
                hourEditor=new HourEditor(timeRange);
                 TableCellView timeTableCellView=new TableCellView(this,i,j+1);
                 //  textEditor.setStyle("-fx-text-fill:red");
                 timeTableCellView.setContentNode(hourEditor);
                 timeTableCellView.setAlignment(Pos.CENTER);
                //  tableCellView.setStyle("-fx-background-color: black");
                 //textEditor.setStyle("-fx-text-fill: ladder(background, white 49%, black 50%)");

                this.table.add(timeTableCellView,i,j+1 );
                j++;
            }
            
            
            i++;
           
            
                      
           

            //  textEditor.setStyle("-fx-text-fill:red");
           // if(hourEditor!=null)
           // {
              
           // }

              

            //  Text t= new Text("asd sfdg dfg hg sfds dsf sfdg ");
            //  HBox hb=new HBox();
            //  hb.setStyle("-fx-background-color: #ffc0cb");

            //  hb.getChildren().add(t);
             // hb.setAlignment(Pos.CENTER);
            //  this.table.add(hb, 1, 2, 4, 1);
             // j++;
        }*/
        
        
        
        
        
        
        
        int i=0;
        for(RoomModel room : day.getRooms())
        {
             TextEditor textEditor=new TextEditor(room.getName());
             
             TableCellView tableCellView=new TableCellView(this,i+1, 0);
             tableCellView.setContentNode(textEditor);
             
            // textEditor.getStyleClass().add("tableCellSala");
             textEditor.setAlignment(Pos.CENTER);
             textEditor.setStyle("-fx-text-fill:white;");
             //tableCellView.getStyleClass().add("tableCellSala");
             
            this.table.add(tableCellView, i+1, 0);
            i++;
        }
        
        i=0;
         for(LocalTimeRangeModel timeRange : day.getTimes())
        {
            HourEditor hourEditor=new HourEditor(timeRange);
            TableCellView tableCellView=new TableCellView(this,0,i+1);
            
          //  textEditor.setStyle("-fx-text-fill:red");
            tableCellView.setContentNode(hourEditor);

            tableCellView.setAlignment(Pos.CENTER);
          //  tableCellView.setStyle("-fx-background-color: black");
            //textEditor.setStyle("-fx-text-fill: ladder(background, white 49%, black 50%)");
            
            this.table.add(tableCellView,0,i+1 );
            
            Text t= new Text("asd sfdg dfg hg sfds dsf sfdg ");
            HBox hb=new HBox();
            hb.setStyle("-fx-background-color: #ffc0cb");
            
            hb.getChildren().add(t);
            hb.setAlignment(Pos.CENTER);
            this.table.add(hb, 1, 2, 4, 1);
            i++;
        }
         
          for(RoomModel room : day.getRooms())
          {
               for(LocalTimeRangeModel timeRange : day.getTimes())
               {
                   int sessionId=day.getSessionIdByRoomTime(room.getId(), timeRange.getId());
             
                   // TableCellView tableCellView=new TableCellView(this,i+1, 0);
                    //tableCellView.setContentNode(textEditor);
               }
          }
         
        this.getChildren().add(table);
    }

    @Override
    public void notifyDataManager(int destinationSessionId, int sourceSessionId, Enums.Position position) {
        sessionDragEvent.notifyDataManager(destinationSessionId, sourceSessionId, position);
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int id) {
        this.tableId = id;
    }
    
    public void setDay(LocalDate dateLocalDate)
    {
        day.setDay(dateLocalDate);
        
        day.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,22));
        day.setAlignment(Pos.CENTER);
        day.setPadding(new Insets(16));
                
        this.getChildren().add(day);
    }
}
