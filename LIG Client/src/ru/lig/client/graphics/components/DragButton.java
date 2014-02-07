package ru.lig.client.graphics.components;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ButtonModel;
import javax.swing.JButton;

public class DragButton extends JButton
{
	private static final long serialVersionUID = 1L;

	public BufferedImage image1 = (BufferedImage) createImage(1, 1);
	public BufferedImage image2 = (BufferedImage) createImage(1, 1);
	public BufferedImage image3 = (BufferedImage) createImage(1, 1);
	
	public DragButton()
	{
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);
		setFocusable(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	protected void paintComponent(Graphics maing)
	{
		ButtonModel buttonModel = getModel();
		Graphics2D g = (Graphics2D) maing.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(buttonModel.isRollover())
		{
			if(buttonModel.isPressed())
			{
				g.drawImage(image3, 0, 0, getWidth(), getHeight(), null);
			}
			else g.drawImage(image2, 0, 0, getWidth(), getHeight(), null);
		} else g.drawImage(image1, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		super.paintComponent(maing);
	}
        
        public void load(BufferedImage dragbutton, boolean hide) {
            if(hide) {
                image1 = dragbutton.getSubimage(0, 0, dragbutton.getWidth() / 3, dragbutton.getHeight() / 2);
                image2 = dragbutton.getSubimage(dragbutton.getWidth() / 3, 0, dragbutton.getWidth() / 3, dragbutton.getHeight() / 2);
                image3 = dragbutton.getSubimage(dragbutton.getWidth() / 3 * 2, 0, dragbutton.getWidth() / 3, dragbutton.getHeight() / 2);
            }else {
                image1 = dragbutton.getSubimage(0, dragbutton.getHeight() / 2, dragbutton.getWidth() / 3, dragbutton.getHeight() / 2);
                image2 = dragbutton.getSubimage(dragbutton.getWidth() / 3, dragbutton.getHeight() / 2, dragbutton.getWidth() / 3, dragbutton.getHeight() / 2);
                image3 = dragbutton.getSubimage(dragbutton.getWidth() / 3 * 2, dragbutton.getHeight() / 2, dragbutton.getWidth() / 3, dragbutton.getHeight() / 2);
            }
        }
}