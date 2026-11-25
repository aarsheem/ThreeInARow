package controller;

import model.RowGameModel;

import java.util.Arrays;

public abstract class RowGameRulesStrategy {

    public abstract void reset(RowGameModel gameModel);

    public abstract void move(RowGameModel gameModel, int row, int col);

    private boolean isWinDir(RowGameModel gameModel, int[] pos, int[] dir){
        int row = pos[0], col = pos[1];
        int rowDir = dir[0], colDir = dir[1];
        String match = gameModel.blocksData[row][col].getContents();
        if(match.equals("")) return false;
        int count = 0;
        while(count < 3){
            if (!gameModel.blocksData[row][col].getContents().equals(match)) return false;
            row += rowDir;
            col += colDir;
            count++;
        }
        return true;
    }

    public boolean isWin(RowGameModel gameModel) {
        if(gameModel.movesLeft > 4) return false;
        final int[] hor = {0, 1}, vert = {1, 0}, diag1 = {1, 1}, diag2 = {1, -1};
        final int[] zero = {0, 0};
        int[] pos = zero.clone();
        for(int i = 0; i < 3; i++){
            if(isWinDir(gameModel, pos, hor)) return true;
            pos[0]++;
        }
        pos = zero.clone();
        for(int i = 0; i < 3; i++){
            if(isWinDir(gameModel, pos, vert)) return true;
            pos[1]++;
        }
        if(isWinDir(gameModel, zero, diag1)) return true;
        if(isWinDir(gameModel, new int[]{0, 2}, diag2)) return true;
        return false;
    }


    public boolean isTie(RowGameModel gameModel) {
        return gameModel.movesLeft == 0;
    }
}
