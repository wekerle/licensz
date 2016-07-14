/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Adaptor.Converter;
import DataManagment.DataManager;
import Helpers.Enums;
import Helpers.StringHelper;
import Listener.SessionDragEventListener;
import Models.DayModel;
import Models.LocalTimeRangeModel;
import Models.RoomModel;
import Models.TopicModel;
import Models.SessionModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    private DayEditor dayView=new DayEditor();
    private GridPane table=new GridPane();
    private SessionDragEventListener sessionDragEvent;
    private int id=0;
    TableCellView[][] matrix;
        
    public void setSessionDragEventListener(SessionDragEventListener sessionDragEvent)
    {
        this.sessionDragEvent=sessionDragEvent;
    }    
    
    public TableView(DayModel day)
    {
        super();
        
        this.id=day.getId();
        dayView.setText("2016-Jul-4");
        
        dayView.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,22));
        dayView.setAlignment(Pos.CENTER);
        dayView.setPadding(new Insets(16));
                
        this.getChildren().add(dayView);
        //this.sessionDragEvent=seessionDragEventListener;
        populateContent_new(day);             
    }
    
  //  public TableView(ArrayList<TopicModel> topics,SessionDragEventListener seessionDragEventListener)
   // {
     //   super();
     //   DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(StringHelper.pattern);
     //   String currentDate=LocalDate.now().format(dateFormatter);
        
     //   dayView.setText(currentDate);
        
      //  dayView.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,22));
      //  dayView.setAlignment(Pos.CENTER);
      //  dayView.setPadding(new Insets(16));
                
      //  this.getChildren().add(dayView);
      //  this.sessionDragEvent=seessionDragEventListener;
      //  populateContent(topics);             
    //}
    
    public void populateContent_new(DayModel day)
    {
        int maxRow=day.getRoomNumberCount();
        int maxCol=day.getTimesNumberCount();
        
        matrix=new  TableCellView[maxRow+1][maxCol+1];
        
        for(int i=0;i<maxRow+1;i++)
        {
            for(int j=0; j<maxCol+1;j++)
            {
                TableCellView tableCellView =new TableCellView(this,i,j);
                tableCellView.setSessionDragEventListener(this);
                                
                this.table.add(tableCellView, i, j);
                matrix[i][j]=tableCellView;
            }       
        }
        int i=0;
        for(RoomModel room : day.getRooms())
        {
             TextEditor textEditor=new TextEditor(room.getName());
             
             TableCellView tableCellView=matrix[0][i+1];
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
            TableCellView tableCellView=matrix[i+1][0];
            
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
    }
    
    public void populateContent(ArrayList<TopicModel> topics)
    {       
        int maxRow=0;
        int maxCol=topics.size();
        
        for(TopicModel p : topics)
        {
            if(maxRow<p.getSessions().size())
            {
                maxRow=p.getSessions().size();
            }
        }
        
        matrix=new  TableCellView[maxRow+1][maxCol+1];
        
        for(int i=0;i<maxRow+1;i++)
        {
            for(int j=0; j<maxCol+1;j++)
            {
                TableCellView tableCellView =new TableCellView(this,i,j);
                tableCellView.setSessionDragEventListener(this);
                                
                this.table.add(tableCellView, i, j);
                matrix[i][j]=tableCellView;
            }       
        }
        
        Converter converter=new Converter();
        this.table.getChildren().clear();
                
        int colNum=1;
        int rowNum=1; 
               
        for(TopicModel topic : topics)
        {            
            colNum=1;
            for(SessionModel session : topic.getSessions())
            {
                MinimalSessionView sessionView=converter.sessionToMinimalSessionView(session);
                
                TableCellView tableCellView=matrix[colNum][rowNum];               
                tableCellView.setMinimalSessionView(sessionView);
                                
                this.table.add(tableCellView,rowNum,colNum);
                colNum++;
            }
            rowNum++;
        }
        
        for(int i=0;i<topics.size();i++)
        {
             TextEditor textEditor=new TextEditor("Sala"+i);
             
             TableCellView tableCellView=matrix[0][i+1];
             tableCellView.setContentNode(textEditor);
             
            // textEditor.getStyleClass().add("tableCellSala");
             textEditor.setAlignment(Pos.CENTER);
             textEditor.setStyle("-fx-text-fill:white;");
             //tableCellView.getStyleClass().add("tableCellSala");
             
            this.table.add(tableCellView, i+1, 0);
        }
        
         for(int i=0;i<maxRow; i++)
        {
            LocalTimeRangeModel timeRange=new LocalTimeRangeModel(10, 0, 10);
            HourEditor hourEditor=new HourEditor(timeRange);
            TableCellView tableCellView=matrix[i+1][0];
            
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
        }
         this.getChildren().add(table);
    }

    @Override
    public void notifyDataManager(int destinationSessionId, int sourceSessionId, Enums.Position position) {
        sessionDragEvent.notifyDataManager(destinationSessionId, sourceSessionId, position);
    }

}
