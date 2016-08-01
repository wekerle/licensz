/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Adaptor.Converter;
import Helpers.Enums;
import Listener.DayChangeEventListener;
import Listener.HourChangeEventListener;
import Listener.SessionDragEventListener;
import Listener.TextChangeEventListener;
import Models.DayModel;
import Models.LocalTimeRangeModel;
import Models.RoomModel;
import Models.SessionModel;
import java.time.LocalDate;
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
public class TableView extends VBox implements SessionDragEventListener,DayChangeEventListener,HourChangeEventListener,TextChangeEventListener
{
    private DayEditor day=new DayEditor();
    private GridPane table=new GridPane();
    private SessionDragEventListener sessionDragEvent;
    private DayChangeEventListener dayChangeEvent;
    private HourChangeEventListener hourChangeEvent;
    private int tableId=0;
        
    public void setSessionDragEventListener(SessionDragEventListener sessionDragEvent)
    {
        this.sessionDragEvent=sessionDragEvent;
    }

    public void setDayChangeEventListener(DayChangeEventListener dayChangeEvent)
    {
        this.dayChangeEvent=dayChangeEvent;
    }
    
    public void setHourChangeEventListener(HourChangeEventListener hourChangeEvent)
    {
        this.hourChangeEvent=hourChangeEvent;
    }     
    
    public TableView()
    {
        super();         
    }
    
    public void populateContent(DayModel dayModel)
    {   
                
        day.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,22));
        day.setAlignment(Pos.CENTER);
        day.setPadding(new Insets(16));
        
        this.getChildren().add(day);
        
        day.setDayChangeEventListener(this);
        
        int i=0;
        for(RoomModel room : dayModel.getRooms())
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
         for(LocalTimeRangeModel timeRange : dayModel.getTimes())
        {
            HourEditor hourEditor=new HourEditor(timeRange);
            hourEditor.setHourChangeEventListener(this);
            hourEditor.setPeriodId(timeRange.getId());
            
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
            hb.setAlignment(Pos.CENTER);
            
            hb.getChildren().add(t);
            
            this.table.add(hb, 1, 2, 4, 1);
            i++;
        }
        i=1;
        for(RoomModel room : dayModel.getRooms())
        {   
            int j=1;
            for(LocalTimeRangeModel timeRange : dayModel.getTimes())
            {                 
                SessionModel session=dayModel.getSessionModelTimeRoom(timeRange.getId(),room.getId());
                TableCellView tableCellView=new TableCellView(this,i, j);

                boolean sameCell=true;
                    
                if(session!=null)
                {
                     Converter c=new Converter();
                     MinimalSessionView minimalSession=c.sessionToMinimalSessionView(session,this);
                     tableCellView.setContentNode(minimalSession); 

                    if(!session.isBreak())
                    {
                        sameCell=false;
                    }else
                    {
                        for(RoomModel roomModel:dayModel.getRooms())
                        {
                            SessionModel sessionModel=dayModel.getSessionModelTimeRoom(timeRange.getId(),roomModel.getId());

                             if(sessionModel!=null && session.getId()!=sessionModel.getId())
                             {
                                 sameCell=false;
                                 break;
                             }
                        }
                    }                                    
                } else
                {
                    sameCell=false;
                }
               
                
                if(sameCell)
                {
                    
                    
                    
                    
                    Text t= new Text("asd sfdg dfg hg sfds dsf sfdg ");
            HBox hb=new HBox();
            hb.setStyle("-fx-background-color: #ffc0cb");
            hb.setAlignment(Pos.CENTER);
            
            hb.getChildren().add(t);
            
            //this.table.add(hb, 1, 2, 4, 1);
                    
                    
                    
                    
                    tableCellView.setStyle("-fx-background-color: #ffc0cb");
                    tableCellView.setAlignment(Pos.CENTER);
                    //t//ableCellView.getC
                    
                    
                    //ColumnConstraints col1Constraints = new ColumnConstraints();
                   // col1Constraints.setPercentWidth(100);
                   
                    
                    this.table.add(tableCellView, 1, j,dayModel.getRooms().size(),1);
                    
                    // GridPane.setValignment(tableCellView,VPos.BOTTOM);
                   // GridPane.setHalignment(tableCellView,HPos.LEFT);
                    
                   // tableCellView.getCo
                }else
                {
                    this.table.add(tableCellView,i, j);
                }
                
                j++;
            }
            
            i++;
        }
        
        this.getChildren().add(table);
    }

    @Override
    public void notifyDataManager(int destinationSessionId, int sourceSessionId, Enums.Position position) {
        sessionDragEvent.notifyDataManager(destinationSessionId, sourceSessionId, position);
    }

    public void setTableId(int id) 
    {
        this.tableId = id;
    }
    
    public void setDay(LocalDate dateLocalDate)
    {
        day.setDay(dateLocalDate);
    }

    @Override
    public void modifyDate(int dayId, LocalDate localdate) 
    {
        dayChangeEvent.modifyDate(tableId, localdate);
    }

    @Override
    public void modifyHour(int periodId, LocalTimeRangeModel timeRange) 
    {
        hourChangeEvent.modifyHour(periodId, timeRange);
    }

    @Override
    public void modifyText(Enums.TextType type, Enums.TextCategory category, int id, String newValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
