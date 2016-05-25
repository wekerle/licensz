/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;

/**
 *
 * @author Ronaldo
 */
public class KeyWordModel implements Serializable{
     private String keyWord;
     private int nrApparances;

    public KeyWordModel(String keyWord, int nrApparances) {
        this.keyWord = keyWord;
        this.nrApparances = nrApparances;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public int getNrApparances() {
        return nrApparances;
    }
}
