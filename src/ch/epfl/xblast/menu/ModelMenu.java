package ch.epfl.xblast.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
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
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.server.ServerBis;

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
    private JLabel selectBoard;
    private JLabel createTitle;
    private JButton startServer;
    private JLabel won;
    private JLabel nobody;
    private JButton menu;
    private JSpinner time;
    private JLabel duration;
    private Font littleBold;
    private Font littlePlain;
    private JLabel minutes;
    private JLabel players;
    private JSpinner nPlayers;
    private JRadioButton b1;
    private JRadioButton b2;
    private JRadioButton b3;
    private JRadioButton b4;
    private JRadioButton[] group;
    private JLabel i1;
    private JLabel i2;
    private JLabel i3;
    private JLabel i4;
    private JPanel mapsUp;
    private JPanel mapsDown;
    private JPanel maps;
    private ButtonGroup bg;
    private JButton backConnect;
    private JButton backServer;
    private JLabel connecting;
    private JLabel ip;

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
        setSelectBoard();
        setCreateTitle();
        setRadioMap();
        setStartServer();
        setWon();
        setNobody();
        setMenu();
        setTime();
        setDuration();
        setMinutes();
        setPlayers();
        setNPlayers();
        setBackConnect();
        setBackServer();
        setConnecting();
        setIp();
    }
    
    private void setFonts(){
        bigButtonFont = new Font("Arial",Font.PLAIN,36);
        titleFont = new Font("Arial",Font.PLAIN,70);
        littleBold = new Font("Arial",Font.BOLD,20);
        littlePlain = new Font("Arial",Font.PLAIN,20);
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
        ipText.setFont(littleBold);
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
        ipField.setFont(littlePlain);
        ipField.setMaximumSize(new Dimension(200,50));
        ipField.setHorizontalAlignment(SwingConstants.CENTER);;
    }
    public JTextField getIpField(){ return ipField;}
    
    private void setIpJoin(){
        ipJoin = new JButton("Connect server");
        ipJoin.setFont(littleBold);
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
    
    private void setSelectBoard(){
        selectBoard = new JLabel("Select board");
        selectBoard.setFont(littlePlain);
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
        b1=new JRadioButton();
        b2=new JRadioButton();
        b3=new JRadioButton();
        b4=new JRadioButton();
        i1=new JLabel(new ImageIcon(ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(6)));
        i2=new JLabel(new ImageIcon(ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(26)));
        i3=new JLabel(new ImageIcon(ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(6)));
        i4=new JLabel("Custom Map");
        i4.setFont(littlePlain);
        i4.setBorder(new LineBorder(Color.BLACK, 1));;
        i1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                b1.setSelected(true);
            }
        });
        i2.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                b2.setSelected(true);
            }
        });
        i3.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                b3.setSelected(true);
            }
        });
        i4.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                b4.setSelected(true);
                Main.mapEdit();
            }
        });
        bg = new ButtonGroup();
        bg.add(b1);
        bg.add(b2);
        bg.add(b3);
        bg.add(b4);
        
        group = new JRadioButton[4];
        group[0]=b1;
        group[1]=b2;
        group[2]=b3;
        group[3]=b4;
        
        b1.setSelected(true);
        b4.addActionListener(e -> Main.mapEdit());
        
        mapsUp = new JPanel();
        mapsUp.setLayout(new BoxLayout(mapsUp,0));
        mapsUp.add(b1);
        mapsUp.add(i1);
        mapsUp.add(Box.createRigidArea(new Dimension(30,0)));
        mapsUp.add(b2);
        mapsUp.add(i2);
        mapsUp.add(Box.createRigidArea(new Dimension(30,0)));
        mapsUp.add(b3);
        mapsUp.add(i3);
        
        mapsDown = new JPanel();
        mapsDown.setLayout(new BoxLayout(mapsDown,0));
        mapsDown.add(b4);
        mapsDown.add(i4);
        
        maps = new JPanel();
        maps.setLayout(new BoxLayout(maps,1));
        maps.add(mapsUp);
        maps.add(Box.createRigidArea(new Dimension(0,50)));       
        maps.add(mapsDown);
    }
    public JPanel getRadioMap(){ return maps;}
    public int mapSelected(){ 
        for (int i=0;i<group.length;++i)
            if (group[i].isSelected())
                return i;
        return 0;
    }

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
        time.setMaximumSize(new Dimension(45,30));
        ((DefaultEditor) time.getEditor()).getTextField().setEditable(false);
        }
    public JSpinner getTime(){ return time;}
    
    private void setDuration(){
        duration = new JLabel("Game duration : ");
        duration.setFont(littlePlain);
        duration.setHorizontalTextPosition(SwingConstants.RIGHT);
    }
    public JLabel getDuration(){ return duration;}
    
    private void setMinutes(){
        minutes = new JLabel(" minutes");
        minutes.setFont(littlePlain);
        minutes.setHorizontalTextPosition(SwingConstants.LEFT);
    }
    public JLabel getMinutes(){ return minutes;}
    
    private void setPlayers(){
        players = new JLabel("Number of players : ");
        players.setFont(littlePlain);
        players.setHorizontalTextPosition(SwingConstants.RIGHT);
    }
    public JLabel getPlayers(){ return players;}
    
    private void setNPlayers(){
        nPlayers = new JSpinner(new SpinnerNumberModel(4, 1, 4, 1));
        nPlayers.setMaximumSize(new Dimension(45,30));
        ((DefaultEditor) nPlayers.getEditor()).getTextField().setEditable(false);
    }
    public JSpinner getNPlayers(){ return nPlayers;}
    
    private void setBackConnect(){
        backConnect = new JButton("Cancel");
        backConnect.setFont(littleBold);
        backConnect.setMaximumSize(new Dimension(100,60));
        backConnect.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JButton getBackConnect(){ return backConnect;}
    
    private void setBackServer(){
        backServer = new JButton("Cancel");
        backServer.setFont(littleBold);
        backServer.setMaximumSize(new Dimension(100,60));
        backServer.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JButton getBackServer(){ return backServer;}
    
    private void setConnecting(){
        connecting = new JLabel();
        connecting.setFont(bigButtonFont);
        connecting.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JLabel getConnecting(String str){
        connecting.setText("Connecting "+str+" ...");
        return connecting;
    }
    private void setIp(){
        ip = new JLabel();
        ip.setFont(littlePlain);
        ip.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JLabel getIp(){
        ip.setText("IP : "+ServerBis.getIp());
        return ip;
    }
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
