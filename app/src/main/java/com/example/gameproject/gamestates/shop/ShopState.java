package com.example.gameproject.gamestates.shop;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.gamestates.shop.characters.CharacterShop;
import com.example.gameproject.gamestates.shop.items.ItemShop;
import com.example.gameproject.helpers.var.GameConstants;
import com.example.gameproject.helpers.var.Paints;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;

public class ShopState extends BaseState implements GameStateInterface {


    private final Paint textPaint;

    private final CustomButton btnBack = new CustomButton(20, 20, ButtonImages.SETTINGS_BACK.getWidth(), ButtonImages.SETTINGS_BACK.getHeight());
    private final CustomButton door = new CustomButton(MainActivity.GAME_WIDTH - ShopImages.SHOP_DOOR_CLOSED_BACKGRAWND.getWidth() - 20 * GameConstants.Sprite.SCALE_MULTIPLIER, MainActivity.GAME_HEIGHT - ShopImages.SHOP_DOOR_CLOSED_BACKGRAWND.getHeight(), ShopImages.SHOP_DOOR_CLOSED_BACKGRAWND.getWidth(), ShopImages.SHOP_DOOR_CLOSED_BACKGRAWND.getHeight());
    private final CustomButton chest = new CustomButton(0, MainActivity.GAME_HEIGHT - (float) ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getHeight() - (float) ShopImages.SHOP_TREASURE_BOX_BACKGRAWND.getHeight(), ShopImages.SHOP_TREASURE_BOX_BACKGRAWND.getWidth(), ShopImages.SHOP_TREASURE_BOX_BACKGRAWND.getHeight());
    private final Paint paint;
    private final int yButtons = MainActivity.GAME_HEIGHT / 2 - ShopImages.SHOP_ARROW_LEFT.getHeight() / 2;
    private final CustomButton arrowLeft = new CustomButton(270, yButtons, ButtonImages.EMPTY_SMALL.getWidth(), ButtonImages.EMPTY_SMALL.getHeight());
    private final float arrowLeftX = arrowLeft.getHitbox().left;
    private final float arrowLeftY = arrowLeft.getHitbox().top;
    private final CustomButton arrowRight = new CustomButton(MainActivity.GAME_WIDTH - 400, yButtons, ButtonImages.EMPTY_SMALL.getWidth(), ButtonImages.EMPTY_SMALL.getHeight());
    private final float arrowRightX = arrowRight.getHitbox().left;
    private final float arrowRightY = arrowRight.getHitbox().top;
    private final float gameWidth = MainActivity.GAME_WIDTH;
    private final float gameHeight = MainActivity.GAME_HEIGHT;
    private final float scaleMultiplier = GameConstants.Sprite.SCALE_MULTIPLIER;
    private final float windowX = 200;
    private final float windowY = 200;
    private final float brickBoxY = gameHeight - ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getHeight();
    private final float lampX = (float) ShopImages.SHOP_LAMP.getWidth() / 2 + 6 * scaleMultiplier;
    private float lampY = gameHeight - (float) ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getHeight() / 2 - (float) ShopImages.SHOP_LAMP.getHeight() / 2;
    private final float barrelX = ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getWidth();
    private final float barrelY = gameHeight - (float) ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getHeight() / 2;
    private final float bar1X = (gameWidth / 2 - (float) ShopImages.SHOP_BAR_1.getWidth() / 2);
    private final float bar1Y = (gameHeight / 8 * 7) + scaleMultiplier;
    private boolean init = false;
    private ShopStates state = ShopStates.ITEMS;
    private boolean isBuying = false;

    private ItemShop shopItemState;
    private CharacterShop shopCharactersState;

    private int page = 0;
    private int maxPagesInThis;

    public ShopState(Game game) {
        super(game);
        textPaint = Paints.TEXT_PAINT;
        paint = Paints.BLUE_PAINT;

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
        if (shopItemState == null) return;
        if (shopCharactersState == null) return;

        switch (state) {
            case ITEMS -> shopItemState.render(canvas);
            case CHARACTERS -> shopCharactersState.render(canvas);
        }
    }

