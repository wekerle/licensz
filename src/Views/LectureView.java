/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Views.TextEditor;
import java.util.ArrayList;
import java.util.StringJoiner;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import licentav2.GlobalVaribles;

/**
 *
 * @author Ronaldo
 */
public class LectureView {
    private TextEditor titleView=new TextEditor();
    private TextEditor authorView=new TextEditor();
    private VBox container=new VBox();
    private int lectureNumber;

    public int getLectureNumber() {
        return lectureNumber;
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
     
     public LectureView(String title,ArrayList<String> authors)
     {
         lectureNumber=GlobalVaribles.getLectureNumber();
         GlobalVaribles.addElementToDragLectureAndNumberMap(lectureNumber, this);
         GlobalVaribles.setLectureNumber(GlobalVaribles.getLectureNumber()+1);                
         
         titleView.setText(title);
         authorView.setText(createListSeparateComma(authors));
         
         container.getChildren().add(titleView);
         container.getChildren().add(authorView);
         
         titleView.setFont(new Font(16));
         container.setPadding(new Insets(10.0));
                  
         container.setOnDragDetected(new EventHandler<MouseEvent>(){
             public void handle(MouseEvent event)
             {
                 if(!GlobalVaribles.isSelected(LectureView.this))
                 {
                      GlobalVaribles.removeAllSelected();
                 }
                 Dragboard db =container.startDragAndDrop(TransferMode.MOVE);
                 
                 ClipboardContent cc =new ClipboardContent();
                                  
                 cc.putString(Integer.toString(lectureNumber));
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
      if(dl.getLectureNumber()==this.lectureNumber)
      {
          return true;
      }
      return false;
    }
}
