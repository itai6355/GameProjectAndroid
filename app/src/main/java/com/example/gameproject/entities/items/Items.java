package com.example.gameproject.entities.items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.entities.objects.Buildings;
import com.example.gameproject.entities.objects.GameObjects;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.helpers.var.ItemHelper;
import com.example.gameproject.main.MainActivity;

public enum Items implements BitmapMethods {

    APPLE(R.drawable.apple, true),
    APPLE_PIE_DISH(R.drawable.apple_pie_dish, true),
    APRICOT(R.drawable.apricot, true),
    BACON(R.drawable.bacon, true),
    BACON_DISH(R.drawable.bacon_dish, true),
    BAGEL(R.drawable.bagel, true),
    BAGEL_DISH(R.drawable.bagel_dish, true),
    BAGUETTE(R.drawable.baguette, true),
    BAGUETTE_DISH(R.drawable.baguette_dish, true),
    TART(R.drawable.bakewelltart, true),
    BANANA(R.drawable.banana, true),
    BANOFFEEPOT(R.drawable.banoffeepot, true),
    BARBEQUE_SAUCE(R.drawable.barbeque_sauce, true),
    BEER(R.drawable.beer, true),
    BELL_PEPPER(R.drawable.bell_pepper, true),
    BELL_PEPPER2(R.drawable.bell_pepper2, true),
    BLUEJELLY(R.drawable.bluejelly, true),
    BOWL_OF_RICE(R.drawable.bowl_of_rice, true),
    BREAD(R.drawable.bread, true),
    BREAD2(R.drawable.bread2, true),
    BREAD_DISH(R.drawable.bread_dish, true),
    BREAD_SLICE(R.drawable.bread_slice, true),
    BUBBLE_GUM(R.drawable.bubble_gum, true),
    BUN(R.drawable.bun, true),
    BURGER(R.drawable.burger, true),
    BURRITO(R.drawable.burrito, true),
    BURRITO_DISH(R.drawable.burrito_dish, true),
    BUTTER(R.drawable.butter, true),
    BUTTER2(R.drawable.butter2, true),
    CABBAGE(R.drawable.cabbage, true),
    CANDY_BAR(R.drawable.candy_bar, true),
    CANDY_CANES(R.drawable.candy_canes, true),
    CARROTCAKE(R.drawable.carrotcake, true),
    CEREAL1(R.drawable.cereal1, true),
    CEREAL2(R.drawable.cereal2, true),
    CHEESE(R.drawable.cheese, true),
    CHEESECAKE(R.drawable.cheesecake, true),
    CHEESECAKE2(R.drawable.cheesecake2, true),
    CHEESECAKEPOT(R.drawable.cheesecakepot, true),
    CHEESECAKE_DISH(R.drawable.cheesecake_dish, true),
    CHEESEPUFF_BOWL(R.drawable.cheesepuff_bowl, true),
    CHERRYCHOCOLATEPOT(R.drawable.cherrychocolatepot, true),
    CHERRYSHORTCAKE(R.drawable.cherryshortcake, true),
    CHERRY_BLACK(R.drawable.cherry_black, true),
    CHERRY_PIE(R.drawable.cherry_pie, true),
    CHERRY_RED(R.drawable.cherry_red, true),
    CHICKEN(R.drawable.chicken, true),
    CHILI_PEPPER(R.drawable.chili_pepper, true),
    CHOCOLATE(R.drawable.chocolate, true),
    CHOCOLATEBERRYSHORTCAKE(R.drawable.chocolateberryshortcake, true),
    CHOCOLATECAKE(R.drawable.chocolatecake, true),
    CHOCOLATECAKE2(R.drawable.chocolatecake2, true),
    CHOCOLATEDONUT(R.drawable.chocolatedonut, true),
    CHOCOLATEPOT(R.drawable.chocolatepot, true),
    CHOCOLATESWISSROLL(R.drawable.chocolateswissroll, true),
    CHOCOLATETWIST(R.drawable.chocolatetwist, true),
    CINNAMONROLL(R.drawable.cinnamonroll, true),
    COFFEE_BAG(R.drawable.coffee_bag, true),
    COOKIECHEESECAKE(R.drawable.cookiecheesecake, true),
    COOKIENCREAMPOT(R.drawable.cookiencreampot, true),
    COOKIES(R.drawable.cookies, true),
    COOKIES2(R.drawable.cookies2, true),
    COOKING_OIL(R.drawable.cooking_oil, true),
    CRANBERRY(R.drawable.cranberry, true),
    CREMECARAMEL(R.drawable.cremecaramel, true),
    CROISSANT(R.drawable.croissant, true),
    CROISSANTAVOCADOSANDWICH(R.drawable.croissantavocadosandwich, true),
    CROISSANTSANDWICH(R.drawable.croissantsandwich, true),
    CUCUMBER(R.drawable.cucumber, true),
    CUPCAKE(R.drawable.cupcake, true),
    CURRY(R.drawable.curry, true),
    CURRY_DISH(R.drawable.curry_dish, true),
    CUSTARDAPPLE(R.drawable.custardapple, true),
    DANISHGLAZED(R.drawable.danishglazed, true),
    DANISHGLAZED2(R.drawable.danishglazed2, true),
    DONU2(R.drawable.donu2, true),
    DONUT(R.drawable.donut, true),
    DRAGONFRUIT(R.drawable.dragonfruit, true),
    DRY_DOG_FOOD(R.drawable.dry_dog_food, true),
    DUMPLINGS(R.drawable.dumplings, true),
    DUMPLINGS_DISH(R.drawable.dumplings_dish, true),
    DURIAN(R.drawable.durian, true),
    EGGSALAD_BOWL(R.drawable.eggsalad_bowl, true),
    EGGTART(R.drawable.eggtart, true),
    EGG_BOX(R.drawable.egg_box, true),
    EGG_BROWN(R.drawable.egg_brown, true),
    EGG_BROWN_P(R.drawable.egg_brown_p, true),
    EGG_WHITE(R.drawable.egg_white, true),
    EGG_WHITE_P(R.drawable.egg_white_p, true),
    ENERGY_BAR(R.drawable.energy_bar, true),
    FIG(R.drawable.fig, true),
    FISH_SALOMON(R.drawable.fish_salomon, true),
    FLOUR(R.drawable.flour, true),
    FRENCHFRIES_DISH(R.drawable.frenchfries_dish, true),
    FRIEDEGG(R.drawable.friedegg, true),
    FRIEDEGG_DISH(R.drawable.friedegg_dish, true),
    FRIED_EGG(R.drawable.fried_egg, true),
    FRUITCAKE(R.drawable.fruitcake, true),
    FRUIT_COCKTAIL_CAN(R.drawable.fruit_cocktail_can, true),
    FUNFETTIDONUT(R.drawable.funfettidonut, true),
    GARLICBREAD(R.drawable.garlicbread, true),
    GUMMYBEAR(R.drawable.giantgummybear, true),
    GINGERBREAD_MAN_DISH(R.drawable.gingerbreadman_dish, true),
    GLAZED_CINNAMONROLL(R.drawable.glazedcinnamonroll, true),
    GRAPEFRUIT(R.drawable.grapefruit, true),
    GRAPES_BLACK(R.drawable.grapes_black, true),
    GRAPES_GREEN(R.drawable.grapes_green, true),
    GRAPE_SODA(R.drawable.grape_soda, true),
    GREEN_JELLY(R.drawable.greenjelly, true),
    GUAVA(R.drawable.guava, true),
    HAMBURGER(R.drawable.hamburger, true),
    HONEY(R.drawable.honey, true),
    HOTDOG(R.drawable.hotdog, true),
    HOTDOG_SAUCE(R.drawable.hotdog_sauce, true),
    HOT_COCOA_MIX(R.drawable.hot_cocoa_mix, true),
    HOT_DOG(R.drawable.hot_dog, true),
    ICE_CREAM(R.drawable.icecream, true),
    I_MUSHROOM(R.drawable.i_mushroom, true),
    I_ROOT(R.drawable.i_root, true),
    I_ROOT_BEER(R.drawable.i_root_beer, true),
    I_STRAWBERRY(R.drawable.i_strawberry, true),
    I_TOMATO(R.drawable.i_tomato, true),
    JAM(R.drawable.jam, true),
    JELLY(R.drawable.jelly, true),
    KETCHUP(R.drawable.ketchup, true),
    KIWI(R.drawable.kiwi, true),
    LEMON(R.drawable.lemon, true),
    LEMON_BLUEBERRY_POT(R.drawable.lemonblueberrypot, true),
    LEMON_CAKE(R.drawable.lemoncake, true),
    LEMON_DONUT(R.drawable.lemondonut, true),
    LEMON_PIE(R.drawable.lemonpie, true),
    LOAF_BREAD(R.drawable.loafbread, true),
    LOLLIPOP(R.drawable.lollipop, true),
    MACRONS(R.drawable.macrons, true),
    MARSHMALLOWS(R.drawable.marshmallows, true),
    MEAT1(R.drawable.meat1, true),
    MEAT1_P(R.drawable.meat1_p, true),
    MEAT2(R.drawable.meat2, true),
    MEATBALL(R.drawable.meatball, true),
    MELON(R.drawable.melon, true),
    MILK_BOTTLE(R.drawable.milk_bottle, true),
    MILK_CHOCOLATE(R.drawable.milk_chocolate, true),
    MILK_GALLON(R.drawable.milk_gallon, true),
    MILK_PACK(R.drawable.milk_pack, true),
    MILK_PLASTIC(R.drawable.milk_plastic, true),
    MINT_CHOCOLATE_POT(R.drawable.mintchocolatepot, true),
    MUSTARD(R.drawable.mustard, true),
    OLIVE_OIL(R.drawable.olive_oil, true),
    OMELET(R.drawable.omlet, true),
    OMELET_DISH(R.drawable.omlet_dish, true),
    ORANGE(R.drawable.orange, true),
    ORANGE_JUICE(R.drawable.orange_juice, true),
    PANCAKES(R.drawable.pancakes, true),
    PANCAKES_BANOFFEE(R.drawable.pancakes_banoffee, true),
    PANCAKES_BERRY(R.drawable.pancakes_berry, true),
    PANCAKES_CHOCOLATE(R.drawable.pancakes_chocolate, true),
    PANCAKES_COOKIES_N_CREAM(R.drawable.pancakes_cookiesn_cream, true),
    PANCAKES_CREAM(R.drawable.pancakes_cream, true),
    PANCAKES_DISH(R.drawable.pancakes_dish, true),
    PANCAKES_MINT_CHOCOLATE(R.drawable.pancakes_mint_chocolate, true),
    PANCAKES_RAINBOW(R.drawable.pancakes_rainbow, true),
    PANNACOTTA(R.drawable.pannacotta, true),
    PASSION_FRUIT(R.drawable.passionfruit, true),
    PEACH(R.drawable.peach, true),
    PEANUT_BUTTER(R.drawable.peanut_butter, true),
    PEAR(R.drawable.pear, true),
    PINK_JELLY(R.drawable.pinkjelly, true),
    PIZZA(R.drawable.pizza, true),
    PIZZA_DISH(R.drawable.pizza_dish, true),
    PLAIN_YOGURT(R.drawable.plain_yogurt, true),
    PLUM(R.drawable.plum, true),
    POPCORN_BOWL(R.drawable.popcorn_bowl, true),
    POTATOCHIP_BLUE(R.drawable.potatochip_blue, true),
    POTATOCHIP_GREEN(R.drawable.potatochip_green, true),
    POTATOCHIP_YELLOW(R.drawable.potatochip_yellow, true),
    POTATO_P(R.drawable.potato_p, true),
    PROFITEROLES(R.drawable.profiteroles, true),
    PUDDING(R.drawable.pudding, true),
    PURPLE_JELLY(R.drawable.purplejelly, true),
    RAINBOW_CAKE(R.drawable.rainbowcake, true),
    RAMEN(R.drawable.ramen, true),
    RASPBERRY_CHEESECAKE_POT(R.drawable.raspberrycheesecakepot, true),
    RED_JELLY(R.drawable.redjelly, true),
    RED_VELVET_CAKE(R.drawable.redvelvetcake, true),
    RED_APPLE(R.drawable.red_apple, true),
    RED_APPLE_P(R.drawable.red_apple_p, true),
    ROASTED_CHICKEN_DISH(R.drawable.roastedchicken_dish, true),
    SALMON(R.drawable.salmon, true),
    SALMON2(R.drawable.salmon2, true),
    SALMON_DISH(R.drawable.salmon_dish, true),
    SALMON_P(R.drawable.salmon_p, true),
    SALT(R.drawable.salt, true),
    SANDWICH(R.drawable.sandwich, true),
    SANDWICH_DISH(R.drawable.sandwich_dish, true),
    SAUSAGE_P(R.drawable.sausage_p, true),
    SLICED_BREAD_P(R.drawable.sliced_bread_p, true),
    SNACK1(R.drawable.snack1, true),
    SODA_CAN(R.drawable.soda_can, true),
    SOFT_DRINK_BLUE(R.drawable.soft_drink_blue, true),
    SOFT_DRINK_GREEN(R.drawable.soft_drink_green, true),
    SOFT_DRINK_RED(R.drawable.soft_drink_red, true),
    SOFT_DRINK_YELLOW(R.drawable.soft_drink_yellow, true),
    SPAGHETTI(R.drawable.spaghetti, true),
    STEAK_DISH(R.drawable.steak_dish, true),
    STRAWBERRY(R.drawable.strawberry, true),
    STRAWBERRY_CAKE(R.drawable.strawberrycake, true),
    STRAWBERRY_CAKE_DISH(R.drawable.strawberrycake_dish, true),
    STRAWBERRY_DONUT(R.drawable.strawberrydonut, true),
    STRAWBERRY_SHORTCAKE(R.drawable.strawberryshortcake, true),
    STRAWBERRY_WAFFLES(R.drawable.strawberrywaffles, true),
    STRAWBERRY_ICE_CREAM(R.drawable.strawberry_ice_cream, true),
    STRAWBERRY_JAM(R.drawable.strawberry_jam, true),
    STRAWBERRY_P(R.drawable.strawberry_p, true),
    SUGAR(R.drawable.sugar, true),
    SUSHI_DISH(R.drawable.sushi_dish, true),
    SWISSROLL(R.drawable.swissroll, true),
    TACO(R.drawable.taco, true),
    TACO_DISH(R.drawable.taco_dish, true),
    TIRAMISU(R.drawable.tirimasu, true),
    TUNA_CAN(R.drawable.tuna_can, true),
    VANILLA_CAKE(R.drawable.vanillacake, true),
    VANILLA_OR_LEMON_ICE_CREAM(R.drawable.vanilla_or_lemon_ice_cream, true),
    WAFFLE(R.drawable.waffle, true),
    WAFFLES(R.drawable.waffles, true),
    WAFFLE_DISH(R.drawable.waffle_dish, true),
    WATER(R.drawable.water, true),
    WATERMELON(R.drawable.watermelon, true),
    WATERMELON2(R.drawable.watermelon2, true),
    WHITE_CHEESE(R.drawable.white_cheese, true),
    WHITE_CHEESE_PIECE_P(R.drawable.white_cheese_piece_p, true),
    WINE_RED(R.drawable.wine_red, true),
    WINE_WHITE(R.drawable.wine_white, true),
    WINE_WHITE2(R.drawable.wine_white2, true),
    WINE_WHITE3(R.drawable.wine_white3, true),
    YELLOW_JELLY(R.drawable.yellowjelly, true),

