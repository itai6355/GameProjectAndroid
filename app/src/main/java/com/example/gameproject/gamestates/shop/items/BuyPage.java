package com.example.gameproject.gamestates.shop.items;

import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.gamestates.shop.ShopImages;
import com.example.gameproject.gamestates.shop.ShopSloth;
import com.example.gameproject.gamestates.shop.ShopState;
import com.example.gameproject.helpers.var.GameConstants;
import com.example.gameproject.helpers.var.ItemHelper;
import com.example.gameproject.helpers.var.Paints;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;

public class BuyPage implements GameStateInterface {

    private final int MAX_AMOUNT = 99;
    private final CustomButton btnBack = new CustomButton(20 + ButtonImages.SETTINGS_BACK.getWidth() + GameConstants.Sprite.X_DRAW_OFFSET, 20, ButtonImages.SETTINGS_BACK.getWidth(), ButtonImages.SETTINGS_BACK.getHeight());
    private Paint BlackPaint;
    int space = 10 * GameConstants.Sprite.SCALE_MULTIPLIER;
    int xMiddle = GAME_WIDTH / 2;
    int yMiddle = GAME_HEIGHT / 2;
    float xDrawBackground = xMiddle - (float) ShopImages.SHOP_BUY_BACKGRAWND.getWidth() / 2;
    float xDrawApprove = xDrawBackground + ShopImages.SHOP_BUY_BACKGRAWND.getWidth() - 2 * ButtonImages.SHOP_APPROVE.getWidth() - (float) space / 2;
    float yDrawBackground = yMiddle - (float) ShopImages.SHOP_BUY_BACKGRAWND.getHeight() / 2;
    float yDrawApprove = yDrawBackground + (float) ShopImages.SHOP_BUY_BACKGRAWND.getHeight() - 2 * ButtonImages.SHOP_APPROVE.getHeight() + GameConstants.Sprite.Y_DRAW_OFFSET;
    private final CustomButton btnApprove = new CustomButton(xDrawApprove, yDrawApprove, ButtonImages.SHOP_APPROVE.getWidth(), ButtonImages.SHOP_APPROVE.getHeight());
    float xDrawBlock = xMiddle - (float) ShopImages.SHOP_BUY_BLOCK.getWidth() / 2;
    float xDrawBar = xDrawBlock - (float) space / 3;
    float xDrawPrice = xDrawBlock + 4 * GameConstants.Sprite.X_DRAW_OFFSET;
    float xDrawADD = xDrawBlock + ShopImages.SHOP_BUY_BLOCK.getWidth() + space;
    float xDrawReduce = xDrawBlock - ButtonImages.SHOP_REDUCE.getWidth() - space;
    float yDrawBlock = yMiddle - (float) ShopImages.SHOP_BUY_BLOCK.getHeight() / 2;
    float yDrawBar = yDrawBlock + ShopImages.SHOP_BUY_BLOCK.getHeight() + (float) space / 3;
    float yDrawPrice = yDrawBar + 3 * GameConstants.Sprite.Y_DRAW_OFFSET;
    float yDrawAmount = yDrawBlock + ShopImages.SHOP_BUY_BLOCK.getHeight() - 2.5f * GameConstants.Sprite.Y_DRAW_OFFSET;
    float yDrawADD = yMiddle - (float) ShopImages.SHOP_ARROW_LEFT.getHeight() / 2;
    private final CustomButton btnAdd = new CustomButton(xDrawADD, yDrawADD, ButtonImages.SHOP_ADD.getWidth(), ButtonImages.SHOP_ADD.getHeight());
    float yDrawReduce = yMiddle - (float) ShopImages.SHOP_ARROW_LEFT.getHeight() / 2;
    private final CustomButton btnReduce = new CustomButton(xDrawReduce, yDrawReduce, ButtonImages.SHOP_REDUCE.getWidth(), ButtonImages.SHOP_REDUCE.getHeight());
    private Items item;
    private int tileSpriteId = -1;
    private int amount;
    float xDrawAmount = xMiddle - (float) (String.valueOf(amount).length() * GameConstants.Sprite.SCALE_MULTIPLIER) / 2;
    private int price;
    private boolean isInPage = false;


    private final Game game;
    private final ShopState shopState;
    private final ItemHelper itemHelper;

    public BuyPage(ItemShop itemShop) {
        this.game       = itemShop != null ? itemShop.getGame()       : null;
        this.shopState  = itemShop != null ? itemShop.getShopState()  : null;
        this.itemHelper = itemShop != null ? itemShop.getItemHelper() : null;
        BlackPaint = Paints.MEDIOM_BLACK_PAINT;
    }

    public BuyPage(Game game, ShopState shopState) {
        this.game       = game;
        this.shopState  = shopState;
        this.itemHelper = new ItemHelper();
        BlackPaint = Paints.MEDIOM_BLACK_PAINT;
    }

