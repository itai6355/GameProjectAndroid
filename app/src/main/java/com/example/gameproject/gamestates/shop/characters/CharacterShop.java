package com.example.gameproject.gamestates.shop.characters;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.entities.entities.GameCharacters;
import com.example.gameproject.entities.entities.Icons;
import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.gamestates.shop.ShopImages;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;

public class CharacterShop extends BaseState implements GameStateInterface {

    private static final int MAX_PAGES = GameCharacters.getValCharacter() - 1;

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
        pages[0] = new CharacterPage(game, GameCharacters.BOY, Icons.BOY_ICON, "Boy", 0);
        pages[1] = new CharacterPage(game, GameCharacters.EGG_BOY, Icons.EGG_BOY_ICON, "Egg Boy", 10);
        pages[2] = new CharacterPage(game, GameCharacters.EGG_GIRL, Icons.EGG_GIRL_ICON, "Egg Girl", 15);
        pages[3] = new CharacterPage(game, GameCharacters.ESKIMOS, Icons.ESKIMOS_ICON, "Eskimo", 25);
        pages[4] = new CharacterPage(game, GameCharacters.INSPECTOR, Icons.INSPECTOR_ICON, "Inspector", 50);
        pages[5] = new CharacterPage(game, GameCharacters.FIGHTER, Icons.FIGHTER_ICON, "Fighter", 60);
        pages[6] = new CharacterPage(game, GameCharacters.HUNTER, Icons.HUNTER_ICON, "Hunter", 100);
        pages[7] = new CharacterPage(game, GameCharacters.RED_NINJA, Icons.RED_NINJA_ICON, "Red Ninja", 250);
        pages[8] = new CharacterPage(game, GameCharacters.KNIGHT, Icons.KNIGHT_ICON, "Knight", 500);
        pages[9] = new CharacterPage(game, GameCharacters.SAMURAI, Icons.SAMURAI_ICON, "Samurai", 1000);
        pages[10] = new CharacterPage(game, GameCharacters.MASTER, Icons.MASTER_ICON, "Master", 2000);
        pages[11] = new CharacterPage(game, GameCharacters.MONK, Icons.MONK_ICON, "Monk", 5000);
        pages[12] = new CharacterPage(game, GameCharacters.NINJABLUE2, Icons.NINJABLUE2_ICON, "Ninja Blue 2", 10000);
        pages[13] = new CharacterPage(game, GameCharacters.NINJABLUE, Icons.NINJABLUE_ICON, "Ninja Blue", 15000);
        pages[14] = new CharacterPage(game, GameCharacters.NINJABOMB, Icons.NINJABOMB_ICON, "Ninja Bomb", 20000);
        pages[15] = new CharacterPage(game, GameCharacters.NINJADARK, Icons.NINJADARK_ICON, "Ninja Dark", 25000);
        pages[16] = new CharacterPage(game, GameCharacters.NINJAESKIMO, Icons.NINJAESKIMO_ICON, "Ninja Eskimo", 30000);
        pages[17] = new CharacterPage(game, GameCharacters.NINJAGRAY, Icons.NINJAGRAY_ICON, "Ninja Gray", 35000);
        pages[18] = new CharacterPage(game, GameCharacters.NINJAGREEN, Icons.NINJAGREEN_ICON, "Ninja Green", 40000);
        pages[19] = new CharacterPage(game, GameCharacters.NINJAMASKED, Icons.NINJAMASKED_ICON, "Ninja Masked", 45000);
        pages[20] = new CharacterPage(game, GameCharacters.NINJARED, Icons.NINJARED_ICON, "Ninja Red", 50000);
        pages[21] = new CharacterPage(game, GameCharacters.NINJAYELLOW, Icons.NINJAYELLOW_ICON, "Ninja Yellow", 55000);
        pages[22] = new CharacterPage(game, GameCharacters.OLDMAN2, Icons.OLDMAN2_ICON, "Old Man 2", 65000);
        pages[23] = new CharacterPage(game, GameCharacters.OLDMAN3, Icons.OLDMAN3_ICON, "Old Man 3", 70000);
        pages[24] = new CharacterPage(game, GameCharacters.OLDMAN, Icons.OLDMAN_ICON, "Old Man", 75000);
        pages[25] = new CharacterPage(game, GameCharacters.PRINCESS, Icons.PRINCESS_ICON, "Princess", 85000);
        pages[26] = new CharacterPage(game, GameCharacters.REDNINJA3, Icons.REDNINJA3_ICON, "Red Ninja 3", 90000);
        pages[27] = new CharacterPage(game, GameCharacters.ROBOTGREEN, Icons.ROBOTGREEN_ICON, "Robot Green", 95000);
        pages[28] = new CharacterPage(game, GameCharacters.ROBOTGREY, Icons.ROBOTGREY_ICON, "Robot Grey", 100000);
        pages[29] = new CharacterPage(game, GameCharacters.SAMURAIBLUE, Icons.SAMURAIBLUE_ICON, "Samurai Blue", 105000);
        pages[30] = new CharacterPage(game, GameCharacters.SORCERERBLACK, Icons.SORCERERBLACK_ICON, "Sorcerer Black", 110000);
        pages[31] = new CharacterPage(game, GameCharacters.SORCERERORANGE, Icons.SORCERERORANGE_ICON, "Sorcerer Orange", 115000);
        pages[32] = new CharacterPage(game, GameCharacters.STATUE, Icons.STATUE_ICON, "Statue", 120000);
        pages[33] = new CharacterPage(game, GameCharacters.SULTAN2, Icons.SULTAN2_ICON, "Sultan 2", 125000);
        pages[34] = new CharacterPage(game, GameCharacters.SULTAN, Icons.SULTAN_ICON, "Sultan", 130000);
        pages[35] = new CharacterPage(game, GameCharacters.VAMPIRE, Icons.VAMPIRE_ICON, "Vampire", 135000);


        if (game.getPlayer().getSkin() == GameCharacters.BOY)
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

    public static CharacterPage[] getPages() {
        return pages;
    }
}