    ALUMINUM_FOIL(R.drawable.aluminum_foil, false),
    BAKING_POWDER(R.drawable.baking_powder, false),
    BALL_PEN(R.drawable.ball_pen, false),
    BANDAGE_BOX(R.drawable.bandage_box, false),
    BASKET_METAL(R.drawable.basket_metal, false),
    BASKET_YELLOW(R.drawable.basket_yellow, false),
    BATHROOM_CLEANER(R.drawable.bathroom_cleaner, false),
    BATTERIES(R.drawable.batteries, false),
    BODY_LOTION(R.drawable.body_lotion, false),
    BOWL(R.drawable.bowl, false),
    CHOPPING_BOARD(R.drawable.chopping_board, false),
    CLEANING_BRUSH(R.drawable.cleaning_brush, false),
    CLEANING_GLOVES(R.drawable.cleaning_gloves, false),
    COFFEE_MUG(R.drawable.coffee_mug, false),
    CREDIT_CARD_1(R.drawable.credit_card_1, false),
    CREDIT_CARD_2(R.drawable.credit_card_2, false),
    CREDIT_CARD_3(R.drawable.credit_card_3, false),
    DETERGENT(R.drawable.detergent, false),
    ERASER(R.drawable.eraser, false),
    FRYING_PAN(R.drawable.frying_pan, false),
    GLUE(R.drawable.glue, false),
    GLUE_STICK(R.drawable.glue_stick, false),
    HAND_SANITISER(R.drawable.hand_sanitiser, false),
    I_WOOL_CLOTH(R.drawable.i_wool_cloth, false),
    KITCHEN_KNIFE_SET(R.drawable.kitchen_knife_set, false),
    KITCHEN_KNIFE_SET_P(R.drawable.kitchen_knife_set_p, false),
    KITCHEN_SOAP(R.drawable.kitchen_soap, false),
    LIGHT_BULB(R.drawable.light_bulb, false),
    LIGHT_BULB_BOX(R.drawable.light_bulb_box, false),
    PAPER_BAG(R.drawable.paper_bag, false),
    POWER_STRIP_TYPEA(R.drawable.power_strip_type_a, false),
    POWER_STRIP_TYPEF(R.drawable.power_strip_typef, false),
    RECEIPT(R.drawable.receipt, false),
    ROLLING_PIN(R.drawable.rolling_pin, false),
    SCISSORS(R.drawable.scissors, false),
    SCISSORS_P(R.drawable.scissors_p, false),
    SCRUB_BRUSH(R.drawable.scrub_brush, false),
    SCRUB_SPONGE(R.drawable.scrub_sponge, false),
    SHAMPOO(R.drawable.shampoo, false),
    SOAP(R.drawable.soap, false),
    SOAP_BOX(R.drawable.soap_box, false),
    SPATULA(R.drawable.spatula, false),
    SUN_CREAM_TUBE(R.drawable.sun_cream_tube, false),
    TEAKETTLE(R.drawable.teakettle, false),
    TOILET_PAPER(R.drawable.toilet_paper, false),
    TOOTHBRUSH(R.drawable.toothbrush, false),
    TOOTHBRUSH_SET(R.drawable.toothbrush_set, false),
    TOOTHPASTE(R.drawable.toothpaste, false),
    TOOTHPASTE_BOX(R.drawable.toothpaste_box, false),
    WAX(R.drawable.wax, false),
    WET_WIPE(R.drawable.wet_wipe, false),
    WHISK(R.drawable.whisk, false),
    CHOCOLATE_DISH(R.drawable.chocolate_dish, false),
    EGGTART_DISH(R.drawable.eggtart_dish, false),
    GARLICBREAD_DISH(R.drawable.garlicbread_dish, false),
    GIANTGUMMYBEAR_DISH(R.drawable.giantgummybear_dish, false),
    HOTDOG_DISH(R.drawable.hotdog_dish, false),
    ICECREAM_BOWL(R.drawable.icecream_bowl, false),
    LOAFBREAD_DISH(R.drawable.loafbread_dish, false),
    MEATBALL_DISH(R.drawable.meatball_dish, false),
    NACHO_DISH(R.drawable.nacho_dish, false),
    POTATOCHIPS_BOWL(R.drawable.potatochips_bowl, false),

