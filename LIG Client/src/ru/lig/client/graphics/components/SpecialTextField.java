/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.graphics.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JTextField;
import ru.lig.client.graphics.FileUtils;
import ru.lig.client.graphics.ImageUtils;

/**
 *
 * @author Константин
 */
public class SpecialTextField extends JTextField {
    
    public int x = 0, y = 0, width = 0, height = 0;
    public BufferedImage static_image = FileUtils.field.getSubimage(0, 0, 48, 18);
    public BufferedImage focus_image = FileUtils.field.getSubimage(0, 18, 48, 18);
    public Color static_color, focus_color;
    public boolean ultraspecial;
    
    public SpecialTextField(int x, int y, int width, int height, Color color, String text) {
        setOpaque(false);
        setBorder(null);
        setCaretColor(color);
        setForeground(color);
        setSelectionColor(new Color(51, 153, 255));
        setSelectedTextColor(Color.WHITE);
        setHorizontalAlignment(CENTER);
        setBounds(x, y, width, height);
        setText(text);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        static_color = focus_color = Color.BLACK;
    }
    
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(static_image != null && focus_image != null) {
            if(isFocusOwner()) {
                g2d.drawImage(focus_image, 0, 0, width, height, null);
                repaint();
            }else g2d.drawImage(static_image, 0, 0, width, height, null);
        }
        if(isFocusOwner()) setForeground(focus_color);
        else setForeground(static_color);
        g2d.dispose();
        super.paintComponent(g);
    }
    
}
