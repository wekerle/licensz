/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ronaldo
 */
public class TableCellView extends VBox {
    // at kell tenni pruivatra
    public int colIndex=0;
    public int rowIndex=0;
    
     public TableCellView(Node n,int rowIndex,int colIndex)
     {
         this.getChildren().add(n);
         this.rowIndex=rowIndex;
         this.colIndex=colIndex;
     }
     
     public TableCellView(int rowIndex,int colIndex)
     {
          this.rowIndex=rowIndex;
         this.colIndex=colIndex;
     }
    
}
