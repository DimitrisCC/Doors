package resources;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * A simple resource loader class
 * It's needed for the executable jar
 */

public class ResourceLoader {
 
    static ResourceLoader resourceLoader = new ResourceLoader();
     
    public static Image loadImage(String imageName){
        return Toolkit.getDefaultToolkit().getImage(resourceLoader.getClass().getResource("images/"+imageName));
    }
    
}