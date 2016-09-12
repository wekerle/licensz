/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import ViewModels.TopicView;
import ViewModels.LectureView;
import Adaptor.Converter;
import DataManagment.DataManager;
import Models.AplicationModel;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import Listener.LectureDragEventListener;
import java.util.HashMap;

/**
 *
 * @author Ronaldo
 */
public class SummaryView extends ScrollPane implements LectureDragEventListener
{    
    // <editor-fold desc="private region" defaultstate="collapsed">    
    private double scrollDirection = 0;
    private VBox verticalLayout =  new VBox();
    private AplicationModel aplicationModel=null;
    private Timeline scrolltimeline = new Timeline();
    private DataManager dataManager;
           
    private void dragScroll() 
    {        
        ScrollBar scrollBar = getVerticalScrollbar();
        if (scrollBar != null) {
            double newValue = scrollBar.getValue() + scrollDirection;
            newValue = Math.min(newValue, 1.0);
            newValue = Math.max(newValue, 0.0);
            scrollBar.setValue(newValue);
        }
    }
     private ScrollBar getVerticalScrollbar() 
     {
        ScrollBar result = null;
        for (Node n : this.lookupAll(".scroll-bar")) 
        {
            if (n instanceof ScrollBar) 
            {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) 
                {
                    result = bar;
                }
            }
        }        
        return result;
    }
     
    private void SetupView()
    {
        
        Converter converter=new Converter();
        
        scrolltimeline.setCycleCount(Timeline.INDEFINITE);
        scrolltimeline.getKeyFrames().add(new KeyFrame(Duration.millis(20), "Scoll", (ActionEvent) -> { dragScroll();}));
        this.setOnDragExited(event -> 
        {
            if (event.getY() > 0) 
            {
                
               // scrollDirection = 1.0 / tree.getExpandedItemCount();
                //el kell oszam a magassagal az oszesnek
                 scrollDirection = 0.01;
            }
            else 
            {
                scrollDirection = -0.01;
            }
            scrolltimeline.play();
        });
        this.setOnDragEntered(event -> 
        {
            scrolltimeline.stop();
        });
        this.setOnDragDone(event -> 
        {
            scrolltimeline.stop();
        });
 
        this.setOnMouseMoved(new EventHandler<MouseEvent>() 
        {
            public void handle(MouseEvent event) { 
                if(event.getSceneX()<SummaryView.this.getHeight()*0.05)
                {
                    SummaryView.this.setVvalue(SummaryView.this.getVvalue());
                } else if(event.getSceneX()>SummaryView.this.getHeight()*0.95)
                {
                    
                }
            }
        });
        
         ArrayList<TopicView> topicViewList= converter.topicListToTopicViewList(aplicationModel.getTopics(),this);
        
        for(TopicView topicView : topicViewList)
        {
            verticalLayout.getChildren().add(topicView.getContainerNode());
        }
        
    }
         //</editor-fold>
    
    public SummaryView(AplicationModel model)
    {
        this.aplicationModel=model;
        this.dataManager=new DataManager(aplicationModel);
        SetupView();
        this.setContent(verticalLayout);
    }

    @Override
    public void notify(int sessionId, int lectureId) 
    {       
        this.dataManager.moveLectureToSession(sessionId, lectureId);
    }   
}
