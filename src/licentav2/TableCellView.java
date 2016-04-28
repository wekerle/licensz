/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import DataManagment.DataManager;
import Models.SessionModel;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ronaldo
 */
public class TableCellView extends VBox {

    private int colIndex=0;
    private int rowIndex=0;
    private TableView table;
    private Node contentNode=null;
    
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
        this.contentNode = contentNode;
        this.getChildren().add(contentNode);
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
                }
            }
        });
        
        this.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                 if (event.getGestureSource() != this &&
                         event.getDragboard().hasString()) {
                    TableCellView.this.setStyle("-fx-background-color:white");
                    TableCellView.this.setStyle("-fx-border-color: blue");
                    TableCellView.this.setStyle("-fx-border-insets: 5");
                    TableCellView.this.setStyle("-fx-border-width: 3");
                    TableCellView.this.setStyle("-fx-border-style: dashed");
                    //System.out.println(1);
                 }

                // event.consume();
            }
        });
        
        this.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                 if (event.getGestureSource() != this &&
                         event.getDragboard().hasString()) {
                   
                   // TableCellView.this.setStyle("-fx-border-color: inherited");
                   // TableCellView.this.setStyle("-fx-border-width: inherited");
                  //  TableCellView.this.setStyle("-fx-border-width: inherited");
                    TableCellView.this.setStyle("-fx-border-style: none");
                    //System.out.println(1);
                 }

                // event.consume();
            }
        });
        
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {                 
                    DataManager dm=new DataManager();
                    int sourceSessionId=Integer.parseInt(db.getString());
                   // int destinationSessionId=TableCellView.this.;
                   //Node n= TableCellView.this.contentNode.Se;
                    
                    SessionModel s1=GlobalVaribles.getSessionByNumber(sourceSessionId);
                    SessionModel s2=GlobalVaribles.getSessionByNumber(sourceSessionId);
                    
                   // table.populateContent(dm.moveSession(table.getAplicationModel(),s2,s1).getTopics());
                 
                    System.out.println(TableCellView.this.rowIndex+","+TableCellView.this.colIndex);
                   success = true;
                }
                event.setDropCompleted(success);

              //  event.consume();
             }
        });
                  
     }
    
}
