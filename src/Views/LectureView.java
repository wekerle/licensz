/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.LectureWithDetailsModel;
import java.util.ArrayList;
import java.util.StringJoiner;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import licentav2.GlobalVaribles;
import licentav2.TextChangeObserver;

/**
 *
 * @author Ronaldo
 */
public class LectureView {
    private TextEditor titleView=new TextEditor();
    private TextEditor authorView=new TextEditor();
    private VBox container=new VBox();
    private int id;
    private LectureWithDetailsModel model;
    
    public int getLectureNumber() {
        return id;
    }
    // ez fugveny 2szer van...... meg kell kerdezzem petert
    private String createListSeparateComma(ArrayList<String> authors)
    {
        //ez csak java 8 -al megy
        StringJoiner stringJoiner =new StringJoiner(", ");
        
        for(String author : authors)
        {
            stringJoiner.add(author);
        }
        return stringJoiner.toString();
    }

    public VBox getNode() {
        return container;
    }
     
     public LectureView(LectureWithDetailsModel model)
     {
         this.model=model;
         this.id=model.getId();
         GlobalVaribles.addElementToDragLectureAndNumberMap(model.getId(), this);
         GlobalVaribles.setLectureNumber(id+1);                
         
         titleView.setText(model.getTitle());
         authorView.setText(createListSeparateComma(model.getAuthors()));
         
         container.getChildren().add(titleView);
         container.getChildren().add(authorView);
         
         titleView.setFont(new Font(16));
         container.setPadding(new Insets(10.0));
                  
         titleView.setTextChangeObserver(new TextChangeObserver(){

             @Override
             public void notifyTextChange() {
                 LectureView.this.model.setTitle(LectureView.this.titleView.getText());              
             }
         });
         
         authorView.setTextChangeObserver(new TextChangeObserver(){
             
             @Override
             public void notifyTextChange() {
                 String text=LectureView.this.authorView.getText();
                 if(text.contains("www")){
                     //ez az error
                     LectureView.this.authorView.setText(LectureView.this.model.getAuthors().toString());
                     Alert alert=new Alert(AlertType.ERROR, "Huje vagy");
                     alert.showAndWait();
                 } else {
                     // ok
                     ArrayList<String> test=new ArrayList<String>();
                     test.add("sanyika");
                     test.add("tibisor");
                     test.add("malac");
                    LectureView.this.model.setAuthors(test);
                 }              
             }
         });
         
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
