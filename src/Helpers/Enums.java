/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

/**
 *
 * @author tibor.wekerle
 */
public class Enums {
    public enum ParserState{
        NONE,
        USE,
        BT,
        RT,
        UF,
        NT       
    }
    public enum TextType{
        NOTHING,
        AUTHORS,
        CHAIRS,
        TITLE     
    }
    public enum TextCategory{
        NOTHING,
        TOPIC,
        SESSION,
        LECTURE   
    }
    
    public enum Position{
        BEFORE,
        AFTER 
    }
}
