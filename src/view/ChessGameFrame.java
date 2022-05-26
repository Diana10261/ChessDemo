package view;

import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.lang.Thread.sleep;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    public JTextField jTextField;
    Chessboard chessboard;
    int a=0;

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        this.WIDTH = screenWidth * 4 / 5;
        this.HEIGTH = screenHeight;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addChessboard();
        addLabel();
        addSave1Button();
        addCurrentPlayerNotice();
        addLoadButton();
        addSaveButton();
        addResetButton();
        addWithdrawButton();
        choose();
        addReplayButton();
        addBackToMainFrameButton();

        addPicture();
    }
//    private void addPicture(){
//        JLabel m = new JLabel();
//        Icon icon = new ImageIcon("./images/background2.jpeg");		//获取图片
//        JLabel la = new JLabel();  //标签
//        la.setIcon(icon);
//        la.setBounds(-100, 0,2000,1760);
//        add(la);
//    }

    public void addPicture(){
        ((JPanel)this.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon("./images/background2.jpeg");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Image temp = img.getImage().getScaledInstance((int)screenSize.getWidth(),(int)screenSize.getHeight(),img.getImage().SCALE_DEFAULT);
        img = new ImageIcon(temp);
        JLabel background = new JLabel(img);
        background.setOpaque(false);
        add(background);
        background.setBounds(0,0, (int) (4*screenSize.getWidth())/5 ,(int)(screenSize.getHeight()));
    }
        /**
         * 在游戏面板中添加棋盘
         */
    public void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE,CHESSBOARD_SIZE,this);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("Current Player is");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }
    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addCurrentPlayerNotice() {
        jTextField=new JTextField();
        jTextField.setText(chessboard.getCurrentColor().toString());
        jTextField.setEditable(false);
//        JButton button = new JButton("Show the Current Player");
//            button.addActionListener((e) ->{
//                if (gameController.getChessboard().getCurrentColor() == ChessColor.BLACK){
//                JOptionPane.showMessageDialog(this, "Current Player is Black");
//                }
//                else if (gameController.getChessboard().getCurrentColor() == ChessColor.WHITE){
//                JOptionPane.showMessageDialog(this, "Current Player is White");
//                }
//        });
//        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
//        button.setSize(250, 40);
//        button.setFont(new Font("Rockwell", Font.BOLD, 18));
//        add(button);
        jTextField.setLocation(HEIGTH, HEIGTH / 10 + 50);
        jTextField.setSize(250, 40);
        jTextField.setFont(new Font("Rockwell", Font.BOLD, 18));
        add(jTextField);
        this.repaint();
    }

    private void addSave1Button() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 140);
        button.setSize(200, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            try {
                gameController.SaveGame(gameController.getChessboard());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }


    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 210);
        button.setSize(200, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
            JFileChooser fc=new JFileChooser("E:\\Java\\ChessDemo\\save");
            int val=fc.showOpenDialog(null);
            if (val == JFileChooser.APPROVE_OPTION) {// 如果点击了"确定", 则获取选择的文件路径
                File file = fc.getSelectedFile();
                FileNameMap fileNameMap = URLConnection.getFileNameMap();
                String mimeType = fileNameMap.getContentTypeFor(file.getName());
                if (!Objects.equals(mimeType, "text/plain")) {
                    JOptionPane.showMessageDialog(null,"File Type Error");
                }
                else {
                    String path = file.getAbsolutePath();
                    gameController.loadGameFromFile(path);
                }
            }
        });

    }


    private void addResetButton() {
        JButton button = new JButton("Reset");
        button.setLocation(HEIGTH, HEIGTH / 10 + 280);
        button.setSize(200, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            String path = ("reset.txt");
            gameController.resetGameFromFile(path);
        });
    }

    private void addSaveButton() {
        JButton button = new JButton("choose to Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 350);
        button.setSize(200, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            JFrame jf = new JFrame("测试窗口");
            jf.setSize(250, 250);
            jf.setLocationRelativeTo(null);
            jf.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            JPanel panel = new JPanel();
            // 添加一个标签
            JLabel label = new JLabel("choose one file");
            panel.add(label);
            // 需要选择的条目
            String[] listData = new String[]{"No.1", "No.2", "No.3"};
            // 创建一个下拉列表框
            final JComboBox<String> comboBox = new JComboBox<>(listData);
            // 添加条目选中状态改变的监听器
            comboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    // 只处理选中的状态
                    if (comboBox.getSelectedIndex() == 0) {
                        try {
                            System.out.println("@");
                            gameController.SaveGame1(gameController.getChessboard());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (comboBox.getSelectedIndex() == 1) {
                        try {
                            gameController.SaveGame2(gameController.getChessboard());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (comboBox.getSelectedIndex() == 2) {
                        try {
                            gameController.SaveGame3(gameController.getChessboard());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            panel.add(comboBox);
            jf.setContentPane(panel);
            jf.setVisible(true);
        });
    }


    private void addWithdrawButton() {
        JButton button = new JButton("withdraw");
        button.setLocation(HEIGTH, HEIGTH / 10 + 420);
        button.setSize(200, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            String path = ("withdraw.txt");
            gameController.loadWithdraw(path);
        });
    }

    public void choose() {
        JButton button = new JButton("choose to load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 490);
        button.setSize(200, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            JFrame jf = new JFrame("测试窗口");
            jf.setSize(250, 250);
            jf.setLocationRelativeTo(null);
            jf.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            JPanel panel = new JPanel();
            // 添加一个标签
            JLabel label = new JLabel("choose one file");
            panel.add(label);
            // 需要选择的条目
            String[] listData = new String[]{"No.1", "No.2", "No.3"};
            // 创建一个下拉列表框
            final JComboBox<String> comboBox = new JComboBox<>(listData);
            // 添加条目选中状态改变的监听器
            comboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    // 只处理选中的状态
                    if (comboBox.getSelectedIndex() == 0) {
                        String path = ("save1.txt");
                        gameController.loadWithdraw(path);
                    }
                    if (comboBox.getSelectedIndex() == 1) {
                        String path = ("save2.txt");
                        gameController.loadWithdraw(path);
                    }
                    if (comboBox.getSelectedIndex() == 2) {
                        String path = ("save3.txt");
                        gameController.loadWithdraw(path);
                    }
                }
            });

            // 添加到内容面板
            panel.add(comboBox);
            jf.setContentPane(panel);
            jf.setVisible(true);
        });
    }
    private void addReplayButton(){
        JButton button = new JButton("replay");
        button.setLocation(HEIGTH, HEIGTH / 10 +560);
        button.setSize(200, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            JFrame jf = new JFrame("control");
            jf.setSize(250, 250);
            jf.setLocationRelativeTo(null);
            jf.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            JPanel panel = new JPanel();
            JButton b1 = new JButton("+");
            b1.addActionListener(d -> {
                a++;
            });
            JButton b2 = new JButton("-");
            b2.addActionListener(d -> {
                a--;
            });
            JButton b3 = new JButton("GO");
            panel.add(b1);
            panel.add(b2);
            panel.add(b3);
            b3.addActionListener(d -> {
                try {
                    System.out.println(a);
                    List<String> chessData = Files.readAllLines(Path.of("replay.txt"));
                    chessboard.loadGame(chessData);
                    chessboard.setLayout(null); // Use absolute layout.
                    chessboard.setSize(1000, 1000);
                    chessboard.initiateEmptyChessboard();
                    if (chessData.size()>=19){
                        for (int i = a*9+1,k=0; i<a*9+9&&k<8; i++,k++) {
                            System.out.println("@"+chessData.get(i));
                            for (int j = 0; j < 8; j++) {
                                if (chessData.get(i).charAt(j) == 'R')  chessboard.initRookOnBoard(k,j,ChessColor.BLACK);
                                if (chessData.get(i).charAt(j) == 'B')  chessboard.initBishopOnBoard(k,j,ChessColor.BLACK);
                                if (chessData.get(i).charAt(j) == 'N')  chessboard.initKnightOnBoard(k,j,ChessColor.BLACK);
                                if (chessData.get(i).charAt(j) == 'P')  chessboard.initPawnOnBoard(k,j,ChessColor.BLACK);
                                if (chessData.get(i).charAt(j) == 'Q')  chessboard.initQueenOnBoard(k,j,ChessColor.BLACK);

                                if (chessData.get(i).charAt(j) == 'r')  chessboard.initRookOnBoard(k,j,ChessColor.WHITE);
                                if (chessData.get(i).charAt(j) == 'b')  chessboard.initBishopOnBoard(k,j,ChessColor.WHITE);
                                if (chessData.get(i).charAt(j) == 'n')  chessboard.initKnightOnBoard(k,j,ChessColor.WHITE);
                                if (chessData.get(i).charAt(j) == 'p')  chessboard.initPawnOnBoard(k,j,ChessColor.WHITE);
                                if (chessData.get(i).charAt(j) == 'q')  chessboard.initQueenOnBoard(k,j,ChessColor.WHITE);

                            }
                        }
                        if (chessData.get(a*9+9).charAt(0) == 'l') chessboard.setCurrentColor(ChessColor.BLACK);
                        else if (chessData.get(a*9+9).charAt(0) == 'w') chessboard.setCurrentColor(ChessColor.WHITE);
                        chessboard.repaint();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            jf.setContentPane(panel);
            jf.setVisible(true);
        });
    }

    private void addBackToMainFrameButton() {//返回到主界面
        JButton button = new JButton("Back");
        button.setLocation(HEIGTH, HEIGTH / 10 + 630);
        button.setSize(200, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainFrame mainframe = null;
                mainframe = new MainFrame(1000,760);
                mainframe.setVisible(true);
            }
        });
    }




}