    MEDIPCK(R.drawable.medipack),

    HOUSE_ONE(Buildings.HOUSE_ONE.getHouseImg()),
    HOUSE_TWO(Buildings.HOUSE_TWO.getHouseImg()),
    HOUSE_SIX(Buildings.HOUSE_SIX.getHouseImg()),
    HOUSE_NINE(Buildings.HOUSE_NINE.getHouseImg()),
    PILLAR_YELLOW(GameObjects.PILLAR_YELLOW.getObjectImg()),
    STATUE_ANGRY_YELLOW(GameObjects.STATUE_ANGRY_YELLOW.getObjectImg()),
    MONK_STATUE_BALL_YELLOW(GameObjects.MONK_STATUE_BALL_YELLOW.getObjectImg()),
    MONK_STATUE_YELLOW(GameObjects.MONK_STATUE_YELLOW.getObjectImg()),
    SOLDIER_SPEAR_YELLOW(GameObjects.SOLDIER_SPEAR_YELLOW.getObjectImg()),
    PLANTER_STICKS_YELLOW(GameObjects.PLANTER_STICKS_YELLOW.getObjectImg()),
    CUBE_YELLOW(GameObjects.CUBE_YELLOW.getObjectImg()),
    FROG_YELLOW(GameObjects.FROG_YELLOW.getObjectImg()),
    SOLDIER_SWORD_YELLOW(GameObjects.SOLDIER_SWORD_YELLOW.getObjectImg()),
    PILLAR_SHORT_YELLOW(GameObjects.PILLAR_SHORT_YELLOW.getObjectImg()),
    PILLAR_SNOW_YELLOW(GameObjects.PILLAR_SNOW_YELLOW.getObjectImg()),
    PILLAR_GREEN(GameObjects.PILLAR_GREEN.getObjectImg()),
    STATUE_ANGRY_GREEN(GameObjects.STATUE_ANGRY_GREEN.getObjectImg()),
    MONK_STATUE_BALL_GREEN(GameObjects.MONK_STATUE_BALL_GREEN.getObjectImg()),
    MONK_STATUE_GREEN(GameObjects.MONK_STATUE_GREEN.getObjectImg()),
    SOLDIER_SPEAR_GREEN(GameObjects.SOLDIER_SPEAR_GREEN.getObjectImg()),
    PLANTER_STICKS_GREEN(GameObjects.PLANTER_STICKS_GREEN.getObjectImg()),
    CUBE_GREEN(GameObjects.CUBE_GREEN.getObjectImg()),
    FROG_GREEN(GameObjects.FROG_GREEN.getObjectImg()),
    SOLDIER_SWORD_GREEN(GameObjects.SOLDIER_SWORD_GREEN.getObjectImg()),
    PILLAR_SHORT_GREEN(GameObjects.PILLAR_SHORT_GREEN.getObjectImg()),
    PILLAR_SNOW_GREEN(GameObjects.PILLAR_SNOW_GREEN.getObjectImg()),
    POT_ONE_FULL(GameObjects.POT_ONE_FULL.getObjectImg()),
    POT_ONE_EMPTY(GameObjects.POT_ONE_EMPTY.getObjectImg()),
    POT_TWO_FULL(GameObjects.POT_TWO_FULL.getObjectImg()),
    POT_TWO_EMPTY(GameObjects.POT_TWO_EMPTY.getObjectImg()),
    BASKET_FULL_RED_FRUIT(GameObjects.BASKET_FULL_RED_FRUIT.getObjectImg()),
    BASKET_FULL_CHICKEN(GameObjects.BASKET_FULL_CHICKEN.getObjectImg()),
    BASKET_EMPTY(GameObjects.BASKET_EMPTY.getObjectImg()),
    BASKET_FULL_BREAD(GameObjects.BASKET_FULL_BREAD.getObjectImg()),
    OVEN_SNOW_YELLOW(GameObjects.OVEN_SNOW_YELLOW.getObjectImg()),
    OVEN_YELLOW(GameObjects.OVEN_YELLOW.getObjectImg()),
    OVEN_GREEN(GameObjects.OVEN_GREEN.getObjectImg()),
    STOMP(GameObjects.STOMP.getObjectImg()),
    SMALL_POT_FULL(GameObjects.SMALL_POT_FULL.getObjectImg()),
    SMALL_POT_EMPTY(GameObjects.SMALL_POT_EMPTY.getObjectImg()),
    PLANT(GameObjects.PLANT.getObjectImg()),
    DRAWERS(GameObjects.DRAWERS.getObjectImg()),
    BOOK_SHELF_SMALL(GameObjects.BOOK_SHELF_SMALL.getObjectImg()),
    BOOK_SHELF_SMALL_EMPTY(GameObjects.BOOK_SHELF_SMALL_EMPTY.getObjectImg()),
    DRAWERS_BIG(GameObjects.DRAWERS_BIG.getObjectImg()),
    BOOK_SHELF(GameObjects.BOOK_SHELF.getObjectImg()),
    BOOK_SHELF_EMPTY(GameObjects.BOOK_SHELF_EMPTY.getObjectImg()),
    CHAIR(GameObjects.CHAIR.getObjectImg()),
    BLUE_POT(GameObjects.BLUE_POT.getObjectImg()),
    PAINTING(GameObjects.PAINTING.getObjectImg()),
    BASEMENT_OAK(GameObjects.BASEMENT_OAK.getObjectImg()),
    BASEMENT_BIRCH(GameObjects.BASEMENT_BIRCH.getObjectImg()),
    BASEMENT_ACACIA(GameObjects.BASEMENT_ACACIA.getObjectImg()),
    BASEMENT_PRISMARIN(GameObjects.BASEMENT_PRISMARIN.getObjectImg()),
    POT_EMPTY(GameObjects.POT_EMPTY.getObjectImg()),
    TABLE(GameObjects.TABLE.getObjectImg()),
    TABLE2(GameObjects.TABLE2.getObjectImg()),
    TABLE3(GameObjects.TABLE3.getObjectImg()),
    WELL(GameObjects.WELL.getObjectImg()),
    PIGENS(GameObjects.PIGENS.getObjectImg()),
    CABINET(GameObjects.CABINET.getObjectImg()),
    OVEN(GameObjects.OVEN.getObjectImg()),
    REFRIGERATOR(GameObjects.REFRIGERATOR.getObjectImg()),
    SOFA_RIGHT(GameObjects.SOFA_RIGHT.getObjectImg()),
    SOFA_DOWN(GameObjects.SOFA_DOWN.getObjectImg()),
    SMALL_SOFA_DOWN(GameObjects.SMALL_SOFA_DOWN.getObjectImg()),
    SMALL_SOFA_UP(GameObjects.SMALL_SOFA_UP.getObjectImg()),
    OAK_BOOKSHELF(GameObjects.OAK_BOOKSHELF.getObjectImg()),
    BIRCH_BOOKSHELF(GameObjects.BIRCH_BOOKSHELF.getObjectImg()),
    CHAIR_BIRCH_DOWN(GameObjects.CHAIR_BIRCH_DOWN.getObjectImg()),
    CHAIR_BIRCH_RIGHT(GameObjects.CHAIR_BIRCH_RIGHT.getObjectImg()),
    CHAIR_OAK_DOWN(GameObjects.CHAIR_OAK_DOWN.getObjectImg()),
    TABLE_BIG(GameObjects.TABLE_BIG.getObjectImg()),
    CARPET(GameObjects.CARPET.getObjectImg()),
    DRAWERS_OAK(GameObjects.DRAWERS_OAK.getObjectImg()),
    TOOLSET_HANGING(GameObjects.TOOLSET_HANGING.getObjectImg()),
    CLOCK(GameObjects.CLOCK.getObjectImg()),
    PLANT2(GameObjects.PLANT2.getObjectImg()),
    STOOL(GameObjects.STOOL.getObjectImg()),
    STOOL_HIGH(GameObjects.STOOL_HIGH.getObjectImg()),
    DESK(GameObjects.DESK.getObjectImg()),
    CABINET_P1(GameObjects.CABINET_P1.getObjectImg()),
    CABINET_P2(GameObjects.CABINET_P2.getObjectImg()),


