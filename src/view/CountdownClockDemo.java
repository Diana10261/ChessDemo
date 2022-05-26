package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountdownClockDemo extends JFrame {

    /**
     * 计时器
     * @author 荆棘
     */
    private static final long serialVersionUID = 1L;
    JLabel jlabel;//显示时间的标签
    int time;//输入框接收的时间，格式为纯数字或者00:00的形式
    int m;//分针
    int s = 5;//秒针
    JButton btn;//开始按钮
    JButton Reset;//重置按钮
    JButton shop;//暂停按钮
    JButton keepOn;//继续按钮
//    JTextField txtField;//输入框，用于接收输入数据
    JPanel jpanelNorth;//窗口上部分面板
    JPanel jpanelCenter;//窗口下部分面板
    private Chessboard chessboard;
    private boolean change;

    TimerThread timerThread = new TimerThread();//执行的线程程序部分
    Thread th;//线程对象

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public boolean isChange() {
        return change;
    }

    /**
     * 初始化UI界面
     */
    public void initUI(){
        // 设置窗口布局
        this.setTitle("我的计时器");
        this.setSize(300, 130);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout()); //设置布局方式为边框布局

        // 运行时显示的画面
        // 显示时间
        jlabel = new JLabel();
        jlabel.setFont(new Font("宋体", 1, 36));
        jlabel.setForeground(Color.blue);
        this.add(jlabel);

        this.setVisible(true);//窗口设为可见
        th = new Thread(timerThread);
        th.start();
    }
    /**
     * 功能：线程执行的程序
     * @author 荆棘
     *
     */
    class TimerThread implements Runnable{
        @Override
        public void run() {
            while (true) {
                // 判断这一分钟是否到末尾，若到末尾则分针减1，秒针回到59
                if(s<0 && m>0){
                    s=59;
                    m--;
                }
                // 规定时间显示的格式
                DecimalFormat f1 = new DecimalFormat("00");
                jlabel.setText(f1.format(m)+":"+f1.format(s));

                // 判断时间是否走完
                if(s == 0 && m == 0){
                    jlabel.setText("00:00");
                    change = true;
                    return;
                }
                //线程休眠一秒，秒针-1
                try {
                    Thread.sleep(1000);
                    s--;
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

}

