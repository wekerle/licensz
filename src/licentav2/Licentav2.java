/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import DataProcessing.DataCollector;
import Models.AplicationModel;
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author Ronaldo
 */
public class Licentav2 extends Application {
    
    BorderPane border = new BorderPane();
    AplicationModel am=new AplicationModel();
    
    @Override
    public void start(Stage primaryStage) {
        
        MenuBar mb=createMenu();
        
        border.setTop(mb);
                  
        border.setCenter(addAnchorPane(addGridPane()));

        Scene scene = new Scene(border);
// Add a style sheet to the scene        
        scene.getStylesheets().add("Styling/layoutstyles.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Layout Sample");
        primaryStage.show();
                 
        DataCollector dc= new DataCollector();
          

        am.setTopics(dc.getTopics());
       // MainView mw=new MainView(am);
        
        //TableView tw=new TableView(am);
                         
        //Scene scene = new Scene(mw, 800, 600);
        
       // Scene scene = new Scene(tw, 800, 600);
        
        
        
        //((VBox) scene.getRoot()).getChildren().addAll(mb);
        
        
        
        
        
        
        
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
       
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    private MenuBar createMenu()
    { 
        MenuBar menuBar = new MenuBar();
 
        // --- Menu File
        Menu menuFile = new Menu("File");
        
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem loadMenuItem = new MenuItem("Load");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        
        menuFile.getItems().addAll(newMenuItem,loadMenuItem, saveMenuItem,
        new SeparatorMenuItem(), exitMenuItem);
    
        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
 
        // --- Menu View
        Menu menuView = new Menu("View");
        MenuItem tableMenuItem = new MenuItem("Time Table");
        MenuItem listMenuItem = new MenuItem("Summary");
        
        tableMenuItem.setOnAction(actionEvent -> clickViewTimeTable());
        listMenuItem.setOnAction(actionEvent -> clickViewSummary());
        
        menuView.getItems().addAll(tableMenuItem,listMenuItem);
 
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
 
        return menuBar;

    }
    
    private void clickViewTimeTable(){
        
        TableView tw=new TableView(am);
         border.setCenter(tw);
    }
    
    private void clickViewSummary(){
        MainView mw=new MainView(am);
         border.setCenter(mw);
    }
    
    private GridPane addGridPane() {

        GridPane grid = new GridPane();   
        grid.getStyleClass().add("grid");

        Text category = new Text("Lets start");
        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(category, 1, 0); 
                
        Text chartSubtitle = new Text("In order to work with this program you need to import datas from txt files or you can continue an earlier saved work.\n"
                + "If you choose to import from txt files, you need to set the path to the folder where are the txt files. The datas in txt\n files should respect "
                + "the following convention:\n-fisrt row:The paper's title\n"
                + "-second row:Authors\n"
                + "-third row:number of pages\n"
                + "-4th row:type(S or F) short or full\n"
                +"-5th row: keywords separated with comma\n"
                +"-6th row: generated keywords separated with comma\n"
                +"-7th row: topic\n"
                +"-8th row: the abstract\n");
        grid.add(chartSubtitle, 1, 1, 2, 1);
        
        return grid;
    }
 
    private AnchorPane addAnchorPane(GridPane grid) {

        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getStyleClass().add("pane");
        
        Button buttonSave = new Button("Save");
        buttonSave.setId("button-custom");
        Button buttonCancel = new Button("Exit");
        buttonCancel.setId("button-custom");

        HBox hb = new HBox();
//        hb.setPadding(new Insets(0, 10, 10, 10));
//        hb.setSpacing(10);
// Use style classes to set properties previously set above (with some changes)        
        hb.getStyleClass().add("hbox");
        hb.setId("hbox-custom");
        hb.getChildren().addAll(buttonSave, buttonCancel);

        anchorpane.getChildren().addAll(grid,hb);
        // Anchor buttons to bottom right, anchor grid to top
        AnchorPane.setBottomAnchor(hb, 8.0);
        AnchorPane.setRightAnchor(hb, 5.0);
        AnchorPane.setTopAnchor(grid, 10.0);

        return anchorpane;
    }
    
}
