package com.example.gameproject.gamestates.shop;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.entities.items.Items;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;

public class BuyPage implements GameStateInterface {

    private Items item;
    private int amount;
    private final int MAX_AMOUNT = 99;

    private boolean isInPage = false;

    private ItemShop itemShop;
    private final CustomButton btnBack = new CustomButton(20 + ButtonImages.SETTINGS_BACK.getWidth() + GameConstants.Sprite.X_DRAW_OFFSET, 20, ButtonImages.SETTINGS_BACK.getWidth(), ButtonImages.SETTINGS_BACK.getHeight());

    //TODO: add values
    private final CustomButton btnAdd = new CustomButton(0, 0, 0, 0);
    private final CustomButton btnReduce = new CustomButton(0, 0, 0, 0);
    private final CustomButton btnApprove = new CustomButton(0, 0, 0, 0);

    public BuyPage(ItemShop itemShop) {
        this.itemShop = itemShop;
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(ButtonImages.EMPTY_SMALL.getBtnImg(btnBack.isPushed()), btnBack.getHitbox().left, btnBack.getHitbox().top, null);
        canvas.drawBitmap(ShopImages.SHOP_ARROW_LEFT.getImage(), btnBack.getHitbox().left + GameConstants.Sprite.SCALE_MULTIPLIER, btnBack.getHitbox().top + GameConstants.Sprite.SCALE_MULTIPLIER, null);

        canvas.drawBitmap(ButtonImages.SHOP_ADD.getBtnImg(btnAdd.isPushed()), btnAdd.getHitbox().left, btnAdd.getHitbox().top, null);
        canvas.drawBitmap(ButtonImages.SHOP_REDUCE.getBtnImg(btnReduce.isPushed()), btnReduce.getHitbox().left, btnReduce.getHitbox().top, null);
        canvas.drawBitmap(ButtonImages.SHOP_APPROVE.getBtnImg(btnApprove.isPushed()), btnApprove.getHitbox().left, btnApprove.getHitbox().top, null);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, btnBack)) btnBack.setPushed(true);
            else if (isIn(event, btnAdd)) btnAdd.setPushed(true);
            else if (isIn(event, btnReduce)) btnReduce.setPushed(true);
            else if (isIn(event, btnApprove)) btnApprove.setPushed(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isIn(event, btnBack) && btnBack.isPushed()) setNotBuying();
            else if (isIn(event, btnAdd) && btnAdd.isPushed() && amount < MAX_AMOUNT) amount++;
            else if (isIn(event, btnReduce) && btnReduce.isPushed() && amount > 0) amount--;
            else if (isIn(event, btnApprove) && btnApprove.isPushed()) buyItem();


            btnAdd.setPushed(false);
            btnReduce.setPushed(false);
            btnBack.setPushed(false);
        }

    }

    private void buyItem() {

    }

    private void setNotBuying() {
        this.isInPage = false;
        item = null;
        amount = 0;
        itemShop.setIsBuying(false);
    }


    public boolean isInPage() {
        return isInPage;
    }

    public void setToPage(boolean isInPage, ShopSloth currSS) {
        this.isInPage = isInPage;
        item = currSS.getItem();
        amount = currSS.getAmount();
    }

}
