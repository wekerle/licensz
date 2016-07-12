/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringJoiner;

/**
 *
 * @author tibor.wekerle
 */
public class StringHelper {
    
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
    private String currentDate=LocalDate.now().format(dateFormatter);
    
    public String createListSeparateComma(ArrayList<String> authors)
    {
        //ez csak java 8 -al megy
        StringJoiner stringJoiner =new StringJoiner(", ");
        
        for(String author : authors)
        {
            stringJoiner.add(author);
        }
        return stringJoiner.toString();
    }
    
    public ArrayList<String> createArralyListFromListSeparateComma(String names)
    {
        ArrayList<String> result =new ArrayList<String>();       
        String[] temp=names.split(",");
        
        for(String author:temp)
        {
            result.add(author);
        }
        return result;
    }
    
    public String getCurrentdate()
    {
         return currentDate;
    }
   
}
