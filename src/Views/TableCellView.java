/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.Enums;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import Listener.SessionDragEventListener;
import Models.LectureModel;
import Models.SessionModel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author Ronaldo
 */
public class TableCellView
{
    
    private MinimalSessionView minimalSessionView=null;
    private SessionDragEventListener sessionDragEvent;
    private Pane content;
      
    public void setSessionDragEventListener(SessionDragEventListener sessionDragEvent)
    {
        this.sessionDragEvent=sessionDragEvent;
    }
            
    public Pane getContent() {
        return content;
    }
    
   

    public void setContentNode(Node contentNode) 
    {
        content.getChildren().add(contentNode);
    }
    
    public void setMinimalSessionView(MinimalSessionView minimalSessionView) 
    {
        this.minimalSessionView = minimalSessionView;
        setContentNode(minimalSessionView);
    }

    public MinimalSessionView getMinimalSessionView() 
    {
        return minimalSessionView;
    }
     
     public TableCellView(TableView table, int colIndex,int rowIndex, boolean isHbox)
     {
        
        
        if(isHbox)
        {
            content=new HBox();
        }
        else
        {
            content=new VBox();
        }
        content.setOnDragOver(new EventHandler<DragEvent>() 
        {
            public void handle(DragEvent event) { 
                
                SessionModel session=TableCellView.this.minimalSessionView.getSessionModel();
                if (event.getGestureSource() != this && event.getDragboard().hasString() && session!=null && !session.isBreak() ) 
                {
                    event.acceptTransferModes(TransferMode.MOVE);
                    
                    double centerY=TableCellView.this.content.getLayoutBounds().getMinY()+TableCellView.this.content.getHeight()/2;
                    
                    if(centerY>event.getY() && !TableCellView.this.content.getStyleClass().contains("tableCellDragOverBorderTop"))
                    {
                        TableCellView.this.content.getStyleClass().add("tableCellDragOverBorderTop");
                        TableCellView.this.content.getStyleClass().remove("tableCellDragOverBorderBottom");
                    } else if(centerY<=event.getY() && !TableCellView.this.content.getStyleClass().contains("tableCellDragOverBorderBottom"))
                    {
                        TableCellView.this.content.getStyleClass().add("tableCellDragOverBorderBottom");
                        TableCellView.this.content.getStyleClass().remove("tableCellDragOverBorderTop");
                    }
                }
            }
        });
                
        content.setOnDragExited(new EventHandler<DragEvent>() 
        {
            public void handle(DragEvent event) 
            {
                if (event.getGestureSource() != this && event.getDragboard().hasString()) 
                {
                    TableCellView.this.content.getStyleClass().remove("tableCellDragOverBorderTop");
                    TableCellView.this.content.getStyleClass().remove("tableCellDragOverBorderBottom");
                }
            }
        });
        
        content.setOnDragDropped(new EventHandler<DragEvent>() 
        {
            public void handle(DragEvent event) 
            {
                Dragboard db = event.getDragboard();
                boolean success = false;
                int sourceSessionId=0;
                int destinationSessionId=0;
                Enums.Position position=Enums.Position.AFTER;
                
                if (db.hasString()) 
                {                                  
                    sourceSessionId=Integer.parseInt(db.getString());
                    destinationSessionId=TableCellView.this.getMinimalSessionView().getSessionId();
                    
                    double centerY=TableCellView.this.content.getLayoutBounds().getMinY()+TableCellView.this.content.getHeight()/2;
                    
                    if(centerY>event.getY())
                    {
                        position=Enums.Position.BEFORE;     
                    }else
                    {
                        position=Enums.Position.AFTER;                        
                    }
                    
                   success = true;
                }
                event.setDropCompleted(success);
                if(success)
                {
                    sessionDragEvent.notifyDataManager(destinationSessionId,sourceSessionId,position);
                }
                
                event.consume();
             }
        });        
     }
     
    public void markWarningBorder()
    {
       if(!content.getStyleClass().contains("warningTableCell"))
       {
           content.getStyleClass().add("warningTableCell");
       }
    }

    public void unMarkWarningBorder()
    {
       if(content.getStyleClass().contains("warningTableCell"))
       {
           content.getStyleClass().remove("warningTableCell");
       }
    }
    
    public boolean containsName(String name)
    {      
        if(this.minimalSessionView==null)
        {
            return false;
        }
        
        SessionModel session=this.minimalSessionView.getSessionModel();
        if(session==null || session.isBreak())
        {
            return false;
        }
        
        if(session.getChair().trim().compareTo(name)==0 || session.getCoChair().trim().compareTo(name)==0)
        {
           return true;
        }
        
        for(LectureModel lecture : session.getLectures())
        {
            for(String author : lecture.getAuthors())
            {
                if(author.trim().compareTo(name)==0)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
