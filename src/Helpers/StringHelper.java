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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;

/**
 *
 * @author tibor.wekerle
 */
 public final class  StringHelper 
 {  
    static public final String datePattern = "yyyy-MMM-dd";
    static public final String timePattern = "HH:mm";
    static public final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
    static public final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timePattern);
    static String currentDate=LocalDate.now().format(dateFormatter);
    
    static public final Font font16Bold=Font.font("TimesNewRoman",FontWeight.BOLD,16); 
    static public final Font font16=Font.font("TimesNewRoman",16);
    static public final Font font18=Font.font("TimesNewRoman",18);
    static public final Font font18Bold=Font.font("TimesNewRoman",FontWeight.BOLD,18);
    static public final Font font20Bold=Font.font("TimesNewRoman",FontWeight.BOLD,20);
    static public final Font font22Bold=Font.font("TimesNewRoman",FontWeight.BOLD,22);
    static public final Font font32Bold=Font.font("TimesNewRoman",FontWeight.BOLD,32);
    
    public static  String createListSeparateComma(ArrayList<String> authors)
    {
        //works only with java 8 or higher
        StringJoiner stringJoiner =new StringJoiner(", ");
        
        for(String author : authors)
        {
            stringJoiner.add(author);
        }
        return stringJoiner.toString();
    }
    
    public static ArrayList<String> createArralyListFromListSeparateComma(String names)
    {
        ArrayList<String> result =new ArrayList<String>();       
        String[] namesArray=names.split(",");
        
        for(String author:namesArray)
        {
            result.add(author.trim());
        }
        return result;
    }
    
    public static StringConverter getConverter()
    {
        StringConverter converter = new StringConverter<LocalDate>()
        {
                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                }
                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) 
                    {
                     return LocalDate.parse(string, dateFormatter);                              
                    }else 
                    {
                        return null;
                    }
                }
        }; 
        return converter;
    }
}
