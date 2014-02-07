/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.graphics.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import ru.lig.client.graphics.FileUtils;
import ru.lig.client.graphics.Frame;
import static ru.lig.client.graphics.FileUtils.*;

/**
 *
 * @author Константин
 */
public class Panel extends JPanel {
    
    public int phase = 0, w, h;
    public Frame frame;
    public BufferedImage bg;
    
    public Panel(Frame frame) {
        setOpaque(false);
        setLayout(null);
        setDoubleBuffered(true);
        setBorder(null);
        setFocusable(true);
        this.frame = frame;
        w = frame.width; h = frame.height;
        bg = background;
    }
    
    public Panel(Frame frame, int width, int height, BufferedImage bg) {
        setOpaque(false);
        setLayout(null);
        setDoubleBuffered(true);
        setBorder(null);
        setFocusable(true);
        this.frame = frame;
        w = width; h = height;
        this.bg = bg;
    }
    
    public void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(bg == null) bg = background;
        g.drawImage(bg, 0, 0, w, h, null);
        paintBorders(g);
    }
    
    public void paintBorders(Graphics2D g) {
            int top = bg == background ? 30 : 5;
            g.drawImage(FileUtils.border, 0, 0, getWidth(), top, null);
            g.drawImage(FileUtils.border, 0, 0, 5, getHeight(), null);
            g.drawImage(FileUtils.border, 0, getHeight()-5, getWidth(), 5, null);
            g.drawImage(FileUtils.border, getWidth()-5, 0, 5, getHeight(), null);
        }
    
}
