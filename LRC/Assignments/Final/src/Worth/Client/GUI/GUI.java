package Worth.Client.GUI;

import javax.swing.*;

import Worth.Client.Const;
import Worth.Client.Utils;
import Worth.Client.TCP.Commands;
import Worth.Client.TCP.Connection;

import java.awt.*;

public class GUI {

    private static final int DEF_WIDTH = 800;
    private static final int DEF_HEIGHT = 600;

    private JFrame frame;

    public GUI() {
        frame = new JFrame("WORTH Client" + (Const.DEBUG ? " (Debug)" : ""));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEF_WIDTH, DEF_HEIGHT);
    }

    public void showLogin() {
        JPanel parent_panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(parent_panel);
        JPanel center_panel = new JPanel(new GridLayout(4, 2, 20, 10));
        parent_panel.add(center_panel, new GridBagConstraints());
        JLabel lIP = new JLabel("Server IP:");
        JTextField tfIP = new JTextField("", 10);
        if(Const.DEBUG) {
            tfIP.setText("127.0.0.1");
            tfIP.setEnabled(false);
        }
        JLabel lUser = new JLabel("Username:");
        JTextField tfUser = new JTextField();
        JLabel lPass = new JLabel("Password:");
        JPasswordField tfPass = new JPasswordField();
        JButton bReg = new JButton("Register");
        JButton bLog = new JButton("Login");
        
        center_panel.add(lIP);
        center_panel.add(tfIP);
        center_panel.add(lUser);
        center_panel.add(tfUser);
        center_panel.add(lPass);
        center_panel.add(tfPass);
        center_panel.add(bReg);
        center_panel.add(bLog);

        bReg.addActionListener(e -> {
            Const.IP = tfIP.getText();
            System.out.println(Utils.register(tfUser.getText(), tfPass.getPassword()));
        });
        bLog.addActionListener(e -> {
            if(!Connection.isConnected()) Connection.connect();
            Commands.login(tfUser.getText(), new String(tfPass.getPassword()));
        });
        
        frame.setVisible(true);
    }
}