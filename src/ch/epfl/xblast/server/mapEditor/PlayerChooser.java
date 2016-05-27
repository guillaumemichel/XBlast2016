package ch.epfl.xblast.server.mapEditor;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.epfl.xblast.PlayerID;

@SuppressWarnings("serial")
public final class PlayerChooser  extends JPanel{
    private PlayerButton currentPlayer = new PlayerButton(1);
    private JCheckBox playerSelection = new JCheckBox("<html>Player<br>selection</html>");
    
    public PlayerChooser(){
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(playerSelection);
        this.add(Box.createRigidArea(new Dimension(0,30)));
        addPlayerSelectors();
        addCurrentPlayer();
    }
    
    public JCheckBox playerSelection(){
        return playerSelection;
    }
    
    public PlayerButton currentPlayer(){
        return currentPlayer;
    }
    
    private void addPlayerSelectors(){
        for (int i = 1; i <= PlayerID.values().length; ++i) {
            PlayerButton player = new PlayerButton(i);
            player.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    PlayerID playerOfparentPlayerButton = ((PlayerButton)e.getSource()).player().id();
                    currentPlayer.setPlayer(playerOfparentPlayerButton.ordinal()+1);
                }
            });
            this.add(player);
            this.add(Box.createRigidArea(new Dimension(0,25)));
            if(i == PlayerID.values().length)
                this.add(Box.createRigidArea(new Dimension(0,40)));
        }
    }
    
    private void addCurrentPlayer(){
        this.add(new JLabel("<html>Current <br> Player: </html>"));
        this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(this.currentPlayer);
    }

}
