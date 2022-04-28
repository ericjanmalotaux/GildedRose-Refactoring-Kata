package com.gildedrose

class GildedRose(var items: Array<Item>) {

    val strategies = mapOf(
        "Aged Brie" to ::Brie,
        "Backstage passes to a TAFKAL80ETC concert" to ::BackstagePass,
        "Sulfuras, Hand of Ragnaros" to ::Sulfuras
    )

    fun updateQuality() {
        items = items
            .map { (strategies[it.name] ?: ::Normal).invoke(it.name, it.sellIn, it.quality) }
            .map { item -> item.advance() }
            .toTypedArray()
    }

    class Normal(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
        override fun advance(): Normal {
            super.advance()
            depreciate()
            if (sellIn < 0) {
                depreciate()
            }
            return this
        }
    }

    class Brie(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
        override fun advance(): Brie {
            super.advance()
            improve()
            if (sellIn < 0) {
                improve()
            }
            return this
        }
    }

    class BackstagePass(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
        override fun advance(): BackstagePass {
            super.advance()
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
            return this
        }
    }

    class Sulfuras(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
        override fun advance(): Sulfuras {
            return this
        }
    }
}
