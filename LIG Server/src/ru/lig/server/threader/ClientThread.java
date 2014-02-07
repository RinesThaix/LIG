/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.server.threader;

import ru.lig.server.utils.Authorizer;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import ru.lig.server.Server_Loader;
import static ru.lig.server.utils.BaseUtils.*;
import static ru.lig.server.Server_Loader.threads;
import ru.lig.server.utils.QuestionsManager;

/**
 *
 * @author Константин
 */
public class ClientThread extends Thread {
    public DataInputStream in = null;
    public PrintStream out = null;
    public Socket socket = null;
    public int id = 0;
    public String contestId = null;
    public ContestUser cu;
    
    public ClientThread(Socket socket, int id) {
        this.socket = socket;
        this.id = id;
    }
    
    @Override
    public void run() {
        try {
            String line = "";
            in = new DataInputStream(socket.getInputStream());
            out = new PrintStream(socket.getOutputStream(), true, "CP1251");
            log("Non-authorized client with socket id " + id + " connected to the server\n");
            pre();
            while(true) {
                line = in.readLine();
                if(line.startsWith("Authorize_me")) {
                    String[] args = line.split(":");
                    if(args.length != 4) throw new Exception("Unknown authorization arguments!");
                    if(!Server_Loader.contestName.equals("none")) {
                        if(args[1].equals(Server_Loader.version)) {
                            contestId = Authorizer.getContestId(id, args[2], args[3]);
                        }else out.println("Wrong version!");
                    }else out.println("Not started");
                }else if(line.startsWith("Get_Question")) {
                    String[] args = line.split(":");
                    if(args.length != 2) throw new Exception("Unknown get_question method arguments!");
                    int question = Integer.parseInt(args[1]);
                    QuestionsManager.sendQuestionInfoForEverybody(question);
                }else if(line.startsWith("Answered")) {
                    String[] args = line.split(":");
                    if(args.length != 3) throw new Exception("Unknown answered method arguments!");
                    Server_Loader.move = 1 - Server_Loader.move;
                    boolean correct = Boolean.valueOf(args[2]);
                    if(correct) cu.solve(Integer.parseInt(args[1]));
                    else cu.addTry(Integer.parseInt(args[1]));
                    for(int i = 0; i < Server_Loader.threads.length; i++) if(threads[i] != null) 
                        threads[i].out.println("IS_CORRECT " + correct);
                    QuestionsManager.updateTableForEverybody();
                }
            }
        }catch(Exception ex) {
            log("Client with ID " + id + " lost connection!\n");
            log("Disconnecting reason: " + ex + "\n");
            if(!(ex instanceof SocketException)) {
                ex.printStackTrace();
                out.println("Disconnected");
            }
            try {
                in.close();
                out.close();
                socket.close();
            }catch(IOException exx) {warn("Error in closing client socket!", true);}
            threads[id] = null;
        }
    }
}
