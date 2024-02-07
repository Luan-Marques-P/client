package com.luan.ui;

import com.luan.conectar.ClienteSocketComInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrontEndApp {
    public FrontEndApp() {
        JFrame frame = new JFrame("Conexão Remota");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Criação do campo para inserir o IP
        JTextField ipField = new JTextField();
        frame.add(ipField, BorderLayout.CENTER);

        // Criação do botão de conectar
        JButton connectButton = new JButton("Conectar");
        frame.add(connectButton, BorderLayout.SOUTH);

        // Adiciona ação ao botão de conectar
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String serverIP = ipField.getText();
                // Criando uma instância de ClienteSocketComInterface
                ClienteSocketComInterface cliente = new ClienteSocketComInterface();
                // Chamando o método conectar na instância criada
                cliente.conectar(serverIP);
            }
        });

        // Configuração da janela
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
