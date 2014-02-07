/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.server.threader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import static ru.lig.server.utils.BaseUtils.*;
import ru.lig.server.Server_Loader;
import ru.lig.server.utils.PasswordBuilder;

/**
 *
 * @author Константин
 */
public class CUHash {
    
    private HashSet<ContestUser> users = new HashSet<>();
    private HashMap<String, String> names = new HashMap<>();
    private HashMap<String, String> passwords = new HashMap<>();
    
    public int loadContestUsers() {
        int count = 0;
        try {
            Scanner scan = new Scanner(Server_Loader.accounts);
            int liner = 0;
            boolean read = false;
            count += 1;
            names.put("D1", "Developer: Konstantin Shandurenko");
            passwords.put("D1", PasswordBuilder.sref_total());
            users.add(new ContestUser("D1"));
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                liner++;
                line = line.replace(" ", "");
                line = line.replace("	", "");
                if(line.startsWith("//") || line.startsWith("#")) continue;
                if(line.startsWith("accounts{")) {
                    read = true;
                    continue;
                }
                if(line.startsWith("}")) return count;
                if(!read) continue;
                String[] args = line.split(",");
                if(args.length != 3 || !contains(line)) warn("Wrong account format in accounts.config at line " + liner + "!", true);
                line = line.replace("(", "");
                line = line.replace(");", "");
                args = line.split(",");
                names.put(args[1], args[0]);
                passwords.put(args[1], args[2]);
                users.add(new ContestUser(args[1]));
                //log(args[0] + " " + args[1] + " " + args[2] + "!\n");
                count++;
            }
        } catch (Exception ex) {
            warn("Can't load contest users list! Cause: ", false);
            ex.printStackTrace();
            warn("", true);
        }
        return count;
        
    }
    
    public void checkQuestions() {
        Iterator<ContestUser> iterator = users.iterator();
        while(iterator.hasNext()) {
            ContestUser user = iterator.next();
            HashSet<Integer> used = user.getUsed();
            Iterator<Integer> iterator2 = used.iterator();
            while(iterator2.hasNext()) Server_Loader.questions.remove(iterator2.next());
        }
    }
    
    public boolean contains(String line) {
        return line.startsWith("(") && line.endsWith(");");
    }
    
    public String getUsersPassword(String login) {
        return passwords.containsKey(login) ? passwords.get(login) : null;
    }
    
    public String getUsersName(String login) {
        return names.containsKey(login) ? names.get(login) : null;
    }
    
    public void loadData() {
        Iterator<ContestUser> iterator = users.iterator();
        while(iterator.hasNext()) {
            ContestUser cu = iterator.next();
            Server_Loader.data.getString(cu.id + "#Solved", "");
            Server_Loader.data.getString(cu.id + "#BadTries", "");
        }
    }
    
    public boolean unloadCU(ContestUser cu) {
        Iterator<ContestUser> iterator = users.iterator();
        while(iterator.hasNext()) {
            ContestUser user = iterator.next();
            if(user.id == cu.id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    public ContestUser getCU(String id) {
        Iterator<ContestUser> iterator = users.iterator();
        while(iterator.hasNext()) {
            ContestUser cu = iterator.next();
            if(cu.id.equals(id)) return cu;
        }
        return null;
    }
}
