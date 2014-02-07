/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.graphics;

import javax.swing.JFrame;
import ru.lig.client.graphics.components.Panel;

/**
 *
 * @author Константин
 */
public class ImageFrame extends JFrame {
    
    public Panel panel;
    public Frame frame;
    public final int width = 300, height = 240;
    public int x, y;
    
    public ImageFrame(Frame frame, int x, int y) {
        setVisible(false);
        setResizable(false);
        setUndecorated(true);
        this.frame = frame;
        this.x = x; this.y = y;
        setBounds(x, y, width, height);
    }
    
    public void update(int x, int y) {
        setBounds(x, y, width, height);
    }
    
}
