/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.graphics.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import ru.lig.client.graphics.FileUtils;
import ru.lig.client.utils.BaseUtils;

/**
 *
 * @author Константин
 */
public class Button extends JButton {
    
    public int type = 0;
    public int x = 0;
    public int y = 0;
    public int width = 0;
    public int height = 0;
    
    public BufferedImage defImg, rolImg, preImg;
    
    public static BufferedImage def_button_1 = FileUtils.button.getSubimage(0, 0, 112, 36);
    public static BufferedImage rol_button_1 = FileUtils.button.getSubimage(0, 36, 112, 36);
    public static BufferedImage pre_button_1 = FileUtils.button.getSubimage(0, 72, 112, 36);
    public static BufferedImage loc_button_1 = FileUtils.button.getSubimage(0, 108, 112, 36);
    
    public static BufferedImage def_button_2 = FileUtils.button.getSubimage(112, 0, 112, 36);
    public static BufferedImage rol_button_2 = FileUtils.button.getSubimage(112, 36, 112, 36);
    public static BufferedImage pre_button_2 = FileUtils.button.getSubimage(112, 72, 112, 36);
    public static BufferedImage loc_button_2 = FileUtils.button.getSubimage(112, 108, 112, 36);
    
    public Button(String title, int type, int x, int y, int width, int height) {
        setText(title);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setFocusable(false);
        setForeground(Color.DARK_GRAY);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBounds(x, y, width, height);
        
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void paintComponent(Graphics g) {
        ButtonModel bm = getModel();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(defImg != null && rolImg != null && preImg != null) {
            if(bm.isRollover() && bm.isEnabled()) {
                if(bm.isPressed()) g2d.drawImage(preImg, 0, 0, width, height, null);
                else g2d.drawImage(rolImg, 0, 0, width, height, null);
            }else g2d.drawImage(defImg, 0, 0, width, height, null);
        }else {
            BufferedImage loc, pre, rol, def;
            switch(type) {
                case 1:
                    loc = loc_button_1;
                    pre = pre_button_1;
                    rol = rol_button_1;
                    def = def_button_1;
                    break;
                case 2:
                    loc = loc_button_2;
                    pre = pre_button_2;
                    rol = rol_button_2;
                    def = def_button_2;
                    break;
                default:
                    BaseUtils.warn("Unknown button type!", true);
                    return;
            }
            if(!bm.isEnabled()) g2d.drawImage(loc, 0, 0, width, height, null);
            else if(bm.isRollover()) {
                if(bm.isPressed()) g2d.drawImage(pre, 0, 0, width, height, null);
                else g2d.drawImage(rol, 0, 0, width, height, null);
            }else g2d.drawImage(def, 0, 0, width, height, null);
        }
        g2d.dispose();
        super.paintComponent(g);
    }
}
