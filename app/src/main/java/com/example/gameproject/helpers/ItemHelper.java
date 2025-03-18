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
        int price;
        switch (item) {
            case APPLE -> price = 9;
            case APPLE_PIE_DISH -> price = 19;
            case APRICOT -> price = 6;
            case BACON -> price = 7;
            case BACON_DISH -> price = 11;
            case BAGEL -> price = 3;
            case BAGEL_DISH -> price = 3;
            case BAGUETTE -> price = 3;
            case BAGUETTE_DISH -> price = 7;
            case TART -> price = 15;
            case BANANA -> price = 8;
            case BANOFFEEPOT -> price = 16;
            case BARBEQUE_SAUCE -> price = 8;
            case BEER -> price = 15;
            case BELL_PEPPER -> price = 4;
            case BELL_PEPPER2 -> price = 4;
            case BLUEJELLY -> price = 4;
            case BOWL_OF_RICE -> price = 16;
            case BREAD -> price = 6;
            case BREAD2 -> price = 10;
            case BREAD_DISH -> price = 2;
            case BREAD_SLICE -> price = 4;
            case BUBBLE_GUM -> price = 6;
            case BUN -> price = 11;
            case BURGER -> price = 11;
            case BURRITO -> price = 14;
            case BURRITO_DISH -> price = 9;
            case BUTTER -> price = 12;
            case BUTTER2 -> price = 10;
            case CABBAGE -> price = 7;
            case CANDY_BAR -> price = 6;
            case CANDY_CANES -> price = 7;
            case CARROTCAKE -> price = 16;
            case CEREAL1 -> price = 10;
            case CEREAL2 -> price = 14;
            case CHEESE -> price = 8;
            case CHEESECAKE -> price = 18;
            case CHEESECAKE2 -> price = 19;
            case CHEESECAKEPOT -> price = 13;
            case CHEESECAKE_DISH -> price = 16;
            case CHEESEPUFF_BOWL -> price = 13;
            case CHERRYCHOCOLATEPOT -> price = 15;
            case CHERRYSHORTCAKE -> price = 13;
            case CHERRY_BLACK -> price = 10;
            case CHERRY_PIE -> price = 15;
            case CHERRY_RED -> price = 3;
            case CHICKEN -> price = 13;
            case CHILI_PEPPER -> price = 5;
            case CHOCOLATE -> price = 8;
            case CHOCOLATEBERRYSHORTCAKE -> price = 14;
            case CHOCOLATECAKE -> price = 14;
            case CHOCOLATECAKE2 -> price = 17;
            case CHOCOLATEDONUT -> price = 19;
            case CHOCOLATEPOT -> price = 16;
            case CHOCOLATESWISSROLL -> price = 17;
            case CHOCOLATETWIST -> price = 19;
            case CINNAMONROLL -> price = 19;
            case COFFEE_BAG -> price = 11;
            case COOKIECHEESECAKE -> price = 17;
            case COOKIENCREAMPOT -> price = 19;
            case COOKIES -> price = 12;
            case COOKIES2 -> price = 12;
            case COOKING_OIL -> price = 12;
            case CRANBERRY -> price = 3;
            case CREMECARAMEL -> price = 3;
            case CROISSANT -> price = 12;
            case CROISSANTAVOCADOSANDWICH -> price = 13;
            case CROISSANTSANDWICH -> price = 10;
            case CUCUMBER -> price = 1;
            case CUPCAKE -> price = 12;
            case CURRY -> price = 16;
            case CURRY_DISH -> price = 17;
            case CUSTARDAPPLE -> price = 7;
            case DANISHGLAZED -> price = 12;
            case DANISHGLAZED2 -> price = 16;
            case DONU2 -> price = 13;
            case DONUT -> price = 19;
            case DRAGONFRUIT -> price = 14;
            case DRY_DOG_FOOD -> price = 16;
            case DUMPLINGS -> price = 15;
            case DUMPLINGS_DISH -> price = 11;
            case DURIAN -> price = 5;
            case EGGSALAD_BOWL -> price = 14;
            case EGGTART -> price = 10;
            case EGG_BOX -> price = 4;
            case EGG_BROWN -> price = 7;
            case EGG_BROWN_P -> price = 12;
            case EGG_WHITE -> price = 3;
            case EGG_WHITE_P -> price = 9;
            case ENERGY_BAR -> price = 10;
            case FIG -> price = 8;
            case FISH_SALOMON -> price = 18;
            case FLOUR -> price = 2;
            case FRENCHFRIES_DISH -> price = 18;
            case FRIEDEGG -> price = 4;
            case FRIEDEGG_DISH -> price = 14;
            case FRIED_EGG -> price = 17;
            case FRUITCAKE -> price = 4;
            case FRUIT_COCKTAIL_CAN -> price = 9;
            case FUNFETTIDONUT -> price = 15;
            case GARLICBREAD -> price = 11;
            case GUMMYBEAR -> price = 8;
            case GINGERBREAD_MAN_DISH -> price = 12;
            case GLAZED_CINNAMONROLL -> price = 18;
            case GRAPEFRUIT -> price = 10;
            case GRAPES_BLACK -> price = 6;
            case GRAPES_GREEN -> price = 5;
            case GRAPE_SODA -> price = 12;
            case GREEN_JELLY -> price = 6;
            case GUAVA -> price = 7;
            case HAMBURGER -> price = 12;
            case HONEY -> price = 8;
            case HOTDOG -> price = 10;
            case HOTDOG_SAUCE -> price = 11;
            case HOT_COCOA_MIX -> price = 9;
            case HOT_DOG -> price = 14;
            case ICE_CREAM -> price = 14;
            case I_MUSHROOM -> price = 5;
            case I_ROOT -> price = 10;
            case I_ROOT_BEER -> price = 14;
            case I_STRAWBERRY -> price = 6;
            case I_TOMATO -> price = 5;
            case JAM -> price = 3;
            case JELLY -> price = 11;
            case KETCHUP -> price = 18;
            case KIWI -> price = 3;
            case LEMON -> price = 8;
            case LEMON_BLUEBERRY_POT -> price = 19;
            case LEMON_CAKE -> price = 17;
            case LEMON_DONUT -> price = 16;
            case LEMON_PIE -> price = 11;
            case LOAF_BREAD -> price = 2;
            case LOLLIPOP -> price = 10;
            case MACRONS -> price = 14;
            case MARSHMALLOWS -> price = 9;
            case MEAT -> price = 17;
            case MEAT1 -> price = 17;
            case MEAT1_P -> price = 18;
            case MEAT2 -> price = 19;
            case MEATBALL -> price = 10;
            case MELON -> price = 11;
            case MILK_BOTTLE -> price = 7;
            case MILK_CHOCOLATE -> price = 10;
            case MILK_GALLON -> price = 3;
            case MILK_PACK -> price = 11;
            case MILK_PLASTIC -> price = 2;
            case MINT_CHOCOLATE_POT -> price = 17;
            case MUSTARD -> price = 19;
            case OLIVE_OIL -> price = 7;
            case OMELET -> price = 13;
            case OMELET_DISH -> price = 9;
            case ORANGE -> price = 4;
            case ORANGE_JUICE -> price = 8;
            case PANCAKES -> price = 19;
            case PANCAKES_BANOFFEE -> price = 16;
            case PANCAKES_BERRY -> price = 17;
            case PANCAKES_CHOCOLATE -> price = 14;
            case PANCAKES_COOKIES_N_CREAM -> price = 13;
            case PANCAKES_CREAM -> price = 19;
            case PANCAKES_DISH -> price = 18;
            case PANCAKES_MINT_CHOCOLATE -> price = 13;
            case PANCAKES_RAINBOW -> price = 19;
            case PANNACOTTA -> price = 16;
            case PASSION_FRUIT -> price = 4;
            case PEACH -> price = 4;
            case PEANUT_BUTTER -> price = 13;
            case PEAR -> price = 11;
            case PINK_JELLY -> price = 7;
            case PIZZA -> price = 17;
            case PIZZA_DISH -> price = 16;
            case PLAIN_YOGURT -> price = 9;
            case PLUM -> price = 7;
            case POPCORN_BOWL -> price = 9;
            case POTATOCHIP_BLUE -> price = 12;
            case POTATOCHIP_GREEN -> price = 13;
            case POTATOCHIP_YELLOW -> price = 14;
            case POTATO_P -> price = 10;
            case PROFITEROLES -> price = 3;
            case PUDDING -> price = 11;
            case PURPLE_JELLY -> price = 9;
            case RAINBOW_CAKE -> price = 14;
            case RAMEN -> price = 4;
            case RASPBERRY_CHEESECAKE_POT -> price = 17;
            case RED_JELLY -> price = 6;
            case RED_VELVET_CAKE -> price = 17;
            case RED_APPLE -> price = 7;
            case RED_APPLE_P -> price = 4;
            case ROASTED_CHICKEN_DISH -> price = 18;
            case SALMON -> price = 18;
            case SALMON2 -> price = 4;
            case SALMON_DISH -> price = 6;
            case SALMON_P -> price = 18;
            case SALT -> price = 4;
            case SANDWICH -> price = 13;
            case SANDWICH_DISH -> price = 12;
            case SAUSAGE_P -> price = 19;
            case SLICED_BREAD_P -> price = 5;
            case SNACK1 -> price = 10;
            case SODA_CAN -> price = 5;
            case SOFT_DRINK_BLUE -> price = 8;
            case SOFT_DRINK_GREEN -> price = 13;
            case SOFT_DRINK_RED -> price = 11;
            case SOFT_DRINK_YELLOW -> price = 9;
            case SPAGHETTI -> price = 17;
            case STEAK_DISH -> price = 13;
            case STRAWBERRY -> price = 4;
            case STRAWBERRY_CAKE -> price = 18;
            case STRAWBERRY_CAKE_DISH -> price = 19;
            case STRAWBERRY_DONUT -> price = 16;
            case STRAWBERRY_SHORTCAKE -> price = 14;
            case STRAWBERRY_WAFFLES -> price = 18;
            case STRAWBERRY_ICE_CREAM -> price = 7;
            case STRAWBERRY_JAM -> price = 9;
            case STRAWBERRY_P -> price = 3;
            case SUGAR -> price = 12;
            case SUSHI_DISH -> price = 14;
            case SWISSROLL -> price = 15;
            case TACO -> price = 10;
            case TACO_DISH -> price = 14;
            case TIRAMISU -> price = 18;
            case TUNA_CAN -> price = 10;
            case VANILLA_CAKE -> price = 7;
            case VANILLA_OR_LEMON_ICE_CREAM -> price = 4;
            case WAFFLE -> price = 15;
            case WAFFLES -> price = 19;
            case WAFFLE_DISH -> price = 18;
            case WATER -> price = 6;
            case WATERMELON -> price = 3;
            case WATERMELON2 -> price = 4;
            case WHITE_CHEESE -> price = 9;
            case WHITE_CHEESE_PIECE_P -> price = 4;
            case WINE_RED -> price = 13;
            case WINE_WHITE -> price = 12;
            case WINE_WHITE2 -> price = 6;
            case WINE_WHITE3 -> price = 13;
            case YELLOW_JELLY -> price = 13;
            case ALUMINUM_FOIL -> price = 8;
            case BAKING_POWDER -> price = 4;
            case BALL_PEN -> price = 12;
            case BANDAGE_BOX -> price = 8;
            case BASKET_METAL -> price = 6;
            case BASKET_YELLOW -> price = 12;
            case BATHROOM_CLEANER -> price = 10;
            case BATTERIES -> price = 7;
            case BODY_LOTION -> price = 14;
            case BOWL -> price = 7;
            case CHOPPING_BOARD -> price = 11;
            case CLEANING_BRUSH -> price = 12;
            case CLEANING_GLOVES -> price = 8;
            case COFFEE_MUG -> price = 4;
            case CREDIT_CARD_1 -> price = 5;
            case CREDIT_CARD_2 -> price = 10;
            case CREDIT_CARD_3 -> price = 15;
            case DETERGENT -> price = 9;
            case ERASER -> price = 11;
            case FRYING_PAN -> price = 9;
            case GLUE -> price = 14;
            case GLUE_STICK -> price = 10;
            case HAND_SANITISER -> price = 11;
            case I_WOOL_CLOTH -> price = 10;
            case KITCHEN_KNIFE_SET -> price = 6;
            case KITCHEN_KNIFE_SET_P -> price = 8;
            case KITCHEN_SOAP -> price = 15;
            case LIGHT_BULB -> price = 12;
            case LIGHT_BULB_BOX -> price = 8;
            case PAPER_BAG -> price = 13;
            case POWER_STRIP_TYPEA -> price = 14;
            case POWER_STRIP_TYPEF -> price = 13;
            case RECEIPT -> price = 16;
            case ROLLING_PIN -> price = 12;
            case SCISSORS -> price = 7;
            case SCISSORS_P -> price = 16;
            case SCRUB_BRUSH -> price = 9;
            case SCRUB_SPONGE -> price = 10;
            case SHAMPOO -> price = 8;
            case SOAP -> price = 15;
            case SOAP_BOX -> price = 14;
            case SPATULA -> price = 7;
            case SUN_CREAM_TUBE -> price = 15;
            case TEAKETTLE -> price = 10;
            case TOILET_PAPER -> price = 9;
            case TOOTHBRUSH -> price = 7;
            case TOOTHBRUSH_SET -> price = 13;
            case TOOTHPASTE -> price = 12;
            case TOOTHPASTE_BOX -> price = 9;
            case WAX -> price = 11;
            case WET_WIPE -> price = 9;
            case WHISK -> price = 9;
            case CHOCOLATE_DISH -> price = 10;
            case EGGTART_DISH -> price = 18;
            case GARLICBREAD_DISH -> price = 19;
            case GIANTGUMMYBEAR_DISH -> price = 19;
            case HOTDOG_DISH -> price = 8;
            case ICECREAM_BOWL -> price = 11;
            case LOAFBREAD_DISH -> price = 14;
            case MEATBALL_DISH -> price = 13;
            case NACHO_DISH -> price = 18;
            case POTATOCHIPS_BOWL -> price = 18;
            default -> price = -1;
        }
        return price;
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

    public enum basicProduct {
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
        CHOCOLATE_DISH, EGGTART_DISH, GARLICBREAD_DISH, GIANTGUMMYBEAR_DISH, HOTDOG_DISH, ICECREAM_BOWL, LOAFBREAD_DISH, MEATBALL_DISH, NACHO_DISH, POTATOCHIPS_BOWL, SUSHI_DISH, CROISSANTSANDWICH, CROISSANTAVOCADOSANDWICH, BACON_DISH, BURGER, BURRITO, BOWL_OF_RICE, BURRITO_DISH, DUMPLINGS, CURRY_DISH, CURRY, DUMPLINGS_DISH, FRIED_EGG, GARLICBREAD, HOTDOG, HOTDOG_SAUCE, FRENCHFRIES_DISH, ENERGY_BAR, EGGSALAD_BOWL, HAMBURGER, HOT_DOG, FRIEDEGG_DISH, EGGTART, OMELET, OMELET_DISH, MEATBALL, SANDWICH, SANDWICH_DISH, ROASTED_CHICKEN_DISH, PIZZA_DISH, PIZZA, TACO, TACO_DISH, SPAGHETTI, STEAK_DISH;

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

    public enum bakeryTools {
        ALUMINUM_FOIL, BAKING_POWDER, BALL_PEN, BANDAGE_BOX, BASKET_METAL, BASKET_YELLOW, BATHROOM_CLEANER, BATTERIES, BODY_LOTION, BOWL, CHOPPING_BOARD, CLEANING_BRUSH, CLEANING_GLOVES, COFFEE_MUG, CREDIT_CARD_1, CREDIT_CARD_2, CREDIT_CARD_3, DETERGENT, ERASER, FRYING_PAN, GLUE, GLUE_STICK, HAND_SANITISER, I_WOOL_CLOTH, KITCHEN_KNIFE_SET, KITCHEN_KNIFE_SET_P, KITCHEN_SOAP, LIGHT_BULB, LIGHT_BULB_BOX, PAPER_BAG, POWER_STRIP_TYPEA, POWER_STRIP_TYPEF, RECEIPT, ROLLING_PIN, SCISSORS, SCISSORS_P, SCRUB_BRUSH, SCRUB_SPONGE, SHAMPOO, SOAP, SOAP_BOX, SPATULA, SUN_CREAM_TUBE, TEAKETTLE, TOILET_PAPER, TOOTHBRUSH, TOOTHBRUSH_SET, TOOTHPASTE, TOOTHPASTE_BOX, WAX, WET_WIPE, WHISK;

        public static boolean contains(Items item) {
            for (bakeryTools p : bakeryTools.values()) {
                if (p.name().equals(item.name())) {
                    return true;
                }
            }
            return false;
        }
    }
}

