/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientapp;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import message.ChatMessage;

/**
 *
 * @author Ohjelmistokehitys
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    TextField chatMessage;
    @FXML
    TextArea chatMessageArea;
    @FXML
    TextField userName;
    
    ClientBackEnd backEnd;
    Thread backThread;
    
    @FXML
    public void sendChatMessage(ActionEvent ae){
        
        ChatMessage cm = new ChatMessage();
        cm.setUserName("as");
        cm.setChatMessage(chatMessage.getText());
        cm.setMessageColor("#6680e6");
        backEnd.sendMessage(cm);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backEnd = new ClientBackEnd(this);
        backThread = new Thread(backEnd);
        backThread.setDaemon(true);
        // kutsuu meidän kutsuman objectin runnia, luo säikeen
        backThread.start();
    }    
    
    public void updateTextArea(String message){
        chatMessageArea.appendText(message + "\n");
    }
    
    @FXML
    public void setTextColor(){
        // poistais värin rgb arvon napista
        // colorpicker.setStyle("-fx-color-label-visible: false ;");    
        
        ColorPicker colorPicker = new ColorPicker(Color.BLUE);
        
        System.out.println("nyt tarttis vaihtaa väri");
    }
    
    @FXML
    public void setUserName(){
        System.out.println("nyt tarttis vaihtaa nimimerkki");
        ChatMessage cm = new ChatMessage();
        cm.setUserName(userName.getText());
        String temp = cm.getUserName();
        System.out.println("user name: " + temp);
    }
}
