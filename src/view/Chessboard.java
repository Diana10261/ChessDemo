package view;


import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.BLACK;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private Color color = null;
    private ChessGameFrame chessGameFrame;
    private String winner = new String();

    public Chessboard(int width, int height, ChessGameFrame chessGameFrame) {
        this.chessGameFrame=chessGameFrame;
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);


        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        initPawnOnBoard(1,0,ChessColor.BLACK);
        initPawnOnBoard(1,1,ChessColor.BLACK);
        initPawnOnBoard(1,2,ChessColor.BLACK);
        initPawnOnBoard(1,3,ChessColor.BLACK);
        initPawnOnBoard(1,4,ChessColor.BLACK);
        initPawnOnBoard(1,5,ChessColor.BLACK);
        initPawnOnBoard(1,6,ChessColor.BLACK);
        initPawnOnBoard(1,7,ChessColor.BLACK);
        initPawnOnBoard(6,0,ChessColor.WHITE);
        initPawnOnBoard(6,1,ChessColor.WHITE);
        initPawnOnBoard(6,2,ChessColor.WHITE);
        initPawnOnBoard(6,3,ChessColor.WHITE);
        initPawnOnBoard(6,4,ChessColor.WHITE);
        initPawnOnBoard(6,5,ChessColor.WHITE);
        initPawnOnBoard(6,6,ChessColor.WHITE);
        initPawnOnBoard(6,7,ChessColor.WHITE);

        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,5,ChessColor.BLACK);
        initBishopOnBoard(7,2,ChessColor.WHITE);
        initBishopOnBoard(7,5,ChessColor.WHITE);

        initKnightOnBoard(0,1,ChessColor.BLACK);
        initKnightOnBoard(0,6,ChessColor.BLACK);
        initKnightOnBoard(7,1,ChessColor.WHITE);
        initKnightOnBoard(7,6,ChessColor.WHITE);

        initKingOnBoard(0,4,ChessColor.BLACK);
        initKingOnBoard(7,4,ChessColor.WHITE);

        initQueenOnBoard(0,3,ChessColor.BLACK);
        initQueenOnBoard(7,3,ChessColor.WHITE);
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }



    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        chessGameFrame.jTextField.setText(currentColor.toString());
        if(getWinner().equals("b") ){
            JOptionPane.showMessageDialog(null,String.format("%40s","Winner is Black!") ,"END",JOptionPane.PLAIN_MESSAGE );
            winner = "n";
        }
        else if(getWinner().equals("w") ){
            JOptionPane.showMessageDialog(null,String.format("%40s","Winner is White!"),"END",JOptionPane.PLAIN_MESSAGE ) ;
            winner = "n";
        }
    }

    public void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    public void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    public void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    public void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    public void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    public void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
    }

    public String getWinner() {
        int c = 2;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.chessComponents[i][j] instanceof KingChessComponent) {
                    c--;
                }
            }
        }
        if (c == 1) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.chessComponents[i][j] instanceof KingChessComponent) {
                        if (this.chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                            winner = "b";
                        } else {
                            winner = "w";
                        }
                    }
                }
            }
        }
        return winner;
    }


}
