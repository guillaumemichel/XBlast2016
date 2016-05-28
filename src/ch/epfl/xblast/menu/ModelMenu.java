package ch.epfl.xblast.menu;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Level;
import ch.epfl.xblast.server.mapEditor.MapEditor;

public final class ModelMenu {
    private JLabel title;
    private JLabel names;
    private JButton create;
    private JButton join;
    private JLabel ipText;
    private JButton backJoin;
    private JTextField ipField;
    private JButton ipJoin;
    private JLabel joinTitle;
    private JButton quit;
    private Font bigButtonFont;
    private Font titleFont;
    private JLabel waiting;
    private JLabel selectBoard;
    private JLabel createTitle;
    private GButton rb1;
    private GButton rb2;
    private GButton rb3;
    private GButton rb4;
    private JButton startServer;
    private JLabel won;
    private JLabel nobody;
    private JButton menu;
    private JSpinner time;

    
    public ModelMenu(){
        setFonts();
        setTitle();
        setNames();
        setCreate();
        setJoin();
        setIpText();
        setBackJoin();
        setIpField();
        setIpJoin();
        setJoinTitle();
        setQuit();
        setWaiting();
        setSelectBoard();
        setCreateTitle();
        setRadioMap();
        setStartServer();
        setWon();
        setNobody();
        setMenu();
        setTime();
    }
    
    private void setFonts(){
        bigButtonFont = new Font("Arial",Font.PLAIN,36);
        titleFont = new Font("Arial",Font.PLAIN,70);
    }
        
