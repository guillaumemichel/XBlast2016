package ch.epfl.xblast.client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

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
    private final Map<Byte, Image> imagesOfDir;
    
    public final static ImageCollection IMAGE_COLLECTION_BLOCK = new ImageCollection("block");
    public final static ImageCollection IMAGE_COLLECTION_EXPLOSION = new ImageCollection("explosion");
    public final static ImageCollection IMAGE_COLLECTION_SCORE = new ImageCollection("score");
    public final static ImageCollection IMAGE_COLLECTION_PLAYER = new ImageCollection("player");
    
    private final static int SIZE_OF_NUMBER_IMAGE = 3;
    
    /**
     * Constructs an image collection of the given directory name that contains the images
     * 
     * @param dirName
     *      The name of the directory where the images are
     *      
     */
    public ImageCollection(String dirName){
        this.dirName=dirName;
        imagesOfDir = Collections.unmodifiableMap(loadImages());
    }
    
    private Map<Byte, Image> loadImages(){
        Map<Byte, Image> images = new TreeMap<>();
        File dir=null;
        
        try{
            dir=new File(ImageCollection.class
                    .getClassLoader()
                    .getResource(this.dirName)
                    .toURI());
        }catch(URISyntaxException e){

        }
        
        for (File file : dir.listFiles()) {
            try{
                images.put(Byte.parseByte(file.getName().substring(0, SIZE_OF_NUMBER_IMAGE)), ImageIO.read(file));
            }catch(IOException e){
                
            }catch(NumberFormatException e){
                
            }
        }  
        return images;
    }
    
    /**
     * Returns the image that corresponds to the index given in this image collection
     * 
     * @param index
     *      The index that allows to retrieve the expected image 
     *      
     * @return
     *      The image of the collection at the index given, or null if there is no corresponding image
     */
    public Image imageOrNull(int index){
        if(imagesOfDir.containsKey((byte) index))
            return imagesOfDir.get((byte)index);
        
        return null;
    }
    
    /**
     * Returns the image that corresponds to the index given in this image collection
     * 
     * @param index
     *      The index that allows to retrieve the expected image
     *      
     * @return
     *      The image of the collection at the index given
     *      
     * @throws NoSuchElementException
     *      If there is no corresponding image
     */
    public Image image(int index) throws NoSuchElementException{
        Image image = imageOrNull(index);
        if(image==null)
            throw new NoSuchElementException();
        
        return image;
    }

}
