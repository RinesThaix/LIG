/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.graphics;

import com.sun.awt.AWTUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import ru.lig.client.Client_Loader;
import ru.lig.client.graphics.components.DragButton;
import ru.lig.client.graphics.components.Dragger;
import ru.lig.client.graphics.components.Panel;
import ru.lig.client.graphics.components.PasswordField;
import ru.lig.client.graphics.components.SpecialTextArea;
import ru.lig.client.graphics.components.SpecialTextField;
import ru.lig.client.graphics.components.TextField;
import ru.lig.client.listeners.ActionListeners;

/**
 *
 * @author Константин
 */
public class Frame extends JFrame {
    //Общее
    public static ActionListeners listeners;
    
    //Авторизация
    private JFrame auth;
    public TextField name;
    public SpecialTextField answer;
    public SpecialTextArea question, scores, time;
    public PasswordField password;
    public JButton auth_button = new JButton("Авторизоваться");
    public Dragger dragger = new Dragger();
    public DragButton close = new DragButton(), hide = new DragButton();
    
    //Таблица вопросов
    public JTextArea round, points = new JTextArea("0 очков");
    public JButton[] categories = new JButton[6];
    public JButton[] questions = new JButton[30];
    public JButton sendAnswer = new JButton("Подтвердить");
    public int width = 991, height = 800;
    public ThemeUtils tu;
    public Panel panel = new Panel(this);
    
    //Картинки для вопросов
    public ImageFrame[] ifs = new ImageFrame[4];
    
    public Frame() {
        setVisible(false);
        listeners = new ActionListeners(this);
        hide.addActionListener(listeners);
        close.addActionListener(listeners);
        auth = new JFrame();
        auth.setTitle("LIG Client Authorization");
        auth.setSize(640, 480);
        auth.setResizable(false);
        auth.setLayout(new BorderLayout());
        auth.setDefaultCloseOperation(EXIT_ON_CLOSE);
        name = new TextField("Логин"); password = new PasswordField("Пароль");
        name.setEditable(true); password.setEditable(true);
        name.setVisible(true); password.setVisible(true);
        name.setFont(name.getFont().deriveFont(14f));
        password.setFont(name.getFont());
        name.setPreferredSize(new Dimension(120, 24));
        password.setPreferredSize(new Dimension(120, 24));
        name.addFocusListener(listeners);
        name.addKeyListener(listeners);
        password.addFocusListener(listeners);
        password.addKeyListener(listeners);
        auth_button.setPreferredSize(new Dimension(120, 24));
        JPanel auth_panel = new JPanel();
        JScrollPane forName = new JScrollPane(name), forPassword = new JScrollPane(password);
        auth_button.setAlignmentX(CENTER_ALIGNMENT);
        auth_button.addActionListener(listeners);
        auth_panel.add(forName); auth_panel.add(forPassword); auth_panel.add(auth_button);
        auth.add(auth_panel, BorderLayout.CENTER);
        auth.pack();
        auth.setLocationRelativeTo(null);
        auth.validate();
        auth.repaint();
        auth.setVisible(true);
        
        int Dx = 250, Dy = 100;
        for(int i = 0; i < 4; i++) {
            int x = -300, y = -240;
            x += (i & 1) != 0 ? width / 2 + Dx : -Dx;
            y += i > 1 ? height / 2 + Dy : -Dy;
            ifs[i] = new ImageFrame(this, x, y);
        }
    }
    
    @SuppressWarnings("empty-statement")
    public void loadMainFrame() {
        auth.setVisible(false);
        for(int i = 0; i < categories.length; i++) {
            categories[i] = new JButton(Client_Loader.categories[i]);
            categories[i].setEnabled(false);
        }
        setUndecorated(true);
        AWTUtilities.setWindowOpaque(this, false);
        setPreferredSize(new Dimension(width, height));
        setSize(this.getPreferredSize());
        setResizable(false);
        setTitle("LIG Client");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
