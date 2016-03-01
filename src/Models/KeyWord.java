/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Ronaldo
 */
public class KeyWord {
     private String keyWord;
     private int nrApparances;

    public KeyWord(String keyWord, int nrApparances) {
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
