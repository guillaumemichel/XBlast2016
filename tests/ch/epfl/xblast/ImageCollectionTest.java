package ch.epfl.xblast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

import org.junit.Test;

import ch.epfl.xblast.client.ImageCollection;

public class ImageCollectionTest {

    @Test (expected=NullPointerException.class)
    public void imageCollectionThrowsExceptionOnInexistingFolder() throws NoSuchElementException, URISyntaxException, IOException{
        ImageCollection col = new ImageCollection("prout");
        Image im = col.image(0);
    }
    
    @Test (expected=NoSuchElementException.class)
    public void imageThrowsExceptionOnInexistingIndex() throws NoSuchElementException, URISyntaxException, IOException{
        ImageCollection col = new ImageCollection("explosion");
        Image im = col.image(100);
    }
    
    @Test
    public void imageOrNullReturnsNullOnInexistingFile() throws NoSuchElementException, URISyntaxException, IOException{
        ImageCollection col = new ImageCollection("explosion");
        assertNull(col.imageOrNull(100));
    }

}