package ch.epfl.xblast.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;

import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.client.XBlastComponent;
import ch.epfl.xblast.server.ServerBis;

/**
 * A View, view of the patron MVC
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 */
public final class View {
    private JFrame frame = new JFrame("XBlast 2016");
    private JLabel title,names,ipText,joinTitle,selectBoard,createTitle,won,nobody,duration,minutes,players,
            i1,i2,i3,i4,connecting,ip;
    private JButton create,join,backJoin,ipJoin,quit,startServer,menu,backConnect,backServer;
    private JTextField ipField;
    private Font bigButtonFont,titleFont,littleBold,littlePlain;
    private JSpinner time,nPlayers;
    private JRadioButton b1,b2,b3,b4;
    private JRadioButton[] group;
    private JPanel mapsUp,mapsDown,maps;
    private ButtonGroup bg;
    private XBlastComponent component=new XBlastComponent();
    
    public View(){
        setFrame();
        setComponents();
    }
    
    private final void setFrame(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //frame.setSize(component.getPreferredSize());
        frame.setPreferredSize(new Dimension(960,708));
        frame.pack();
    }
    
    private final void setComponents(){
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
    
    private void setNames(){
        names = new JLabel("By Michel & Vandenbroucque",SwingConstants.CENTER);
        names.setFont(new Font("Arial",Font.ITALIC,28));
        names.setMaximumSize(new Dimension(450,40));
        names.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
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
    
    private void setQuit(){
        quit = new JButton("Quit");
        quit.setFont(bigButtonFont);
        quit.setMaximumSize(new Dimension(300,100));
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public JButton getQuit(){ return quit;}
    
    private void setSelectBoard(){
        selectBoard = new JLabel("Select board");
        selectBoard.setFont(littleBold);
        selectBoard.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void setCreateTitle(){
        createTitle = new JLabel("Create game");
        createTitle.setFont(titleFont);
        createTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void setRadioMap(){
        b1=new JRadioButton();
        b2=new JRadioButton();
        b3=new JRadioButton();
        b4=new JRadioButton();
        i1=new JLabel(new ImageIcon(ImageCollection.IMAGE_COLLECTION_MAPS.imageOrNull(0).getScaledInstance(264, 172, Image.SCALE_SMOOTH)));
        i2=new JLabel(new ImageIcon(ImageCollection.IMAGE_COLLECTION_MAPS.imageOrNull(1).getScaledInstance(264, 172, Image.SCALE_SMOOTH)));
        i3=new JLabel(new ImageIcon(ImageCollection.IMAGE_COLLECTION_MAPS.imageOrNull(2).getScaledInstance(264, 172, Image.SCALE_SMOOTH)));
        i4=new JLabel("Custom Map");
        i4.setFont(littlePlain);
        i4.setOpaque(true);
        i4.setBackground(Color.LIGHT_GRAY);
        i4.setBorder(new LineBorder(Color.BLACK, 1));
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
        b4.addActionListener(e -> ControllerMenu.mapEdit());
        
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
    public int mapSelected(){ 
        for (int i=0;i<group.length;++i)
            if (group[i].isSelected())
                return i;
        return 0;
    }
    public JRadioButton getB4(){ return b4;}
    public JLabel getI4(){ return i4;}

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
    
    private void setNobody(){
        nobody = new JLabel("Nobody");
        nobody.setFont(new Font("Arial",Font.PLAIN,36));
    }
    
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
    
    private void setMinutes(){
        minutes = new JLabel(" minutes");
        minutes.setFont(littlePlain);
        minutes.setHorizontalTextPosition(SwingConstants.LEFT);
    }
    
    private void setPlayers(){
        players = new JLabel("Number of players : ");
        players.setFont(littlePlain);
        players.setHorizontalTextPosition(SwingConstants.RIGHT);
    }
    
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
    private void setIp(){
        ip = new JLabel("IP : "+ServerBis.getIp());
        ip.setFont(littlePlain);
        ip.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private JLabel getConnecting(String str){
        connecting.setText("Connecting "+str+" ...");
        return connecting;
    }
    
    /**
     * Get the frame of the view
     * 
     * @return
     *      the frame of the view
     */
    public final JFrame getFrame(){ return frame;}
    

    /**
     * Get the panel of the Main menu
     * 
     * @return 
     *      The panel "main menu"
     */
    public final JPanel createMenuView(){ 
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        
        panel.add(Box.createRigidArea(new Dimension(0,40)));
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(names);
        panel.add(Box.createRigidArea(new Dimension(0,50)));
        panel.add(create);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(join);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(quit);
        return panel;

    }
    
    /**
     * Get the panel of the menu "Join game"
     * 
     * @return 
     *      The panel "Join game"
     */
    public final JPanel createJoinMenu(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,1));
        
        JPanel ip = new JPanel();
        ip.setLayout(new BoxLayout(ip,0));
        
        ip.add(ipText);
        ip.add(ipField);
        ip.add(ipJoin);
        
        panel.add(Box.createRigidArea(new Dimension(0,60)));
        panel.add(joinTitle);
        panel.add(Box.createRigidArea(new Dimension(0,160)));
        panel.add(ip);
        panel.add(Box.createRigidArea(new Dimension(0,200)));
        panel.add(backJoin);
        return panel;
    }
    
    /**
     * Get the panel of the menu "Create game"
     * 
     * @return 
     *      The panel "Create game"
     */
    public final JPanel createCreateMenu(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,1));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);    
        
        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options,0));
        options.setMaximumSize(new Dimension(700,60));
        options.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        options.add(duration);
        options.add(time);
        options.add(minutes);
        options.add(Box.createRigidArea(new Dimension(180,0)));       
        options.add(players);
        options.add(nPlayers);
        
        panel.add(Box.createRigidArea(new Dimension(0,10)));       
        panel.add(createTitle);
        panel.add(Box.createRigidArea(new Dimension(0,10)));       
        panel.add(selectBoard);
        panel.add(Box.createRigidArea(new Dimension(0,10)));       
        panel.add(maps);

        panel.add(options);
        panel.add(ip);
        panel.add(startServer);
        panel.add(backJoin);
 
        return panel;
    }
    /**
     * Get the button group of map selection
     * 
     * @return
     *      The button group of map selections
     */
    public final ButtonGroup getBg(){ return bg;}
    
