package com.gildedrose;

import java.util.Arrays;

class GildedRose {
    Item[] items;

    public GildedRose(Item... items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items).filter(item -> !item.name.equals("Sulfuras, Hand of Ragnaros")).forEach(item -> {
            item.sellIn = item.sellIn - 1;
            degrade(item, degradation(item));
        });
    }

    private int degradation(Item item) {
        int amount;
        switch (item.name) {
            case "Aged Brie":
                amount = -defaultDegradation(item);
                break;
            case "Backstage passes to a TAFKAL80ETC concert":
                amount = backstageDegradation(item);
                break;
            case "Conjured Mana Cake":
                amount = 2 * defaultDegradation(item);
                break;
            default:
                amount = defaultDegradation(item);
                break;
        }
        return amount;
    }

    private int defaultDegradation(Item item) {
        return item.sellIn >= 0 ? 1 : 2;
    }

    private int backstageDegradation(Item item) {
        if (item.sellIn < 0) return -item.quality;
        else if (item.sellIn < 5) return -3;
        else if (item.sellIn < 10) return -2;
        else return -1;
    }

    private void degrade(Item item, int amount) {
        item.quality = Math.min(50, Math.max(0, item.quality - amount));
    }
}
