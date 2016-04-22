/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Ronaldo
 */
public class MinimalSessionView {
    private VBox contentNode=new VBox();
    private TextEditor titleView=new TextEditor();
    private VBox containerNode=new VBox();
    private int id=0;
    
    public MinimalSessionView(String title,int id)
    {
        this.id=id;
        titleView.setText(title);  
        containerNode.getChildren().add(titleView);
        containerNode.getChildren().add(contentNode);
        containerNode.setPadding(new Insets(10.0));
        
        
        titleView.setFont(Font.font("TimesNewRoman",18));
                
        containerNode.setOnDragDetected(new EventHandler<MouseEvent>(){
             public void handle(MouseEvent event)
             {
                
                 Dragboard db =containerNode.startDragAndDrop(TransferMode.MOVE);                
                 ClipboardContent cc =new ClipboardContent();    
                 
                 GlobalVaribles.mini=MinimalSessionView.this;
                 
                 cc.putString(Integer.toString(id));
                 db.setContent(cc);
                 System.out.println(id);
              //   event.consume();

             }
         });
        
        containerNode.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                if (event.getTransferMode() == TransferMode.MOVE) {
                 GlobalVaribles.destMini=MinimalSessionView.this;
                }
               // event.consume();
            }
        });
        containerNode.setOnDragEntered(new EventHandler<DragEvent>()              
                {
                    @Override
                    public void handle(DragEvent arg0) {
                         System.out.println(123);
                    }}
                );                 
    }
    
    public VBox getContainerNode() {
        return containerNode;
    }
    
    public int getId(){
        return this.id;
    }
    
}
