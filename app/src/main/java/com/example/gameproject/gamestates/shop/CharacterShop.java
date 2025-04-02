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

    static void setSkin(CharacterPage skin) {
        for (CharacterPage page : pages)
            if (!page.equals(skin)) page.setSkinBtn.setPushed(false);
            else if (!skin.setSkinBtn.isPushed()) pages[0].setSkinBtn.setPushed(true);
    }

    @Override
    public void update(double delta) {
        pages[page].update(delta);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(ShopImages.CHARACTER_SHOP_BOOK.getImage(), (float) (MainActivity.GAME_WIDTH / 2 - ShopImages.CHARACTER_SHOP_BOOK.getWidth() / 2), (float) (MainActivity.GAME_HEIGHT / 2 - ShopImages.CHARACTER_SHOP_BOOK.getHeight() / 2), null);


        pages[page].render(canvas);

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
        //TODO: Add more characters.
        pages[0] = new CharacterPage(game, GameCharacters.BOY, Icons.BOY_ICON, "Boy", 0);
        pages[1] = new CharacterPage(game, GameCharacters.EGG_BOY, Icons.EGG_BOY_ICON, "Egg Boy", 10);
        pages[2] = new CharacterPage(game, GameCharacters.EGG_GIRL, Icons.EGG_GIRL_ICON, "Egg Girl", 15);
        pages[3] = new CharacterPage(game, GameCharacters.ESKIMOS, Icons.ESKIMOS_ICON, "Eskimo", 25);
        pages[4] = new CharacterPage(game, GameCharacters.INSPECTOR, Icons.INSPECTOR_ICON, "Inspector", 50);
        pages[5] = new CharacterPage(game, GameCharacters.FIGHTER, Icons.FIGHTER_ICON, "Fighter", 60);
        pages[6] = new CharacterPage(game, GameCharacters.HUNTER, Icons.HUNTER_ICON, "Hunter", 100);
        pages[7] = new CharacterPage(game, GameCharacters.RED_NINJA, Icons.RED_NINJA_ICON, "Red Ninja", 250);

        pages[0].Buy();
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
