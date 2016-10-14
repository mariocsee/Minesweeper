package mariocsee.android.minesweeper.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import mariocsee.android.minesweeper.MainActivity;
import mariocsee.android.minesweeper.R;
import mariocsee.android.minesweeper.model.MinesweeperModel;

/*
 * Created by mariocsee on 9/28/16.
 */

public class MinesweeperView extends View {

    private Paint paintBg;
    private Paint paintLine;
    private Paint paintBomb;
    private Paint paintOpen;

    private Bitmap flagBitmap;
    private Bitmap bombBitmap;
    private Bitmap oneBitmap;
    private Bitmap twoBitmap;
    private Bitmap threeBitmap;
    private Bitmap fourBitmap;
    private Bitmap fiveBitmap;
    private Bitmap sixBitmap;
    private Bitmap sevenBitmap;
    private Bitmap eightBitmap;

    private boolean gameEnd = false;

    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.GRAY);
        paintBg.setStyle(Paint.Style.FILL);

        paintBomb = new Paint();

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);

        paintOpen = new Paint();
        paintOpen.setARGB(0,0,0,0);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inSampleSize = 2;

        flagBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flag, options);
        bombBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bomb, options);
        oneBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.one, options);
        twoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.two, options);
        threeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.three, options);
        fourBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.four, options);
        fiveBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.five, options);
        sixBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.six, options);
        sevenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seven, options);
        eightBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.eight, options);

        MinesweeperModel.getInstance().setMines();
        MinesweeperModel.getInstance().setMineCount();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draws tiles
        drawGameArea(canvas);
        drawModel(canvas);
        drawCover(canvas);
    }

    private void drawModel(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                float centerX = (i) * getWidth() / 5;
                float centerY = (j) * getHeight() / 5;

                if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.MINE) {
                    // draws bomb
                    canvas.drawBitmap(bombBitmap, centerX, centerY, paintBomb);
                }
                else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.ONE) {
                    canvas.drawBitmap(oneBitmap, centerX, centerY, paintBomb);
                }
                else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.TWO) {
                    canvas.drawBitmap(twoBitmap, centerX, centerY, paintBomb);
                }
                else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.THREE) {
                    canvas.drawBitmap(threeBitmap, centerX, centerY, paintBomb);
                }
                else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.FOUR) {
                    canvas.drawBitmap(fourBitmap, centerX, centerY, paintBomb);
                }
                else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.FIVE) {
                    canvas.drawBitmap(fiveBitmap, centerX, centerY, paintBomb);
                }
                else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.SIX) {
                    canvas.drawBitmap(sixBitmap, centerX, centerY, paintBomb);
                }
                else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.SEVEN) {
                    canvas.drawBitmap(sevenBitmap, centerX, centerY, paintBomb);
                }
                else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.EIGHT) {
                    canvas.drawBitmap(eightBitmap, centerX, centerY, paintBomb);
                }
            }
        }
        Log.i("MODEL_TAG", "Bombs and Numbers displayed");
    }


    private void drawCover(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                float centerX = (i) * getWidth() / 5;
                float centerY = (j) * getHeight() / 5;

                if (MinesweeperModel.getInstance().getCoverContent(i, j) == MinesweeperModel.UNTOUCHED) {
                    canvas.drawRect(centerX, centerY, centerX + (getWidth() / 5), centerY + (getHeight() / 5), paintBg);
                }
                else if (MinesweeperModel.getInstance().getCoverContent(i, j) == MinesweeperModel.TOUCHED) {
                    canvas.drawRect(centerX, centerY, centerX + getWidth() / 5, centerY + getHeight() / 5, paintOpen);
                    Log.i("COVER_TAG", "Tile [" + i +"] [" + j + "] is opened.");
                }
                else if (MinesweeperModel.getInstance().getCoverContent(i, j) == MinesweeperModel.FLAG) {
                    canvas.drawRect(centerX, centerY, centerX + getWidth() / 5, centerY + getHeight() / 5, paintBg);
                    canvas.drawBitmap(flagBitmap, centerX, centerY, paintBomb);
                    Log.i("COVER_TAG", "Tile [" + i +"] [" + j + "] is flagged.");
                }

            }
        }
        Log.i("MODEL_TAG", "Cover displayed");

        canvas.drawLine(0, getHeight() / 5, getWidth(), getHeight() / 5,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 5, getWidth(),
                2 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 3 * getHeight() / 5, getWidth(),
                3 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 4 * getHeight() / 5, getWidth(),
                4 * getHeight() / 5, paintLine);

        canvas.drawLine(getWidth() / 5, 0, getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 5, 0, 2 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(3 * getWidth() / 5, 0, 3 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(4 * getWidth() / 5, 0, 4 * getWidth() / 5, getHeight(),
                paintLine);
        Log.i("MODEL_TAG", "Lines drawn");
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (gameEnd) {
                MinesweeperModel.getInstance().cleanBoard();
                MinesweeperModel.getInstance().setMines();
                MinesweeperModel.getInstance().setMineCount();
                invalidate();
                gameEnd = false;
            }
            else {
                int tX = ((int) event.getX()) / (getWidth() / 5);
                int tY = ((int) event.getY()) / (getHeight() / 5);

                handleCoverTouch(tX, tY);

                winningModel();
            }

            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private void handleCoverTouch(int tX, int tY) {
        if (tX < 5 && tY < 5 &&
                MinesweeperModel.getInstance().getCoverContent(tX, tY) == MinesweeperModel.UNTOUCHED &&
                MinesweeperModel.getInstance().getActionType() == MinesweeperModel.REVEAL) {
            MinesweeperModel.getInstance().setCoverContent( tX, tY, MinesweeperModel.getInstance().getTouched() );
        }
        else if (tX < 5 && tY < 5 &&
                MinesweeperModel.getInstance().getCoverContent(tX, tY) == MinesweeperModel.UNTOUCHED &&
                MinesweeperModel.getInstance().getActionType() == MinesweeperModel.FLAG) {
            MinesweeperModel.getInstance().setCoverContent( tX, tY, MinesweeperModel.getInstance().getFlagged() );
        }
    }

    private void drawGameArea(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

        // four horizontal lines
        canvas.drawLine(0, getHeight() / 5, getWidth(), getHeight() / 5,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 5, getWidth(),
                2 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 3 * getHeight() / 5, getWidth(),
                3 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 4 * getHeight() / 5, getWidth(),
                4 * getHeight() / 5, paintLine);

        // four vertical lines
        canvas.drawLine(getWidth() / 5, 0, getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 5, 0, 2 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(3 * getWidth() / 5, 0, 3 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(4 * getWidth() / 5, 0, 4 * getWidth() / 5, getHeight(),
                paintLine);
    }

    private void winningModel() {
        if (MinesweeperModel.getInstance().checkAllTiles() && !(MinesweeperModel.getInstance().gameLost()) ) {
            //game won
            ((MainActivity) getContext()).showSnackBarWithDelete(
                    "Congratulations you win!");
            gameEnd = true;
        }
        else if (MinesweeperModel.getInstance().gameLost()) {
            ((MainActivity) getContext()).showSnackBarWithDelete(
                    "Oh no! You lost!");
            gameEnd = true;
        }
        else if (!(MinesweeperModel.getInstance().checkAllTiles()) && !(MinesweeperModel.getInstance().gameLost())) {

        }
    }


    /*
    public void restartGame() {
        MinesweeperModel.getInstance().cleanBoard();
        MinesweeperModel.getInstance().setMines();
        MinesweeperModel.getInstance().setMineCount();
        invalidate();
    }
    */
}
