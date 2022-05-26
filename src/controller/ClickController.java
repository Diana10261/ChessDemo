package controller;


import model.ChessComponent;
import view.Chessboard;
import view.CountdownClockDemo;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;
    private GameController gameController;
    private int sec = 20;
    private CountdownClockDemo countdownClockDemo;
    private Timer timer;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
        gameController = new GameController(chessboard);
    }

    public void onClick(ChessComponent chessComponent) throws IOException {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                if (timer != null)
                    timer.cancel();
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                first.setSelected(false);
                first = null;
                gameController.withdraw(chessboard);
                gameController.replay(chessboard);
                countdownClockDemo = new CountdownClockDemo();
                countdownClockDemo.initUI();
//                Timer timer = new Timer(10000, new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        if (countdownClockDemo.isChange()){
//                            System.out.println("@@");
//                            chessboard.swapColor();
//                        }
//                    }
//                });
//                timer.start();

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        System.out.println("-------任务执行--------");
                        chessboard.swapColor();
                    }
                }, 5000);
            }
        }
    }

    public void onMouseOver(ChessComponent chessComponent) throws IOException {

    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        if (chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint())){

            return true;
        }
        else return false;
    }
}