    POTION_PURPLE(R.drawable.potion_purple, false),
    POTION_RED(R.drawable.potion_red, false),
    POTION_BLUE(R.drawable.potion_blue, false),
    POTION_WHITE(R.drawable.potion_white, false),


    COIN(R.drawable.coins, 16, 16, 14);

    final boolean isAdible;
    final boolean isBuildable;
    final Bitmap atlas;
    final Bitmap[] images;
    final boolean isAni;
    final int amount;

    Items(int resID, int width, int height, int amount) {
        options.inScaled = false;
        isAni = true;
        isBuildable = false;
        this.amount = amount;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        images = new Bitmap[amount];
        for (int i = 0; i < amount; i++)
            images[i] = getScaledBitmap(Bitmap.createBitmap(atlas, i * width, 0, width, height));
        isAdible = false;
    }

    Items(int resID, boolean isAdible) {
        options.inScaled = false;
        isAni = false;
        isBuildable = false;
        this.isAdible = isAdible;
        this.amount = 1;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        images = new Bitmap[4];
        images[0] = getItemBiggerSize(atlas);
        images[1] = getItemSize(atlas);
        images[2] = getSmallItemSize(atlas);
        images[3] = getSmallestItemSize(atlas);
        ItemHelper.getItems().add(this);
    }

