package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageLoader {
    
    private static ImageLoader instance;
    
    private static Map<String, BufferedImage> imageCache = new HashMap<>();
    
    private ImageLoader() {}
    
    private BufferedImage _load(String res) {
        try {
            return ImageIO.read(getClass().getResource(res));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static BufferedImage load(String res) {
        if (imageCache.containsKey(res)) return imageCache.get(res);
        else {
        	if (instance == null) instance = new ImageLoader();
        	BufferedImage img =  instance._load(res);
        	imageCache.put(res, img);
        	return img;
        }
    }
    
}
