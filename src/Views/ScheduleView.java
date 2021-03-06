/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import ViewModels.TableView;
import Adaptor.Converter;
import DataManagment.DataManager;
import Helpers.Enums;
import Listener.SessionDragEventListener;
import Models.AplicationModel;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author tibor.wekerle
 */
public class ScheduleView extends ScrollPane implements SessionDragEventListener
{  
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
            if (n instanceof ScrollBar) {
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
        Converter c=new Converter();       
        verticalLayout.getChildren().clear();
        
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

        this.setOnDragDone(event -> {
            scrolltimeline.stop();
            SetupView();
        });
        
        ArrayList<TableView> tableViewList= c.dayModelListToTableViewList(aplicationModel.getDays(),aplicationModel.getConstraints(),this);
                
        for(TableView tableview : tableViewList)
        {                       
            verticalLayout.getChildren().add(tableview);
        }                   
    }
    
    public ScheduleView(AplicationModel model)
    {
        this.aplicationModel=model;
        this.dataManager=new DataManager(aplicationModel);
        SetupView();
        this.setContent(verticalLayout);       
    }

    @Override
    public void notifyDataManager(int destinationSessionId, int sourceSessionId, Enums.Position position) {              
        if(position==Enums.Position.AFTER)
        {            
            this.dataManager.moveDestinationSessionAfterSourceSession(destinationSessionId,sourceSessionId);
        }else
        {
            this.dataManager.moveDestinationSessionBeforeSourceSession(destinationSessionId,sourceSessionId);
        }    
    }
}
