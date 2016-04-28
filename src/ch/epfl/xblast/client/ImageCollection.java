package ch.epfl.xblast.client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

public final class ImageCollection {
    private final String dirName;
    
    private final List<Image> imagesOfDir=Collections.unmodifiableList(loadFiles(this.dirName));
    
    public ImageCollection(String dirName){
        this.dirName=dirName;
    }
    
    private static List<Image> loadFiles(String dirName){
        List<Image> images = new ArrayList<>();
        File image;
        
        try{
            image=new File(ImageCollection.class
                    .getClassLoader()
                    .getResource(dirName)
                    .toURI());
        }catch(URISyntaxException e){
            image=null;
        }
        
        for (File file : image.listFiles()) {
            try{
                images.add(ImageIO.read(file));
            }catch(IOException e){
                
            }
        }  
        return images;
    }
    
    public Image imageOrNull(int index) throws IOException{
        return imagesOfDir.get(index);
    }
    
    public Image image(int index) throws URISyntaxException, IOException, NoSuchElementException{
        Image image = imageOrNull(index);
        if(image==null)
            throw new NoSuchElementException();
        
        return image;
    }

}
