/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.application.Platform;
import message.ChatMessage;

/**
 *
 * @author Ohjelmistokehitys
 */
public class ClientBackEnd implements Runnable {

    private Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private FXMLDocumentController controller;
    
    public ClientBackEnd(FXMLDocumentController controller){
        
        try {
            // 127.0.0.1. = localhost
            clientSocket = new Socket("localhost", 3010);
            this.controller = controller;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        try {
            // järjestyksellä merkitystä, kun saman objektin output ja input
            // jos tehtäis input ensin, outputtia ei voitais luoda, koska stream jo varattu
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // read and write from socket until user closes the app
        while(true){
            try {
                final ChatMessage m = (ChatMessage)input.readObject();
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        controller.updateTextArea(m.getUserName() + ":" + m.getChatMessage());
                    }
                });
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void sendMessage(ChatMessage cm) {

        try {
            // kirjoittaa datan sockettiin, käyttis päättää, koska lähetetään
            output.writeObject(cm);
            // varmistaa, että data lähtee heti, lähtis ilman tätäkin
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
