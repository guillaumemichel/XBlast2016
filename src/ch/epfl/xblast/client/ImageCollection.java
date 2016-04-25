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
        File dir = new File(ImageCollection.class
                .getClassLoader()
                .getResource(dirName)
                .toURI());
        File[] images=dir.listFiles();
        
        for(int i=0;i<images.length;++i)
            if(index==Integer.parseInt(images[i].getName().substring(0, 3)))
                return ImageIO.read(images[i]);
        
        return null;
    }
    
    public Image image(int index) throws URISyntaxException, IOException, NoSuchElementException{
        Image image =imageOrNull(index);
        if(image==null)
            throw new NoSuchElementException();
        
        return image;
    }

}
