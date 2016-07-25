/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Adaptor.Converter;
import DataManagment.DataManager;
import Helpers.Enums;
import Listener.SessionDragEventListener;
import Models.AplicationModel;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
             
        ArrayList<TableView> tableViewList= c.dayModelListToTableViewList(aplicationModel.getDays(),null);
                
        for(TableView tableview : tableViewList)
        {                       
            verticalLayout.getChildren().add(tableview);
        }
        // this is only a hardcode, and it is used only for test
        ArrayList<TableView> tableViewList2= c.dayModelListToTableViewList(aplicationModel.getDays(),null);
        for(TableView tableview : tableViewList2)
        {                       
            verticalLayout.getChildren().add(tableview);
        }
        ArrayList<TableView> tableViewList3= c.dayModelListToTableViewList(aplicationModel.getDays(),null);
        for(TableView tableview : tableViewList3)
        {                       
            verticalLayout.getChildren().add(tableview);
        }
        
       // getClas();
        System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
        
        /* Image img1 = new Image(System.getProperty("user.dir")+"/src/about.png");
        // Image img2 = new Image(getClass().getResourceAsStream("close.png"));
         
         HBox hb = new HBox();
                hb.setPadding(new Insets(15, 15, 15, 12));
                hb.setSpacing(10);
         // Adding buttons and images to buttons with no text on buttons
                Button button1 = new Button("", new ImageView(img1));
                hb.getChildren().add(button1);
               // Button button2 = new Button("", new ImageView(img2));
               // hb.getChildren().add(button2);
                // Adding buttons and images with text on buttons
                Button button3 = new Button("Info Button", new ImageView(img1));
                hb.getChildren().add(button3);
                
                
                verticalLayout.getChildren().add(hb);*/
           
    }
    
    public ScheduleView(AplicationModel model)
    {
        this.aplicationModel=model;
        this.dataManager=new DataManager(aplicationModel);
        SetupView();
        this.setContent(verticalLayout);
        
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
