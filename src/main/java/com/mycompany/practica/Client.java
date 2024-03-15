/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author armi8
 */
public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;
    private static JFrame mainFrame;
    private static CardLayout cardLayout;
    private static JPanel loginPanel;
    private static JPanel welcomePanel;

    public static void main(String[] args) {

        mainFrame = new JFrame("Inicio de Sesión");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
        mainFrame.setLayout(new CardLayout());

        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Usuario:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Iniciar sesión");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("¡Bienvenido!");
        JButton logoutButton = new JButton("Cerrar sesión");
        JButton exitButton = new JButton("Salir");

        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(logoutButton);
        buttonPanel.add(exitButton);
        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);

        cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
        mainFrame.add(loginPanel, "login");
        mainFrame.add(welcomePanel, "welcome");

        cardLayout.show(mainFrame.getContentPane(), "login");
        mainFrame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try (
                        Socket socket = new Socket(SERVER_ADDRESS, PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    out.println(username);
                    out.println(password);
                    String response = in.readLine();
                    if (response.equals("Login confirmed.")) {
                        JOptionPane.showMessageDialog(mainFrame, response);
                        cardLayout.show(mainFrame.getContentPane(), "welcome");
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, response, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainFrame.getContentPane(), "login");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

}
