/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import DataManagment.DataManager;
import Models.SessionModel;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ronaldo
 */
public class TableCellView extends VBox {

    private int colIndex=0;
    private int rowIndex=0;
    private TableView table;
    private MinimalSessionView minimalSessionView=null;
    
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
        this.table=table;
         
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
                    DataManager dm=new DataManager(table.getAplicationModel());
                    
                    int sourceSessionId=Integer.parseInt(db.getString());
                    int destinationSessionId=TableCellView.this.getMinimalSessionView().getSessionId();
                    
                    SessionModel s1=dm.getSessionBySessionId(sourceSessionId);
                    SessionModel s2=dm.getSessionBySessionId(destinationSessionId);
                    
                    double centerY=TableCellView.this.getLayoutBounds().getMinY()+TableCellView.this.getHeight()/2;
                    
                    if(centerY>event.getY()){
                         dm.moveSession2BeforeSession1(s2,s1);
                         table.populateContent(table.getAplicationModel().getTopics());
                    }else
                    {
                        dm.moveSession2AfterSession1(s2,s1);
                        table.populateContent(table.getAplicationModel().getTopics());
                    }
                    
                   success = true;
                }
                event.setDropCompleted(success);
             }
        });
                  
     }
    
}
