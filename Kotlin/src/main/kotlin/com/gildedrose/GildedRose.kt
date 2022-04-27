package com.gildedrose

class GildedRose(val items: Array<Item>) {

    fun updateQuality() {
        items
            .filterNot { item -> item.name == "Sulfuras, Hand of Ragnaros" }
            .forEach { item ->
                item.apply {

                    sellIn = sellIn - 1
                    if (name == "Aged Brie") {
                        if (quality < 50) {
                            quality += 1
                        }
                        if (sellIn < 0) {
                            if (quality < 50) {
                                quality += 1
                            }
                        }
                    } else if (name == "Backstage passes to a TAFKAL80ETC concert") {
                        if (sellIn < 0) {
                            quality = 0
                        } else {
                            if (quality < 50) {
                                quality += 1
                                if (sellIn < 10) {
                                    if (quality < 50) {
                                        quality += 1
                                    }
                                }
                                if (sellIn < 5) {
                                    if (quality < 50) {
                                        quality += 1
                                    }
                                }
                            }
                        }
                    } else {
                        if (quality > 0) {
                            quality -= 1
                        }
                        if (sellIn < 0) {
                            if (quality > 0) {
                                quality -= 1
                            }
                        }
                    }
                }
            }
    }
}
