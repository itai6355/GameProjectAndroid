package com.example.gameproject.entities.items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.ItemHelper;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

public enum Items implements BitmapMethods {

    APPLE(R.drawable.apple),
    APPLE_PIE_DISH(R.drawable.apple_pie_dish),
    APRICOT(R.drawable.apricot),
    BACON(R.drawable.bacon),
    BACON_DISH(R.drawable.bacon_dish),
    BAGEL(R.drawable.bagel),
    BAGEL_DISH(R.drawable.bagel_dish),
    BAGUETTE(R.drawable.baguette),
    BAGUETTE_DISH(R.drawable.baguette_dish),
    TART(R.drawable.bakewelltart),
    BANANA(R.drawable.banana),
    BANOFFEEPOT(R.drawable.banoffeepot),
    BARBEQUE_SAUCE(R.drawable.barbeque_sauce),
    BEER(R.drawable.beer),
    BELL_PEPPER(R.drawable.bell_pepper),
    BELL_PEPPER2(R.drawable.bell_pepper2),
    BLUEJELLY(R.drawable.bluejelly),
    BOWL_OF_RICE(R.drawable.bowl_of_rice),
    BREAD(R.drawable.bread),
    BREAD2(R.drawable.bread2),
    BREAD_DISH(R.drawable.bread_dish),
    BREAD_SLICE(R.drawable.bread_slice),
    BUBBLE_GUM(R.drawable.bubble_gum),
    BUN(R.drawable.bun),
    BURGER(R.drawable.burger),
    BURRITO(R.drawable.burrito),
    BURRITO_DISH(R.drawable.burrito_dish),
    BUTTER(R.drawable.butter),
    BUTTER2(R.drawable.butter2),
    CABBAGE(R.drawable.cabbage),
    CANDY_BAR(R.drawable.candy_bar),
    CANDY_CANES(R.drawable.candy_canes),
    CARROTCAKE(R.drawable.carrotcake),
    CEREAL1(R.drawable.cereal1),
    CEREAL2(R.drawable.cereal2),
    CHEESE(R.drawable.cheese),
    CHEESECAKE(R.drawable.cheesecake),
    CHEESECAKE2(R.drawable.cheesecake2),
    CHEESECAKEPOT(R.drawable.cheesecakepot),
    CHEESECAKE_DISH(R.drawable.cheesecake_dish),
    CHEESEPUFF_BOWL(R.drawable.cheesepuff_bowl),
    CHERRYCHOCOLATEPOT(R.drawable.cherrychocolatepot),
    CHERRYSHORTCAKE(R.drawable.cherryshortcake),
    CHERRY_BLACK(R.drawable.cherry_black),
    CHERRY_PIE(R.drawable.cherry_pie),
    CHERRY_RED(R.drawable.cherry_red),
    CHICKEN(R.drawable.chicken),
    CHILI_PEPPER(R.drawable.chili_pepper),
    CHOCOLATE(R.drawable.chocolate),
    CHOCOLATEBERRYSHORTCAKE(R.drawable.chocolateberryshortcake),
    CHOCOLATECAKE(R.drawable.chocolatecake),
    CHOCOLATECAKE2(R.drawable.chocolatecake2),
    CHOCOLATEDONUT(R.drawable.chocolatedonut),
    CHOCOLATEPOT(R.drawable.chocolatepot),
    CHOCOLATESWISSROLL(R.drawable.chocolateswissroll),
    CHOCOLATETWIST(R.drawable.chocolatetwist),
    CINNAMONROLL(R.drawable.cinnamonroll),
    COFFEE_BAG(R.drawable.coffee_bag),
    COOKIECHEESECAKE(R.drawable.cookiecheesecake),
    COOKIENCREAMPOT(R.drawable.cookiencreampot),
    COOKIES(R.drawable.cookies),
    COOKIES2(R.drawable.cookies2),
    COOKING_OIL(R.drawable.cooking_oil),
    CRANBERRY(R.drawable.cranberry),
    CREMECARAMEL(R.drawable.cremecaramel),
    CROISSANT(R.drawable.croissant),
    CROISSANTAVOCADOSANDWICH(R.drawable.croissantavocadosandwich),
    CROISSANTSANDWICH(R.drawable.croissantsandwich),
    CUCUMBER(R.drawable.cucumber),
    CUPCAKE(R.drawable.cupcake),
    CURRY(R.drawable.curry),
    CURRY_DISH(R.drawable.curry_dish),
    CUSTARDAPPLE(R.drawable.custardapple),
    DANISHGLAZED(R.drawable.danishglazed),
    DANISHGLAZED2(R.drawable.danishglazed2),
    DONU2(R.drawable.donu2),
    DONUT(R.drawable.donut),
    DRAGONFRUIT(R.drawable.dragonfruit),
    DRY_DOG_FOOD(R.drawable.dry_dog_food),
    DUMPLINGS(R.drawable.dumplings),
    DUMPLINGS_DISH(R.drawable.dumplings_dish),
    DURIAN(R.drawable.durian),
    EGGSALAD_BOWL(R.drawable.eggsalad_bowl),
    EGGTART(R.drawable.eggtart),
    EGG_BOX(R.drawable.egg_box),
    EGG_BROWN(R.drawable.egg_brown),
    EGG_BROWN_P(R.drawable.egg_brown_p),
    EGG_WHITE(R.drawable.egg_white),
    EGG_WHITE_P(R.drawable.egg_white_p),
    ENERGY_BAR(R.drawable.energy_bar),
    FIG(R.drawable.fig),
    FISH_SALOMON(R.drawable.fish_salomon),
    FLOUR(R.drawable.flour),
    FRENCHFRIES_DISH(R.drawable.frenchfries_dish),
    FRIEDEGG(R.drawable.friedegg),
    FRIEDEGG_DISH(R.drawable.friedegg_dish),
    FRIED_EGG(R.drawable.fried_egg),
    FRUITCAKE(R.drawable.fruitcake),
    FRUIT_COCKTAIL_CAN(R.drawable.fruit_cocktail_can),
    FUNFETTIDONUT(R.drawable.funfettidonut),
    GARLICBREAD(R.drawable.garlicbread),
    GUMMYBEAR(R.drawable.giantgummybear),
    GINGERBREAD_MAN_DISH(R.drawable.gingerbreadman_dish),
    GLAZED_CINNAMONROLL(R.drawable.glazedcinnamonroll),
    GRAPEFRUIT(R.drawable.grapefruit),
    GRAPES_BLACK(R.drawable.grapes_black),
    GRAPES_GREEN(R.drawable.grapes_green),
    GRAPE_SODA(R.drawable.grape_soda),
    GREEN_JELLY(R.drawable.greenjelly),
    GUAVA(R.drawable.guava),
    HAMBURGER(R.drawable.hamburger),
    HONEY(R.drawable.honey),
    HOTDOG(R.drawable.hotdog),
    HOTDOG_SAUCE(R.drawable.hotdog_sauce),
    HOT_COCOA_MIX(R.drawable.hot_cocoa_mix),
    HOT_DOG(R.drawable.hot_dog),
    ICE_CREAM(R.drawable.icecream),
    I_MUSHROOM(R.drawable.i_mushroom),
    I_ROOT(R.drawable.i_root),
    I_ROOT_BEER(R.drawable.i_root_beer),
    I_STRAWBERRY(R.drawable.i_strawberry),
    I_TOMATO(R.drawable.i_tomato),
    JAM(R.drawable.jam),
    JELLY(R.drawable.jelly),
    KETCHUP(R.drawable.ketchup),
    KIWI(R.drawable.kiwi),
    LEMON(R.drawable.lemon),
    LEMON_BLUEBERRY_POT(R.drawable.lemonblueberrypot),
    LEMON_CAKE(R.drawable.lemoncake),
    LEMON_DONUT(R.drawable.lemondonut),
    LEMON_PIE(R.drawable.lemonpie),
    LOAF_BREAD(R.drawable.loafbread),
    LOLLIPOP(R.drawable.lollipop),
    MACRONS(R.drawable.macrons),
    MARSHMALLOWS(R.drawable.marshmallows),
    MEAT(R.drawable.meat),
    MEAT1(R.drawable.meat1),
    MEAT1_P(R.drawable.meat1_p),
    MEAT2(R.drawable.meat2),
    MEATBALL(R.drawable.meatball),
    MELON(R.drawable.melon),
    MILK_BOTTLE(R.drawable.milk_bottle),
    MILK_CHOCOLATE(R.drawable.milk_chocolate),
    MILK_GALLON(R.drawable.milk_gallon),
    MILK_PACK(R.drawable.milk_pack),
    MILK_PLASTIC(R.drawable.milk_plastic),
    MINT_CHOCOLATE_POT(R.drawable.mintchocolatepot),
    MUSTARD(R.drawable.mustard),
    OLIVE_OIL(R.drawable.olive_oil),
    OMELET(R.drawable.omlet),
    OMELET_DISH(R.drawable.omlet_dish),
    ORANGE(R.drawable.orange),
    ORANGE_JUICE(R.drawable.orange_juice),
    PANCAKES(R.drawable.pancakes),
    PANCAKES_BANOFFEE(R.drawable.pancakes_banoffee),
    PANCAKES_BERRY(R.drawable.pancakes_berry),
    PANCAKES_CHOCOLATE(R.drawable.pancakes_chocolate),
    PANCAKES_COOKIES_N_CREAM(R.drawable.pancakes_cookiesn_cream),
    PANCAKES_CREAM(R.drawable.pancakes_cream),
    PANCAKES_DISH(R.drawable.pancakes_dish),
    PANCAKES_MINT_CHOCOLATE(R.drawable.pancakes_mint_chocolate),
    PANCAKES_RAINBOW(R.drawable.pancakes_rainbow),
    PANNACOTTA(R.drawable.pannacotta),
    PASSION_FRUIT(R.drawable.passionfruit),
    PEACH(R.drawable.peach),
    PEANUT_BUTTER(R.drawable.peanut_butter),
    PEAR(R.drawable.pear),
    PINK_JELLY(R.drawable.pinkjelly),
    PIZZA(R.drawable.pizza),
    PIZZA_DISH(R.drawable.pizza_dish),
    PLAIN_YOGURT(R.drawable.plain_yogurt),
    PLUM(R.drawable.plum),
    POPCORN_BOWL(R.drawable.popcorn_bowl),
    POTATOCHIP_BLUE(R.drawable.potatochip_blue),
    POTATOCHIP_GREEN(R.drawable.potatochip_green),
    POTATOCHIP_YELLOW(R.drawable.potatochip_yellow),
    POTATO_P(R.drawable.potato_p),
    PROFITEROLES(R.drawable.profiteroles),
    PUDDING(R.drawable.pudding),
    PURPLE_JELLY(R.drawable.purplejelly),
    RAINBOW_CAKE(R.drawable.rainbowcake),
    RAMEN(R.drawable.ramen),
    RASPBERRY_CHEESECAKE_POT(R.drawable.raspberrycheesecakepot),
    RED_JELLY(R.drawable.redjelly),
    RED_VELVET_CAKE(R.drawable.redvelvetcake),
    RED_APPLE(R.drawable.red_apple),
    ROASTED_CHICKEN_DISH(R.drawable.roastedchicken_dish),
    SALMON(R.drawable.salmon),
    SALMON2(R.drawable.salmon2),
    SALMON_DISH(R.drawable.salmon_dish),
    SALMON_P(R.drawable.salmon_p),
    SALT(R.drawable.salt),
    SANDWICH(R.drawable.sandwich),
    SANDWICH_DISH(R.drawable.sandwich_dish),
    SAUSAGE_P(R.drawable.sausage_p),
    SLICED_BREAD_P(R.drawable.sliced_bread_p),
    SNACK1(R.drawable.snack1),
    SODA_CAN(R.drawable.soda_can),
    SOFT_DRINK_BLUE(R.drawable.soft_drink_blue),
    SOFT_DRINK_GREEN(R.drawable.soft_drink_green),
    SOFT_DRINK_RED(R.drawable.soft_drink_red),
    SOFT_DRINK_YELLOW(R.drawable.soft_drink_yellow),
    SPAGHETTI(R.drawable.spaghetti),
    STEAK_DISH(R.drawable.steak_dish),
    STRAWBERRY(R.drawable.strawberry),
    STRAWBERRY_CAKE(R.drawable.strawberrycake),
    STRAWBERRY_CAKE_DISH(R.drawable.strawberrycake_dish),
    STRAWBERRY_DONUT(R.drawable.strawberrydonut),
    STRAWBERRY_SHORTCAKE(R.drawable.strawberryshortcake),
    STRAWBERRY_WAFFLES(R.drawable.strawberrywaffles),
    STRAWBERRY_ICE_CREAM(R.drawable.strawberry_ice_cream),
    STRAWBERRY_JAM(R.drawable.strawberry_jam),
    STRAWBERRY_P(R.drawable.strawberry_p),
    SUGAR(R.drawable.sugar),
    SUSHI_DISH(R.drawable.sushi_dish),
    SWISSROLL(R.drawable.swissroll),
    TACO(R.drawable.taco),
    TACO_DISH(R.drawable.taco_dish),
    TIRAMISU(R.drawable.tirimasu),
    TUNA_CAN(R.drawable.tuna_can),
    VANILLA_CAKE(R.drawable.vanillacake),
    VANILLA_OR_LEMON_ICE_CREAM(R.drawable.vanilla_or_lemon_ice_cream),
    WAFFLE(R.drawable.waffle),
    WAFFLES(R.drawable.waffles),
    WAFFLE_DISH(R.drawable.waffle_dish),
    WATER(R.drawable.water),
    WATERMELON(R.drawable.watermelon),
    WATERMELON2(R.drawable.watermelon2),
    WHITE_CHEESE(R.drawable.white_cheese),
    WHITE_CHEESE_PIECE_P(R.drawable.white_cheese_piece_p),
    WINE_RED(R.drawable.wine_red),
    WINE_WHITE(R.drawable.wine_white),
    WINE_WHITE2(R.drawable.wine_white2),
    WINE_WHITE3(R.drawable.wine_white3),
    YELLOW_JELLY(R.drawable.yellowjelly),
    COIN(R.drawable.coins, 16, 16, 14);

    final Bitmap atlas;
    final Bitmap[] images;
    final boolean isAni;
    final int amount;

    Items(int resID, int width, int height, int amount) {
        options.inScaled = false;
        isAni = true;
        this.amount = amount;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        images = new Bitmap[amount];
        for (int i = 0; i < amount; i++)
            images[i] = getScaledBitmap(Bitmap.createBitmap(atlas, i * width, 0, width, height));
    }

    Items(int resID) {
        options.inScaled = false;
        isAni = false;
        this.amount = 1;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        images = new Bitmap[2];
        images[0] = getItemSize(atlas);
        images[1] = getSmallItemSize(atlas);
        ItemHelper.getItems().add(this);
    }


    public Bitmap getImage(int index) {
        return images[index];
    }

    public Bitmap getImage() {
        return images[0];
    }
    public Bitmap getSmallImage() {
        return images[1];
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


}

