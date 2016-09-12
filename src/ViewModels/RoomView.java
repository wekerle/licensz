/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Listener.TextChangeEventListener;
import Models.RoomModel;
import javafx.scene.layout.HBox;

/**
 *
 * @author tibor.wekerle
 */
public class RoomView extends HBox
{
    private TextEditor titleView=new TextEditor();
    private int id;
    private RoomModel roomModel;
    
    public RoomView(RoomModel room)
    {
        this.roomModel=room;
        titleView.setText(roomModel.getName());
        
        titleView.setTextChangeEventListener(new TextChangeEventListener() 
        {
            @Override
            public void modifyText(String newValue) 
            {
                roomModel.setName(newValue);
            }
        });
        this.id=room.getId();
        this.getChildren().add(titleView);
    }
}
