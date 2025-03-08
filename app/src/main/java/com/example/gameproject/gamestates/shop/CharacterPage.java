package com.example.gameproject.gamestates.shop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.gameproject.entities.entities.GameCharacters;
import com.example.gameproject.entities.entities.Icons;
import com.example.gameproject.entities.entities.PlayerShopAI;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;

public class CharacterPage {

    private final GameCharacters skin;
    private final Icons icon;
    private final String name;
    private boolean bought = false;
    private final PlayerShopAI playerShopAI;
    private final int PRICE;

    private final Game game;

    private final Paint textPaint = new Paint();
    private final Paint textGoldPaint = new Paint();
    private final Paint textGreenPaint = new Paint();

    float xStart = (float) (MainActivity.GAME_WIDTH / 2 - ShopImages.CHARACTER_SHOP_BOOK.getWidth() / 2);
    float yStart = (float) (MainActivity.GAME_HEIGHT / 2 - ShopImages.CHARACTER_SHOP_BOOK.getHeight() / 2);

    private boolean settingSkin = false;
    public CustomButton setSkinBtn = new CustomButton((float) (MainActivity.GAME_WIDTH / 2 + ButtonImages.SHOP_SET_SKIN.getWidth()), yStart + ButtonImages.SHOP_SET_SKIN.getHeight() + GameConstants.Sprite.Y_DRAW_OFFSET, ButtonImages.SHOP_SET_SKIN.getWidth(), ButtonImages.SHOP_SET_SKIN.getHeight());

    private CustomButton btnBuy;

    public CharacterPage(Game game, GameCharacters skin, Icons icon, String name, int price) {
        this.skin = skin;
        this.icon = icon;
        this.name = name;
        this.PRICE = price;
        this.game = game;
        initPaint();
        RectF bound = new RectF(xStart, yStart, xStart + ShopImages.CHARACTER_SHOP_BOOK.getWidth() - GameConstants.Sprite.SIZE - GameConstants.Sprite.X_DRAW_OFFSET, yStart + ShopImages.CHARACTER_SHOP_BOOK.getHeight());
        playerShopAI = new PlayerShopAI(skin, bound);
        btnBuy = new CustomButton(xStart + 2 * icon.getWidth() - (float) ShopImages.SHOP_BAR_2.getWidth() / 4 * 3, yStart + 2 * icon.getHeight(), ShopImages.SHOP_BAR_2.getImage().getWidth(), ShopImages.SHOP_BAR_2.getImage().getHeight());
    }


    public void draw(Canvas canvas) {
        canvas.drawBitmap(icon.getImage(), xStart + icon.getWidth(), yStart + icon.getWidth(), null);

        canvas.drawBitmap(ShopImages.SHOP_BAR_3.getImage(), xStart + 2 * icon.getWidth() + 2 * GameConstants.Sprite.SCALE_MULTIPLIER, yStart + GameConstants.Sprite.SCALE_MULTIPLIER, null);

        canvas.drawText(name, xStart + 2 * icon.getWidth() + 7 * GameConstants.Sprite.SCALE_MULTIPLIER, yStart + 10 * GameConstants.Sprite.SCALE_MULTIPLIER, textPaint);

        canvas.drawBitmap(ShopImages.SHOP_BAR_2.getImage(), xStart + 2 * icon.getWidth() - (float) ShopImages.SHOP_BAR_2.getWidth() / 4 * 3, yStart + 2 * icon.getHeight(), null);
        if (!bought) {
            canvas.drawText(String.valueOf(PRICE), xStart + 2 * icon.getWidth() - (float) ShopImages.SHOP_BAR_2.getWidth() / 3 * 2 + 2 * GameConstants.Sprite.X_DRAW_OFFSET, yStart + 2 * icon.getHeight() + 2 * GameConstants.Sprite.Y_DRAW_OFFSET, textGoldPaint);
            canvas.drawBitmap(GameImages.COIN_SMALL.getImage(), xStart + 2 * icon.getWidth() - (float) ShopImages.SHOP_BAR_2.getWidth() / 3 * 2 + 10 * GameConstants.Sprite.X_DRAW_OFFSET + String.valueOf(PRICE).length() * GameConstants.Sprite.SCALE_MULTIPLIER, yStart + 2 * icon.getHeight() - GameConstants.Sprite.SCALE_MULTIPLIER, null);
        } else {
            canvas.drawText("BOUGHT", xStart + 2 * icon.getWidth() - (float) ShopImages.SHOP_BAR_2.getWidth() / 3 * 2 + GameConstants.Sprite.X_DRAW_OFFSET, yStart + 2 * icon.getHeight() + 2 * GameConstants.Sprite.Y_DRAW_OFFSET, textGreenPaint);
            canvas.drawBitmap(ButtonImages.SHOP_SET_SKIN.getBtnImg(setSkinBtn.isPushed()), setSkinBtn.getHitbox().left, setSkinBtn.getHitbox().top, null);
        }
        playerShopAI.render(canvas);

    }

    public void update(double delta) {
        playerShopAI.update(delta);
    }

    public void touchEvents(MotionEvent event) {
        playerShopAI.touchEvents(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            settingSkin = true;
            btnBuy.setPushed(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (settingSkin && isIn(event, setSkinBtn)) setSkin();
            else if (isIn(event, btnBuy)) {
                System.out.println("Test42:  Player in the btnBuy");
                if (!bought) {
                    System.out.println("Test42:  Player didnt bought the skin");
                    if (btnBuy.isPushed()) {
                        System.out.println("Test42:  the button is pused , starting the buying");
                        startBuying();
                    }
                }
            }

            btnBuy.setPushed(false);
            settingSkin = false;
        }


    }

    private void startBuying() {
        int playerCoins = game.getPlayer().getCoins();
        if (playerCoins >= PRICE) {
            game.getPlayer().setCoins(playerCoins - PRICE);
            Buy();
        }
    }

    public void setSkin() {
        String skinName = name;
        if (setSkinBtn.isPushed())
            skinName = "Boy";

        setSkinBtn.setPushed(!setSkinBtn.isPushed());
        CharacterShop.setSkin(this);
        game.getPlayer().setSkinAndIcon(skinName);


    }

    public void Buy() {
        this.bought = true;
        setSkin();
    }

    public boolean isBought() {
        return bought;
    }

    public String getName() {
        return name;
    }

    private void initPaint() {
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textPaint.getTextSize() + 50);
        textPaint.setStrokeWidth(5);
        textPaint.setStyle(Paint.Style.STROKE);

        textGoldPaint.setColor(Color.YELLOW);
        textGoldPaint.setTextSize(textPaint.getTextSize() - 10);
        textGoldPaint.setStrokeWidth(3);
        textGoldPaint.setStyle(Paint.Style.STROKE);

        textGreenPaint.setColor(Color.GREEN);
        textGreenPaint.setTextSize(textPaint.getTextSize() - 10);
        textGreenPaint.setStrokeWidth(3);
        textGreenPaint.setStyle(Paint.Style.STROKE);
    }

    public boolean isIn(MotionEvent e, CustomButton b) {
        return b.getHitbox().contains(e.getX(), e.getY());
    }


}
