/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import Adaptor.Converter;
import DataProcessing.DataCollector;
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
public class MainView extends ScrollPane {
        
    private double scrollDirection = 0;
    private VBox verticalLayout =  new VBox();
    private AplicationModel am=null;
    private static Timeline scrolltimeline = new Timeline();
    
    private void dragScroll() {
        
            ScrollBar sb = getVerticalScrollbar();
            if (sb != null) {
                double newValue = sb.getValue() + scrollDirection;
                newValue = Math.min(newValue, 1.0);
                newValue = Math.max(newValue, 0.0);
                sb.setValue(newValue);
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
                //VBox mainVb=new VBox();
        //ScrollPane sp=new ScrollPane();
        
        Converter c=new Converter();
        
        scrolltimeline.setCycleCount(Timeline.INDEFINITE);
       // GlobalVaribles.getScrollTimeline().setCycleCount(10);
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
       // this.setOnDragDropped(event -> {
        //    GlobalVaribles.stopScrollTimeline();
       // });
 
        this.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) { 
                System.out.println(MainView.this.getVvalue());
                System.out.println(MainView.this.getHeight());
                System.out.println(event.getSceneX());
                if(event.getSceneX()<MainView.this.getHeight()*0.05)
                {
                    MainView.this.setVvalue(MainView.this.getVvalue());
                     System.out.println(MainView.this.getVvalue());
                } else if(event.getSceneX()>MainView.this.getHeight()*0.95)
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
        
       // ArrayList<SessionView> swl= c.sessionListToSessionViewList(am.getSessions());
        
        //for(SessionView sw : swl)
       // {
       //     verticalLayout.getChildren().add(sw.getContainerNode());
       // }
    }
    public MainView(AplicationModel am)
    {
        this.am=am;
        SetupView();
        this.setContent(verticalLayout);
    }
    
}
