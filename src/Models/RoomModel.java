/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import licenta.IdGenerator;

/**
 *
 * @author Ronaldo
 */
public class RoomModel implements Serializable
{
    private int id;
    private String name;

    public RoomModel(String name) 
    {
        this.name = name;
        id=IdGenerator.getNewId();
    }
    
    public int getId() 
    {
        return id;
    }

    public String getName() 
    {
        return name;
    }
    
}
