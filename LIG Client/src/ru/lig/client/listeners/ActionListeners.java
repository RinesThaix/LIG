/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.listeners;

import static java.awt.Frame.ICONIFIED;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import ru.lig.client.Client_Loader;
import ru.lig.client.graphics.Frame;
import ru.lig.client.utils.BaseUtils;

/**
 *
 * @author Константин
 */
public class ActionListeners implements ActionListener, FocusListener, KeyListener {
    
    public Frame frame;
    
    public ActionListeners(Frame frame) {
        this.frame = frame;
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == frame.close) System.exit(0);
        else if(e.getSource() == frame.hide) frame.setExtendedState(ICONIFIED);
        else if(e.getSource() == frame.auth_button) auth();
        else if(Client_Loader.myMove) {
            if(e.getSource() == frame.sendAnswer) {
                String answer = frame.answer.getText().toLowerCase();
                answer = answer.replace("\"", "");
                if(Client_Loader.answers.contains(answer))
                    BaseUtils.sendString("Answered:" + Client_Loader.currentQuestionId + ":true");
                else BaseUtils.sendString("Answered:" + Client_Loader.currentQuestionId + ":false");
            }else {
                boolean used = false;
                for(int i = 0; i < frame.questions.length; i++) if(e.getSource() == frame.questions[i]) {
                    used = true;
                    int question = i + 1;
                    Client_Loader.currentQuestionId = question;
                    BaseUtils.sendString("Get_Question:" + question);
                }
                if(!used) {

                }
            }
        }
    }
    
    public void correctAnswer(boolean you) {
        Frame g = Client_Loader.gm.graphics;
        JOptionPane.showMessageDialog(g, "Ответ верен (" + Client_Loader.answers.get(0) + ")!");
        Client_Loader.questions.remove(Client_Loader.currentQuestionId - 1);
        if(you) Client_Loader.points += BaseUtils.getQuestionScore(Client_Loader.currentQuestionId);
        update();
    }
    
    public void wrongAnswer(boolean you) {
        Frame g = Client_Loader.gm.graphics;
        try {
            JOptionPane.showMessageDialog(g, "Время истекло или ответ неверен!\nПравильный: " + Client_Loader.answers.get(0));
        }catch(NullPointerException ex) {}
        Client_Loader.questions.remove(Client_Loader.currentQuestionId - 1);
        if(you) Client_Loader.points -= BaseUtils.getQuestionScore(Client_Loader.currentQuestionId);
        update();
    }
    
    private void update() {
        Client_Loader.currentQuestion = null;
        frame.tu.setTableState();
    }
    
    public void auth() {
        BaseUtils.sendString("Authorize_me:" + Client_Loader.version + ":" + frame.name.getText() + ":" + frame.password.getText());
        frame.auth_button.setEnabled(false);
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource() == frame.name && frame.name.getText().equals("Логин")) frame.name.setText("");
        else if(e.getSource() == frame.password && new String(frame.password.getPassword()).equals("Пароль")) frame.password.setText("");
        else if(e.getSource() == frame.answer && frame.answer.getText().equals("Ваш ответ..")) frame.answer.setText("");
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        if(e.getSource() == frame.name && frame.name.getText().equals("")) frame.name.setText("Логин");
        else if(e.getSource() == frame.password && new String(frame.password.getPassword()).equals("")) frame.password.setText("Пароль");
        else if(e.getSource() == frame.answer && frame.answer.getText().equals("")) frame.answer.setText("Ваш ответ..");
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if(KeyEvent.getKeyText(e.getKeyCode()).equals("Enter")) {
            if(frame.auth_button.isEnabled() && (e.getSource() == frame.name || e.getSource() == frame.password)) auth();
            if(e.getSource() == frame.answer) {
                if(!Client_Loader.myMove) return;
                String answer = frame.answer.getText().toLowerCase();
                answer = answer.replace("\"", "");
                if(Client_Loader.answers.contains(answer))
                    BaseUtils.sendString("Answered:" + Client_Loader.currentQuestionId + ":true");
                else BaseUtils.sendString("Answered:" + Client_Loader.currentQuestionId + ":false");
            }
        }
    }
    
}
