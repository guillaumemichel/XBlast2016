package ch.epfl.xblast.server.mapEditor;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

@SuppressWarnings("serial")
public final class Options extends JPanel{
    private GridOfBlocks associatedGrid;

    public Options(GridOfBlocks associatedGrid){
        this.associatedGrid = associatedGrid;
        this.setLayout(new FlowLayout());
        JButton done = new JButton("Use !");
        done.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(Options.this.associatedGrid.toListOfBytes());
                SwingUtilities.windowForComponent(Options.this).dispose();
            }
        });
        this.add(done);
        
        addButtonLoadFile();
        addButtonSaveGrid();
    }

    private void addButtonLoadFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        
        JButton loadFile = new JButton("Load");
        loadFile.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                File map = null;
                if(fileChooser.showOpenDialog(getParent())== JFileChooser.APPROVE_OPTION)
                    map = fileChooser.getSelectedFile();

                List<Integer> l = new ArrayList<>();
                try(InputStream s =new BufferedInputStream(new FileInputStream(map))){
                    int b;
                    while((b = s.read()) != -1){
                        int realValue = Character.getNumericValue(b);
                        if(realValue == 3 || realValue > 5)
                            throw new IllegalArgumentException("One value in the file is invalid !");
                            
                        l.add(realValue);
                    }
                      
                    if(l.size() != 195)
                        throw new IllegalArgumentException("The size of the file must be 195 !");
                    else
                        Options.this.associatedGrid.loadGridfromListOfBytes(l);
                }catch(FileNotFoundException exception){
                    
                }catch(IOException exception){
                    
                }catch(IllegalArgumentException exception){
                    JOptionPane.showMessageDialog(Options.this.getParent(), exception.getMessage());
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
                    File toSave = saveChooser.getSelectedFile();

                    try(FileOutputStream out = new FileOutputStream(toSave)){
                        Byte[] bByte = new Byte[Options.this.associatedGrid.toListOfBytes().size()];
                        Options.this.associatedGrid.toListOfBytes().toArray(bByte);
                        byte[] bbyte = new byte[bByte.length];
                        int j = 0;
                        for(Byte b : bByte)
                            bbyte[j++] = b;
                        out.write(bbyte);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    
                }
            }
            
        });
        this.add(saveGrid);
    }
}
