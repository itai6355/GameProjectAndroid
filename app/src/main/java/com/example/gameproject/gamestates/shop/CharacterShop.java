package com.example.gameproject.gamestates.shop;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.entities.entities.GameCharacters;
import com.example.gameproject.entities.entities.Icons;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;

public class CharacterShop extends ShopState implements GameStateInterface {

    private final int MAX_PAGES = 8;

    private final CharacterPage[] pages = new CharacterPage[MAX_PAGES];

    private int page = 0;

    public CharacterShop(Game game) {
        super(game);
        initPages();
    }



    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(ShopImages.CHARACTER_SHOP_BOOk.getImage(),
                (float) (MainActivity.GAME_WIDTH / 2 - ShopImages.CHARACTER_SHOP_BOOk.getWidth() / 2),
                (float) (MainActivity.GAME_HEIGHT / 2 - ShopImages.CHARACTER_SHOP_BOOk.getHeight() / 2), null);

        pages[page].draw(canvas);

    }

    @Override
    public void touchEvents(MotionEvent event) {

    }

    public int getMAX_PAGES() {
        return MAX_PAGES;
    }

    public void setPage(int page) {
        this.page = page;
    }

    private void initPages() {
        pages[0] = new CharacterPage(GameCharacters.BOY, Icons.BOY_ICON, "Boy");
        pages[1] = new CharacterPage(GameCharacters.EGG_BOY, Icons.EGG_BOY_ICON, "Egg Boy");
        pages[2] = new CharacterPage(GameCharacters.EGG_GIRL, Icons.EGG_GIRL_ICON, "Egg Girl");
        pages[3] = new CharacterPage(GameCharacters.ESKIMOS, Icons.ESKIMOS_ICON, "Eskimo");
        pages[4] = new CharacterPage(GameCharacters.INSPECTOR, Icons.INSPECTOR_ICON, "Inspector");
        pages[5] = new CharacterPage(GameCharacters.FIGHTER, Icons.FIGHTER_ICON, "Fighter");
        pages[6] = new CharacterPage(GameCharacters.HUNTER, Icons.HUNTER_ICON, "Hunter");
        pages[7] = new CharacterPage(GameCharacters.RED_NINJA, Icons.RED_NINJA_ICON, "Red Ninja");
    }
}
