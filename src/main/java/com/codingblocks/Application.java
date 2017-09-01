package com.codingblocks;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {

    private final JFrame frame;
    private static EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public Application() {

        Server.serve();

        frame = new JFrame("Coding Blocks player");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

        JPanel controlsPane = new JPanel();

        JButton pauseButton = new JButton("Pause");
        controlsPane.add(pauseButton);

        JButton rewindButton = new JButton("Rewind");
        controlsPane.add(rewindButton);

        JButton skipButton = new JButton("Skip");
        controlsPane.add(skipButton);

        contentPane.add(controlsPane, BorderLayout.SOUTH);

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.pause();
            }
        });

        rewindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.skip();
            }
        });

        skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.rewind();
            }
        });

        frame.setContentPane(contentPane);
        frame.setVisible(true);
        mediaPlayerComponent.getMediaPlayer().playMedia("/home/anuj/Videos/krake/jaali.mp4");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Application());
    }

    public static void rewind() {
        mediaPlayerComponent.getMediaPlayer().skip(-10000);
    }

    public static void skip(){
        mediaPlayerComponent.getMediaPlayer().skip(10000);
    }

    public static void pause(){
        mediaPlayerComponent.getMediaPlayer().pause();
    }

}
