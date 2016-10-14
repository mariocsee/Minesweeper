package mariocsee.android.minesweeper.model;

/*
 * Created by mariocsee on 9/29/16.
 */

import android.util.Log;

import java.util.Random;

public class MinesweeperModel {

    private static MinesweeperModel instance = null;
    Random rand = new Random();

    private MinesweeperModel() {
    }

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }
        return instance;
    }

    public static final short EMPTY = 0;

    public static final short ONE = 1;
    public static final short TWO = 2;
    public static final short THREE = 3;
    public static final short FOUR = 4;
    public static final short FIVE = 5;
    public static final short SIX = 6;
    public static final short SEVEN = 7;
    public static final short EIGHT = 8;

    public static final short MINE = 9;

    private short[][] model = {
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
    };

    public static final short REVEAL = 10;
    public static final short FLAG = 11;

    private short actionType = REVEAL;

    public static final short TOUCHED = 12;
    public static final short UNTOUCHED = 13;

    private short[][] cover = {
            {UNTOUCHED, UNTOUCHED, UNTOUCHED, UNTOUCHED, UNTOUCHED},
            {UNTOUCHED, UNTOUCHED, UNTOUCHED, UNTOUCHED, UNTOUCHED},
            {UNTOUCHED, UNTOUCHED, UNTOUCHED, UNTOUCHED, UNTOUCHED},
            {UNTOUCHED, UNTOUCHED, UNTOUCHED, UNTOUCHED, UNTOUCHED},
            {UNTOUCHED, UNTOUCHED, UNTOUCHED, UNTOUCHED, UNTOUCHED}
    };

    public void cleanBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                model[i][j] = EMPTY;
                cover[i][j] = UNTOUCHED;
            }
        }
        actionType = REVEAL;
    }

    public short getFieldContent(int x, int y) {
        return model[x][y];
    }

    public short getCoverContent(int x, int y) {
        return cover[x][y];
    }

    public short setFieldContent(int x, int y, short content) {
        return model[x][y] = content;
    }

    public short setCoverContent(int x, int y, short content) {
        return cover[x][y] = content;
    }

    public short getTouched() {
        return TOUCHED;
    }

    public short getFlagged() {
        return FLAG;
    }

    public short getActionType() {
        return actionType;
    }

    public void actionFlag() {
        actionType = FLAG;
        Log.d("MODEL_TAG", "actionType = FLAG!");
    }

    public void actionReveal() {
        actionType = REVEAL;
        Log.d("MODEL_TAG", "actionType = REVEAL!");
    }

    public void setMines() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(rand.nextInt(3) == 1) {
                    model[i][j] = MINE;
                    Log.i("MODEL_TAG", "Model " + "[" + i + "]" + "[" + j + "]" + " has a mine!");
                }
            }
        }
    }

    // Counts the number of mines around
    public void setMineCount() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(model[i][j] != MINE) {

                    int mineCounter = 0;

                    if (i > 0 && j > 0 && model [i-1][j-1] == MINE) {
                        mineCounter++;
                    }
                    if (i > 0 && model [i-1][j] == MINE) {
                        mineCounter++;
                    }
                    if (i > 0 && j < 4 && model [i-1][j+1] == MINE) {
                        mineCounter++;
                    }
                    if (j > 0 && model [i][j-1] == MINE) {
                        mineCounter++;
                    }
                    if (model [i][j] == MINE) {
                        mineCounter++;
                    }
                    if (j < 4 && model [i][j+1] == MINE) {
                        mineCounter++;
                    }
                    if (i < 4 && j > 0 && model [i+1][j-1] == MINE) {
                        mineCounter++;
                    }
                    if (i < 4 && model [i+1][j] == MINE) {
                        mineCounter++;
                    }
                    if (i < 4 &&  j < 4 && model [i+1][j+1] == MINE) {
                        mineCounter++;
                    }

                    short mineCounterShort = intToShort(mineCounter);

                    model[i][j] = mineCounterShort;
                    Log.i("MODEL_TAG", "Model " + "[" + i + "]" + "[" + j + "]" + " has " + mineCounter + " around it!");
                }
            }
        }
    }

    private int countMines() {
        int mineCounter = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (model[i][j] == MINE) {
                    mineCounter++;
                }
            }
        }
        return mineCounter;
    }

    private boolean mineRevealed() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (model[i][j] == MINE && cover[i][j] == TOUCHED) {
                    Log.i("MODEL_TAG", "Mine touched!");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean nonmineFlagged() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (model[i][j] != MINE && cover[i][j] == FLAG) {
                    Log.i("MODEL_TAG", "Non-mine flagged!");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean gameLost() {
        return mineRevealed() || nonmineFlagged();
    }

    public boolean checkAllTiles() {

        int minesFlagged = 0;
        int nonMinesOpened = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (model[i][j] == MINE && cover[i][j] == FLAG) {
                    minesFlagged++;
                }
                if (model[i][j] != MINE && cover[i][j] == TOUCHED) {
                    nonMinesOpened++;
                }
            }
        }
        return minesFlagged == countMines() && nonMinesOpened == (25 - countMines());
    }

    // Function that converts ints to short so the int mineCounter can be turned into a short and assigned to model[i]
    private short intToShort(int x) {
        if (x == 1) {
            return ONE;
        }
        else if (x == 2) {
            return TWO;
        }
        else if (x == 3) {
            return THREE;
        }
        else if (x == 4) {
            return FOUR;
        }
        else if (x == 5) {
            return FIVE;
        }
        else if (x == 6) {
            return SIX;
        }
        else if (x == 7) {
            return SEVEN;
        }
        else if (x == 8) {
            return EIGHT;
        }
        else {
            return EMPTY;
        }
    }

}
