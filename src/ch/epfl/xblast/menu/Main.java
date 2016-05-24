package ch.epfl.xblast.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import ch.epfl.xblast.client.Client;
import ch.epfl.xblast.server.Server;

public final class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            ModelMenu model = new ModelMenu();
            ViewMenu view = new ViewMenu(model);
            ControllerMenu c = new ControllerMenu(view,model);
        });
    }
}
