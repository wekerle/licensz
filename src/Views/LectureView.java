/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.Enums;
import Helpers.StringHelper;
import Listener.TextChangeEventListener;
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
public class LectureView{
    private TextEditor titleView=new TextEditor();
    private TextEditor authorView=new TextEditor();
    private VBox container=new VBox();
    private int id;
    private StringHelper stringHelper=new StringHelper();
    private TextChangeEventListener textChangeEvent;

    public void setTextChange(TextChangeEventListener textChange) {
        this.textChangeEvent = textChange;
    }
    
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
         
         titleView.setText(title);
         authorView.setText(stringHelper.createListSeparateComma(authors));
         
         titleView.setTextChangeEventListener(new TextChangeEventListener() {

             @Override
             public void modifyText(Enums.TextType type, Enums.TextCategory category, int id, String newValue) {
                 if(type==Enums.TextType.NOTHING)
                 {
                     type=Enums.TextType.TITLE;
                 }
                 
                 if(category==Enums.TextCategory.NOTHING)
                 {
                     category=Enums.TextCategory.LECTURE;
                 }
                 
                 if(id==0)
                 {
                     id=LectureView.this.id;
                 }
                 LectureView.this.textChangeEvent.modifyText(type, category, id, newValue);
             }
         });
         
         authorView.setTextChangeEventListener(new TextChangeEventListener() {

             @Override
             public void modifyText(Enums.TextType type, Enums.TextCategory category, int id, String newValue) {
                 if(type==Enums.TextType.NOTHING)
                 {
                     type=Enums.TextType.AUTHORS;
                 }
                 
                 if(category==Enums.TextCategory.NOTHING)
                 {
                     category=Enums.TextCategory.LECTURE;
                 }
                 
                 if(id==0)
                 {
                     id=LectureView.this.id;
                 }
                 LectureView.this.textChangeEvent.modifyText(type, category, id, newValue);
             }
         });
         
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
                                  
                 cc.putString(Integer.toString(id));
                 db.setContent(cc);
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
