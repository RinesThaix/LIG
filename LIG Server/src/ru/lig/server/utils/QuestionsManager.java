/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.server.utils;

import java.util.Iterator;
import ru.lig.server.Config;
import ru.lig.server.Server_Loader;
import ru.lig.server.threader.ClientThread;
import static ru.lig.server.utils.BaseUtils.*;

/**
 *
 * @author Константин
 */
public class QuestionsManager {
    
    public static String getCategories() {
        int round = Server_Loader.round;
        Config config = Server_Loader.questions_data;
        int i = 1;
        StringBuilder sb = new StringBuilder();
        while(config.isSet("Category#" + round + "#" + i)) {
            if(!sb.toString().equals("")) sb.append(" ");
            sb.append(config.getString("Category#" + round + "#" + i));
            i++;
        }
        if(sb.toString().equals("")) warn("Can't get categories (contest " + Server_Loader.contestName + ", round " + Server_Loader.round + ")", true);
        return sb.toString();
    }
    
    public static String getQuestion(int k) {
        try {
        return Server_Loader.questions_data.getString("Question#" + Server_Loader.round + "#" + k);
        }catch(Exception ex) {
            warn("Can't get " + k + " question's text (contest " + Server_Loader.contestName + ", round " +
                    Server_Loader.round + ")", false);
            ex.printStackTrace();
            warn("", true);
            return null;
        }
    }
    
    public static String getAnswers(int k) {
        int round = Server_Loader.round;
        try {
            Config config = Server_Loader.questions_data;
            int i = 1;
            StringBuilder sb = new StringBuilder();
            while(config.isSet("Answer#" + round + "#" + k + "#" + i)) {
                sb.append(config.getString("Answer#" + round + "#" + k + "#" + i)).append("#");
                ++i;
            }
            return sb.toString();
        }catch(Exception ex) {
            warn("Can't get answers for question " + k + " (contest " + Server_Loader.contestName + ", round " + round + ")", false);
            ex.printStackTrace();
            warn("", true);
            return null;
        }
    }
    
    public static void updateTableForEverybody() {
        for(int i = 0; i < Server_Loader.threads.length; i++) if(Server_Loader.threads[i] != null) updateTable(Server_Loader.threads[i]);
    }
    
    public static void updateTable(ClientThread ct) {
        ct.out.println("POINTS " + ct.cu.getPoints());
        ct.out.println("QUESTIONS_CATEGORIES " + getCategories()); //Отправляем категории вопросов
        StringBuilder sb = new StringBuilder();
        sb.append("QUESTIONS ").append(Server_Loader.questions.size()).append(" "); //Кол-во оставшихся вопросов
        Iterator<Integer> iterator = Server_Loader.questions.iterator();
        while(iterator.hasNext()) sb.append(iterator.next()).append(" "); //Номера оставшихся вопросов
        ct.out.println(sb.toString());
        ClientThread thread = Server_Loader.threads[Server_Loader.move];
        boolean move = false;
        if(thread != null && thread.contestId != null && thread.contestId.equals(ct.contestId)) move = true;
        ct.out.println("MOVE " + move);
        ct.out.println("CURRENT_ROUND " + Server_Loader.round); //Отправляем нынешний раунд
    }
    
    public static void sendQuestionInfoForEverybody(int k) {
        for(int i = 0; i < Server_Loader.threads.length; i++) if(Server_Loader.threads[i] != null) sendQuestionInfo(Server_Loader.threads[i], k);
    }
    
    public static void sendQuestionInfo(ClientThread ct, int k) {
        ct.out.println("QUESTION " + k + " " + getQuestion(k));
        ct.out.println("ANSWERS " + getAnswers(k));
    }
    
}
