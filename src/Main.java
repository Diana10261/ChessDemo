import view.BackMusic;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addComponentListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(1000, 800);
            mainFrame.setVisible(true);
            BackMusic.playBgm("./music/music.wav");
//            addComponentListener(new ComponentListener() {
//                @Override
//                public void componentResized(ComponentEvent e) {
//                    int fraWidth = mainFrame.getWidth();//获取面板宽度
//                    int fraHeight = mainFrame.getHeight();//获取面板高度
//                    mainFrame.setWIDTH(fraWidth);
//                    mainFrame.setHEIGTH(fraHeight);
//                    mainFrame.repaint();
//                }
//                @Override
//                public void componentMoved(ComponentEvent e) {
//                }
//                @Override
//                public void componentShown(ComponentEvent e) {
//                }
//                @Override
//                public void componentHidden(ComponentEvent e) {
//                }
//            });
        });
    }
}
