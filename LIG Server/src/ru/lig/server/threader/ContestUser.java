/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.server.threader;

import java.util.HashSet;
import ru.lig.server.Config;
import ru.lig.server.Server_Loader;
import ru.lig.server.utils.QuestionsManager;

/**
 *
 * @author Константин
 */
public class ContestUser {
    
    public String id;
    
    public ContestUser(String id) {
        this.id = id;
    }
    
    public void solve(int k) {
        Config data = Server_Loader.data;
        String solved = data.getString(id + "#Solved");
        String key = String.valueOf(Server_Loader.round + "." + k);
        String[] args = solved.split("#");
        for(int i = 0; i < args.length; i++) if(args[i].equals(key)) return;
        solved += key + "#";
        data.set(id + "#Solved", solved);
        Server_Loader.questions.remove(k);
    }
    
    public void addTry(int k) {
        Config data = Server_Loader.data;
        String tries = data.getString(id + "#BadTries");
        String key = String.valueOf(Server_Loader.round + "." + k);
        String[] args = tries.split("#");
        for(int i = 0; i < args.length; i++) if(args[i].equals(key)) return;
        tries += key + "#";
        data.set(id + "#BadTries", tries);
        Server_Loader.questions.remove(k);
    }
    
    public HashSet<Integer> getUsed() {
        Config data = Server_Loader.data;
        String tries = data.getString(id + "#BadTries");
        String solved = data.getString(id + "#Solved");
        HashSet<Integer> used = new HashSet();
        if(tries != null && !tries.equals("")) {
            String[] args = tries.split("#");
            for(String s : args) {
                String args2[] = s.split("\\.");
                if(Integer.parseInt(args2[0]) == Server_Loader.round) used.add(Integer.parseInt(args2[1]));
            }
        }
        if(solved != null && !solved.equals("")) {
            String[] args = solved.split("#");
            for(String s : args) {
                String args2[] = s.split("\\.");
                if(Integer.parseInt(args2[0]) == Server_Loader.round) used.add(Integer.parseInt(args2[1]));
            }
        }
        return used;
    }
    
    public int getPoints() {
        Config data = Server_Loader.data;
        String solved = data.getString(id + "#Solved"), tries = data.getString(id + "#BadTries");
        String[] args1 = solved.split("#"), args2 = tries.split("#");
        int multiplier = 100, total = 0;
        if(!args1[0].equals("")) for(int i = 0; i < args1.length; i++) {
            String[] args3 = args1[i].split("\\.");
            int round = Integer.parseInt(args3[0]), question = Integer.parseInt(args3[1]);
            int cost = round * multiplier * ((--question % 5) + 1);
            total += cost;
        }
        if(!args2[0].equals("")) for(int i = 0; i < args2.length; i++) {
            String[] args3 = args2[i].split("\\.");
            int round = Integer.parseInt(args3[0]), question = Integer.parseInt(args3[1]);
            int cost = round * multiplier * ((--question % 5) + 1);
            total -= cost;
        }
        return total;
    }
}