    /**
     * Create the screen at the end of the game, displaying the winners
     * 
     * @param n
     *      The byte received by the server giving the winner
     *      
     * @return
     *      The panel of the end of the game
     */
    public final JPanel createWinners(byte n){
        JPanel panel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new BoxLayout(panel,1));
        
        JPanel winner = new JPanel();
        winner.setLayout(new BoxLayout(winner,0));
        if (n==0) winner.add(nobody);
        else {
            if ((n>>3)%2==1) winner.add(new JLabel(new ImageIcon(
                    ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(6))));
            if ((n>>2)%2==1) winner.add(new JLabel(new ImageIcon(
                    ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(26))));
            if ((n>>1)%2==1) winner.add(new JLabel(new ImageIcon(
                    ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(46))));
            if (n%2==1) winner.add(new JLabel(new ImageIcon(
                    ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(66))));
        }
        winner.add(won);
        panel.add(Box.createRigidArea(new Dimension(0,200)));
        panel.add(winner, Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0,140)));
        panel.add(menu);
        
        winner.setAlignmentX(Component.CENTER_ALIGNMENT);
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }
    /**
     * Create the screen of the client waiting for connection
     * 
     * @param str
     *      The address of the server
     *      
     * @return
     *      The panel of the waiting client
     */
    public final JPanel createWaitingClient(String str){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,1));
        panel.add(Box.createRigidArea(new Dimension(0,260)));
        panel.add(getConnecting(str));
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(backConnect);
        return panel;
    }
    
    /**
     * Create the screen of the server waiting for the clients
     * 
     * @return
     *      The panel of the waiting server
     */
    public final JPanel createWaitingServer(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,1));
        panel.add(Box.createRigidArea(new Dimension(0,260)));
        panel.add(getConnecting(ServerBis.getIp()));
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(backServer);
        return panel;
    }
    
    /**
     * Get the XBlastComponent of the game
     * 
     * @return
     *      The XBlast component of the view
     */
    public final XBlastComponent getComponent(){ return component;}
}
