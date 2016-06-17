/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

import Helpers.Enums;

/**
 *
 * @author tibor.wekerle
 */
public interface SessionDragEventListener {
     void notify(int destinationSessionId,int sourceSessionId,Enums.Position position);
}
