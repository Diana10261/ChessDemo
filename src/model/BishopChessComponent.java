
package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;


/**
 * 这个类表示国际象棋里面的车
 */
public class BishopChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */

    Thread tmain;
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image bishopImage;


    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop1-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop1-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定bishopImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (Math.abs(source.getX() + source.getY()) == Math.abs(destination.getX() + destination.getY()) ) {
            for (int col = Math.min(source.getY(), destination.getY()) + 1 ,  row = Math.max(source.getX(), destination.getX()) - 1;
                 col < Math.max(source.getY(), destination.getY()) ; col++ , row --) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (Math.abs(source.getX() - source.getY()) == Math.abs(destination.getX() - destination.getY()) ) {
            for (int col = Math.min(source.getY(), destination.getY()) + 1 ,  row = Math.min(source.getX(), destination.getX()) + 1;
                 col < Math.max(source.getY(), destination.getY()) ; col++ , row ++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        ChessComponent[][] chessComponents = new ChessComponent[8][8];
        int count =0;
        ChessboardPoint destination = new ChessboardPoint(0,0);
        super.paintComponent(g);
//        g.drawImage(BishopImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
            for (int i=0;i<8;i++){
                for (int j=0;j<8;j++){
                    destination.setX(i);
                    destination.setY(j);
//                    if (canMoveTo(chessComponents,destination)){
////                        g.setColor(Color.GREEN);
////                        g.drawRect(i*50,j*50,getWidth(),getHeight());
//                        System.out.println(i+"@"+j);
////                        g.fillRect(i,j,getWidth(),getHeight());
//                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource().equals(bishopImage)){
            try{
                tmain.sleep(200);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
}

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void run(){
        while(true){

        }
    }

}
