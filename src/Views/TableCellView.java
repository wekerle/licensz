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

/**
 *
 * @author Ronaldo
 */
public class TableCellView extends VBox {

    private int colIndex=0;
    private int rowIndex=0;
    private MinimalSessionView minimalSessionView=null;
    private SessionDragEventListener sessionDragEvent;
    
    public void setSessionDragEventListener(SessionDragEventListener sessionDragEvent)
    {
        this.sessionDragEvent=sessionDragEvent;
    }
    
    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setContentNode(Node contentNode) {
        this.getChildren().add(contentNode);
    }
    
    public void setMinimalSessionView(MinimalSessionView minimalSessionView) {
        this.minimalSessionView = minimalSessionView;
        setContentNode(minimalSessionView);
    }

    public MinimalSessionView getMinimalSessionView() {
        return minimalSessionView;
    }
     
     public TableCellView(TableView table, int rowIndex,int colIndex)
     {
        this.rowIndex=rowIndex;
        this.colIndex=colIndex;
         
        this.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {               
                if (event.getGestureSource() != this &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                    
                    double centerY=TableCellView.this.getLayoutBounds().getMinY()+TableCellView.this.getHeight()/2;
                    
                    if(centerY>event.getY() && !TableCellView.this.getStyleClass().contains("tableCellDragOverBorderTop"))
                    {
                        TableCellView.this.getStyleClass().add("tableCellDragOverBorderTop");
                        TableCellView.this.getStyleClass().remove("tableCellDragOverBorderBottom");
                    } else if(centerY<=event.getY() && !TableCellView.this.getStyleClass().contains("tableCellDragOverBorderBottom") )
                    {
                        TableCellView.this.getStyleClass().add("tableCellDragOverBorderBottom");
                        TableCellView.this.getStyleClass().remove("tableCellDragOverBorderTop");
                    }
                }
            }
        });
                
        this.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                 if (event.getGestureSource() != this &&
                         event.getDragboard().hasString()) {

                         TableCellView.this.getStyleClass().remove("tableCellDragOverBorderTop");
                         TableCellView.this.getStyleClass().remove("tableCellDragOverBorderBottom");

                 }
            }
        });
        
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {                 
                    
                    int sourceSessionId=Integer.parseInt(db.getString());
                    int destinationSessionId=TableCellView.this.getMinimalSessionView().getSessionId();
                    
                    double centerY=TableCellView.this.getLayoutBounds().getMinY()+TableCellView.this.getHeight()/2;
                    
                    if(centerY>event.getY()){
                        
                        sessionDragEvent.notify(destinationSessionId,sourceSessionId,Enums.Position.BEFORE);                    
                    }else
                    {
                        sessionDragEvent.notify(destinationSessionId,sourceSessionId,Enums.Position.AFTER);
                    }
                    
                   success = true;
                }
                event.setDropCompleted(success);
             }
        });
                  
     }
    
}
