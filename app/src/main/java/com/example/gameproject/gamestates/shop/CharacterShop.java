package com.example.gameproject.gamestates.shop;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.entities.entities.GameCharacters;
import com.example.gameproject.entities.entities.Icons;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;

public class CharacterShop extends ShopState implements GameStateInterface {

    private static final int MAX_PAGES = 8;

    private static final CharacterPage[] pages = new CharacterPage[MAX_PAGES];

    private int page = 0;

    public CharacterShop(Game game) {
        super(game);
        initPages();
    }


    @Override
    public void update(double delta) {
        pages[page].update(delta);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(ShopImages.CHARACTER_SHOP_BOOK.getImage(),
                (float) (MainActivity.GAME_WIDTH / 2 - ShopImages.CHARACTER_SHOP_BOOK.getWidth() / 2),
                (float) (MainActivity.GAME_HEIGHT / 2 - ShopImages.CHARACTER_SHOP_BOOK.getHeight() / 2), null);

        pages[page].draw(canvas);

    }

    @Override
    public void touchEvents(MotionEvent event) {
        pages[page].touchEvents(event);

    }

    public int getMAX_PAGES() {
        return MAX_PAGES;
    }

    public void setPage(int page) {
        this.page = page;
    }

    private void initPages() {
        pages[0] = new CharacterPage(GameCharacters.BOY, Icons.BOY_ICON, "Boy", 100);
        pages[1] = new CharacterPage(GameCharacters.EGG_BOY, Icons.EGG_BOY_ICON, "Egg Boy", 573);
        pages[2] = new CharacterPage(GameCharacters.EGG_GIRL, Icons.EGG_GIRL_ICON, "Egg Girl", 21);
        pages[3] = new CharacterPage(GameCharacters.ESKIMOS, Icons.ESKIMOS_ICON, "Eskimo", 567);
        pages[4] = new CharacterPage(GameCharacters.INSPECTOR, Icons.INSPECTOR_ICON, "Inspector", 1000);
        pages[5] = new CharacterPage(GameCharacters.FIGHTER, Icons.FIGHTER_ICON, "Fighter", 34);
        pages[6] = new CharacterPage(GameCharacters.HUNTER, Icons.HUNTER_ICON, "Hunter", 54);
        pages[7] = new CharacterPage(GameCharacters.RED_NINJA, Icons.RED_NINJA_ICON, "Red Ninja", 121);

        pages[2].Buy();
        pages[6].Buy();
    }

    static void setSkin(CharacterPage skin) {
        for (CharacterPage page : pages)
            if (!page.equals(skin))
                page.setSkinBtn.setPushed(false);
        //TODO: set the skin to the player and update the database

    }

    public CharacterPage getPage(int page) {
        return pages[page];
    }

    public boolean isPageBought(int page) {
        return pages[page].isBought();
    }

    public void buyPage(int page) {
        pages[page].Buy();
    }
}
