/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.graphics;

import java.awt.Color;
import javax.swing.JFrame;
import ru.lig.client.graphics.components.Dragger;
import ru.lig.client.graphics.components.Panel;

/**
 *
 * @author Константин
 */
public class ImageFrame extends JFrame {
    
    public Panel panel;
    public Frame frame;
    public Dragger dragger = new Dragger(this);
    public final int width = 300, height = 240, draggerH = 15;
    public int x, y, num;
    
    public ImageFrame(Frame frame, int x, int y, int num) {
        setVisible(false);
        setResizable(false);
        setUndecorated(true);
        this.frame = frame;
        this.x = x; this.y = y;
        setBounds(x, y, width, height);
        this.num = num;
        dragger.title.setForeground(Color.DARK_GRAY);
        dragger.title.setText(num + "");
        dragger.setBounds(0, 0, width, draggerH);
        dragger.setVisible(true);
        add(dragger);
    }
    
    public void update(int x, int y) {
        this.x = x;
        this.y = y;
        setBounds(x, y, width, height);
    }
    
}
