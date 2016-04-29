package ch.epfl.xblast.client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * An image collection
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class ImageCollection {
    private final String dirName;
    
    private final List<Image> imagesOfDir;
    
    public ImageCollection(String dirName) throws NullPointerException{
        this.dirName=dirName;
        imagesOfDir = Collections.unmodifiableList(Objects.requireNonNull(loadImages(this.dirName)));
    }
    
    private static List<Image> loadImages(String dirName){
        List<Image> images = new ArrayList<>();
        File dir;
        
        try{
            dir=new File(ImageCollection.class
                    .getClassLoader()
                    .getResource(dirName)
                    .toURI());
        }catch(URISyntaxException e){
            dir=null;
            throw new NullPointerException("Invalid diectory name !");
        }
        
        for (File file : dir.listFiles()) {
            try{
                images.add(ImageIO.read(file));
            }catch(IOException e){
                
            }
        }  
        return images;
    }
    
    public Image imageOrNull(int index){
        return imagesOfDir.get(index);
    }
    
    public Image image(int index) throws NoSuchElementException{
        Image image = imageOrNull(index);
        if(image==null)
            throw new NoSuchElementException();
        
        return image;
    }

}
