package com.gildedrose;

import java.util.Arrays;

class GildedRose {
    Item[] items;

    public GildedRose(Item... items) {
        this.items = items;
    }

    public void updateQuality() {
        this.items = Arrays.stream(items)
                .map(item -> item.name.equals("Sulfuras, Hand of Ragnaros")
                        ? new Item(item.name, item.sellIn, item.quality)
                        : new Item(item.name, item.sellIn - 1, Math.min(50, Math.max(0, item.quality - degradation(item)))))
                .toArray(Item[]::new);
    }

    private int degradation(Item item) {
        if ("Aged Brie".equals(item.name)) return -defaultDegradation(item);
        else if ("Backstage passes to a TAFKAL80ETC concert".equals(item.name)) return backstageDegradation(item);
        else if ("Conjured Mana Cake".equals(item.name)) return 2 * defaultDegradation(item);
        else return defaultDegradation(item);
    }

    private int defaultDegradation(Item item) {
        return item.sellIn > 0 ? 1 : 2;
    }

    private int backstageDegradation(Item item) {
        if (item.sellIn <= 0) return -item.quality;
        else if (item.sellIn <= 5) return -3;
        else if (item.sellIn <= 10) return -2;
        else return -1;
    }
}
