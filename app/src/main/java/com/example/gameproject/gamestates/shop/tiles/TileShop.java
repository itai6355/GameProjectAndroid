package com.example.gameproject.gamestates.shop.tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.entities.items.Items;
import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.gamestates.shop.ShopSloth;
import com.example.gameproject.gamestates.shop.ShopState;
import com.example.gameproject.gamestates.shop.items.BuyPage;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.helpers.var.GameConstants;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;

public class TileShop extends BaseState implements GameStateInterface {

    private final int ShopWidth = 10;
    private final int ShopHeight = 4;

    private final int xStart = 375;
    private final int yStart = 200;

    private final int Xspace = 13 * GameConstants.Sprite.SCALE_MULTIPLIER;
    private final int Yspace = 100;

    private final int MAX_BIOMES = Items.BLOCKS.length;

    private final ShopState shopState;
    private final BuyPage buyPage;

    private final TileCategoryPage[] biomePages = new TileCategoryPage[MAX_BIOMES];
    private final CustomButton[] biomeButtons = new CustomButton[MAX_BIOMES];

    private int activeBiome = 0;

    private ShopSloth currSS;

    private int xCurrIndex = 0;
    private int yCurrIndex = 0;

    private final int xBtn;
    private final int yBtn;
    private final int xSpace = 10 * GameConstants.Sprite.SCALE_MULTIPLIER;

    public TileShop(Game game, ShopState shopState) {
        super(game);

        this.shopState = shopState;
        buyPage = new BuyPage(game, shopState);

        xBtn = MainActivity.GAME_WIDTH / 2 - (ButtonImages.EMPTY_SUPER_SMALL.getWidth() * MAX_BIOMES - xSpace) / 2;

        yBtn = yStart - ButtonImages.EMPTY_SUPER_SMALL.getHeight() - 50;

        initBiomes();
    }

    private void initBiomes() {

        for (int i = 0; i < MAX_BIOMES; i++) {
            biomePages[i] = new TileCategoryPage(Items.BLOCKS[i], xStart, yStart);
            biomeButtons[i] = new CustomButton(xBtn + (i * ButtonImages.EMPTY_SUPER_SMALL.getWidth() + xSpace), yBtn, ButtonImages.EMPTY_SUPER_SMALL.getWidth(), ButtonImages.EMPTY_SUPER_SMALL.getHeight());
        }
    }

    @Override
    public void update(double delta) {

        if (buyPage.isInPage()) {
            buyPage.update(delta);
        } else {
            biomePages[activeBiome].update(delta);
        }
    }

    @Override
    public void render(Canvas canvas) {

        if (buyPage.isInPage()) {
            buyPage.render(canvas);
        } else {

            biomePages[activeBiome].render(canvas);
            drawButtons(canvas);
        }
    }

    @Override
    public void touchEvents(MotionEvent event) {

        if (buyPage.isInPage()) {
            buyPage.touchEvents(event);
            return;
        }

        TileCategoryPage page = biomePages[activeBiome];

        for (int i = 0; i < ShopWidth; i++)
            for (int j = 0; j < ShopHeight; j++)
                if (page.getShopSlots()[page.getCurrPage()][i][j].isIn(event)) {

                    page.setXCurr(i);
                    page.setYCurr(j);

                    xCurrIndex = i;
                    yCurrIndex = j;
                    break;
                }

        currSS = page.getShopSlots()[page.getCurrPage()][xCurrIndex][yCurrIndex];

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (isIn(event, currSS) && currSS.hasItem()) currSS.setPushed(true);

            else for (CustomButton button : biomeButtons)
                if (isIn(event, button)) button.setPushed(true);

        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            if (isIn(event, currSS) && currSS.isPushed()) {
                buyPage.setToPage(true, currSS);
                shopState.setIsBuying(true);
                game.getPlayer().setSelectedTileSpriteId(currSS.getCustomSpriteId());
            } else {

                for (int i = 0; i < biomeButtons.length; i++) {

                    if (isIn(event, biomeButtons[i])) {

                        activeBiome = i;
                        biomePages[i].setPage(0);
                    }
                }
            }

            for (CustomButton button : biomeButtons)
                button.setPushed(false);

            currSS.setPushed(false);
        }
    }

    private void drawButtons(Canvas canvas) {

        for (int i = 0; i < MAX_BIOMES; i++) {

            float x = biomeButtons[i].getHitbox().left;
            float y = biomeButtons[i].getHitbox().top;

            canvas.drawBitmap(ButtonImages.EMPTY_SUPER_SMALL.getBtnImg(biomeButtons[i].isPushed()), x, y, null);

            Bitmap icon = Items.getTileSprite(Items.BLOCK_THUMB[i]);

            float xMid = x + biomeButtons[i].getHitbox().width() / 2f - icon.getWidth() / 2f;
            float yMid = y + biomeButtons[i].getHitbox().height() / 2f - icon.getHeight() / 2f;

            canvas.drawBitmap(icon, xMid, yMid, null);
        }
    }

    public void setPage(int page) {
        biomePages[activeBiome].setPage(page);
    }

    public int getMAX_PAGES() {
        return biomePages[activeBiome].getMAX_PAGES();
    }
}