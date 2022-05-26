package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class PawnChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image pawn_WHITE;
    private static Image pawn_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image pawnImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (pawn_WHITE == null) {
            pawn_WHITE = ImageIO.read(new File("./images/pawn1-white.png"));
        }

        if (pawn_BLACK == null) {
            pawn_BLACK = ImageIO.read(new File("./images/pawn1-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiatepawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = pawn_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = pawn_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatepawnImage(color);
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
        if (chessComponents[source.getX()][source.getY()].getChessColor() == ChessColor.WHITE) {
            if (source.getY() == destination.getY()) {
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)){
                    return false;
                }
                else {
                    if (source.getX() == 6){
                        if (destination.getX()!=source.getX()-1 && destination.getX()!=source.getX()-2) return false;
                    }
                    else {
                        if (destination.getX()!=source.getX()-1)  return false;
                    }
                }
            } else if (source.getY()!=7&&chessComponents[source.getX()-1][source.getY()+1].getChessColor() == ChessColor.BLACK &&
                    destination.getY() == source.getY()+1){
                return true;
            }
            else if (source.getY()!=0 && chessComponents[source.getX()-1][source.getY()-1].getChessColor() == ChessColor.BLACK &&
                    destination.getY() == source.getY()-1){
                return true;
            }
            else return false;// Not on the same column.
        }

        if (chessComponents[source.getX()][source.getY()].getChessColor() == ChessColor.BLACK) {
            if (source.getY() == destination.getY()) {
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)){
                    return false;
                }
                else {
                    if (source.getX() == 1){
                        if (destination.getX()!=source.getX()+1 && destination.getX()!=source.getX()+2) return false;
                    }
                    else {
                        if (destination.getX()!=source.getX()+1)  return false;
                    }
                }
            }else if (source.getY()!=7&&chessComponents[source.getX()+1][source.getY()+1].getChessColor() == ChessColor.WHITE &&
                    destination.getY() == source.getY()+1){
                return true;
            }
            else if (source.getY()!=0 && chessComponents[source.getX()+1][source.getY()-1].getChessColor() == ChessColor.WHITE &&
                    destination.getY() == source.getY()-1){
                return true;
            }
            else return false;// Not on the same column.
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
        super.paintComponent(g);
//        g.drawImage(pawnImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
