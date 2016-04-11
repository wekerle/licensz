/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;

/**
 *
 * @author Ronaldo
 */
public class AplicationModel {
    private ArrayList<Part> parts=new ArrayList<Part>();
        
    public void setParts(ArrayList<Part> parts)
    {
        this.parts=parts;
    }
    
    public ArrayList<Part> getParts() {
        return this.parts;
    }
    
}