    Items(int resID) {
        options.inScaled = false;
        isAni = false;
        isBuildable = false;
        this.isAdible = false;
        this.amount = 1;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        images = new Bitmap[4];
        images[0] = getItemSize(atlas);
        images[1] = regSize(atlas);
        images[2] = deSize(atlas);
        images[3] = SmalldeSize(atlas);
        ItemHelper.getItems().add(this);
    }


    Items(Bitmap res) {
        options.inScaled = false;
        isAni = false;
        isBuildable = true;
        this.isAdible = false;
        this.amount = 1;
        atlas = res;
        images = new Bitmap[4];
        images[0] = getItemSize(atlas);
        images[1] = regSize(atlas);
        images[2] = deSize(atlas);
        images[3] = SmalldeSize(atlas);
        ItemHelper.getItems().add(this);
    }

    public boolean isPotion() {
        return this == POTION_PURPLE || this == POTION_RED || this == POTION_BLUE || this == POTION_WHITE;
    }


    public Bitmap getImage(int index) {
        return images[index];
    }

    public Bitmap getImage() {
        if (isPotion())
            return images[2];
        return images[1];
    }

    public Bitmap getSmallImage() {
        return images[2];
    }

    public Bitmap getSmallestImage() {
        return images[3];
    }

    public Bitmap getBiggerImage() {
        return images[0];
    }

    public boolean isAdible() {
        return isAdible;
    }

    public boolean isBuildable() {
        return isBuildable;
    }

    public boolean isAni() {
        return isAni;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name();
    }

    public boolean isBuilding() {
        for (Buildings building : Buildings.values())
            if (building.name().equals(name()))
                return true;

        return false;
    }

    public boolean isObject() {
        for (GameObjects object : GameObjects.values())
            if (object.name().equals(name()))
                return true;

        return false;
    }

    public Buildings getBuildingType() {
        for (Buildings building : Buildings.values()) {
            if (building.name().equals(name())) {
                return building;
            }
        }
        return null;
    }

    public GameObjects getObjectType() {
        for (GameObjects object : GameObjects.values()) {
            if (object.name().equals(name())) {
                return object;
            }
        }
        return null;
    }
}


