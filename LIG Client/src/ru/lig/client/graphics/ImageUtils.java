/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.graphics;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.imageio.ImageIO;
import static ru.lig.client.utils.BaseUtils.*;
import static ru.lig.client.graphics.FileUtils.*;

/**
 *
 * @author Константин
 */
public class ImageUtils {
    
    public static void loadImages() {
        button = openImage("button.png");
        favicon = openImage("favicon.png");
        background = openImage("bg2.jpg");
        field = openImage("field.png");
        black_field = openImage("black_field.png");
        white_field = openImage("white_field.png");
        border = openImage("border.png");
        dragbutton = openImage("dragbutton.png");
        loadQuestionImages();
    }
    
    public static BufferedImage openImage(String name) {
        try {
            BufferedImage image = ImageIO.read(ImageUtils.class.getResource("/ru/lig/client/graphics/data/" + name));
            logln("Local image loaded: " + name);
            return image;
        }catch(Exception ex) {
            warn("Can't load local image!", false);
            ex.printStackTrace();
            warn("", true);
            return null;
        }
    }
    
    public static void loadQuestionImages() {
        BufferedImage image;
        for(int i = 1; i <= 30; i++) {
            BufferedImage[] images = new BufferedImage[4];
            Arrays.fill(images, null);
            for(int j = 1; j <= 4; j++) try {
                image = ImageIO.read(ImageUtils.class.getResource("/ru/lig/client/questions/" + i + " " + j + ".jpg"));
                images[j - 1] = image;
            }catch(Exception ex) {
                break;
            }
            FileUtils.questionImages.put(i, images);
        }
        logln("Loaded question images!");
    }
    
    public static BufferedImage fill(BufferedImage texture, int w, int h) {
        int sizex = texture.getWidth();
        int sizey = texture.getHeight();
        BufferedImage res = new BufferedImage(w, h, 2);
        for(int i = 0; i < w/sizex; i++) for(int k = 0; k < h/sizey; k++) res.getGraphics().drawImage(texture, i*sizex, k*sizey, null);
        return res;
    }
    
    public static BufferedImage getPanel(int w, int h, BufferedImage img) {
        BufferedImage res = new BufferedImage(w, h, 2);
	int onew = img.getWidth() / 3;
	int oneh = img.getHeight() / 3;
	
	res.getGraphics().drawImage(img.getSubimage(0, 0, onew, oneh), 0, 0, onew, oneh, null);
	res.getGraphics().drawImage(img.getSubimage(onew * 2, 0, onew, oneh), w - onew, 0, onew, oneh, null);
	res.getGraphics().drawImage(img.getSubimage(0, oneh * 2, onew, oneh), 0, h - oneh, onew, oneh, null);
	res.getGraphics().drawImage(img.getSubimage(onew, oneh, onew * 2, oneh * 2), w - onew, h - oneh, onew, oneh, null);

	try{res.getGraphics().drawImage(fill(img.getSubimage(onew, 0, onew, oneh), w - onew * 2, oneh), onew, 0, w - onew * 2, oneh, null);}catch(Exception e){}
	try{res.getGraphics().drawImage(fill(img.getSubimage(0, oneh, onew, oneh), onew, h - oneh * 2), 0, oneh, onew, h - oneh * 2, null);}catch(Exception e){}
	try{res.getGraphics().drawImage(fill(img.getSubimage(onew, oneh * 2, onew, oneh), w - onew * 2, oneh), onew, h - oneh, w - onew * 2, oneh, null);}catch(Exception e){}
	try{res.getGraphics().drawImage(fill(img.getSubimage(onew * 2, oneh, onew, oneh), onew, h - oneh * 2), w - onew, oneh, onew, h - oneh * 2, null);}catch(Exception e){}
	try{res.getGraphics().drawImage(fill(img.getSubimage(onew, oneh, onew, oneh), w - onew * 2, h - oneh * 2), onew, oneh, w - onew * 2, h - oneh * 2, null);}catch(Exception e){}

	return res;
    }
    
}