    @Override
    public void update(double delta) {
        updatePrice();
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(ShopImages.SHOP_BUY_BACKGRAWND.getImage(), xDrawBackground, yDrawBackground, null);
        canvas.drawBitmap(ShopImages.SHOP_BUY_BLOCK.getImage(), xDrawBlock, yDrawBlock, null);
        canvas.drawText(String.valueOf(amount), xDrawAmount, yDrawAmount, BlackPaint);

        canvas.drawBitmap(ShopImages.SHOP_BAR_2_SCALED.getImage(), xDrawBar, yDrawBar, null);

        Bitmap preview = (item != null && item.isTile())
                ? Items.getTileSprite(tileSpriteId)
                : (item != null ? item.getBiggerImage() : null);
        if (preview != null)
            canvas.drawBitmap(preview,
                    xMiddle - preview.getWidth() / 2f,
                    yMiddle - preview.getHeight() / 2f, null);

        canvas.drawText(String.valueOf(price), xDrawPrice, yDrawPrice, BlackPaint);
        canvas.drawBitmap(GameImages.COIN_SMALL.getImage(),
                xDrawPrice + (float) ShopImages.SHOP_BAR_2_SCALED.getWidth() / 2
                        + String.valueOf(price).length() * GameConstants.Sprite.SCALE_MULTIPLIER,
                yDrawPrice - 2 * GameConstants.Sprite.Y_DRAW_OFFSET - 2 * GameConstants.Sprite.SCALE_MULTIPLIER,
                null);

        canvas.drawBitmap(ButtonImages.EMPTY_SMALL.getBtnImg(btnBack.isPushed()), btnBack.getHitbox().left, btnBack.getHitbox().top, null);
        canvas.drawBitmap(ShopImages.SHOP_ARROW_LEFT.getImage(),
                btnBack.getHitbox().left + GameConstants.Sprite.X_DRAW_OFFSET + 2 * GameConstants.Sprite.SCALE_MULTIPLIER,
                btnBack.getHitbox().top + GameConstants.Sprite.Y_DRAW_OFFSET, null);

        canvas.drawBitmap(ButtonImages.SHOP_ADD.getBtnImg(btnAdd.isPushed()), btnAdd.getHitbox().left, btnAdd.getHitbox().top, null);
        canvas.drawBitmap(ButtonImages.SHOP_REDUCE.getBtnImg(btnReduce.isPushed()), btnReduce.getHitbox().left, btnReduce.getHitbox().top, null);
        canvas.drawBitmap(ButtonImages.SHOP_APPROVE.getBtnImg(btnApprove.isPushed()), btnApprove.getHitbox().left, btnApprove.getHitbox().top, null);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, btnBack))         btnBack.setPushed(true);
            else if (isIn(event, btnAdd))     btnAdd.setPushed(true);
            else if (isIn(event, btnReduce))  btnReduce.setPushed(true);
            else if (isIn(event, btnApprove)) btnApprove.setPushed(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if      (isIn(event, btnBack)    && btnBack.isPushed())                          setNotBuying();
            else if (isIn(event, btnAdd)     && btnAdd.isPushed()    && amount < MAX_AMOUNT) amount++;
            else if (isIn(event, btnReduce)  && btnReduce.isPushed() && amount > 0)          amount--;
            else if (isIn(event, btnApprove) && btnApprove.isPushed()) buyItems();

            btnApprove.setPushed(false);
            btnAdd.setPushed(false);
            btnReduce.setPushed(false);
            btnBack.setPushed(false);
        }
    }

    private void buyItems() {
        if (game == null) return;
        Player player = game.getPlayer();
        if (player.getCoins() >= price) {
            player.setCoins(player.getCoins() - price);
            for (int i = 0; i < amount; i++)
                player.addToInventory(Items.TILE, tileSpriteId);
            setNotBuying();
        } else {
            Toast.makeText(MainActivity.getGameContext(), "Not enough coins", Toast.LENGTH_SHORT).show();
        }
    }

    private void setNotBuying() {
        this.isInPage = false;
        if (shopState != null) shopState.setIsBuying(false);
        item = Items.COIN;
        tileSpriteId = -1;
        amount = 0;
    }

    public boolean isInPage() {
        return isInPage;
    }

    public void setToPage(boolean isInPage, ShopSloth currSS) {
        this.isInPage    = isInPage;
        this.item        = currSS.getItem();
        this.tileSpriteId = currSS.getCustomSpriteId();
        this.amount      = currSS.getAmount();
        updatePrice();
    }

    private void updatePrice() {
        if (item == null || itemHelper == null) { price = 0; return; }
        price = item.isTile() ? 10 * amount : itemHelper.getPrice(item) * amount;
    }
}