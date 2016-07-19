/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licenta;

/**
 *
 * @author tibor.wekerle
 */
public final class IdGenerator {
    public static int id=0;
    public static int getNewId()
    {
        id++;
        return id;
    }
}
