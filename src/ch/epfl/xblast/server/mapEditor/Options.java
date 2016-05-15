package ch.epfl.xblast.server.mapEditor;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public final class Options extends JPanel{
    private GridOfBlocks associatedGrid;

    public Options(GridOfBlocks associatedGrid){
        this.associatedGrid = associatedGrid;
        this.setLayout(new FlowLayout());
        JButton done = new JButton();
        done.setText("Done !");
        done.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(Options.this.associatedGrid.toListOfIntegers());
                MapEditor parent =(MapEditor) SwingUtilities.getWindowAncestor(Options.this);
                parent.setBoard(Options.this.associatedGrid.toBoard());
                SwingUtilities.windowForComponent(Options.this).dispose();
            }
        });
        this.add(done);
    }

}
