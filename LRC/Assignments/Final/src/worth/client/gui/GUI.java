package worth.client.gui;

import javax.swing.*;

import worth.client.Const;
import worth.client.Utils;
import worth.client.tcp.Commands;
import worth.client.tcp.Connection;
import worth.client.tcp.Response;

import java.awt.*;


public class GUI {

    private static final int DEF_WIDTH = 800;
    private static final int DEF_HEIGHT = 600;

    private JFrame frame;
    private JPanel parent_panel;

    public GUI() {
        frame = new JFrame("WORTH Client" + (Const.DEBUG ? " (Debug)" : ""));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEF_WIDTH, DEF_HEIGHT);
        parent_panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(parent_panel);
    }

    public void showLogin() {
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
            Response r = Utils.register(tfUser.getText(), tfPass.getPassword());
            if(r.getStatus()) JOptionPane.showMessageDialog(frame, "Registration successful", "OK", JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(frame, r.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        });
        bLog.addActionListener(e -> {
            if(!Connection.isConnected()) {
                if(!Connection.connect()) JOptionPane.showMessageDialog(frame, "Cannot connect to the server", "Error", JOptionPane.ERROR_MESSAGE);
            }
            Response r = Commands.login(tfUser.getText(), new String(tfPass.getPassword()));
            if(r.getStatus()) {
                JOptionPane.showMessageDialog(frame, "Login successful", "OK", JOptionPane.INFORMATION_MESSAGE);
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong username / password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        frame.setVisible(true);
    }

    public void showMainMenu() {
        System.out.println(Commands.listProjects().getMessages());
        System.out.println(Commands.deleteProject("project").getStatus());
        System.out.println(Commands.deleteProject("project2").getStatus());
        // Commands.createProject("project3");
        parent_panel.removeAll();;
        String week[] = {"M", "T", "W", "T", "F", "S"};
        JList<String> s = new JList<>(week);
        parent_panel.add(s);
        frame.setVisible(false);
        frame.setVisible(true);
    }
}