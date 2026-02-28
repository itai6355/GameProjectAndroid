package com.example.gameproject.environments;

import android.graphics.PointF;

import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.Buildings;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.entities.objects.GameObjects;
import com.example.gameproject.entities.particals.Particle;
import com.example.gameproject.entities.particals.Particles;
import com.example.gameproject.helpers.var.GameConstants;
import com.example.gameproject.helpers.var.HelpMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapHelper {

    private static final int HOUSE_REG_WIDTH = 400;
    private static final int HOUSE_REG_HEIGHT = 400;
    private static final int HOUSE_BIG_WIDTH = 640;
    private static final int HOUSE_BIG_HEIGHT = 640;

    private static final int HOUSE_X_START = GameConstants.Sprite.SIZE;
    private static final int HOUSE_Y_START = GameConstants.Sprite.SIZE;

    private static final int HOUSE_REG_WIDTH_POS = HOUSE_REG_WIDTH + HOUSE_X_START;
    private static final int HOUSE_REG_HEIGHT_POS = HOUSE_REG_HEIGHT + HOUSE_Y_START;
    private static final int HOUSE_BIG_WIDTH_POS = HOUSE_BIG_WIDTH + HOUSE_X_START;
    private static final int HOUSE_BIG_HEIGHT_POS = HOUSE_BIG_HEIGHT + HOUSE_Y_START;


    public static ArrayList<Particle> getParticles() {
        ArrayList<Particle> particles = new ArrayList<>();
        particles.add(new Particle(Particles.POTION_EFFECT));

        return particles;
    }


    private static List<GameObject> createRoomBig2() {
        var list = new ArrayList<GameObject>();
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_Y_START - (float) GameObjects.REFRIGERATOR.getHeight() / 2 + 10), GameObjects.REFRIGERATOR));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.REFRIGERATOR.getWidth(), HOUSE_Y_START - (float) GameObjects.CABINET.getHeight() / 4), GameObjects.CABINET));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.REFRIGERATOR.getWidth() + GameObjects.CABINET.getWidth(), HOUSE_Y_START - (float) GameObjects.OVEN.getHeight() / 4), GameObjects.OVEN));

        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_BIG_HEIGHT_POS - GameObjects.SOFA_RIGHT.getHeight() - GameObjects.PLANT.getHitboxHeight()), GameObjects.PLANT));
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_BIG_HEIGHT_POS - GameObjects.SOFA_RIGHT.getHeight()), GameObjects.SOFA_RIGHT));

        list.add(new GameObject(new PointF((float) (HOUSE_BIG_WIDTH / 2 - GameObjects.CARPET.getWidth() / 2) + 50, (float) (HOUSE_BIG_HEIGHT_POS / 2 - GameObjects.CARPET.getHeight() / 2) + 50), GameObjects.CARPET));

        list.add(new GameObject(new PointF(HOUSE_BIG_WIDTH_POS - GameObjects.TABLE_BIG.getWidth(), HOUSE_BIG_HEIGHT_POS - GameObjects.TABLE_BIG.getHeight() - GameObjects.CHAIR_BIRCH_DOWN.getHeight()), GameObjects.CHAIR_BIRCH_DOWN));
        list.add(new GameObject(new PointF(HOUSE_BIG_WIDTH_POS - GameObjects.TABLE_BIG.getWidth() - 35, HOUSE_BIG_HEIGHT_POS - GameObjects.TABLE_BIG.getHeight() - 50), GameObjects.TABLE_BIG));
        return list;
    }

    private static List<GameObject> createRoomBig3() {
        var list = new ArrayList<GameObject>();
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.PLANT2.getWidth() + 10, HOUSE_BIG_HEIGHT_POS - GameObjects.SMALL_SOFA_UP.getHeight() - 10), GameObjects.SMALL_SOFA_UP));
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_BIG_HEIGHT_POS - GameObjects.PLANT2.getHeight() - GameObjects.SOFA_RIGHT.getHitboxHeight() + 20), GameObjects.SOFA_RIGHT));
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_BIG_HEIGHT_POS - GameObjects.PLANT2.getHeight()), GameObjects.PLANT2));

        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_Y_START - (float) GameObjects.CLOCK.getHeight() / 4), GameObjects.CLOCK));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.CLOCK.getWidth(), HOUSE_Y_START - (float) GameObjects.REFRIGERATOR.getHeight() / 3), GameObjects.REFRIGERATOR));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.CLOCK.getWidth() + GameObjects.REFRIGERATOR.getWidth(), HOUSE_Y_START - 20), GameObjects.CABINET_P1));
        list.add(new GameObject(new PointF(HOUSE_BIG_WIDTH_POS - GameObjects.CABINET_P2.getWidth() + 32, HOUSE_Y_START + GameObjects.CABINET_P1.getHeight() - 20), GameObjects.CABINET_P2));
        list.add(new GameObject(new PointF(HOUSE_BIG_WIDTH_POS - GameObjects.TOOLSET_HANGING.getWidth() - 15, HOUSE_Y_START - 90), GameObjects.TOOLSET_HANGING));

        list.add(new GameObject(new PointF(HOUSE_BIG_WIDTH_POS - GameObjects.DESK.getWidth(), HOUSE_BIG_HEIGHT_POS - GameObjects.DESK.getHeight()), GameObjects.DESK));
        list.add(new GameObject(new PointF(HOUSE_BIG_WIDTH_POS - (float) (GameObjects.DESK.getWidth() / 2 + GameObjects.STOOL_HIGH.getWidth() / 2), HOUSE_BIG_HEIGHT_POS - GameObjects.DESK.getHeight() - (float) GameObjects.STOOL_HIGH.getHeight() / 6 * 7), GameObjects.STOOL_HIGH));
        list.add(new GameObject(new PointF(HOUSE_BIG_WIDTH_POS - GameObjects.DESK.getWidth() - GameObjects.STOOL.getWidth() - 10, HOUSE_BIG_HEIGHT_POS - GameObjects.STOOL.getHeight()), GameObjects.STOOL));
        return list;
    }

    private static List<GameObject> createRoomMail() {
        var list = new ArrayList<GameObject>();
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_Y_START - (float) GameObjects.PIGENS.getHeight() / 2), GameObjects.PIGENS));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.PIGENS.getWidth(), HOUSE_Y_START - (float) GameObjects.PIGENS.getHeight() / 2), GameObjects.OAK_BOOKSHELF));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.OAK_BOOKSHELF.getWidth() + GameObjects.PIGENS.getWidth() + 10, HOUSE_Y_START - (float) GameObjects.PIGENS.getHeight() / 2), GameObjects.PIGENS));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.OAK_BOOKSHELF.getWidth() + 2 * GameObjects.PIGENS.getWidth() + 10, HOUSE_Y_START - (float) GameObjects.PIGENS.getHeight() / 2), GameObjects.OAK_BOOKSHELF));
        list.add(new GameObject(new PointF(HOUSE_X_START + 2 * (GameObjects.OAK_BOOKSHELF.getWidth() + GameObjects.PIGENS.getWidth() + 10), HOUSE_Y_START - (float) GameObjects.PIGENS.getHeight() / 2), GameObjects.PIGENS));

        list.add(new GameObject(new PointF(HOUSE_X_START, (float) HOUSE_BIG_HEIGHT / 3 + 50), GameObjects.OAK_BOOKSHELF));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.OAK_BOOKSHELF.getWidth(), (float) HOUSE_BIG_HEIGHT / 3 + 50), GameObjects.PLANT2));
        list.add(new GameObject(new PointF(HOUSE_X_START, (float) HOUSE_BIG_HEIGHT / 3 * 2 + 50), GameObjects.OAK_BOOKSHELF));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.OAK_BOOKSHELF.getWidth(), (float) HOUSE_BIG_HEIGHT / 3 * 2 + 50), GameObjects.PLANT2));


        int x = HOUSE_BIG_WIDTH_POS / 3 * 2;
        int y = HOUSE_BIG_HEIGHT_POS / 3 + 10;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                list.add(new GameObject(new PointF(x + i * GameObjects.CHAIR_OAK_DOWN.getWidth(), y + j * (GameObjects.CHAIR_OAK_DOWN.getHeight() + 12)), GameObjects.CHAIR_OAK_DOWN));

        return list;
    }

    private static List<GameObject> createRoomBig1() {
        var list = new ArrayList<GameObject>();
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_Y_START + (float) GameObjects.BOOK_SHELF.getHeight() / 2), GameObjects.BOOK_SHELF));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.BOOK_SHELF.getWidth(), HOUSE_Y_START), GameObjects.PLANT));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.BOOK_SHELF.getWidth() + GameObjects.PLANT.getWidth(), HOUSE_Y_START + (float) GameObjects.BOOK_SHELF.getHeight() / 2), GameObjects.BOOK_SHELF));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.BOOK_SHELF.getWidth() * 2 + GameObjects.PLANT.getWidth(), HOUSE_Y_START), GameObjects.PLANT));
        list.add(new GameObject(new PointF(HOUSE_X_START + (GameObjects.BOOK_SHELF.getWidth() + GameObjects.PLANT.getWidth()) * 2, HOUSE_Y_START + (float) GameObjects.BOOK_SHELF.getHeight() / 2), GameObjects.BOOK_SHELF));

        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_BIG_HEIGHT_POS - GameObjects.TABLE.getHeight()), GameObjects.TABLE));
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_BIG_HEIGHT_POS - GameObjects.TABLE.getHeight() - 200), GameObjects.TABLE2));
        list.add(new GameObject(new PointF(HOUSE_BIG_WIDTH_POS - GameObjects.TABLE.getWidth(), HOUSE_BIG_HEIGHT_POS - GameObjects.TABLE.getHeight()), GameObjects.TABLE2));
        list.add(new GameObject(new PointF(HOUSE_BIG_WIDTH_POS - GameObjects.TABLE.getWidth(), HOUSE_BIG_HEIGHT_POS - GameObjects.TABLE.getHeight() - 200), GameObjects.TABLE));
        return list;
    }

    private static List<GameObject> createRoom3() {
        var list = new ArrayList<GameObject>();
        list.add(new GameObject(new PointF(HOUSE_REG_WIDTH_POS - GameObjects.BOOK_SHELF_EMPTY.getWidth() - GameObjects.PLANT.getWidth(), HOUSE_Y_START), GameObjects.PLANT));
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_REG_HEIGHT_POS), GameObjects.TABLE));
        list.add(new GameObject(new PointF(HOUSE_REG_WIDTH_POS - GameObjects.BOOK_SHELF_EMPTY.getWidth(), HOUSE_Y_START + (float) GameObjects.BOOK_SHELF_EMPTY.getHeight() / 2), GameObjects.BOOK_SHELF_EMPTY));
        return list;
    }

    private static List<GameObject> createRoom2() {
        var list = new ArrayList<GameObject>();
        list.add(new GameObject(new PointF(HOUSE_REG_WIDTH_POS - GameObjects.BLUE_POT.getWidth(), HOUSE_REG_HEIGHT_POS - GameObjects.BLUE_POT.getHeight()), GameObjects.BLUE_POT));
        list.add(new GameObject(new PointF(HOUSE_X_START + GameObjects.DRAWERS_BIG.getWidth(), (float) GameObjects.PLANT.getHeight() / 4 * 3), GameObjects.PLANT));
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_Y_START + (float) GameObjects.DRAWERS_BIG.getHeight() / 2), GameObjects.DRAWERS_BIG));
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_REG_HEIGHT_POS - GameObjects.TABLE3.getHeight()), GameObjects.TABLE3));

        return list;
    }

    private static List<GameObject> createRoom1() {
        var list = new ArrayList<GameObject>();
        list.add(new GameObject(new PointF(HOUSE_REG_WIDTH_POS - GameObjects.PLANT.getWidth(), HOUSE_Y_START), GameObjects.PLANT));
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_REG_HEIGHT_POS - GameObjects.TABLE2.getHeight()), GameObjects.TABLE2));
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_REG_HEIGHT_POS - GameObjects.CHAIR.getHeight() - GameObjects.TABLE2.getHeight()), GameObjects.CHAIR));
        list.add(new GameObject(new PointF(HOUSE_X_START, HOUSE_Y_START + (float) GameObjects.BOOK_SHELF.getHeight() / 2), GameObjects.BOOK_SHELF));
        return list;
    }

    //reg:
    //HOUSE WIDTH: 400, HEIGHT: 400
    //big:
    //HOUSE WIDTH: 640, HEIGHT: 640

    //WITH PUDDING OF 80 FOR EVERY SIDE!! (walls)

    public static ArrayList<GameObject> getObjectsReg1() {
        return new ArrayList<>(createRoom2());
    }

    public static ArrayList<GameObject> getObjectsReg2() {
        return new ArrayList<>(createRoom1());
    }

    public static ArrayList<GameObject> getObjectsMail() {
        return new ArrayList<>(createRoomMail());
    }

    public static ArrayList<GameObject> getObjectsFlat1() {
        return new ArrayList<>(createRoom1());
    }

    public static ArrayList<GameObject> getObjectsFlat2() {
        return new ArrayList<>(createRoom2());
    }

    public static ArrayList<GameObject> getObjectsFlat3() {
        return new ArrayList<>(createRoom3());
    }

    public static ArrayList<GameObject> getObjectsGreen1() {
        return new ArrayList<>(createRoomBig1());
    }

    public static ArrayList<GameObject> getObjectsGreen2() {
        return new ArrayList<>(createRoomBig2());
    }

    public static ArrayList<GameObject> getObjectsGreen3() {
        return new ArrayList<>(createRoomBig3());
    }


    public static int[][] getInsideFlatHouseArray() {
        return new int[][]{{374, 377, 377, 377, 377, 377, 378},
                {396, 0, 1, 1, 1, 2, 400},
                {396, 22, 23, 23, 23, 24, 400},
                {396, 22, 23, 23, 23, 24, 400},
                {396, 22, 23, 23, 23, 24, 400},
                {396, 44, 45, 45, 45, 46, 400},
                {462, 465, 463, 394, 464, 465, 466}};
    }

    public static int[][] getInsideBlacksmithHouseArray() {
        return new int[][]{{389, 392, 392, 392, 392, 392, 392, 392, 392, 393},
                {411, 143, 144, 144, 144, 144, 144, 144, 145, 415},
                {411, 165, 166, 166, 166, 166, 166, 166, 167, 415},
                {411, 165, 166, 166, 166, 166, 166, 166, 167, 415},
                {411, 165, 166, 166, 166, 166, 166, 166, 167, 415},
                {411, 165, 166, 166, 166, 166, 166, 166, 167, 415},
                {411, 165, 166, 166, 166, 166, 166, 166, 167, 415},
                {411, 165, 166, 166, 166, 166, 166, 166, 167, 415},
                {411, 187, 188, 188, 188, 188, 188, 188, 189, 415},
                {477, 480, 480, 478, 394, 479, 480, 480, 480, 481}};
    }

    public static int[][] getInsideMailHouseArray() {
        return new int[][]{{379, 382, 382, 382, 382, 382, 382, 382, 382, 383},
                {401, 155, 155, 155, 155, 155, 155, 155, 155, 405},
                {401, 155, 155, 155, 155, 155, 155, 155, 155, 405},
                {401, 155, 155, 155, 155, 155, 155, 155, 155, 405},
                {401, 155, 155, 155, 155, 155, 155, 155, 155, 405},
                {401, 155, 155, 155, 155, 155, 155, 155, 155, 405},
                {401, 155, 155, 155, 155, 155, 155, 155, 155, 405},
                {401, 155, 155, 155, 155, 155, 155, 155, 155, 405},
                {401, 155, 155, 155, 155, 155, 155, 155, 155, 405},
                {467, 470, 470, 468, 394, 469, 470, 470, 470, 471}};

    }


    public static int[][] getInsideRegHouseArr() {
        return new int[][]{{384, 387, 387, 387, 387, 387, 388},
                {406, 298, 298, 298, 298, 298, 410},
                {406, 298, 298, 298, 298, 298, 410},
                {406, 298, 298, 298, 298, 298, 410},
                {406, 298, 298, 298, 298, 298, 410},
                {406, 298, 298, 298, 298, 298, 410},
                {472, 475, 473, 394, 474, 475, 476}};
    }

}
