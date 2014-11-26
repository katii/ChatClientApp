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
import java.util.logging.Level;
import java.util.logging.Logger;
import message.ChatMessage;

/**
 *
 * @author Ohjelmistokehitys
 */
public class ClientBackEnd implements Runnable {

    private Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    
    public ClientBackEnd(){
        
        try {
            // 127.0.0.1. = localhost
            clientSocket = new Socket("localhost", 3010);
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
                ChatMessage m = (ChatMessage)input.readObject();
                System.out.println(m.getChatMessage());
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
}
