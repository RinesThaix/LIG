package ru.lig.client.graphics.components;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Dragger extends JPanel{
    private static final long serialVersionUID = 1L;
    private int x = 0;
    private int y = 0;
    public JLabel title = new JLabel();
    public final JFrame frame;
    public Dragger(JFrame f) {
        frame = f;
        setOpaque(false);
        setLayout(new BorderLayout());
        add(title, BorderLayout.CENTER);
        setBorder(new EmptyBorder(0, 10, 0, 10));
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                frame.setLocation(e.getX() + frame.getX() - x,
                        e.getY() + frame.getY() - y);
            }
        });
        addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
            public void mouseClicked(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
        });
    }
    
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
    }
}
