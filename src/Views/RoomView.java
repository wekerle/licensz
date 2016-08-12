/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Helpers.Enums;
import Listener.TextChangeEventListener;
import javafx.scene.layout.HBox;

/**
 *
 * @author tibor.wekerle
 */
public class RoomView extends HBox implements TextChangeEventListener
{
    private TextEditor titleView=new TextEditor();
    private TextChangeEventListener textChangeEvent;
    private int id;
    
    public RoomView(String text,int id,TextChangeEventListener textChangeEvent)
    {
        titleView.setText(text);
        titleView.setTextChangeEventListener(textChangeEvent);
        this.id=id;
        this.getChildren().add(titleView);
        this.textChangeEvent=textChangeEvent;
    }

    @Override
    public void modifyText(Enums.TextType type, Enums.TextCategory category, int id, String newValue) {
        textChangeEvent.modifyText(Enums.TextType.ROOM_NAME, Enums.TextCategory.NOTHING, this.id, newValue);
    }
}
