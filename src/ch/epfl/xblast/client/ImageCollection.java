package ch.epfl.xblast.client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

public final class ImageCollection {
    private final String dirName;
    
    public ImageCollection(String dirName){
        this.dirName=dirName;
    }
    
    public Image imageOrNull(int index) throws URISyntaxException, IOException{
        File dir = new File(ImageCollection.class.getClassLoader().getResource(dirName).toURI());
        File[] images=dir.listFiles();
        
        for(File image : images)
            if(index==Integer.parseInt(image.getName().substring(0, 3)))
                return ImageIO.read(image);
        
        return null;
    }
    
    public Image image(int index) throws URISyntaxException, IOException, NoSuchElementException{
        Image image = imageOrNull(index);
        if(image==null)
            throw new NoSuchElementException();
        
        return image;
    }

}
