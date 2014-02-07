package ru.lig.server.utils;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import ru.lig.server.Server_Loader;

public class BaseUtils {
    
    public static void log(Object o) {
        System.out.print(o.toString());
    }
    
    public static void pre() {
        System.out.print("> ");
    }
    
    public static void logln(Object o) {
        System.out.println(o.toString());
        pre();
    }
    
    public static void warn(Object o, boolean critical) {
        System.err.println(o.toString());
        if (!critical) pre();
        else System.exit(-1);
    }
    
    //Проверка на конец раунда
    public static void checkRoundEnd() {
        if(Server_Loader.questions.isEmpty()) {
            Server_Loader.round++;
            if(Server_Loader.round <= Server_Loader.roundsCount) {
                Server_Loader.questions = Server_Loader.questions_base.get(Server_Loader.round);
                QuestionsManager.updateTableForEverybody();
            }else {
                //Конец игры
                for(int i = 0; i < Server_Loader.threads.length; i++) if(Server_Loader.threads[i] != null) 
                    Server_Loader.threads[i].out.println("GAMEOVER");
            }
        }
    }
}
