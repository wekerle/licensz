/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProcessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;


/**
 *
 * @author Ronaldo
 */
public class TeacherAffiliationReaderFromFile
{
          
    public HashMap<String,String> readTeacherAffiliationFromFile(File file) 
    {
        
        HashMap<String,String> map=new HashMap<String,String>();
        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            String line = bufferReader.readLine();
            
            String name,affiliation;
            while (line != null) { 
                
                String[] splitString=line.split("\t");
                name=splitString[0];
                affiliation=splitString[1];
                
                map.put(name, affiliation);
                line = bufferReader.readLine();
            }
        } catch(Exception e) {

            }
        return map;
       
    }
}
