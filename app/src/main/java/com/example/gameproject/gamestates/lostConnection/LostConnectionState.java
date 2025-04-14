package com.example.gameproject.gamestates.lostConnection;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.Paints;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;

public class LostConnectionState extends BaseState implements GameStateInterface {

    private int index = 0;
    private int sprite = 3;
    private int add = 1;

    private boolean isMenu = false;
    private final Paint Paint;

    private long lastUpdateTime = 0;
    private final long UPDATE_INTERVAL = 333;


    int width = (ButtonImages.EMPTY_SMALL.getWidth());
    int height = (ButtonImages.EMPTY_SMALL.getHeight());
    private final CustomButton btn1 = new CustomButton(20, 20, width, height);
    private final CustomButton btn2 = new CustomButton(140, 20, width, height);
    private final CustomButton btn3 = new CustomButton(260, 20, width, height);

    private final CustomButton Menu = new CustomButton(20, MainActivity.GAME_HEIGHT - ButtonImages.EMPTY_SMALL.getHeight() - 20, width, height);
    private final CustomButton Back = new CustomButton(20, MainActivity.GAME_HEIGHT - ButtonImages.EMPTY_SMALL.getHeight() - 20, width, height);

    public LostConnectionState(Game game) {
        super(game);
        this.Paint = Paints.WHITE_PAINT;
    }

    @Override
    public void update(double delta) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastUpdateTime >= UPDATE_INTERVAL) {
            index += add;
            if (index > 4 || index <= 0) add *= -1;
            lastUpdateTime = currentTime;
        }
    }

    @Override
    public void render(Canvas canvas) {


        if (!isMenu)
            canvas.drawBitmap(ButtonImages.PLAYING_SETTING.getBtnImg(Menu.isPushed()), Menu.getHitbox().left, Menu.getHitbox().top, null);
        else {
            int space = 3 * GameConstants.Sprite.SCALE_MULTIPLIER;
            canvas.drawBitmap(ButtonImages.SETTINGS_BACK.getBtnImg(Back.isPushed()), Back.getHitbox().left, Back.getHitbox().top, null);
            canvas.drawBitmap(ButtonImages.EMPTY_SMALL.getBtnImg(btn1.isPushed()), btn1.getHitbox().left, btn1.getHitbox().top, null);
            canvas.drawBitmap(GameImages.LOADING11.getSmallImage(), btn1.getHitbox().left + space, btn1.getHitbox().top + space, null);

            canvas.drawBitmap(ButtonImages.EMPTY_SMALL.getBtnImg(btn2.isPushed()), btn2.getHitbox().left, btn2.getHitbox().top, null);
            canvas.drawBitmap(GameImages.LOADING21.getSmallImage(), btn2.getHitbox().left + space, btn2.getHitbox().top + space, null);

            canvas.drawBitmap(ButtonImages.EMPTY_SMALL.getBtnImg(btn3.isPushed()), btn3.getHitbox().left, btn3.getHitbox().top, null);
            canvas.drawBitmap(GameImages.LOADING31.getSmallImage(), btn3.getHitbox().left + space, btn3.getHitbox().top + space, null);

        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int xLoading = (width - GameImages.getLoadingImage(sprite, index).getWidth()) / 2;
        int yLoading = (height - GameImages.getLoadingImage(sprite, index).getHeight()) / 2;

        canvas.drawBitmap(GameImages.getLoadingImage(sprite, index), xLoading, yLoading, null);

        String message = "Lost Connection...";
        float textWidth = Paint.measureText(message);
        float x = (width - textWidth) / 2;
        float y = (float) height / 2 + (float) GameImages.getLoadingImage(sprite, index).getHeight() / 2 + 4 * GameConstants.Sprite.Y_DRAW_OFFSET;
        canvas.drawText(message, x, y, Paint);

    }

    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, btn1)) {
                btn1.setPushed(true);
            } else if (isIn(event, btn2)) {
                btn2.setPushed(true);
            } else if (isIn(event, btn3)) {
                btn3.setPushed(true);
            }
            if (isMenu) {
                if (isIn(event, Back))
                    Back.setPushed(true);
            } else {
                if (isIn(event, Menu))
                    Menu.setPushed(true);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isIn(event, btn1) && btn1.isPushed()) sprite = 1;
            else if (isIn(event, btn2) && btn2.isPushed()) sprite = 2;
            else if (isIn(event, btn3) && btn3.isPushed()) sprite = 3;
            if (isMenu) {
                if (isIn(event, Back) && Back.isPushed())
                    isMenu = false;
            } else {
                if (isIn(event, Menu) && Menu.isPushed())
                    isMenu = true;

            }

            btn1.setPushed(false);
            btn2.setPushed(false);
            btn3.setPushed(false);
            if (isMenu) Back.setPushed(false);
            else Menu.setPushed(false);

        }
    }
}
