package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item... items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
                continue;
            }
            item.sellIn = item.sellIn - 1;
            if (item.name.equals("Aged Brie")) {
                degrade(item, brieDegradation(item));
            } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                degrade(item, backstageDegradation(item));
            } else {
                degrade(item, defaultDegradation(item));
            }
        }
    }

    private int defaultDegradation(Item item) {
        return item.sellIn >= 0 ? 1 : 2;
    }

    private int backstageDegradation(Item item) {
        int amount;
        if (item.sellIn < 0) amount = -item.quality;
        else if (item.sellIn < 5)  amount = -3;
        else if (item.sellIn < 10) amount = -2;
        else amount = -1;
        return amount;
    }

    private int brieDegradation(Item item) {
        return item.sellIn >= 0 ? -1 : -2;
    }

    private void degrade(Item item, int amount) {
        item.quality = Math.min(50, Math.max(0, item.quality - amount));
    }
}
