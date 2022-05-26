package controller;

import model.*;
import view.Chessboard;
import view.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }



    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData);
            chessboard.setLayout(null); // Use absolute layout.
            chessboard.setSize(1000, 1000);
            boolean correction1 = true;
            boolean correction2 = true;
            boolean correction3 = true;
            ArrayList<String> check = new ArrayList<>();
            for (int i = 1; i < chessData.size(); i++) {
                if (chessData.get(i).contains("p") || chessData.get(i).contains("r") || chessData.get(i).contains("k") || chessData.get(i).contains("q")||
                        chessData.get(i).contains("b") || chessData.get(i).contains("n") || chessData.get(i).contains("P") || chessData.get(i).contains("R") ||
                        chessData.get(i).contains("K") || chessData.get(i).contains("Q") || chessData.get(i).contains("N") || chessData.get(i).contains("B") ||
                        chessData.get(i).contains("_")){
                    check.add(chessData.get(i));
                }
            }
            if (check.size()!=8)  correction1 = false;
            else {
                for (int i = 1; i < check.size(); i++) {
                    if (check.get(i).length() != 8)  correction1 = false;
                }
            }
            if (!correction1)  JOptionPane.showMessageDialog(null,"Chessboard is not 8*8");
            else {
                for (int i = 1; i < 9; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (chessData.get(i).charAt(j) != 'p'&& chessData.get(i).charAt(j) != 'r' &&chessData.get(i).charAt(j) != 'b'&&
                                chessData.get(i).charAt(j) != 'n' && chessData.get(i).charAt(j) != 'k' && chessData.get(i).charAt(j) != 'q' &&
                                chessData.get(i).charAt(j) != 'K'  && chessData.get(i).charAt(j) != 'Q' && chessData.get(i).charAt(j) != 'R' &&
                                chessData.get(i).charAt(j) != 'B' && chessData.get(i).charAt(j) != 'N' && chessData.get(i).charAt(j) != 'P' &&
                                chessData.get(i).charAt(j) != '_'){
                            correction2 = false;
                        }
                    }
                }
                if (chessData.size()<10||(chessData.get(9).charAt(0) != 'b' && chessData.get(9).charAt(0) != 'w'))  correction3 = false;
                if (!correction2) JOptionPane.showMessageDialog(null,"The chess is not one of the six chesses!");
                else if (!correction3)  JOptionPane.showMessageDialog(null,"Fail to Get Current Player");
                else {
                    chessboard.initiateEmptyChessboard();
                    for (int i = 1; i < 9; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (chessData.get(i).charAt(j) == 'R')  chessboard.initRookOnBoard(i-1,j,ChessColor.BLACK);
                            if (chessData.get(i).charAt(j) == 'B')  chessboard.initBishopOnBoard(i-1,j,ChessColor.BLACK);
                            if (chessData.get(i).charAt(j) == 'N')  chessboard.initKnightOnBoard(i-1,j,ChessColor.BLACK);
                            if (chessData.get(i).charAt(j) == 'P')  chessboard.initPawnOnBoard(i-1,j,ChessColor.BLACK);
                            if (chessData.get(i).charAt(j) == 'Q')  chessboard.initQueenOnBoard(i-1,j,ChessColor.BLACK);

                            if (chessData.get(i).charAt(j) == 'r')  chessboard.initRookOnBoard(i-1,j,ChessColor.WHITE);
                            if (chessData.get(i).charAt(j) == 'b')  chessboard.initBishopOnBoard(i-1,j,ChessColor.WHITE);
                            if (chessData.get(i).charAt(j) == 'n')  chessboard.initKnightOnBoard(i-1,j,ChessColor.WHITE);
                            if (chessData.get(i).charAt(j) == 'p')  chessboard.initPawnOnBoard(i-1,j,ChessColor.WHITE);
                            if (chessData.get(i).charAt(j) == 'q')  chessboard.initQueenOnBoard(i-1,j,ChessColor.WHITE);

                        }
                    }
                    if (chessData.get(9).charAt(0) == 'w'){
                        chessboard.setCurrentColor(ChessColor.WHITE);
                        JOptionPane.showMessageDialog(null,"Current Player is White");

                    }
                    else if (chessData.get(9).charAt(0) == 'l'){
                        chessboard.setCurrentColor(ChessColor.BLACK);
                        JOptionPane.showMessageDialog(null,"Current Player is Black");
                    }
                }
            }
            chessboard.repaint();
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Files SaveGame(Chessboard chessboard) throws IOException{
        ChessComponent chessComponent;
        char name;
        char[][] chars = new char[8][8];
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String dateString = formatter.format(date);
        File save = new File("E:\\Java\\ChessDemo\\save\\"+dateString+".txt");
        save.createNewFile();
        FileWriter writer = new FileWriter(save);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponent = chessboard.getChessComponents()[i][j];
                if (chessComponent.getChessColor() == ChessColor.BLACK){
                    if (chessComponent instanceof RookChessComponent)  chars[i][j] = 'R';
                    if (chessComponent instanceof KnightChessComponent)  chars[i][j] = 'N';
                    if (chessComponent instanceof BishopChessComponent)  chars[i][j] = 'B';
                    if (chessComponent instanceof PawnChessComponent)  chars[i][j] = 'P';
                    if (chessComponent instanceof QueenChessComponent)  chars[i][j] = 'Q';
                }
                else if (chessComponent.getChessColor() == ChessColor.WHITE){
                    if (chessComponent instanceof RookChessComponent)  chars[i][j] = 'r';
                    if (chessComponent instanceof KnightChessComponent)  chars[i][j] = 'n';
                    if (chessComponent instanceof BishopChessComponent)  chars[i][j] = 'b';
                    if (chessComponent instanceof PawnChessComponent)  chars[i][j] = 'p';
                    if (chessComponent instanceof QueenChessComponent)  chars[i][j] = 'q';

                }
                else if (chessComponent.getChessColor() == ChessColor.NONE)  chars[i][j] = '_';
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                name = chars[i][j];
                if (j==0)  writer.write("\r\n"+name);
                else writer.write(name);
            }
        }
        if (chessboard.getCurrentColor() == ChessColor.BLACK)  writer.write("\r\nl");
        else writer.write("\r\nw");
        writer.close();
        return null;
    }

    public Files SaveGame1(Chessboard chessboard) throws IOException{
        ChessComponent chessComponent;
        char name;
        char[][] chars = new char[8][8];
        File save = new File("save1.txt");
        save.createNewFile();
        FileWriter writer = new FileWriter(save);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponent = chessboard.getChessComponents()[i][j];
                if (chessComponent.getChessColor() == ChessColor.BLACK){
                    if (chessComponent instanceof RookChessComponent)  chars[i][j] = 'R';
                    if (chessComponent instanceof KnightChessComponent)  chars[i][j] = 'N';
                    if (chessComponent instanceof BishopChessComponent)  chars[i][j] = 'B';
                    if (chessComponent instanceof PawnChessComponent)  chars[i][j] = 'P';
                    if (chessComponent instanceof QueenChessComponent)  chars[i][j] = 'Q';
                }
                else if (chessComponent.getChessColor() == ChessColor.WHITE){
                    if (chessComponent instanceof RookChessComponent)  chars[i][j] = 'r';
                    if (chessComponent instanceof KnightChessComponent)  chars[i][j] = 'n';
                    if (chessComponent instanceof BishopChessComponent)  chars[i][j] = 'b';
                    if (chessComponent instanceof PawnChessComponent)  chars[i][j] = 'p';
                    if (chessComponent instanceof QueenChessComponent)  chars[i][j] = 'q';

                }
                else if (chessComponent.getChessColor() == ChessColor.NONE)  chars[i][j] = '_';
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                name = chars[i][j];
                if (j==0)  writer.write("\r\n"+name);
                else writer.write(name);
            }
        }
        if (chessboard.getCurrentColor() == ChessColor.BLACK)  writer.write("\r\nl");
        else writer.write("\r\nw");
        writer.close();
        return null;
    }

    public Files SaveGame2(Chessboard chessboard) throws IOException{
        ChessComponent chessComponent;
        char name;
        char[][] chars = new char[8][8];
        File save = new File("save2.txt");
        save.createNewFile();
        FileWriter writer = new FileWriter(save);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponent = chessboard.getChessComponents()[i][j];
                if (chessComponent.getChessColor() == ChessColor.BLACK){
                    if (chessComponent instanceof RookChessComponent)  chars[i][j] = 'R';
                    if (chessComponent instanceof KnightChessComponent)  chars[i][j] = 'N';
                    if (chessComponent instanceof BishopChessComponent)  chars[i][j] = 'B';
                    if (chessComponent instanceof PawnChessComponent)  chars[i][j] = 'P';
                    if (chessComponent instanceof QueenChessComponent)  chars[i][j] = 'Q';
                }
                else if (chessComponent.getChessColor() == ChessColor.WHITE){
                    if (chessComponent instanceof RookChessComponent)  chars[i][j] = 'r';
                    if (chessComponent instanceof KnightChessComponent)  chars[i][j] = 'n';
                    if (chessComponent instanceof BishopChessComponent)  chars[i][j] = 'b';
                    if (chessComponent instanceof PawnChessComponent)  chars[i][j] = 'p';
                    if (chessComponent instanceof QueenChessComponent)  chars[i][j] = 'q';

                }
                else if (chessComponent.getChessColor() == ChessColor.NONE)  chars[i][j] = '_';
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                name = chars[i][j];
                if (j==0)  writer.write("\r\n"+name);
                else writer.write(name);
            }
        }
        if (chessboard.getCurrentColor() == ChessColor.BLACK)  writer.write("\r\nl");
        else writer.write("\r\nw");
        writer.close();
        return null;
    }
    public Files SaveGame3(Chessboard chessboard) throws IOException{
        ChessComponent chessComponent;
        char name;
        char[][] chars = new char[8][8];
        File save = new File("save3.txt");
        save.createNewFile();
        FileWriter writer = new FileWriter(save);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponent = chessboard.getChessComponents()[i][j];
                if (chessComponent.getChessColor() == ChessColor.BLACK){
                    if (chessComponent instanceof RookChessComponent)  chars[i][j] = 'R';
                    if (chessComponent instanceof KnightChessComponent)  chars[i][j] = 'N';
                    if (chessComponent instanceof BishopChessComponent)  chars[i][j] = 'B';
                    if (chessComponent instanceof PawnChessComponent)  chars[i][j] = 'P';
                    if (chessComponent instanceof QueenChessComponent)  chars[i][j] = 'Q';
                }
                else if (chessComponent.getChessColor() == ChessColor.WHITE){
                    if (chessComponent instanceof RookChessComponent)  chars[i][j] = 'r';
                    if (chessComponent instanceof KnightChessComponent)  chars[i][j] = 'n';
                    if (chessComponent instanceof BishopChessComponent)  chars[i][j] = 'b';
                    if (chessComponent instanceof PawnChessComponent)  chars[i][j] = 'p';
                    if (chessComponent instanceof QueenChessComponent)  chars[i][j] = 'q';

                }
                else if (chessComponent.getChessColor() == ChessColor.NONE)  chars[i][j] = '_';
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                name = chars[i][j];
                if (j==0)  writer.write("\r\n"+name);
                else writer.write(name);
            }
        }
        if (chessboard.getCurrentColor() == ChessColor.BLACK)  writer.write("\r\nl");
        else writer.write("\r\nw");
        writer.close();
        return null;
    }

    public List<String> resetGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData);
            chessboard.setLayout(null); // Use absolute layout.
            chessboard.setSize(1000, 1000);
            chessboard.initiateEmptyChessboard();
            for (int i = 1; i < 9; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessData.get(i).charAt(j) == 'R')  chessboard.initRookOnBoard(i-1,j,ChessColor.BLACK);
                    if (chessData.get(i).charAt(j) == 'B')  chessboard.initBishopOnBoard(i-1,j,ChessColor.BLACK);
                    if (chessData.get(i).charAt(j) == 'N')  chessboard.initKnightOnBoard(i-1,j,ChessColor.BLACK);
                    if (chessData.get(i).charAt(j) == 'P')  chessboard.initPawnOnBoard(i-1,j,ChessColor.BLACK);
                    if (chessData.get(i).charAt(j) == 'Q')  chessboard.initQueenOnBoard(i-1,j,ChessColor.BLACK);

                    if (chessData.get(i).charAt(j) == 'r')  chessboard.initRookOnBoard(i-1,j,ChessColor.WHITE);
                    if (chessData.get(i).charAt(j) == 'b')  chessboard.initBishopOnBoard(i-1,j,ChessColor.WHITE);
                    if (chessData.get(i).charAt(j) == 'n')  chessboard.initKnightOnBoard(i-1,j,ChessColor.WHITE);
                    if (chessData.get(i).charAt(j) == 'p')  chessboard.initPawnOnBoard(i-1,j,ChessColor.WHITE);
                    if (chessData.get(i).charAt(j) == 'q')  chessboard.initQueenOnBoard(i-1,j,ChessColor.WHITE);

                }
            }
            chessboard.setCurrentColor(ChessColor.BLACK);
            chessboard.repaint();
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Files withdraw(Chessboard chessboard) throws IOException{
        ChessComponent chessComponent;
        char name;
        char[][] chars = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponent = chessboard.getChessComponents()[i][j];
                if (chessComponent.getChessColor() == ChessColor.BLACK){
                    if (chessComponent instanceof RookChessComponent)  chars[i][j] = 'R';
                    if (chessComponent instanceof KnightChessComponent)  chars[i][j] = 'N';
                    if (chessComponent instanceof BishopChessComponent)  chars[i][j] = 'B';
                    if (chessComponent instanceof PawnChessComponent)  chars[i][j] = 'P';
                    if (chessComponent instanceof QueenChessComponent)  chars[i][j] = 'Q';
                }
                else if (chessComponent.getChessColor() == ChessColor.WHITE){
                    if (chessComponent instanceof RookChessComponent)  chars[i][j] = 'r';
                    if (chessComponent instanceof KnightChessComponent)  chars[i][j] = 'n';
                    if (chessComponent instanceof BishopChessComponent)  chars[i][j] = 'b';
                    if (chessComponent instanceof PawnChessComponent)  chars[i][j] = 'p';
                    if (chessComponent instanceof QueenChessComponent)  chars[i][j] = 'q';

                }
                else if (chessComponent.getChessColor() == ChessColor.NONE)  chars[i][j] = '_';
            }
        }

        File save = new File("withdraw.txt");
