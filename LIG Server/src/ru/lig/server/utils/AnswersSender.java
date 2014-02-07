/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.server.utils;

import ru.lig.server.threader.ContestUser;
import ru.lig.server.Server_Loader;

/**
 *
 * @author Константин
 */
public class AnswersSender {
    public static void send(Answer a, Object... o) {
        switch(a) {
            case Correct:
                send("Correct");
                end();
                break;
            case Wrong:
                send("Wrong");
                end();
                break;
            case System_Error:
                send("System Error");
                end();
                break;
        }
    }
    
    public static void end() {
        Server_Loader.busy = -1;
    }
    
    private static void send(String ans) {
        ContestUser cu = Server_Loader.users.getCU(Server_Loader.threads[Server_Loader.busy].contestId);
        if(cu == null) {
            BaseUtils.warn("Contest User in AnswersSender class is null!", true);
            return;
        }
        int current = Server_Loader.currentProblem;
        if(ans.contains("Accepted")) cu.solve(current);
        else cu.addTry(current);
        Server_Loader.threads[Server_Loader.busy].out.println("ANSWER " + ans);
        QuestionsManager.updateTableForEverybody();
    }
}
