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
import Models.ConstraintModel;
import Models.DateAndPeriodModel;
import Models.DayModel;
import Models.LectureModel;
import Models.LocalTimeRangeModel;
import Models.RoomModel;
import Models.SessionModel;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Ronaldo
 */
public class TableView extends VBox implements SessionDragEventListener
{
    // <editor-fold desc="private region" defaultstate="collapsed">
    private DayEditor dayEditor=new DayEditor();
    private GridPane table=new GridPane();
    private SessionDragEventListener sessionDragEvent;
    private DayModel dayModel;
    private HashMap<String,ConstraintModel> teacherConstraintMap;
    private HashMap<Integer,TableCellView> sessionIdTableCellMap=new HashMap<Integer,TableCellView>();
    private HBox messageBox = new HBox();
    private Text warningMessageText=new Text();

    private void populateContent(DayModel dayModel)
    {   
        this.table.getChildren().clear();
        this.getChildren().clear();
        
        messageBox.getStyleClass().add("warningBoxCell");        
        messageBox.getChildren().add(warningMessageText);
        messageBox.setVisible(false);
        
        dayEditor.setFont(StringHelper.font22Bold);
        dayEditor.setAlignment(Pos.CENTER);
        dayEditor.setPadding(new Insets(16));
        dayEditor.setDay(dayModel.getDay());
        
        this.getChildren().add(dayEditor);
        this.getChildren().add(messageBox);
        
        dayEditor.setDayChangeEventListener(new DayChangeEventListener() 
        {
            @Override
            public void modifyDate(LocalDate localdate) 
            {
                dayModel.setDay(localdate);
            }
        });
        
        int i=0;
        for(RoomModel room : dayModel.getRooms())
        {
             RoomView roomView=new RoomView(room);
             
             TableCellView tableCellView=new TableCellView(this,i+1, 0,false);
             tableCellView.setContentNode(roomView);
             
             roomView.setAlignment(Pos.CENTER);
             roomView.setStyle("-fx-text-fill:white;");
             
            this.table.add(tableCellView.getContent(), i+1, 0);
            i++;
        }
        
        i=0;
        for(LocalTimeRangeModel time : dayModel.getTimes())
        {
            HourEditor hourEditor=new HourEditor(time);
            hourEditor.setHourChangeEventListener(new HourChangeEventListener() 
            {
                @Override
                public void modifyHour(LocalTimeRangeModel timeRange) 
                {
                    time.setStartTime(timeRange.getStartTime());
                    time.setEndTime(timeRange.getEndTime());
                    TableView.this.dayModel.recalculateTimesNextToCurrentTime(time);
                    TableView.this.populateContent(dayModel);
                }
            });
            
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
                if(session!=null)
                {
                    sessionIdTableCellMap.put(session.getId(), tableCellView);
                }
                        
                boolean sameCell=true;
                    
                if(session!=null)
                {
                    Converter c=new Converter();
                    MinimalSessionView minimalSession=c.sessionToMinimalSessionView(session);
                    tableCellView.setMinimalSessionView(minimalSession); 
                    tableCellView.setSessionDragEventListener(this);

                    if(session.isBreak())
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
                    }else
                    {
                        sameCell=false;
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
        verifyConstraint();
        this.getChildren().add(table);
    }
    // </editor-fold>
    
    public void setSessionDragEventListener(SessionDragEventListener sessionDragEvent)
    {
        this.sessionDragEvent=sessionDragEvent;
    }
        
    public TableView(DayModel dayModel, HashMap<String,ConstraintModel> teacherConstraintMap)
    {
        this.dayModel=dayModel;
        this.teacherConstraintMap=teacherConstraintMap;
        populateContent(dayModel);
    }
    
    public void verifyConstraint()
    {
        for(Map.Entry<Integer, HashMap<Integer, SessionModel>> timeRoomSession:dayModel.getTimeRoomMap().entrySet())
        {
            LocalTimeRangeModel time=dayModel.getTimeById(timeRoomSession.getKey());
            for(Map.Entry<Integer, SessionModel> roomSession: timeRoomSession.getValue().entrySet())
            {
                SessionModel session=roomSession.getValue();
                
                if(!session.isBreak())
                {
                    for(LectureModel lecture:session.getLectures())
                    {
                        for(String name:lecture.getAuthors())
                        {
                            ConstraintModel constraint=teacherConstraintMap.get(name);
                            if(constraint!=null)
                            {
                                for(DateAndPeriodModel datePeriod:constraint.getDatesAndPeriods())
                                {
                                    TableCellView tableCell=sessionIdTableCellMap.get(session.getId());
                                    if(datePeriod.getDate().getDayOfYear()==dayModel.getDay().getDayOfYear() && datePeriod.getTimeRange().intersects(time))
                                    {                                   
                                        warningMessageText=new Text("WARNING:The following table contains datas wich not satisface the teacher avaiblity constraint");
                                        messageBox.setVisible(true);                                   
                                        tableCell.markWarningBorder();

                                    }else
                                    {
                                        tableCell.unMarkWarningBorder();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void verifySameTeacher()
    {
        for(Map.Entry<Integer, HashMap<Integer, SessionModel>> timeRoomSession:dayModel.getTimeRoomMap().entrySet())
        {
            LocalTimeRangeModel time=dayModel.getTimeById(timeRoomSession.getKey());
            for(Map.Entry<Integer, SessionModel> roomSession: timeRoomSession.getValue().entrySet())
            {
                SessionModel session=roomSession.getValue();
                
                if(!session.isBreak())
                {
                    for(LectureModel lecture:session.getLectures())
                    {
                        for(String name:lecture.getAuthors())
                        {
                            warningMessageText=new Text("WARNING:The teacher/chair can not be at the same time in to different rooms");
                            messageBox.setVisible(true);                                   
                            //tableCell.markWarningBorder();
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void notifyDataManager(int destinationSessionId, int sourceSessionId, Enums.Position position) 
    {
        sessionDragEvent.notifyDataManager(destinationSessionId, sourceSessionId, position);
    }
}
