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

/**
 * A custom JPanel representing a player chooser
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
@SuppressWarnings("serial")
public final class PlayerChooser  extends JPanel{
    private PlayerButton currentPlayer = new PlayerButton(1);
    private JCheckBox playerSelection = new JCheckBox("<html>Player<br>selection</html>");
    
    /**
     * Constructs a player chooser
     */
    public PlayerChooser(){
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(playerSelection);
        this.add(Box.createRigidArea(new Dimension(0,30)));
        addPlayerSelectors();
        addCurrentPlayer();
    }
    
    /**
     * Returns the check box indicating if we use the player chooser
     * 
     * @return
     *      The check box indicating if we use the player chooser
     */
    public JCheckBox playerSelection(){
        return playerSelection;
    }
    
    /**
     * Return the current chosen player
     * 
     * @return
     *      The current chosen player
     */
    public PlayerButton currentPlayer(){
        return currentPlayer;
    }
    
    private void addPlayerSelectors(){
        for (int i = 1; i <= PlayerID.values().length; ++i) {
            PlayerButton player = new PlayerButton(i);
            player.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    PlayerID playerOfparentPlayerButton = ((PlayerButton)e.getSource()).playerID();
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
