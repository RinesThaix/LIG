/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import ru.lig.client.Client_Loader;

/**
 *
 * @author Константин
 */
public class Ticker extends Thread {
    
    public int secondsLeft = 60;
    
    public Ticker() {
        Client_Loader.timer = new Timer(1000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(!Client_Loader.gm.graphics.time.isVisible()) Client_Loader.timer.stop();
                else if(secondsLeft == 0) {
                    BaseUtils.sendString("Answered:" + Client_Loader.currentQuestionId + ":false");
                    Client_Loader.timer.stop();
                }else {
                    secondsLeft--;
                    Client_Loader.gm.graphics.time.setText("Время: " + secondsLeft);
                }
            }
            
        });
        Client_Loader.timer.start();
    }
    
}
