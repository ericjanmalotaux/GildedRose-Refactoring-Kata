package com.gildedrose

class GildedRose(val items: Array<Item>) {

    fun updateQuality() {
        items
            .filterNot { item -> item.name == "Sulfuras, Hand of Ragnaros" }
            .forEach { item ->
                item.sellIn = item.sellIn - 1
                if (item.name == "Aged Brie") {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1
                    }
                    if (item.sellIn < 0) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1
                        }
                    }
                } else if (item.name == "Backstage passes to a TAFKAL80ETC concert") {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1

                        if (item.sellIn < 10) {
                            if (item.quality < 50) {
                                item.quality = item.quality + 1
                            }
                        }

                        if (item.sellIn < 5) {
                            if (item.quality < 50) {
                                item.quality = item.quality + 1
                            }
                        }
                    }
                } else {
                    if (item.quality > 0) {
                        item.quality = item.quality - 1
                    }
                }
                if (item.sellIn < 0) {
                    if (item.name != "Aged Brie") {
                        if (item.name == "Backstage passes to a TAFKAL80ETC concert") {
                            item.quality = 0
                        } else {
                            if (item.quality > 0) {
                                item.quality = item.quality - 1
                            }
                        }
                    }
                }
            }
    }
}
