/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProcessing;

import Helpers.Enums.ParserState;
import Models.TermModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;


/**
 *
 * @author Ronaldo
 */
public class TermReaderFromFile
{
          
    public HashMap<String,TermModel> readTermsFromFile(File file) 
    {
        
        HashMap<String,TermModel> map=new HashMap<String,TermModel>();
        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            String line = bufferReader.readLine();
            
            TermModel term=null;
            TermModel context=null;
            ParserState flag=ParserState.NONE; 
            String name;
            while (line != null) { 
                
                if(line.startsWith(" USE: "))
                {   
                    name=line.substring(5).trim().toLowerCase();
                    flag=ParserState.USE;
                    
                }else if(line.startsWith(" BT: "))
                {
                    name=line.substring(4).trim().toLowerCase();
                    flag=ParserState.BT;
                    
                }else if(line.startsWith(" RT: "))
                {
                    name=line.substring(4).trim().toLowerCase();
                    flag=ParserState.RT;
                }else if(line.startsWith(" UF: "))
                {
                    name=line.substring(4).trim().toLowerCase();
                    flag=ParserState.UF;
                }else if(line.startsWith(" NT: "))
                {
                    name=line.substring(4).trim().toLowerCase();
                    flag=ParserState.NT;
                }else if(line.startsWith(" "))
                {
                    name=line.trim().toLowerCase();
                }else
                {
                    name=line.trim().toLowerCase();
                    flag=ParserState.NONE;
                }
                
                if(map.containsKey(name))
                {
                    term=map.get(name);
                }
                else
                {
                    term=new TermModel(name);
                    map.put(name, term);
                }
                switch(flag){
                    case NONE:
                        context=term;                       
                        break;
                    case BT:
                        context.setBroaderTerm(term);
                        break;
                    case NT:
                        context.addNarrowerTerm(term);
                        break;
                    case RT:
                        context.addRelatedTerms(term);
                        break;
                    case UF:
                        context.addUseFor(term);
                        break;
                    case USE:
                        context.setUSE(term);
                        break;
                }    
                 line = bufferReader.readLine();
            }
        } catch(Exception e) {

            }
        return map;
       
    }
}
