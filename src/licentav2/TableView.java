/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

import Adaptor.Converter;
import Models.AplicationModel;
import Models.Part;
import Models.Session;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ronaldo
 */
public class TableView extends GridPane{
    private AplicationModel am=null;
    
    public TableView(AplicationModel am)
    {
        super();
        this.am=am;
        populateContent(am.getParts());
    }
    
    private void populateContent(ArrayList<Part> parts)
    {
        int colNum=1;
        int maxRow=0;
        Converter c=new Converter();
        for(Part p : parts)
        {
            int rowNum=1;
            if(maxRow<p.getSessions().size())
            {
                maxRow=p.getSessions().size();
            }
            for(Session s : p.getSessions())
            {
                MinimalSessionView sw=c.sessionToMinimalSessionView(s);
                this.add(sw.getContainerNode(),colNum,rowNum);
                rowNum++;
            }
            colNum++;
        }
        
        for(int i=0;i<parts.size();i++)
        {
            this.add(new TextEditor("Sala"+i), i+1, 0);
        }
        
         for(int i=0;i<maxRow; i++)
        {
            this.add(new TextEditor("10:00-10:00"), 0, i+1);
        }
    }
}
