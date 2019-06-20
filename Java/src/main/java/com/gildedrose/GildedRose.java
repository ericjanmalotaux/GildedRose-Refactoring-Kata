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
                degrade(item, -1);


                if (item.sellIn < 0) {
                    degrade(item, -1);
                }
            } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                degrade(item, -1);

                if (item.sellIn < 10) {
                    degrade(item, -1);
                }

                if (item.sellIn < 5) {
                    degrade(item, -1);
                }


                if (item.sellIn < 0) {
                    item.quality = 0;
                }
            } else {
                degrade(item, 1);


                if (item.sellIn < 0) {
                    degrade(item, 1);
                }
            }
        }
    }

    private void degrade(Item item, int amount) {
        item.quality = Math.min(50, Math.max(0, item.quality - amount));
    }
}
