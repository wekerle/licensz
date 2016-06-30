/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Adaptor.Converter;
import DataManagment.DataManager;
import Helpers.Enums;
import Helpers.StringHelper;
import Listener.SessionDragEventListener;
import Models.AplicationModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.util.Timer;

/**
 *
 * @author tibor.wekerle
 */
public class ScheduleView extends ScrollPane implements SessionDragEventListener{
    
    private double scrollDirection = 0;
    private VBox verticalLayout =  new VBox();
    private AplicationModel aplicationModel=null;
    private Timeline scrolltimeline = new Timeline();
    private DataManager dataManager;
    private StringHelper stringHelper=new StringHelper();
    
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
           
       // verticalLayout.getChildren().clear();
        ArrayList<TableView> tableViewList= new ArrayList<TableView>();
        
        TableView tableView1=new TableView(aplicationModel.getTopics(),this);
        TableView tableView2=new TableView(aplicationModel.getTopics(),this);
        TableView tableView3=new TableView(aplicationModel.getTopics(),this);
                
        tableViewList.add(tableView1);
        tableViewList.add(tableView2);
        tableViewList.add(tableView3);
                
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
        });
    }
    
    public void addMinimalSessionViewToTable(MinimalSessionView session,int destinationSessionId, Enums.Position position)
    {
        for (Node node : verticalLayout.getChildren())
        {
          TableView tableView=(TableView)node;
          tableView.addMinimalSessionViewToTableCell(session, destinationSessionId,position);        
        }
    }
    
    public MinimalSessionView cutMinimalSessionViewFromTable(int sessionId)
    {
        for (Node node : verticalLayout.getChildren())
        {
          TableView tableView=(TableView)node;
          MinimalSessionView result=tableView.cutMinimalSessionViewFromTableCell(sessionId); 
          if(result!=null)
          {
                return result;  
          }
        }
        return null;
    }

    @Override
    public void notifyDataManager(int destinationSessionId, int sourceSessionId, Enums.Position position) {      
        scrolltimeline.stop();
        double verticalScroll=this.getVvalue();
        
        if(position==Enums.Position.AFTER)
        {            
            this.dataManager.moveDestinationSessionAfterSourceSession(destinationSessionId,sourceSessionId);
        }else
        {
            this.dataManager.moveDestinationSessionBeforeSourceSession(destinationSessionId,sourceSessionId);
        }    
        
        this.setVvalue(verticalScroll);
        
       // ActionListener action=new ActionListener(){

           // @Override
           // public void actionPerformed(ActionEvent e) {
             //    SetupView();
           // }
   // };
        //Timer t=new Timer(10,action);
        //t.setRepeats(false);
        //t.start();
    }

}
