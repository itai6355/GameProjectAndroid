package com.example.gameproject.gamestates.shop;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.helpers.ItemHelper;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;

public class ItemShop extends ShopState implements GameStateInterface {
    //TODO: sort the items more clearly.
    private Category category = Category.FOOD;
    private final int MAX_CATEGORIES = Category.values().length;


    private final int ShopWidth = 10;
    private final int ShopHeight = 4;


    private ShopSloth currSS;
    private final ShopState shopState;
    private final ItemHelper itemHelper;

    private int xCurrIndex = 0;
    private int yCurrIndex = 0;


    private final BuyPage buyPage;
    private final CategoryPage[] CategoryPages = new CategoryPage[MAX_CATEGORIES];


    public ItemShop(Game game, ShopState shopState) {
        super(game);
        buyPage = new BuyPage(this);
        this.shopState = shopState;
        itemHelper = new ItemHelper();


        for (int i = 0; i < MAX_CATEGORIES; i++)
            CategoryPages[i] = new CategoryPage(category);

    }


    @Override
    public void update(double delta) {
        if (buyPage.isInPage()) {
            buyPage.update(delta);
        } else CategoryPages[category.value].update(delta);
    }

    @Override
    public void render(Canvas canvas) {
        if (buyPage.isInPage()) buyPage.render(canvas);
        else CategoryPages[category.value].render(canvas);

    }


    @Override
    public void touchEvents(MotionEvent event) {
        if (buyPage.isInPage()) {
            buyPage.touchEvents(event);
            return;
        }
        CategoryPage categoryPage = CategoryPages[category.value];

        for (int i = 0; i < ShopWidth; i++)
            for (int j = 0; j < ShopHeight; j++)
                if (categoryPage.getShopItems()[0][i][j].isIn(event)) {
                    categoryPage.setXCurr(i);
                    categoryPage.setYCurr(j);
                }


        currSS = categoryPage.getShopItems()[categoryPage.getPage()][xCurrIndex][yCurrIndex];

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, currSS) && currSS.hasItem()) {
                currSS.setPushed(true);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isIn(event, currSS) && currSS.isPushed()) {
                buyPage.setToPage(true, currSS);
                shopState.setIsBuying(true);
            }
            currSS.setPushed(false);
        }


    }


    public ShopSloth getCurrSS() {
        return currSS;
    }

    public ItemHelper getItemHelper() {
        return itemHelper;
    }

    public ShopState getShopState() {
        return shopState;
    }

    public void setPage(int page) {
        CategoryPages[category.value].setPage(page);
    }

    public int getMAX_PAGES() {
        return CategoryPages[category.value].getMAX_PAGES();
    }

    enum Category {
        BASIC(0), FRUIT(1), FOOD(2), MEAT(3), SNACKS(4), CAKE(5);

        final int value;

        Category(int value) {
            this.value = value;
        }

        public Enum[] getItems() {
            return switch (this) {
                case BASIC -> ItemHelper.basicProduct.values();
                case FRUIT -> ItemHelper.fruit.values();
                case FOOD -> ItemHelper.food.values();
                case MEAT -> ItemHelper.meat.values();
                case SNACKS -> ItemHelper.snacks.values();
                case CAKE -> ItemHelper.cakes.values();
            };
        }
    }

}
