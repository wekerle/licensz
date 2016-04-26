/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import Adaptor.Converter;
import DataManagment.DataManager;
import Models.AplicationModel;
import Models.Topic;
import Models.Session;
import java.util.ArrayList;
import static javafx.application.ConditionalFeature.FXML;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ronaldo
 */
public class TableView extends GridPane{
    private AplicationModel am=null;
    private DataManager dm=new DataManager();
    
    public TableView(AplicationModel am)
    {
        super();
        this.am=am;
        populateContent(am.getTopics());
        
        this.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {               
                if (event.getGestureSource() != this &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }

              //  event.consume();
            }
        });
        
        this.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            /* the drag-and-drop gesture entered the target */
            /* show to the user that it is an actual gesture target */
                 if (event.getGestureSource() != this &&
                         event.getDragboard().hasString()) {
                    // contentNode.setStyle("-fx-background-color:white");
                    
                    System.out.println(1);
                 }

                // event.consume();
            }
        });
        
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {                 
                    //ez csak teszt
                    //System.out.println(db.getString());
                    DataManager dm=new DataManager();
                    
                    Session s1=GlobalVaribles.getSessionByNumber(GlobalVaribles.mini.getId());
                    Session s2=GlobalVaribles.getSessionByNumber(GlobalVaribles.destMini.getId());
                    
                   populateContent(dm.moveSession(am,s2,s1).getTopics());
                   
                   //ez egy masik teszt
                   
                    Integer colIndex = GridPane.getColumnIndex(GlobalVaribles.mini.getContainerNode());
                    Integer rowIndex = GridPane.getRowIndex(GlobalVaribles.mini.getContainerNode());
                    
                    Integer colIndex2 = GridPane.getColumnIndex(GlobalVaribles.destMini.getContainerNode());
                    Integer rowIndex2 = GridPane.getRowIndex(GlobalVaribles.destMini.getContainerNode());
 
                    System.out.printf("Mouse source cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
                    System.out.printf("Mouse source cell [%d, %d]%n", colIndex2.intValue(), rowIndex2.intValue());
                   success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);

              //  event.consume();
             }
        });
        
    }
    
    @FXML
    private void mouseEntered(MouseEvent e) {
        Node source = (Node)e.getSource() ;
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
    }
    
    private void populateContent(ArrayList<Topic> topics)
    {
        int colNum=1;
        int maxRow=0;
        int maxCol=topics.size();
        
        for(Topic p : topics)
        {
            if(maxRow<p.getSessions().size())
            {
                maxRow=p.getSessions().size();
            }
        }
        
        TableCellView[][] matrix=new  TableCellView[maxRow+1][maxCol+1];
        
        for(int i=0;i<maxRow+1;i++)
        {
            for(int j=0; j<maxCol+1;j++)
            {
                TableCellView tcw =new TableCellView(i,j);
                this.add(tcw, i, j);
                matrix[i][j]=tcw;
            }       
        }
        
        Converter c=new Converter();
        getChildren().clear();
        for(Topic p : topics)
        {
            int rowNum=1;           
            for(Session s : p.getSessions())
            {
                MinimalSessionView sw=c.sessionToMinimalSessionView(s);
                
                TableCellView tcw=matrix[rowNum][colNum];
                
                tcw.getChildren().add(sw.getContainerNode());
                
                this.add(tcw,rowNum,colNum);
                colNum++;
            }
            rowNum++;
        }
        
        for(int i=0;i<topics.size();i++)
        {
             TextEditor te=new TextEditor("Sala"+i);
             
             TableCellView tcw=matrix[0][i];
             tcw.getChildren().add(te);
             
            this.add(tcw, i+1, 0);
        }
        
         for(int i=0;i<maxRow; i++)
        {
            TextEditor te=new TextEditor("10:00-10:00");
            TableCellView tcw=matrix[i][0];
             tcw.getChildren().add(te);
            this.add(tcw, 0, i+1);
        }
    }

}
