/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.server;

import ru.lig.server.threader.CUHash;
import ru.lig.server.threader.ClientThread;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import static ru.lig.server.utils.BaseUtils.*;
import ru.lig.server.utils.CommandsListener;

/**
 *
 * @author Константин
 */
public class Server_Loader implements Runnable {
    
    public static final String version = "1.2.298Alpha";
    public static int port = 20223;
    public static Socket client = null;
    public static ServerSocket server = null;
    public static ClientThread[] threads = new ClientThread[2];
    public static Scanner scan;
    public static int busy = -1;
    public static String problem;
    public static Config config, accounts, data, questions_data;
    public static CUHash users = new CUHash();
    public static HashMap<String, Integer> problemsIds = new HashMap();
    public static int currentProblem = -1;
    public static String contestName = "none";
    public static File contestFolder;
    
    public static int round = 0, roundsCount = 0, move = 0;
    public static HashSet<Integer> questions = new HashSet();
    public static HashMap<Integer, HashSet<Integer>> questions_base = new HashMap();
    
    public static void main(String[] args) {
        log("Enabling LIG server..\n");
        log("Loading main configurations..\n");
        try {
            config = new Config("main");
            port = Integer.parseInt(config.getString("port", "20222"));
            accounts = new Config("accounts");
        }catch(Exception ex) {
            warn("Can't load main configurations!", true);
        }
        log("Loaded port: " + port + "\n");
        try {
            server = new ServerSocket(port);
            log("Connection is established!\n");
        }catch(IOException ex) {
            warn("Can't create server socket!", true);
        }
        log("Loading accounts..\n");
        int count = users.loadContestUsers();
        log(count + " account(s) loaded!\n");
        scan = new Scanner(System.in);
        new Thread(new Server_Loader()).start();
        log("Enter \"start [name]\" to start contest..\n");
        pre();
        while(true) {
            try {
                client = server.accept();
                boolean used = false;
                for(int i = 0; i < threads.length; i++) {
                    if(threads[i] == null) {
                        (threads[i] = new ClientThread(client, i)).start();
                        used = true;
                        break;
                    }
                }
                if(!used) warn("Clients count exceed!", false);
            }catch(IOException ex) {
                warn("Can't create client socket!", false);
            }
        }
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                if(scan.hasNextLine()) {
                    String cmd = scan.nextLine();
                    CommandsListener.process(cmd);
                    pre();
                }
            }catch(Exception ex) {}
        }
    }
}
