/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProcessing;

import Models.TermModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;


/**
 *
 * @author Ronaldo
 */
public class TermReaderFromFile{
          
    enum ParserState{
        NONE,
        USE,
        BT,
        RT,
        UF,
        NT
        
    }
    public HashMap<String,TermModel> readTermsFromFile(File file) {
        
        HashMap<String,TermModel> hm=new HashMap<String,TermModel>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            
            TermModel term=null;
            TermModel context=null;
            ParserState flag=ParserState.NONE; 
            String name;
            while (line != null) { 
                
                if(line.startsWith(" USE: "))
                {   
                    name=line.substring(5).trim();
                    flag=ParserState.USE;
                    
                }else if(line.startsWith(" BT: "))
                {
                    name=line.substring(4).trim();
                    flag=ParserState.BT;
                    
                }else if(line.startsWith(" RT: "))
                {
                    name=line.substring(4).trim();
                    flag=ParserState.RT;
                }else if(line.startsWith(" UF: "))
                {
                    name=line.substring(4).trim();
                    flag=ParserState.UF;
                }else if(line.startsWith(" NT: "))
                {
                    name=line.substring(4).trim();
                    flag=ParserState.NT;
                }else if(line.startsWith(" "))
                {
                    name=line.trim();
                }else
                {
                    name=line.trim();
                    flag=ParserState.NONE;
                }
                
                if(hm.containsKey(name))
                {
                    term=hm.get(name);
                }
                else
                {
                    term=new TermModel(name);
                    hm.put(name, term);
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
                 line = br.readLine();
            }
        } catch(Exception e) {

            }
        return hm;
       
    }
}
