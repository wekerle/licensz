/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Observer;

/**
 *
 * @author Ronaldo
 */
public interface SessionChairsTextChangeListener extends TextChangeObserver{
    void notifyTextChangeSessionChairs(int sesionId,String chairs);
}
