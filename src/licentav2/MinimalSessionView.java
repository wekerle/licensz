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
    
    public MinimalSessionView(String title)
    {
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
                  // ki kell cserelni valami normlisabbra az 1est                
                 cc.putString(Integer.toString(1));
                 GlobalVaribles.dragMinimalSessionView=MinimalSessionView.this;
                 db.setContent(cc);
              //   event.consume();

             }
         });
        
        containerNode.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                if (event.getTransferMode() == TransferMode.MOVE) {
                  GlobalVaribles.dragMinimalSessionView=null;
                }
               // event.consume();
            }
        });
        
    }
    
    public VBox getContainerNode() {
        return containerNode;
    }
    
}
