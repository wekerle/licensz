/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import DataProcessing.DataCollector;
import Models.AplicationModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author Ronaldo
 */
public class Licentav2 extends Application {
    
    
    
    @Override
    public void start(Stage primaryStage) {
        
        DataCollector dc= new DataCollector();
        
        AplicationModel am=new AplicationModel();

        am.setParts(dc.getParts());
        MainView mw=new MainView(am);
        
        TableView tw=new TableView(am);
                         
        //Scene scene = new Scene(mw, 800, 600);
        
        Scene scene = new Scene(mw, 800, 600);
        
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
    
}
