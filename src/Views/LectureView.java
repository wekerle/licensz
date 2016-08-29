/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.Enums;
import Helpers.StringHelper;
import Listener.TextChangeEventListener;
import Models.LectureModel;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ronaldo
 */
public class LectureView
{
    private TextEditor titleView=new TextEditor();
    private TextEditor authorView=new TextEditor();
    private VBox container=new VBox();
    private int id;
    private LectureModel lectureModel;
    
    public int getLectureNumber() 
    {
        return id;
    }

    public VBox getNode() 
    {
        return container;
    }
     
     public LectureView(LectureModel lecture)
     {
         this.lectureModel=lecture;
         this.id=lectureModel.getId();        
         SummaryView.dragLectureAndNumberMap.put(id, this);              
         
         titleView.setText(lectureModel.getTitle());
         authorView.setText(StringHelper.createListSeparateComma(lectureModel.getAuthors()));
         
         titleView.setTextChangeEventListener(new TextChangeEventListener() 
         {
             @Override
             public void modifyText(String newValue) {
                lectureModel.setTitle(newValue);
             }
         });
         
         authorView.setTextChangeEventListener(new TextChangeEventListener() 
         {
             @Override
             public void modifyText(String newValue) 
             {
                ArrayList<String> authors=StringHelper.createArralyListFromListSeparateComma(newValue);
                lectureModel.setAuthors(authors);
             }
         });
         
         container.getChildren().add(titleView);
         container.getChildren().add(authorView);
         
         titleView.setFont(StringHelper.font16);
         container.setPadding(new Insets(10.0));
                           
         container.setOnDragDetected(new EventHandler<MouseEvent>()
         {
             public void handle(MouseEvent event)
             {
                if(!SummaryView.isSelected(LectureView.this))
                {
                   SummaryView.removeAllSelected();
                }
                Dragboard db =container.startDragAndDrop(TransferMode.MOVE);

                ClipboardContent cc =new ClipboardContent();

                cc.putString(Integer.toString(id));
                db.setContent(cc);
             }
         });
                  
        container.setOnMouseClicked(new EventHandler<MouseEvent>()
        {          
            @Override
            public void handle(MouseEvent event) {
                if(event.isControlDown())
                {
                    SummaryView.addSelected(LectureView.this);
                }
                else
                {
                    SummaryView.removeAllSelected();
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
