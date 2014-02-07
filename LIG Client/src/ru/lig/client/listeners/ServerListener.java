/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.listeners;

import java.awt.HeadlessException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import ru.lig.client.utils.BaseUtils;
import static ru.lig.client.utils.BaseUtils.logln;
import static ru.lig.client.utils.BaseUtils.sendFile;
import ru.lig.client.Client_Loader;
import static ru.lig.client.Client_Loader.*;
import ru.lig.client.graphics.Frame;
import ru.lig.client.graphics.ThemeUtils;

/**
 *
 * @author Константин
 */
public class ServerListener {
    
    public static void process(String response) {
        try {
            if(Client_Loader.debug) BaseUtils.logln("Server: " + response);
            if (response.equals("Ready_to_get")) {
            sendFile();
        }else if(response.startsWith("ANSWER "))  {
            response = response.substring(6, response.length());
            check(response);
            gm.graphics.tu.setTableState();
        }else if(response.startsWith("AUTH")) {
            String[] ans = response.split(" ");
            if(ans[1].equals("false")) {
                logln("Authorization failed");
                gm.graphics.auth_button.setEnabled(true);
                JOptionPane.showMessageDialog(gm.graphics, "Логин или пароль неверны!");
            }else if(ans[1].equals("true")) {
                logln("Authorization successed (Your contest id is " + ans[2] + ")");
                //AUTH true [contest id]
                contestId = ans[2];
                gm.graphics.loadMainFrame();
            }
        }else if(response.equals("Not started")) {
            JOptionPane.showMessageDialog(gm.graphics, "Игра еще не началась!");
            socket.close();
        }else if(response.equals("Wrong version!")) {
            JOptionPane.showMessageDialog(gm.graphics, "Версия клиента устарела!");
            socket.close();
        }else if(response.equals("Disconnected")) {
            JOptionPane.showMessageDialog(gm.graphics, "Вы были отключены от сервера!");
            socket.close();
        }else if(response.startsWith("CURRENT_ROUND")) {
            round = Integer.parseInt(response.substring(14, response.length()));
            Frame frame = Client_Loader.gm.graphics;
            if(frame.tu == null) {
                frame.tu = new ThemeUtils(frame);
                frame.setContentPane(frame.panel);
                frame.setVisible(true);
            }else frame.tu.setTableState();
        }else if(response.startsWith("QUESTIONS_CATEGORIES")) {
            String[] args = response.split(" ");
            for(int i = 1; i < categories.length + 1; i++) categories[i - 1] = args[i];
        }else if(response.startsWith("QUESTIONS")) {
            String[] args = response.split(" ");
            //int total = args[1];
            for(int i = 2; i < args.length; i++) questions.add(Integer.parseInt(args[i]) - 1);
        }else if(response.startsWith("QUESTION")) {
            StringBuilder sb = new StringBuilder();
            String[] args = response.split(" ");
            for(int i = 2; i < args.length; i++) {
                if(!sb.toString().equals("")) sb.append(" ");
                sb.append(args[i]);
            }
            currentQuestion = sb.toString().replace("\\n", "\n");
            currentQuestionId = Integer.parseInt(args[1]);
        }
        else if(response.startsWith("ANSWERS")) {
            Client_Loader.answers = new ArrayList();
            String[] args = response.substring(8, response.length()).split("#");
            for(int i = 0; i < args.length; i++) answers.add(args[i]);
            Client_Loader.gm.graphics.tu.setQuestionState();
        }else if(response.startsWith("POINTS")) {
            String[] args = response.split(" ");
            Client_Loader.points = Integer.parseInt(args[1]);
        }else if(response.startsWith("MOVE")) {
            String bool = response.split(" ")[1];
            Client_Loader.myMove = Boolean.valueOf(bool);
        }else if(response.startsWith("IS_CORRECT")) {
            String[] args = response.split(" ");
            boolean correct = Boolean.valueOf(args[1]);
            if(timer != null && timer.isRunning()) timer.stop();
            if(correct) Frame.listeners.correctAnswer(Client_Loader.myMove);
            else Frame.listeners.wrongAnswer(Client_Loader.myMove);
        }
        }catch(IOException | NumberFormatException | HeadlessException ex) {
            BaseUtils.warn("Processing error:", false);
            ex.printStackTrace();
            BaseUtils.warn("", true);
        }
    }
    
    public static void check(String ans) {
        Frame g = gm.graphics;
        switch(ans) {
            case "Correct":
                JOptionPane.showMessageDialog(g, "Вы ответили верно!");
                break;
            case "Wrong":
                JOptionPane.showMessageDialog(g, "Вы дали неверный ответ!");
                break;
            default:
                JOptionPane.showMessageDialog(g, "Произошло ошибка системы. Скорее всего, дальнейшая игра невозможна. Сообщите "
                        + "организаторам или разработчику приложения.");
                break;
        }
    }
    
}
