package com.example.gameproject.gamestates.shop;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.ItemHelper;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;

public class ItemShop extends BaseState implements GameStateInterface {

    private static final Category[] Categories = new Category[7];
    private final int MAX_CATEGORIES = Category.values().length;

    private final int ShopWidth = 10;
    private final int ShopHeight = 4;
    private final ShopState shopState;
    private final ItemHelper itemHelper;
    private final BuyPage buyPage;
    private final CategoryPage[] CategoryPages = new CategoryPage[MAX_CATEGORIES];
    private final CustomButton[] CategoriesButtons = new CustomButton[MAX_CATEGORIES];
    float xBtnStart, yBtnStart;
    float xBtnMiddle, yBtnMiddle;
    int xSpace = 10 * GameConstants.Sprite.SCALE_MULTIPLIER;
    private final int xBtn = MainActivity.GAME_WIDTH / 2 - (ButtonImages.EMPTY_SUPER_SMALL.getWidth() * MAX_CATEGORIES - xSpace) / 2;
    private Category category = Category.FOOD;


//    private final int xBtn = xStart;
//    private final int yBtn = yStart + (ShopSloth.SLOT_SIZE + 100) * (ShopHeight);
    private ShopSloth currSS;
    private int xCurrIndex = 0;
    private int yCurrIndex = 0;
    private final int xStart = 375;
    private final int yStart = 200;
    private final int yBtn = yStart - ButtonImages.EMPTY_SUPER_SMALL.getHeight() - 50;


    public ItemShop(Game game, ShopState shopState) {
        super(game);
        buyPage = new BuyPage(this);
        this.shopState = shopState;
        itemHelper = new ItemHelper();

        InitCategorys();
    }

    public static void addCategory(Category category) {
        Categories[category.value] = category;
    }

    private void InitCategorys() {
        for (int i = 0; i < MAX_CATEGORIES; i++) {
            CategoryPages[i] = new CategoryPage(Categories[i], xStart, yStart);
            CategoryPages[i].setIcon();
            CategoriesButtons[i] = new CustomButton(xBtn + (i * ButtonImages.EMPTY_SUPER_SMALL.getWidth() + xSpace), yBtn, ButtonImages.EMPTY_SUPER_SMALL.getWidth(), ButtonImages.EMPTY_SUPER_SMALL.getHeight());
        }
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
        else {
            CategoryPages[category.value].render(canvas);
            drawButtons(canvas);
        }

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
                    xCurrIndex = i;
                    yCurrIndex = j;
                    break;
                }


        currSS = categoryPage.getShopItems()[0][xCurrIndex][yCurrIndex];

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, currSS) && currSS.hasItem()) currSS.setPushed(true);
            else for (CustomButton buttonsPage : CategoriesButtons)
                if (isIn(event, buttonsPage)) buttonsPage.setPushed(true);

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isIn(event, currSS) && currSS.isPushed()) {
                buyPage.setToPage(true, currSS);
                shopState.setIsBuying(true);
            } else for (int i = 0; i < CategoriesButtons.length; i++)
                if (isIn(event, CategoriesButtons[i])) setCategory(Category.values()[i]);

            for (CustomButton buttonsPage : CategoriesButtons) buttonsPage.setPushed(false);
            currSS.setPushed(false);
        }


    }

    private void drawButtons(Canvas canvas) {

        for (int i = 0; i < MAX_CATEGORIES; i++) {
            xBtnStart = CategoriesButtons[i].getHitbox().left;
            yBtnStart = CategoriesButtons[i].getHitbox().top;
            xBtnMiddle = xBtnStart + CategoriesButtons[i].getHitbox().width() / 2 - CategoryPages[i].getIcon().getWidth() / 2f;
            yBtnMiddle = yBtnStart + CategoriesButtons[i].getHitbox().height() / 2 - CategoryPages[i].getIcon().getHeight() / 2f;

            canvas.drawBitmap(ButtonImages.EMPTY_SUPER_SMALL.getBtnImg(CategoriesButtons[i].isPushed()), xBtnStart, yBtnStart, null);
            canvas.drawBitmap(CategoryPages[i].getIcon(), xBtnMiddle, yBtnMiddle, null);
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

    public void setCategory(Category category) {
        shopState.setPage(0);
        this.category = category;
        CategoryPages[category.value].setPage(0);
    }

    enum Category {
        BASIC(0), FRUIT(1), FOOD(2), MEAT(3), SNACKS(4), CAKE(5), BAKERY_TOOLS(6);

        final int value;

        Category(int value) {
            this.value = value;
            addCategory(this);
        }

        public Enum[] getItems() {
            return switch (this) {
                case BASIC -> ItemHelper.basicProduct.values();
                case FRUIT -> ItemHelper.fruit.values();
                case FOOD -> ItemHelper.food.values();
                case MEAT -> ItemHelper.meat.values();
                case SNACKS -> ItemHelper.snacks.values();
                case CAKE -> ItemHelper.cakes.values();
                case BAKERY_TOOLS -> ItemHelper.bakeryTools.values();
            };
        }
    }

}