    private void setTitle(){
        title = new JLabel("XBlast");
        title.setFont(new Font("GB18030 Bitmap",Font.BOLD,86));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JLabel getTitle(){ return title;}
    
    private void setNames(){
        names = new JLabel("By Michel & Vandenbroucque",SwingConstants.CENTER);
        names.setFont(new Font("Arial",Font.ITALIC,28));
        names.setMaximumSize(new Dimension(450,40));
        names.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JLabel getNames(){ return names;}
    
    private void setCreate(){
        create = new JButton("Create Game");
        create.setFont(bigButtonFont);
        create.setMaximumSize(new Dimension(450,100));
        create.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JButton getCreate(){ return create;}
    
    private void setJoin(){
        join = new JButton("Join Game");
        join.setFont(bigButtonFont);
        join.setMaximumSize(new Dimension(450,100));
        join.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JButton getJoin(){ return join;}
    
    private void setIpText(){
        ipText = new JLabel("Select IP : ");
        ipText.setFont(new Font("Arial",Font.BOLD,20));
        ipText.setHorizontalAlignment(SwingConstants.RIGHT);
    }
    public JLabel getIpText(){ return ipText;}
    
    private void setBackJoin(){
        backJoin = new JButton("Back");
        backJoin.setFont(bigButtonFont);
        backJoin.setMaximumSize(new Dimension(450,100));
        backJoin.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JButton getBackJoin(){ return backJoin;}
    
    private void setIpField(){
        ipField = new JTextField();
        ((AbstractDocument)ipField.getDocument()).setDocumentFilter(new IpFilter());
        ipField.setFont(new Font("Arial",Font.PLAIN,20));
        ipField.setMaximumSize(new Dimension(200,50));
        ipField.setHorizontalAlignment(SwingConstants.CENTER);;
    }
    public JTextField getIpField(){ return ipField;}
    
    private void setIpJoin(){
        ipJoin = new JButton("Connect server");
        ipJoin.setFont(new Font("Arial",Font.BOLD,20));
        ipJoin.setMaximumSize(new Dimension(200,50));
    }
    public JButton getIpJoin(){ return ipJoin;}
    
    private void setJoinTitle(){
        joinTitle = new JLabel("Join a game");
        joinTitle.setFont(titleFont);
        joinTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JLabel getJoinTitle(){ return joinTitle;}
    
    private void setQuit(){
        quit = new JButton("Quit");
        quit.setFont(bigButtonFont);
        quit.setMaximumSize(new Dimension(300,100));
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JButton getQuit(){ return quit;}
    
    private void setWaiting(){
        waiting = new JLabel();
        waiting.setFont(bigButtonFont);
        waiting.setMaximumSize(new Dimension(700,200));
    }
    public JLabel getWaiting(int n){
        if (n>1) waiting.setText("Waiting for "+n+" more players");
        else waiting.setText("Waiting for 1 more player");
        
        return waiting;
    }
    
    private void setSelectBoard(){
        selectBoard = new JLabel("Select board");
        selectBoard.setFont(new Font("Arial",Font.PLAIN,20));
        selectBoard.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JLabel getSelectBoard(){ return selectBoard;}
    
    private void setCreateTitle(){
        createTitle = new JLabel("Create game");
        createTitle.setFont(titleFont);
        createTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JLabel getCreateTitle(){ return createTitle;}
    
    private void setRadioMap(){
        rb1=new GButton(new JButton(new ImageIcon(ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(6))));
        rb2=new GButton(new JButton(new ImageIcon(ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(26))));
        rb3=new GButton(new JButton(new ImageIcon(ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(6))));
        rb4=new GButton(new JButton(new ImageIcon(ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(53))));
        rb1.setGamestate(Level.DEFAULT_LEVEL.gameState());
        rb2.setGamestate(Level.DEFAULT_LEVEL_2.gameState());
        rb3.setGamestate(Level.DEFAULT_LEVEL.gameState());
        rb1.getButton().setSelected(true);
        rb4.getB().addActionListener(e -> {
            MapEditor.main();            
        });
    }
    public GButton getRB1(){ return rb1;}
    public GButton getRB2(){ return rb2;}
    public GButton getRB3(){ return rb3;}
    public GButton getRB4(){ return rb4;}

    private void setStartServer(){
        startServer = new JButton("Start server");
        startServer.setFont(bigButtonFont);
        startServer.setMaximumSize(new Dimension(450,100));
        startServer.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JButton getStartServer(){ return startServer;}
    
    private void setWon(){
        won = new JLabel(" won !");
        won.setFont(new Font("Arial",Font.PLAIN,36));
    }
    public JLabel getWon(){ return won;}
    
    private void setNobody(){
        nobody = new JLabel("Nobody");
        nobody.setFont(new Font("Arial",Font.PLAIN,36));
    }
    public JLabel getNobody(){ return nobody;}
    
    private void setMenu(){
        menu = new JButton("Menu");
        menu.setFont(bigButtonFont);
        menu.setMaximumSize(new Dimension(450,100));
    }
    public JButton getMenu(){ return menu;}

    private void setTime(){
        time = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        time.setMaximumSize(new Dimension(200,100));
        ((DefaultEditor) time.getEditor()).getTextField().setEditable(false);    }
    public JSpinner getTime(){ return time;}
}
    
class GButton extends JPanel{
    private GameState g;
    private JRadioButton radio;
    private JButton b;
    public GButton(JButton b){
        setLayout(new GridBagLayout());
        radio = new JRadioButton();
        add(radio);
        add(b);
        b.addActionListener(e -> radio.setSelected(true));
        radio.setEnabled(false);
        this.b=b;
        setMaximumSize(new Dimension(100,105));
    }
    public void setGamestate(GameState g){ this.g=g; }
    public GameState gamestate(){ return g;}
    public JRadioButton getButton(){ return radio;}
    public JButton getB(){ return b;}
}

class IpFilter extends DocumentFilter { 

    @Override
    public void insertString(DocumentFilter.FilterBypass bypass, int offset, String str, AttributeSet set)
            throws BadLocationException {
        int l = str.length();
        boolean valid = true;
               
        for (int i = 0; i < l; i++){
            if (!(str.charAt(i)==46 || (str.charAt(i)>=48 && str.charAt(i)<=57))) {
                valid = false;
                break;
                
            }
        }
        if (valid)
            super.insertString(bypass, offset, str, set);
        else
            Toolkit.getDefaultToolkit().beep();
    }

    @Override
    public void replace(DocumentFilter.FilterBypass bypass, int offset, int length, String str, AttributeSet set)
            throws BadLocationException {
        int l = str.length();
        boolean valid = true;
               
        for (int i = 0; i < l; i++){
            if (!(str.charAt(i)==46 || (str.charAt(i)>=48 && str.charAt(i)<=57))) {
                valid = false;
                break;
                
            }
        }
        if (valid)
            super.replace(bypass, offset, length, str, set);
        else
            Toolkit.getDefaultToolkit().beep();
    }
}
