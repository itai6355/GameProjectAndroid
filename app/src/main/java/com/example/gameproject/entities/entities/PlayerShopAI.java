package com.example.gameproject.entities.entities;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.gameproject.environments.GameMap;
import com.example.gameproject.environments.MapManager;
import com.example.gameproject.environments.Tiles;
import com.example.gameproject.gamestates.playing.Playing;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.HelpMethods;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.main.GamePanel;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;

import java.util.Random;

public class PlayerShopAI {

    private final Random random = new Random();

    private int aniTick, aniIndex;
    private int x;
    private final int y;
    private final RectF bound;
    private final GameCharacters skin;
    private int faceDir;



    public PlayerShopAI(GameCharacters skin, RectF bound) {
        this.skin = skin;
        this.bound = bound;
        x = (int) (random.nextInt((int) bound.width()) + bound.left);
        faceDir = random.nextInt(2) + 2;
        y = (int) bound.bottom - GameConstants.Sprite.SIZE - GameConstants.Sprite.Y_DRAW_OFFSET;
    }


    public void update(double delta) {
        updateAnimation();
        float deltaChange = (float) (delta * 300);

        switch (faceDir) {
            case GameConstants.Face_Dir.RIGHT:
                if (x + deltaChange < bound.right) x += (int) deltaChange;
                else faceDir = GameConstants.Face_Dir.LEFT;
                break;

            case GameConstants.Face_Dir.LEFT:
                if (x - deltaChange > bound.left) x -= (int) deltaChange;
                else faceDir = GameConstants.Face_Dir.RIGHT;
                break;
        }

    }

    public void render(Canvas canvas) {
        canvas.drawBitmap(skin.getSprite(aniIndex, faceDir), x, y, null);
    }

    protected void updateAnimation() {
        aniTick++;
        if (aniTick >= GameConstants.Animation.SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GameConstants.Animation.AMOUNT)
                aniIndex = 0;
        }
    }

    public void touchEvents(MotionEvent event) {

    }


    public boolean isIn(MotionEvent e, CustomButton b) {
        return b.getHitbox().contains(e.getX(), e.getY());
    }
}
