package com.gildedrose

class GildedRose(var items: Array<Item>) {

    val strategies = mapOf(
        "Aged Brie" to Brie::update,
        "Backstage passes to a TAFKAL80ETC concert" to BackstagePass::update,
        "Sulfuras, Hand of Ragnaros" to Sulfuras::update
    )

    fun updateQuality() {
        items = items
            .map { (strategies[it.name] ?: Normal::update).invoke(it.name, it.sellIn, it.quality) }
            .toTypedArray()
    }

    abstract class ExtendedItem(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {

        companion object {
            @JvmStatic
            protected fun advance(i: Int) = i - 1
            @JvmStatic
            protected fun depreciate(i: Int) = if (i > 0) i - 1 else i
            @JvmStatic
            protected fun improve(i: Int) = if (i < 50) i + 1 else i
            @JvmStatic
            protected fun writeOff() = 0
        }
    }

    class Normal(name: String, sellIn: Int, quality: Int) : ExtendedItem(name, sellIn, quality) {

        companion object {
            fun update(name: String, sellIn: Int, quality: Int): Item {
                val sellIn = advance(sellIn)
                var newQuality = depreciate(quality)
                if (sellIn < 0) {
                    newQuality = depreciate(newQuality)
                }
                return Item(name, sellIn, newQuality)
            }
        }
    }

    class Brie(name: String, sellIn: Int, quality: Int) : ExtendedItem(name, sellIn, quality) {

        companion object {
            fun update(name: String, sellIn: Int, quality: Int): Item {
                val sellIn = advance(sellIn)
                var newQuality = improve(quality)
                if (sellIn < 0) {
                    newQuality = improve(newQuality)
                }
                return Item(name, sellIn, newQuality)
            }
        }
    }

    class BackstagePass(name: String, sellIn: Int, quality: Int) : ExtendedItem(name, sellIn, quality) {

        companion object {
            fun update(name: String, sellIn: Int, quality: Int): Item {
                val sellIn = advance(sellIn)
                var newQuality = quality
                if (sellIn >= 0) {
                    newQuality = improve(newQuality)
                    if (sellIn < 10) {
                        newQuality = improve(newQuality)
                    }
                    if (sellIn < 5) {
                        newQuality = improve(newQuality)
                    }
                } else {
                    newQuality = writeOff()
                }
                return Item(name, sellIn, newQuality)
            }
        }
    }

    class Sulfuras(name: String, sellIn: Int, quality: Int) : ExtendedItem(name, sellIn, quality) {

        companion object {
            fun update(name: String, sellIn: Int, quality: Int) = Item(name, sellIn, quality).apply { }
        }
    }
}
