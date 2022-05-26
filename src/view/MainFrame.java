package view;

import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.Pipe;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainFrame extends JFrame {
    private int WIDTH;
    private int HEIGTH;
    public final int CHESSBOARD_SIZE;


    private static Image cover;

    public MainFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int screenWidth = screenSize.width;
//        int screenHeight = screenSize.height;

//        this.WIDTH = screenWidth * 4 / 5;
//        this.HEIGTH = screenHeight;
//        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addLabel();
        addStartButton();
//        addPicture();
        addCreatePlayerButton();
        addLoadButton();
        addRankingListButton();
        setBak();
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public void setHEIGTH(int HEIGTH) {
        this.HEIGTH = HEIGTH;
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("Let's play chess!");
        statusLabel.setLocation((int) (HEIGTH*0.8), (int) (HEIGTH *0.1));
        statusLabel.setSize((int) (HEIGTH*0.5), (int) (HEIGTH*0.1));
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        add(statusLabel);
    }
    private String winner = new String();
    

    public void setBak(){
        ((JPanel)this.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon("./images/backgroundM.jpg");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Image temp = img.getImage().getScaledInstance((int)screenSize.getWidth(),(int)screenSize.getHeight(),img.getImage().SCALE_DEFAULT);
        img = new ImageIcon(temp);
        JLabel background = new JLabel(img);
        background.setOpaque(false);
        add(background);
        background.setBounds(0,0, (int) (4*screenSize.getWidth())/5 ,(int)(screenSize.getHeight()));
    }

    private void addStartButton() {
        JButton button = new JButton("Start New");
        button.addActionListener((e) ->{
            ChessGameFrame chessGameFrame = new ChessGameFrame(1000, 760);
            chessGameFrame.setVisible(true);
        });
//        button.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                buttonMouseEntered(evt);
//            }
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                buttonMouseExited(evt);
//            }
//        });
//        button.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//            public void mouseMoved(java.awt.event.MouseEvent evt) {
//                buttonMouseMoved(evt);
//            }
//        });
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(layout.createSequentialGroup()
//                                .addComponent(button)
//                                .addContainerGap(155, Short.MAX_VALUE))
//        );
//        layout.setVerticalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(layout.createSequentialGroup()
//                                .addComponent(button)
//                                .addContainerGap(162, Short.MAX_VALUE))
//        );
//        pack();
        button.setLocation(HEIGTH -100, HEIGTH / 10 + 120);
        button.setSize((int) (HEIGTH*0.2), (int) (HEIGTH*0.07));
        button.setFont(new Font("Rockwell", Font.BOLD, 18));
        add(button);
    }

//    private void buttonMouseMoved(java.awt.event.MouseEvent evt) {
//// TODO add your handling code here:
//// jButton1.setBackground(Color.red);
//    }
//
//    private void buttonMouseEntered(java.awt.event.MouseEvent evt) {
//// TODO add your handling code here:
//        Component button;
//        button.setBackground(Color.BLUE);
//    }
//
//    private void buttonMouseExited(java.awt.event.MouseEvent evt) {
//// TODO add your handling code here:
//    }

    private void addCreatePlayerButton() {
        JButton button = new JButton("Create a New Player");
        button.addActionListener((e) -> {
            String name = JOptionPane.showInputDialog(this, "Input your name: ");
            String age = JOptionPane.showInputDialog(this, "Input your age: ");
            String skill = JOptionPane.showInputDialog(this, "Input your skill: ");
            Player player = new Player(name, Integer.parseInt(age), skill);
            System.out.println(player.getName() + ", " + player.getAge() + ", " + player.getSkill());
            File file = new File("savePlayer.txt");
            FileOutputStream fos = null;
            OutputStreamWriter osw = null;
            try {
                if (!file.exists()) {
                    boolean hasFile = file.createNewFile();
                    if (hasFile) {
                        System.out.println("file not exists, create new file");
                    }
                    fos = new FileOutputStream(file);
                } else {
                    System.out.println("file exists");
                    fos = new FileOutputStream(file, true);
                }
                osw = new OutputStreamWriter(fos, "utf-8");
                osw.write("\r\nname:"+name+"\r\nage:"+age+"\r\nskill:"+skill+"\r\nscore: "+player.getScore()); //写入内容
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {   //关闭流
                try {
                    if (osw != null) {
                        osw.close();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        button.setLocation(HEIGTH-100, HEIGTH / 10 + 240);
        button.setSize(250, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 18));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load Player");
        button.setLocation(HEIGTH-100, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            String path = ("savePlayer.txt");
            try {
                List<String> playerData = Files.readAllLines(Path.of(path));
                for (int i = 1; i < playerData.size(); i=i+4) {
                    JOptionPane.showMessageDialog(null,playerData.get(i)+
                            "\r\n"+playerData.get(i+1)+"\r\n"+ playerData.get(i+2)+"\r\n"+playerData.get(i+3));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    private void addRankingListButton() {
        JButton button = new JButton("Ranking List");
        button.setLocation(HEIGTH-100, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            String path = ("savePlayer.txt");
            try {
                List<String> playerData = Files.readAllLines(Path.of(path));
                ArrayList<String> score = new ArrayList<>();
                for (int i = 4; i < playerData.size(); i=i+4) {
                    score.add(playerData.get(i).substring(6)+"("+playerData.get(i-3)+")");
                }
                for (int i = 0; i <score.size() ; i++) {
                    System.out.println(score.get(i));
                }
                for (int i=0;i<score.size();i++){
                    for (int j=i+1;j<score.size(); j++){
                        if (score.get(i).charAt(0) > score.get(j).charAt(0)){ //
                            Collections.swap(score,j,i);
                        }
                        if (score.get(i).charAt(0) == score.get(j).charAt(0)){
                            if (score.get(i).charAt(6) > score.get(i).charAt(6)){
                                Collections.swap(score,j,i);
                            }
                        }
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < score.size(); i++) {
                    sb.append("No."+(i+1)+": "+score.get(i)+"\r\n");
                }
                JOptionPane.showMessageDialog(null,""+sb);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

//    public void paint(Graphics g) {

////准备一张缓冲背景图片
//
//        BufferedImage backgroundM = (BufferedImage) this.createImage(this.getWidth(),this.getHeight());
//
////获取缓冲图片画笔
//
//        Graphics g_buffer = backgroundM.getGraphics();
//
////将要绘制的内容，绘制到缓冲图片上
//
//        g_buffer.drawImage(backgroundM.image.getImage(), background.x, 0,10292,(int)this.getSize().getHeight(), null);
//
//        g_buffer.drawImage(background2.image.getImage(), background2.x, (int)(this.getSize().getHeight() * 0.8),background2.width,background2.height, null);
//
//        for(int i = 0;i < pipeList.size();i++){
//
//            Pipe pipe = pipeList.get(i);
//
//            g_buffer.drawImage(pipe.image.getImage(), pipe.x, pipe.y, pipe.width, pipe.height, null);
//
//        }
//
////将缓冲图片绘制到窗体
//
//        g.drawImage(backgroundM, 0, 0, null);
//
//    }


}
