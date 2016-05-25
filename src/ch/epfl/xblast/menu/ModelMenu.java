package ch.epfl.xblast.menu;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public final class ModelMenu {
    JLabel title;
    JLabel names;
    JButton create;
    JButton join;
    JLabel ipText;
    JButton backJoin;
    JTextField ipField;
    NumberFormat amountFormat;

    
    public ModelMenu(){
        setTitle();
        setNames();
        setCreate();
        setJoin();
        setIpText();
        setBackJoin();
        setIpField();
    }
        
    private void setTitle(){
        title = new JLabel("XBlast");
        title.setFont(new Font("GB18030 Bitmap",Font.BOLD,86));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void setNames(){
        names = new JLabel("By Vandenbroucque & Michel",SwingConstants.CENTER);
        names.setFont(new Font("Arial",Font.ITALIC,28));
        names.setMaximumSize(new Dimension(450,40));
        names.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void setCreate(){
        create = new JButton("Create Game");
        create.setFont(new Font("Arial",Font.PLAIN,36));
        create.setMaximumSize(new Dimension(450,100));
        create.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void setJoin(){
        join = new JButton("Join Game");
        join.setFont(new Font("Arial",Font.PLAIN,36));
        join.setMaximumSize(new Dimension(450,100));
        join.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void setIpText(){
        ipText = new JLabel("Select IP : ");
        ipText.setFont(new Font("Arial",Font.BOLD,20));
        ipText.setMaximumSize(new Dimension(200,50));
        ipText.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
    
    private void setBackJoin(){
        backJoin = new JButton("Back");
        backJoin.setFont(new Font("Arial",Font.PLAIN,36));
        backJoin.setMaximumSize(new Dimension(450,100));
        backJoin.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void setIpField(){
        ipField = new JTextField();
        ipField.setColumns(15);
        amountFormat = NumberFormat.getIntegerInstance();
        ipField.setMaximumSize(new Dimension(200,50));
    }
}
