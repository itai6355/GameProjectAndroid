package com.example.gameproject.gamestates.shop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;

public class ShopState extends BaseState implements GameStateInterface {


    private boolean init = false;
    private final CustomButton btnBack = new CustomButton(20, 20, ButtonImages.SETTINGS_BACK.getWidth(), ButtonImages.SETTINGS_BACK.getHeight());


    private final Paint paint = new Paint();
    final Paint BlackPaint = new Paint();
    final Paint textPaint = new Paint();


    private final ShopStates state = ShopStates.ITEMS;

    private ItemShop shopItemState;
    private CharacterShop shopCharactersState;

    private int page = 0;
    private int maxPagesInThis;
    int yButtons = MainActivity.GAME_HEIGHT / 2 - ShopImages.SHOP_ARROW_LEFT.getHeight() / 2;

    private final CustomButton door = new CustomButton(MainActivity.GAME_WIDTH - ShopImages.SHOP_DOOR_CLOSED_BACKGRAWND.getWidth() - 20 * GameConstants.Sprite.SCALE_MULTIPLIER, MainActivity.GAME_HEIGHT - ShopImages.SHOP_DOOR_CLOSED_BACKGRAWND.getHeight(), ShopImages.SHOP_DOOR_CLOSED_BACKGRAWND.getWidth(), ShopImages.SHOP_DOOR_CLOSED_BACKGRAWND.getHeight());

    private final CustomButton chest = new CustomButton(0, MainActivity.GAME_HEIGHT - (float) ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getHeight() - (float) ShopImages.SHOP_TREASURE_BOX_BACKGRAWND.getHeight(), ShopImages.SHOP_TREASURE_BOX_BACKGRAWND.getWidth(), ShopImages.SHOP_TREASURE_BOX_BACKGRAWND.getHeight());

    private final CustomButton arrowLeft = new CustomButton(270, yButtons, ButtonImages.EMPTY_SMALL.getWidth(), ButtonImages.EMPTY_SMALL.getHeight());

    private final CustomButton arrowRight = new CustomButton(MainActivity.GAME_WIDTH - 400, yButtons, ButtonImages.EMPTY_SMALL.getWidth(), ButtonImages.EMPTY_SMALL.getHeight());


    public ShopState(Game game) {
        super(game);
        initPaint();
    }

    @Override
    public void update(double delta) {
        if (!init) initStates();
        switch (state) {
            case ITEMS -> {
                shopItemState.update(delta);
                shopItemState.setPage(page);
                this.maxPagesInThis = shopItemState.getMAX_PAGES();
            }
            case CHARACTERS -> {
                shopCharactersState.update(delta);
                shopCharactersState.setPage(page);
                this.maxPagesInThis = shopCharactersState.getMAX_PAGES();
            }
        }

    }

