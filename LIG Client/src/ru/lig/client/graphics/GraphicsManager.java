/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.graphics;

/**
 *
 * @author Константин
 */
public class GraphicsManager {
    
    public Frame graphics;
    
    public void viewAuthorization() {
        ImageUtils.loadImages();
        graphics = new Frame();
    }
    
    public void viewMainFrame() {
        graphics.loadMainFrame();
    }
    
}
