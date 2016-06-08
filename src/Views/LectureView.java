/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.StringHelper;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import licentav2.GlobalVaribles;

/**
 *
 * @author Ronaldo
 */
public class LectureView {
    private TextEditor titleView=new TextEditor();
    private TextEditor authorView=new TextEditor();
    private VBox container=new VBox();
    private int id;
    private StringHelper stringHelper=new StringHelper();
    
    public int getLectureNumber() {
        return id;
    }

    public VBox getNode() {
        return container;
    }
     
     public LectureView(String title,ArrayList<String> authors,int id)
     {
         this.id=id;
         GlobalVaribles.addElementToDragLectureAndNumberMap(id, this);
         GlobalVaribles.setLectureNumber(id+1);                
         
         titleView.setText(title);
         authorView.setText(stringHelper.createListSeparateComma(authors));
         
         container.getChildren().add(titleView);
         container.getChildren().add(authorView);
         
         titleView.setFont(new Font(16));
         container.setPadding(new Insets(10.0));
                  
       /*  titleView.setTextChangeObserver(new TextChangeObserver(){

             @Override
             public void notifyTextChange() {
                 LectureView.this.model.setTitle(LectureView.this.titleView.getText());              
             }
         });
         
         authorView.setTextChangeObserver(new TextChangeObserver(){
             
             @Override
             public void notifyTextChange() {
                 String text=LectureView.this.authorView.getText();
                 if(text.contains(";")){
                     LectureView.this.authorView.setText(stringHelper.createListSeparateComma(LectureView.this.model.getAuthors()));
                     Alert alert=new Alert(AlertType.ERROR, "The authors name's must be separate with comma");
                     alert.showAndWait();
                 } else {
                    LectureView.this.model.setAuthors(stringHelper.createArralyListFromListSeparateComma(LectureView.this.authorView.getText()));
                 }              
             }
         });*/
         
         container.setOnDragDetected(new EventHandler<MouseEvent>(){
             public void handle(MouseEvent event)
             {
                 if(!GlobalVaribles.isSelected(LectureView.this))
                 {
                      GlobalVaribles.removeAllSelected();
                 }
                 Dragboard db =container.startDragAndDrop(TransferMode.MOVE);
                 
                 ClipboardContent cc =new ClipboardContent();
                                  
                 cc.putString(Integer.toString(id));
                 db.setContent(cc);
              //   event.consume();

             }
         });
                  
         container.setOnMouseClicked(new EventHandler<MouseEvent>() {          
             @Override
             public void handle(MouseEvent event) {
                 if(event.isControlDown())
                 {
                     GlobalVaribles.addSelected(LectureView.this);
                 }
                 else
                 {
                     GlobalVaribles.removeAllSelected();
                 }
             }
        });
         
     }
     
    public boolean isEqual(LectureView dl){
      if(dl.getLectureNumber()==this.id)
      {
          return true;
      }
      return false;
    }
}
