package com.gildedrose

class GildedRose(val items: Array<Item>) {

    fun updateQuality() {
        items
            .filterNot { item -> item.name == "Sulfuras, Hand of Ragnaros" }
            .forEach { item ->
                item.apply {
                    sellIn -= 1
                    when (name) {
                        "Aged Brie" -> {
                            improve()
                            if (sellIn < 0) {
                                improve()
                            }
                        }
                        "Backstage passes to a TAFKAL80ETC concert" -> {
                            if (sellIn >= 0) {
                                improve()
                                if (sellIn < 10) {
                                    improve()
                                }
                                if (sellIn < 5) {
                                    improve()
                                }
                            } else {
                                writeOff()
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

    private fun Item.writeOff() {
        quality = 0
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