    public void setPage(int page) {
        this.page = page;
    }

    private void drawBackground(Canvas canvas) {
        int coinsLength = calculateCoinsLength();
        float coinsX = gameWidth - (coinsLength * scaleMultiplier * 1.5f) - GameImages.COIN_SMALL.getImage().getWidth();
        float coinsY = 110 - GameImages.COIN_SMALL.getImage().getHeight();

        canvas.drawRect(0, 0, gameWidth, gameHeight, paint);
        canvas.drawBitmap(ShopImages.SHOP_WINDOW_BACKGRAWND.getImage(), windowX, windowY, null);
        canvas.drawBitmap(ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getImage(), 0, brickBoxY, null);
        canvas.drawBitmap(ShopImages.SHOP_LAMP.getImage(), lampX, lampY, null);

        float chestY = chest.isPushed() ? chest.getHitbox().top - 4 * scaleMultiplier : chest.getHitbox().top;
        canvas.drawBitmap(ButtonImages.CHEST.getBtnImg(chest.isPushed()), chest.getHitbox().left, chestY, null);

        canvas.drawBitmap(ShopImages.SHOP_BARREL_BACKGRAWND.getImage(), barrelX, barrelY, null);
        canvas.drawBitmap(ButtonImages.DOOR_IMAGE.getBtnImg(door.isPushed()), door.getHitbox().left, door.getHitbox().top, null);
        canvas.drawBitmap(ButtonImages.SETTINGS_BACK.getBtnImg(btnBack.isPushed()), btnBack.getHitbox().left, btnBack.getHitbox().top, null);

        if (!isBuying) {
            drawArrow(canvas, arrowLeft, arrowLeftX, arrowLeftY);
            drawArrow(canvas, arrowRight, arrowRightX, arrowRightY);
        }

        canvas.drawText(String.valueOf(game.getPlayer().getCoins()), coinsX, 100, textPaint);
        canvas.drawBitmap(GameImages.COIN_SMALL.getImage(), coinsX - GameImages.COIN_SMALL.getImage().getWidth(), coinsY, null);

        canvas.drawBitmap(ShopImages.SHOP_BAR_1.getImage(), bar1X, bar1Y, null);
        canvas.drawText("Page: " + (page + 1) + "/" + maxPagesInThis, bar1X + 13 * scaleMultiplier, bar1Y + 14 * scaleMultiplier, textPaint);
    }

    private int calculateCoinsLength() {
        return String.valueOf(game.getPlayer().getCoins()).length() * GameConstants.Sprite.SCALE_MULTIPLIER;
    }

    private void drawArrow(Canvas canvas, CustomButton arrow, float arrowX, float arrowY) {
        canvas.drawBitmap(ButtonImages.EMPTY_SMALL.getBtnImg(arrow.isPushed()), arrowX, arrowY, null);
        if (arrow == arrowLeft)
            canvas.drawBitmap(ShopImages.SHOP_ARROW_LEFT.getImage(), arrowX + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, arrowY + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, null);
        else
            canvas.drawBitmap(ShopImages.SHOP_ARROW_RIGHT.getImage(), arrowX + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, arrowY + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, null);
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
                if (door.isPushed()) setState(ShopStates.CHARACTERS);
            } else if (isIn(event, chest)) {
                if (chest.isPushed()) setState(ShopStates.ITEMS);
            } else if (isIn(event, arrowLeft)) {
                if (arrowLeft.isPushed() && page > 0) page--;
            } else if (isIn(event, arrowRight)) {
                if (arrowRight.isPushed() && page < maxPagesInThis - 1) page++;
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



    private void initStates() {
        shopItemState = new ItemShop(game, this);
        shopCharactersState = new CharacterShop(game);
        init = true;
    }

    public void setIsBuying(boolean isBuying) {
        this.isBuying = isBuying;
    }

    public void setState(ShopStates state) {
        this.state = state;
        page = 0;
    }

    enum ShopStates {
        ITEMS, CHARACTERS
    }

}


