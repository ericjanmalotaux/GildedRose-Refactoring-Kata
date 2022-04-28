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

    abstract class ExtendedItem(name: String, sellIn: Int, quality: Int): Item(name, sellIn, quality) {

        open fun advance(): Item {
            sellIn -= 1
            return this
        }


        protected fun writeOff() {
            quality = 0
        }

        protected fun depreciate() {
            if (quality > 0) {
                quality -= 1
            }
        }

        protected fun improve() {
            if (quality < 50) {
                quality += 1
            }
        }
    }

    class Normal(name: String, sellIn: Int, quality: Int) : ExtendedItem(name, sellIn, quality) {
        override fun advance(): Normal {
            super.advance()
            depreciate()
            if (sellIn < 0) {
                depreciate()
            }
            return this
        }
    }

    class Brie(name: String, sellIn: Int, quality: Int) : ExtendedItem(name, sellIn, quality) {
        override fun advance(): Brie {
            super.advance()
            improve()
            if (sellIn < 0) {
                improve()
            }
            return this
        }
    }

    class BackstagePass(name: String, sellIn: Int, quality: Int) : ExtendedItem(name, sellIn, quality) {
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

    class Sulfuras(name: String, sellIn: Int, quality: Int) : ExtendedItem(name, sellIn, quality) {
        override fun advance(): Sulfuras {
            return this
        }
    }
}
