/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import ru.lig.client.Client_Loader;
import static ru.lig.client.Client_Loader.*;

/**
 *
 * @author Константин
 */
public class BaseUtils {
    
    public static void log(Object o) {
        System.out.print(o.toString());
    }
    
    public static void logln(Object o) {
        System.out.println(o.toString());
    }
    
    public static void warn(Object o, boolean critical) {
        System.err.println(o.toString());
        if(critical) System.exit(-1);
    }
    
    public static int a = 0;
    
    public static void sendString(String s) {
        System.out.println("Sent: " + s);
        out.println(s);
    }
    
    public static void sendFile() throws IOException {
        sendString("Sent");
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        if(bos != null) {
            File toSend = new File(pathToFile);
            sendString(toSend.length() + "");
            byte[] bytes = new byte[(int) toSend.length()];
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(toSend);
            }catch(FileNotFoundException ex) {
                warn("File not found!", false);
                return;
            }
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(bytes, 0, bytes.length);
            bos.write(bytes, 0, bytes.length);
            bos.flush();
        }
    }
    
    public static int multiplier = 100;
    
    public static int getQuestionScore(int questionNumber) {
        int round = Client_Loader.round;
        int column = --questionNumber % 5;
        return round * multiplier * (column + 1);
    }
}
