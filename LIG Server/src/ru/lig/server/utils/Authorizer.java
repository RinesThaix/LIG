/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.server.utils;

import java.util.Iterator;
import ru.lig.server.Server_Loader;
import static ru.lig.server.Server_Loader.*;
import ru.lig.server.threader.CUHash;

/**
 *
 * @author Константин
 */
public class Authorizer {
    
    public static String getContestId(int id, String name, String password) {
        BaseUtils.log("Authorizing user with socket id " + id + " (Tries to login as " + name + ")\n");
        CUHash users = Server_Loader.users;
        String pswd = users.getUsersPassword(name);
        if(pswd == null || !password.equals(pswd)) {
            threads[id].out.println("AUTH false");
            BaseUtils.log("Wrong username or password!\n");
            name = null;
        }else {
            threads[id].cu = Server_Loader.users.getCU(name);
            threads[id].out.println("AUTH true " + name);
            threads[id].contestId = name;
            QuestionsManager.updateTable(threads[id]);
            BaseUtils.log("User with contest id " + name + " logged in!\n");
        }
        BaseUtils.pre();
        return name;
    }
}
