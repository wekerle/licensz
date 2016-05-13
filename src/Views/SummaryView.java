/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Adaptor.Converter;
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

/**
 *
 * @author Ronaldo
 */
public class SummaryView extends ScrollPane {
        
    private double scrollDirection = 0;
    private VBox verticalLayout =  new VBox();
    private AplicationModel am=null;
    private  Timeline scrolltimeline = new Timeline();
    
    private void dragScroll() {
        
            ScrollBar scrollBar = getVerticalScrollbar();
            if (scrollBar != null) {
                double newValue = scrollBar.getValue() + scrollDirection;
                newValue = Math.min(newValue, 1.0);
                newValue = Math.max(newValue, 0.0);
                scrollBar.setValue(newValue);
            }
        }
     private ScrollBar getVerticalScrollbar() {
            ScrollBar result = null;
            for (Node n : this.lookupAll(".scroll-bar")) {
                if (n instanceof ScrollBar) {
                    ScrollBar bar = (ScrollBar) n;
                    if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                        result = bar;
                    }
                }
            }        
            return result;
        }
     
    private void SetupView()
    {
        
        Converter c=new Converter();
        
        scrolltimeline.setCycleCount(Timeline.INDEFINITE);
        scrolltimeline.getKeyFrames().add(new KeyFrame(Duration.millis(20), "Scoll", (ActionEvent) -> { dragScroll();}));
        this.setOnDragExited(event -> {
            if (event.getY() > 0) {
                
               // scrollDirection = 1.0 / tree.getExpandedItemCount();
                //el kell oszam a magassagal az oszesnek
                 scrollDirection = 0.01;
            }
            else {
                scrollDirection = -0.01;
            }
            scrolltimeline.play();
        });
        this.setOnDragEntered(event -> {
            scrolltimeline.stop();
        });
        this.setOnDragDone(event -> {
            scrolltimeline.stop();
        });
 
        this.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) { 
                if(event.getSceneX()<SummaryView.this.getHeight()*0.05)
                {
                    SummaryView.this.setVvalue(SummaryView.this.getVvalue());
                } else if(event.getSceneX()>SummaryView.this.getHeight()*0.95)
                {
                    
                }
               // event.consume();
            }
        });
        
         ArrayList<TopicView> pwl= c.topicListToTopicViewList(am.getTopics());
        
        for(TopicView pw : pwl)
        {
            verticalLayout.getChildren().add(pw.getContainerNode());
        }
        
    }
    public SummaryView(AplicationModel am)
    {
        this.am=am;
        SetupView();
        this.setContent(verticalLayout);
    }
    
}
