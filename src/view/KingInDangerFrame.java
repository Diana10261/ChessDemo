package view;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class KingInDangerFrame extends JFrame{
    //        public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    public static int winner;

    JLabel statusLabel = new JLabel("King In Danger!");

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public KingInDangerFrame(int width, int height) {
        setTitle("King In Danger!"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setLayout(null);

        addLabel();
        addCloseButton();
    }
    private void addLabel() {
        statusLabel.setLocation(3*WIDTH/16, HEIGHT / 13);
        statusLabel.setSize(360, 80);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 25));
        add(statusLabel);
    }
    private void addCloseButton(){
        JButton closeButton = new JButton("I know!");
        closeButton.addActionListener(e -> {setVisible(false);winner = 'b';winner = 'w';});
        closeButton.setLocation(3*WIDTH/10,HEIGHT/10+160);
        closeButton.setSize(200,60);
        closeButton.setFont(new Font("Rockwell",Font.BOLD,20));
        add(closeButton);
    }
}


//package view;
//
//import javax.swing.*;
//
//public class LosingFrame extends JFrame {
//
//    private final int WIDTH;
//    private final int HEIGHT;
//    public final int CHESSBOARD_SIZE;
//    public static int Attack;
//    public static int Attack0;
//    JLabel statusLabel = new JLabel("You Lose!");
//
//    public JLabel getStatusLabel(){return statusLabel;}
//
//    public LosingFrame(int width, int height){
//        setTitle("You Lose!");
//        this.WIDTH = width;
//        this.HEIGHT = height;
//        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;
//
//        setSize(WIDTH, HEIGHT);
//        setLocationRelativeTo(null);
//        setLayout(null);
//
//    }
//
//}
