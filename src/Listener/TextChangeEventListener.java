/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

import Helpers.Enums.TextCategory;
import Helpers.Enums.TextType;

/**
 *
 * @author tibor.wekerle
 */
public interface TextChangeEventListener 
{
    void modifyText(TextType type,TextCategory category,int id,String newValue);
}
