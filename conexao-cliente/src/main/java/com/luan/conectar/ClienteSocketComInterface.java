package com.luan.conectar;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.Frame;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import com.luan.ui.FrontEndApp;


public class ClienteSocketComInterface {
    static {
        Loader.load(avutil.class); // Garante que as bibliotecas nativas sejam carregadas
    }

    private JFrame frame;
    private JLabel label;

    public ClienteSocketComInterface() {
        criarInterfaceUsuario();
    }

    private void criarInterfaceUsuario() {
        frame = new JFrame("Cliente - Conectar ao Servidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField ipField = new JTextField(20);
        JButton connectButton = new JButton("Conectar");
        JPanel panel = new JPanel();

        connectButton.addActionListener(e -> conectar(ipField.getText().trim()));
        panel.add(ipField);
        panel.add(connectButton);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void conectar(String enderecoServidor) {
        iniciarRecepcao(enderecoServidor);
    }

    private void iniciarRecepcao(String enderecoServidor) {
        SwingUtilities.invokeLater(() -> {
            try {
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("udp://" + enderecoServidor + ":12346");
                grabber.start();

                if (label == null) {
                    label = new JLabel();
                    frame.getContentPane().removeAll();
                    frame.add(label);
                    frame.setSize(800, 600); // Ajuste conforme necessário
                    frame.setVisible(true);
                }

                Java2DFrameConverter converter = new Java2DFrameConverter();
                while (true) {
                    Frame frame = grabber.grab();
                    if (frame != null) {
                        BufferedImage img = converter.convert(frame);
                        ImageIcon icon = new ImageIcon(img);
                        label.setIcon(icon);
                        this.frame.revalidate();
                        this.frame.repaint();
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Não foi possível iniciar a recepção da transmissão.", "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        // Imprime o java.library.path no console
        System.out.println("java.library.path: " + System.getProperty("java.library.path"));

        // Inicializa a aplicação
        SwingUtilities.invokeLater(FrontEndApp::new);
    }
}
