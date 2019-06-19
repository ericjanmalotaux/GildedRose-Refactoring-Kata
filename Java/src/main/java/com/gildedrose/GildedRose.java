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
            if (item.name.equals("Aged Brie")
                    || item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        improve(item);

                        if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                            if (item.sellIn < 10) {
                                improve(item);
                            }

                            if (item.sellIn < 5) {
                                improve(item);
                            }
                        }
                    } else {
                degrade(item);
            }


            if (item.sellIn < 0) {
                if (item.name.equals("Aged Brie")) {
                    improve(item);
                } else {
                    if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        item.quality = 0;
                    } else {
                        degrade(item);
                    }
                }
            }
        }
    }

    private void improve(Item item) {
        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }
    }

    private void degrade(Item item) {
        if (item.quality > 0) {
            item.quality = item.quality - 1;
        }
    }
}
