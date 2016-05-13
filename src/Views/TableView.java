/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Adaptor.Converter;
import DataManagment.DataManager;
import Models.AplicationModel;
import Models.TopicModel;
import Models.SessionModel;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Ronaldo
 */
public class TableView extends GridPane{
    private AplicationModel aplicationModel=null;
    private DataManager dataManager=new DataManager();

    public AplicationModel getAplicationModel() {
        return aplicationModel;
    }
         
    public TableView(AplicationModel aplicationModel)
    {
        super();
        this.aplicationModel=aplicationModel;
        populateContent(aplicationModel.getTopics());             
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
                TableCellView tableCellView =new TableCellView(this,i,j);
                this.add(tableCellView, i, j);
                matrix[i][j]=tableCellView;
            }       
        }
        
        Converter converter=new Converter();
        getChildren().clear();
        
        int colNum=1;
        int rowNum=1; 
        for(TopicModel p : topics)
        {
            
            colNum=1;
            for(SessionModel s : p.getSessions())
            {
                MinimalSessionView sw=converter.sessionToMinimalSessionView(s);
                
                TableCellView tableCellView=matrix[colNum][rowNum];               
                tableCellView.setMinimalSessionView(sw);
                
                this.add(tableCellView,rowNum,colNum);
                colNum++;
            }
            rowNum++;
        }
        
        for(int i=0;i<topics.size();i++)
        {
             TextEditor textEditor=new TextEditor("Sala"+i);
             
             TableCellView tableCellView=matrix[0][i+1];
             tableCellView.setContentNode(textEditor);
             
            // textEditor.getStyleClass().add("tableCellSala");
             textEditor.setAlignment(Pos.CENTER);
             textEditor.setStyle("-fx-text-fill:white;");
             //tableCellView.getStyleClass().add("tableCellSala");
             
            this.add(tableCellView, i+1, 0);
        }
        
         for(int i=0;i<maxRow; i++)
        {
            TextEditor textEditor=new TextEditor("10:00-10:00");
            TableCellView tableCellView=matrix[i+1][0];
            
          //  textEditor.setStyle("-fx-text-fill:red");
            tableCellView.setContentNode(textEditor);

            tableCellView.setAlignment(Pos.CENTER);
          //  tableCellView.setStyle("-fx-background-color: black");
            //textEditor.setStyle("-fx-text-fill: ladder(background, white 49%, black 50%)");
            
            this.add(tableCellView,0,i+1 );
        }
    }

}
