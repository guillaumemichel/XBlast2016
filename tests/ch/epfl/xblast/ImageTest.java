package ch.epfl.xblast;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JFrame;

import ch.epfl.xblast.client.ImageCollection;

public class ImageTest {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable()
        {
            public void run(){
                ImageFrame frame = new ImageFrame(new ImageCollection("explosion").image(5));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        }
        );
    }
}

class ImageFrame extends JFrame{

    public ImageFrame(Image image){
        setTitle("ImageTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        ImageComponent component = new ImageComponent(image);
        add(component);

    }

    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 400;
}


class ImageComponent extends JComponent{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Image image;
    public ImageComponent(Image image){
        this.image=image;
    }
    public void paintComponent (Graphics g){
        if(image == null) return;
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);

        g.drawImage(image, 50, 50, this);

        for (int i = 0; i*imageWidth <= getWidth(); i++)
            for(int j = 0; j*imageHeight <= getHeight();j++)
                if(i+j>0) g.copyArea(0, 0, imageWidth, imageHeight, i*imageWidth, j*imageHeight);
    }

}