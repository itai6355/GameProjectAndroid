package com.example.gameproject.helpers;

import com.example.gameproject.entities.items.Items;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemHelper {


    final static ArrayList<Items> items = new ArrayList<>();


    private final HashMap<Items, Integer> itemPrices;

    public ItemHelper() {
        itemPrices = new HashMap<>();
        initItemPrices();

    }

    private void initItemPrices() {
        for (Items item : Items.values()) {
            itemPrices.put(item, getPrice(item));
        }

    }

    public int getPrice(Items item) {
        return switch (item) {
            default -> 1;
        };
    }

    public static ArrayList<Items> getItems() {
        return items;
    }

    public HashMap<Items, Integer> getItemPrices() {
        return itemPrices;
    }

    public static void PrintAll() {
        for (Items item : items) {
            System.out.println("Items: " + item);
        }
    }

    public enum basicProduct{
        SLICED_BREAD_P, PLAIN_YOGURT, POTATO_P, HONEY, HOT_COCOA_MIX, FRUIT_COCKTAIL_CAN, BUTTER, CHEESE, BUTTER2, BARBEQUE_SAUCE, BAGUETTE_DISH, BREAD_DISH, BREAD_SLICE, BREAD2, BAGUETTE, BAGEL, BAGEL_DISH, EGG_WHITE, EGG_BROWN_P, EGG_BROWN, EGG_WHITE_P, EGG_BOX, FLOUR, COOKIES, COFFEE_BAG, COOKING_OIL, BUN, OLIVE_OIL, MILK_PACK, MILK_PLASTIC, MILK_BOTTLE, MILK_GALLON, SALT, WATER, WHITE_CHEESE, WHITE_CHEESE_PIECE_P, TUNA_CAN, VANILLA_OR_LEMON_ICE_CREAM, SUGAR;

        public static boolean contains(Items item) {
            for (basicProduct p : basicProduct.values()) {
                if (p.name().equals(item.name())) {
                    return true;
                }
            }
            return false;
        }
        }

    public enum fruit {
        FRUITCAKE, APPLE, APRICOT, BANANA, CABBAGE, BELL_PEPPER, BELL_PEPPER2, CUSTARDAPPLE, CUCUMBER, DRAGONFRUIT, CHILI_PEPPER, CHERRY_BLACK, CRANBERRY, GRAPEFRUIT, DURIAN, GRAPES_BLACK, GUAVA, GRAPES_GREEN, I_ROOT, FIG, I_STRAWBERRY, I_TOMATO, KIWI, MELON, ORANGE, LEMON, PEACH, RED_APPLE, PEAR, PASSION_FRUIT, WATERMELON, STRAWBERRY, STRAWBERRY_P;

        public static boolean contains(Items item) {
            for (fruit p : fruit.values()) {
                if (p.name().equals(item.name())) {
                    return true;
                }
            }
            return false;
        }
        }

    public enum food {
        SUSHI_DISH, CROISSANTSANDWICH, CROISSANTAVOCADOSANDWICH, BACON_DISH, BURGER, BURRITO, BOWL_OF_RICE, BURRITO_DISH, DUMPLINGS, CURRY_DISH, CURRY, DUMPLINGS_DISH, FRIED_EGG, GARLICBREAD, HOTDOG, HOTDOG_SAUCE, FRENCHFRIES_DISH, ENERGY_BAR, EGGSALAD_BOWL, HAMBURGER, HOT_DOG, FRIEDEGG_DISH, EGGTART, OMELET, OMELET_DISH, MEATBALL, SANDWICH, SANDWICH_DISH, ROASTED_CHICKEN_DISH, PIZZA_DISH, PIZZA, TACO, TACO_DISH, SPAGHETTI, STEAK_DISH;

        public static boolean contains(Items item) {
            for (food p : food.values()) {
                if (p.name().equals(item.name())) {
                    return true;
                }
            }
            return false;
        }
        }

    public enum meat {
        BACON, CHICKEN, FISH_SALOMON, MEAT1, MEAT1_P, MEAT2, MEAT, MUSTARD, KETCHUP, SAUSAGE_P, SALMON_P, SALMON;

        public static boolean contains(Items item) {
            for (meat p : meat.values()) {
                if (p.name().equals(item.name())) {
                    return true;
                }
            }
            return false;
        }
        }

    public enum snacks {
        STRAWBERRY_ICE_CREAM, ORANGE_JUICE, I_ROOT_BEER, ICE_CREAM, BUBBLE_GUM, CEREAL1, CEREAL2, CANDY_BAR, CANDY_CANES, CHEESEPUFF_BOWL, CHOCOLATE, COOKIES2, DRY_DOG_FOOD, GUMMYBEAR, GINGERBREAD_MAN_DISH, GRAPE_SODA, BEER, MACRONS, JELLY, LOLLIPOP, MILK_CHOCOLATE, MARSHMALLOWS, GREEN_JELLY, BLUEJELLY, SNACK1, SODA_CAN, SOFT_DRINK_BLUE, SOFT_DRINK_GREEN, RED_JELLY, PURPLE_JELLY, PUDDING, POPCORN_BOWL, POTATOCHIP_BLUE, POTATOCHIP_GREEN, POTATOCHIP_YELLOW, PINK_JELLY, PEANUT_BUTTER, WINE_WHITE2, WINE_WHITE3, YELLOW_JELLY, WINE_WHITE, WINE_RED, STRAWBERRY_JAM, SOFT_DRINK_YELLOW, SOFT_DRINK_RED;
        public static boolean contains(Items item) {
            for (snacks p : snacks.values()) {
                if (p.name().equals(item.name())) {
                    return true;
                }
            }
            return false;
        }
        }

    public enum cakes {
        COOKIENCREAMPOT, CHEESECAKE, CARROTCAKE, CHEESECAKE2, TART, APPLE_PIE_DISH, CHEESECAKEPOT, CHEESECAKE_DISH, BANOFFEEPOT, CHERRY_PIE, CHOCOLATEDONUT, CINNAMONROLL, DONUT, DANISHGLAZED, CUPCAKE, CROISSANT, CHOCOLATEPOT, CHOCOLATEBERRYSHORTCAKE, CHERRYCHOCOLATEPOT, CHERRYSHORTCAKE, CHOCOLATECAKE, CHOCOLATESWISSROLL, COOKIECHEESECAKE, DANISHGLAZED2, DONU2, CHOCOLATETWIST, CHOCOLATECAKE2, GLAZED_CINNAMONROLL, FUNFETTIDONUT, LEMON_CAKE, PANCAKES_CHOCOLATE, PANCAKES_COOKIES_N_CREAM, PANCAKES, PANCAKES_BANOFFEE, PANCAKES_CREAM, PANCAKES_DISH, PANCAKES_BERRY, LEMON_BLUEBERRY_POT, LEMON_PIE, MINT_CHOCOLATE_POT, LEMON_DONUT, PANCAKES_MINT_CHOCOLATE, RAINBOW_CAKE, RED_VELVET_CAKE, PANCAKES_RAINBOW, PANNACOTTA, RASPBERRY_CHEESECAKE_POT, STRAWBERRY_SHORTCAKE, WAFFLE_DISH, STRAWBERRY_WAFFLES, STRAWBERRY_CAKE, STRAWBERRY_CAKE_DISH, TIRAMISU, WAFFLE, WAFFLES, SWISSROLL, STRAWBERRY_DONUT;

        public static boolean contains(Items item) {
            for (cakes p : cakes.values()) {
                if (p.name().equals(item.name())) {
                    return true;
                }
            }
            return false;
        }
    }
}
