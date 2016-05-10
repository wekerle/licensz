/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import Adaptor.Converter;
import DataManagment.DataManager;
import Models.AplicationModel;
import Models.TopicModel;
import Models.SessionModel;
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

    public AplicationModel getAplicationModel() {
        return am;
    }
         
    public TableView(AplicationModel am)
    {
        super();
        this.am=am;
        populateContent(am.getTopics());             
    }
    
    public void populateContent(ArrayList<TopicModel> topics)
    {       
        int maxRow=0;
        int maxCol=topics.size();
        
        for(TopicModel p : topics)
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
                TableCellView tcw =new TableCellView(this,i,j);
                this.add(tcw, i, j);
                matrix[i][j]=tcw;
            }       
        }
        
        Converter c=new Converter();
        getChildren().clear();
        
        int colNum=1;
        int rowNum=1; 
        for(TopicModel p : topics)
        {
            
            colNum=1;
            for(SessionModel s : p.getSessions())
            {
                MinimalSessionView sw=c.sessionToMinimalSessionView(s);
                
                TableCellView tcw=matrix[colNum][rowNum];               
                tcw.setMinimalSessionView(sw);
                
                this.add(tcw,rowNum,colNum);
                colNum++;
            }
            rowNum++;
        }
        
        for(int i=0;i<topics.size();i++)
        {
             TextEditor te=new TextEditor("Sala"+i);
             
             TableCellView tcw=matrix[0][i+1];
             tcw.setContentNode(te);
             
            this.add(tcw, i+1, 0);
        }
        
         for(int i=0;i<maxRow; i++)
        {
            TextEditor te=new TextEditor("10:00-10:00");
            TableCellView tcw=matrix[i+1][0];
            tcw.setContentNode(te);
            this.add(tcw,0,i+1 );
        }
    }

}
