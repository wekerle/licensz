/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licentav2;

/**
 *
 * @author tibor.wekerle
 */
public class IdGenerator {
    private int id=0;
    public int getNewId()
    {
        id++;
        return this.id;
    }
}
