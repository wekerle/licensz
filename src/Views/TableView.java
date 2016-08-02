/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Adaptor.Converter;
import Helpers.Enums;
import Helpers.StringHelper;
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
                
        day.setFont(StringHelper.font22Bold);
        day.setAlignment(Pos.CENTER);
        day.setPadding(new Insets(16));
        
        this.getChildren().add(day);
        
        day.setDayChangeEventListener(this);
        
        int i=0;
        for(RoomModel room : dayModel.getRooms())
        {
             TextEditor textEditor=new TextEditor(room.getName());
             
             TableCellView tableCellView=new TableCellView(this,i+1, 0,false);
             tableCellView.setContentNode(textEditor);
             
             textEditor.setAlignment(Pos.CENTER);
             textEditor.setStyle("-fx-text-fill:white;");
             
            this.table.add(tableCellView.getContent(), i+1, 0);
            i++;
        }
        
        i=0;
         for(LocalTimeRangeModel timeRange : dayModel.getTimes())
        {
            HourEditor hourEditor=new HourEditor(timeRange);
            hourEditor.setHourChangeEventListener(this);
            hourEditor.setPeriodId(timeRange.getId());
            
            TableCellView tableCellView=new TableCellView(this,0,i+1,false);
            tableCellView.setContentNode(hourEditor);

            ((VBox)tableCellView.getContent()).setAlignment(Pos.CENTER);           
            this.table.add(tableCellView.getContent(),0,i+1 );
            
            i++;
        }
        i=1;
        for(RoomModel room : dayModel.getRooms())
        {   
            int j=1;
            for(LocalTimeRangeModel timeRange : dayModel.getTimes())
            {                 
                SessionModel session=dayModel.getSessionModelTimeRoom(timeRange.getId(),room.getId());
                TableCellView tableCellView=new TableCellView(this,i, j,true);

                boolean sameCell=true;
                    
                if(session!=null)
                {
                    Converter c=new Converter();
                    MinimalSessionView minimalSession=c.sessionToMinimalSessionView(session,this);
                    tableCellView.setMinimalSessionView(minimalSession); 
                    tableCellView.setSessionDragEventListener(this);

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
                    ((HBox)tableCellView.getContent()).setStyle("-fx-background-color: #ffc0cb");
                    ((HBox)tableCellView.getContent()).setAlignment(Pos.CENTER);
                    
                    this.table.add(tableCellView.getContent(), 1, j,dayModel.getRooms().size(),1);

                }
                else
                {
                    this.table.add(tableCellView.getContent(),i, j);
                }
                
                j++;
            }
            
            i++;
        }
        
        this.getChildren().add(table);
    }

    @Override
    public void notifyDataManager(int destinationSessionId, int sourceSessionId, Enums.Position position) 
    {
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
    public void modifyText(Enums.TextType type, Enums.TextCategory category, int id, String newValue) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
