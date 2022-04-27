package com.gildedrose

class GildedRose(val items: Array<Item>) {

    fun updateQuality() {
        items
            .filterNot { item -> item.name == "Sulfuras, Hand of Ragnaros" }
            .forEach { item ->
                item.apply {
                    sellIn = sellIn - 1
                    when (name) {
                        "Aged Brie" -> {
                            improve()
                            if (sellIn < 0) {
                                improve()
                            }
                        }
                        "Backstage passes to a TAFKAL80ETC concert" -> {
                            if (sellIn < 0) {
                                quality = 0
                            } else {
                                improve()
                                if (sellIn < 10) {
                                    improve()
                                }
                                if (sellIn < 5) {
                                    improve()
                                }
                            }
                        }
                        else -> {
                            depreciate()
                            if (sellIn < 0) {
                                depreciate()
                            }
                        }
                    }
                }
            }
    }

    private fun Item.depreciate() {
        if (quality > 0) {
            quality -= 1
        }
    }

    private fun Item.improve() {
        if (quality < 50) {
            quality += 1
        }
    }
}