//        File file = new File("playback");
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            if (!save.exists()) {
                boolean hasFile = save.createNewFile();
                if (hasFile) {
                    System.out.println("file not exists, create new file");
                }
                fos = new FileOutputStream(save);
            } else {
                System.out.println("file exists");
                fos = new FileOutputStream(save, true);
            }
            osw = new OutputStreamWriter(fos, "utf-8");
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    name = chars[i][j];
                    if (j==0)  osw.write("\r\n"+name);
                    else osw.write(name);
                }
            }
            if (chessboard.getCurrentColor() == ChessColor.BLACK)  osw.write("\r\nl");
            else osw.write("\r\nw");
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
        return null;
    }



    public List<String> loadWithdraw(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData);
            chessboard.setLayout(null); // Use absolute layout.
            chessboard.setSize(1000, 1000);
            chessboard.initiateEmptyChessboard();
            if (chessData.size()>=19){
                for (int i = chessData.size() - 18,k=0; i<=chessData.size()-11&&k<8; i++,k++) {
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
                if (chessData.get(chessData.size()-10).charAt(0) == 'l') chessboard.setCurrentColor(ChessColor.BLACK);
                else if (chessData.get(chessData.size()-10).charAt(0) == 'w') chessboard.setCurrentColor(ChessColor.WHITE);
                for (int i = 0; i<9; i++) {
                    System.out.println("**"+chessData.get(chessData.size() - 1));
                    chessData.remove(chessData.size() - 1);
                }
                FileWriter delete = new FileWriter("withdraw.txt",false);
                delete.write("");
                delete.close();
                FileWriter fileWriter = new FileWriter("withdraw.txt");
                for (int i = 1; i < chessData.size(); i++) {
                    System.out.println("&"+chessData.get(i));
                    fileWriter.write("\r\n"+chessData.get(i));
                }
                fileWriter.close();
                chessboard.repaint();
                return chessData;
            }
            if (chessData.size()==10){
               resetGameFromFile("reset.txt");
                FileWriter delete = new FileWriter("withdraw.txt",false);
                delete.write("");
                delete.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Files replay(Chessboard chessboard) throws IOException{
        ChessComponent chessComponent;
        char name;
        char[][] chars = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponent = chessboard.getChessComponents()[i][j];
                if (chessComponent.getChessColor() == ChessColor.BLACK){
                    if (chessComponent instanceof RookChessComponent)  chars[i][j] = 'R';
                    if (chessComponent instanceof KnightChessComponent)  chars[i][j] = 'N';
                    if (chessComponent instanceof BishopChessComponent)  chars[i][j] = 'B';
                    if (chessComponent instanceof PawnChessComponent)  chars[i][j] = 'P';
                    if (chessComponent instanceof QueenChessComponent)  chars[i][j] = 'Q';
                }
                else if (chessComponent.getChessColor() == ChessColor.WHITE){
                    if (chessComponent instanceof RookChessComponent)  chars[i][j] = 'r';
                    if (chessComponent instanceof KnightChessComponent)  chars[i][j] = 'n';
                    if (chessComponent instanceof BishopChessComponent)  chars[i][j] = 'b';
                    if (chessComponent instanceof PawnChessComponent)  chars[i][j] = 'p';
                    if (chessComponent instanceof QueenChessComponent)  chars[i][j] = 'q';

                }
                else if (chessComponent.getChessColor() == ChessColor.NONE)  chars[i][j] = '_';
            }
        }
        File save = new File("replay.txt");
        save.createNewFile();
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            if (!save.exists()) {
                boolean hasFile = save.createNewFile();
                if (hasFile) {
                    System.out.println("file not exists, create new file");
                }
                fos = new FileOutputStream(save);
            } else {
                System.out.println("file exists");
                fos = new FileOutputStream(save, true);
            }
            osw = new OutputStreamWriter(fos, "utf-8");
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    name = chars[i][j];
                    if (j==0)  osw.write("\r\n"+name);
                    else osw.write(name);
                }
            }
            if (chessboard.getCurrentColor() == ChessColor.BLACK)  osw.write("\r\nl");
            else osw.write("\r\nw");
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
        return null;
    }

}
