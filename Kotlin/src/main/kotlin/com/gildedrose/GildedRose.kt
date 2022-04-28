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

        companion object {
            @JvmStatic
            protected fun advance(i: Int): Int = i - 1
        }
    }

    class Normal(name: String, sellIn: Int, quality: Int) : ExtendedItem(name, sellIn, quality) {

        companion object {
            fun update(name: String, sellIn: Int, quality: Int): Normal {
                val sellIn = advance(sellIn)
                return Normal(name, sellIn, quality).apply {
                    depreciate()
                    if (sellIn < 0) {
                        depreciate()
                    }
                }
            }
        }
    }

    class Brie(name: String, sellIn: Int, quality: Int) : ExtendedItem(name, sellIn, quality) {

        companion object {
            fun update(name: String, sellIn: Int, quality: Int): Brie {
                val sellIn = advance(sellIn)
                return Brie(name, sellIn, quality).apply {
                    improve()
                    if (sellIn < 0) {
                        improve()
                    }
                }
            }
        }
    }

    class BackstagePass(name: String, sellIn: Int, quality: Int) : ExtendedItem(name, sellIn, quality) {

        companion object {
            fun update(name: String, sellIn: Int, quality: Int): BackstagePass {
                val sellIn = advance(sellIn)
                return BackstagePass(name, sellIn, quality).apply {
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
            }
        }
    }

    class Sulfuras(name: String, sellIn: Int, quality: Int) : ExtendedItem(name, sellIn, quality) {

        companion object {
            fun update(name: String, sellIn: Int, quality: Int) = Sulfuras(name, sellIn, quality).apply { }
        }
    }
}
