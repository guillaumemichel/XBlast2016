package ch.epfl.xblast.server.mapEditor;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.server.Block;

/**
 * A custom JPanel representing an option panel
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
@SuppressWarnings("serial")
public final class Options extends JPanel{

    /**
     * Constructs the options panel
     */
    public Options(){
        this.setLayout(new FlowLayout());
        
        addButtonLoadFile();
        addButtonSaveGrid();
        addButtonUse();
    }

    private void addButtonLoadFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        
        JButton loadFile = new JButton("Load");
        loadFile.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = null;
                if(fileChooser.showOpenDialog(getParent())== JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();

                    List<Byte> l = new ArrayList<>();
                    try(InputStream s =new BufferedInputStream(new FileInputStream(file))){
                        int b;
                        while((b = s.read()) != -1){
                            int realValue = Character.getNumericValue(b);
                            if(realValue == Block.CRUMBLING_WALL.ordinal() || realValue > Block.BONUS_RANGE.ordinal())
                                throw new IllegalArgumentException("One value in the file is invalid !");
                                
                            l.add((byte)realValue);
                        }
                          
                        if(l.size() != Cell.COUNT){
                            throw new IllegalArgumentException("The size of the file must be of 195 bytes!");
                        }else{
                            GridOfBlocks parentGrid = ((MapEditor) SwingUtilities.windowForComponent(Options.this)).grid();
                            parentGrid.loadGridfromListOfBytes(l);
                        }
                        
                    }catch(FileNotFoundException exception){
                        
                    }catch(IOException exception){
                        
                    }catch(IllegalArgumentException exception){
                        JOptionPane.showMessageDialog(Options.this.getParent(), exception.getMessage());
                    }
                }
            }
            });
        this.add(loadFile);
    }
    
    private void addButtonSaveGrid(){
        JButton saveGrid = new JButton("Save");
        JFileChooser saveChooser = new JFileChooser();
        
        saveGrid.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(saveChooser.showSaveDialog(getParent()) == JFileChooser.APPROVE_OPTION){
                    File toSave = new File(saveChooser.getSelectedFile()+".txt");

                    try(FileWriter out = new FileWriter(toSave)){
                        GridOfBlocks parentGrid = ((MapEditor) SwingUtilities.windowForComponent(Options.this)).grid();

                        List<Byte> mapIntegers = parentGrid.toListOfBytes();
                        for(byte b : mapIntegers)
                            out.write(b+"");
                        out.flush();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }  
                }
            }
            
        });
        this.add(saveGrid);
    }
    
    private void addButtonUse(){
        JButton done = new JButton("Use !");
        done.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(Options.this.associatedGrid.toListOfBytes());
                SwingUtilities.windowForComponent(Options.this).dispose();
            }
        });
        this.add(done);
    }
}