    @Override
    public void render(Canvas canvas) {
        drawBackground(canvas);
        if (!init) initStates();

        switch (state) {
            case ITEMS -> shopItemState.render(canvas);
            case CHARACTERS -> shopCharactersState.render(canvas);
        }
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
        canvas.drawBitmap(ShopImages.SHOP_WINDOW_BACKGRAWND.getImage(), 200, 200, null);
        canvas.drawBitmap(ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getImage(), 0, MainActivity.GAME_HEIGHT - ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getHeight(), null);
        canvas.drawBitmap(ShopImages.SHOP_LAMP.getImage(), (float) ShopImages.SHOP_LAMP.getWidth() / 2 + 6 * GameConstants.Sprite.SCALE_MULTIPLIER, MainActivity.GAME_HEIGHT - (float) ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getHeight() / 2 - (float) ShopImages.SHOP_LAMP.getHeight() / 2, null);
        if (!chest.isPushed())
            canvas.drawBitmap(ButtonImages.CHEST.getBtnImg(chest.isPushed()), chest.getHitbox().left, chest.getHitbox().top, null);
        else
            canvas.drawBitmap(ButtonImages.CHEST.getBtnImg(chest.isPushed()), chest.getHitbox().left, chest.getHitbox().top - 4 * GameConstants.Sprite.SCALE_MULTIPLIER, null);
        canvas.drawBitmap(ShopImages.SHOP_BARREL_BACKGRAWND.getImage(), ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getWidth(), MainActivity.GAME_HEIGHT - (float) ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getHeight() / 2, null);
        canvas.drawBitmap(ButtonImages.DOOR_IMAGE.getBtnImg(door.isPushed()), door.getHitbox().left, door.getHitbox().top, null);
        canvas.drawBitmap(ButtonImages.SETTINGS_BACK.getBtnImg(btnBack.isPushed()), btnBack.getHitbox().left, btnBack.getHitbox().top, null);

        canvas.drawBitmap(ButtonImages.EMPTY_SMALL.getBtnImg(arrowLeft.isPushed()), arrowLeft.getHitbox().left, arrowLeft.getHitbox().top, null);
        canvas.drawBitmap(ShopImages.SHOP_ARROW_LEFT.getImage(), arrowLeft.getHitbox().left + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, arrowLeft.getHitbox().top + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, null);

        canvas.drawBitmap(ButtonImages.EMPTY_SMALL.getBtnImg(arrowRight.isPushed()), arrowRight.getHitbox().left, arrowRight.getHitbox().top, null);
        canvas.drawBitmap(ShopImages.SHOP_ARROW_RIGHT.getImage(), arrowRight.getHitbox().left + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, arrowRight.getHitbox().top + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, null);

        canvas.drawBitmap(ShopImages.SHOP_BAR_1.getImage(),
                (float) (MainActivity.GAME_WIDTH / 2 - ShopImages.SHOP_BAR_1.getWidth() / 2),
                ((float) MainActivity.GAME_HEIGHT / 8 * 7) + GameConstants.Sprite.SCALE_MULTIPLIER, null);


        canvas.drawText("Page: " + (page + 1) + "/" + maxPagesInThis,
                (float) (MainActivity.GAME_WIDTH / 2 - ShopImages.SHOP_BAR_1.getWidth() / 2) + 13 * GameConstants.Sprite.SCALE_MULTIPLIER,
                ((float) MainActivity.GAME_HEIGHT / 8 * 7) + 14 * GameConstants.Sprite.SCALE_MULTIPLIER,
                textPaint);
    }


    @Override
    public void touchEvents(MotionEvent event) {
        if (!init) initStates();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, btnBack)) btnBack.setPushed(true);
            else if (isIn(event, door)) door.setPushed(true);
            else if (isIn(event, chest)) chest.setPushed(true);
            else if (isIn(event, arrowLeft)) arrowLeft.setPushed(true);
            else if (isIn(event, arrowRight)) arrowRight.setPushed(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isIn(event, btnBack)) {
                if (btnBack.isPushed()) game.setCurrentGameState(Game.GameState.PLAYING);
            } else if (isIn(event, door)) {
                if (door.isPushed()) game.setCurrentGameState(Game.GameState.PLAYING);
            } else if (isIn(event, chest)) {
                if (chest.isPushed()) game.setCurrentGameState(Game.GameState.INVENTORY);
            } else if (isIn(event, arrowLeft)) {
                if (arrowLeft.isPushed() && page > 0) page--;
            } else if (isIn(event, arrowRight)) {
                if (arrowRight.isPushed() && page < maxPagesInThis - 1)
                    page++;
            }
            arrowLeft.setPushed(false);
            arrowRight.setPushed(false);
            chest.setPushed(false);
            door.setPushed(false);
            btnBack.setPushed(false);
        }

        switch (state) {
            case ITEMS -> shopItemState.touchEvents(event);
            case CHARACTERS -> shopCharactersState.touchEvents(event);
        }
    }


    private void initPaint() {
        paint.setColor(Color.parseColor("#151328"));
        BlackPaint.setColor(Color.BLACK);
        BlackPaint.setTextSize(BlackPaint.getTextSize() + 15);
        BlackPaint.setStrokeWidth(3);
        BlackPaint.setStyle(Paint.Style.STROKE);

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textPaint.getTextSize() + 50);
        textPaint.setStrokeWidth(5);
        textPaint.setStyle(Paint.Style.STROKE);
    }

    private void initStates() {
        init = true;
        shopItemState = new ItemShop(game);
        shopCharactersState = new CharacterShop(game);
    }


    enum ShopStates {
        ITEMS, CHARACTERS
    }

}


