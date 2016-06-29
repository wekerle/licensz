/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

import Helpers.Enums;
import Views.MinimalSessionView;
import Views.TableView;

/**
 *
 * @author tibor.wekerle
 */
public interface SessionDragEventListener {
     void notifyDataManager(int destinationSessionId,int sourceSessionId,Enums.Position position);
     void notifyView(TableView table, MinimalSessionView destinationSession,int sourceSessionId, Enums.Position position);
}
