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
        switch (item) {
            default:
                return 1;
        }
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

}
