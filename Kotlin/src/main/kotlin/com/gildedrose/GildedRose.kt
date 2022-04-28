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

    sealed class ExtendedItem {
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

    object Normal : ExtendedItem() {
        fun update(name: String, sellIn: Int, quality: Int) =
            advance(sellIn).let { newSellIn ->
                depreciate(quality)
                    .let { if (newSellIn < 0) depreciate(it) else it }
                    .let { Item(name, newSellIn, it) }
            }
    }

    object Brie : ExtendedItem() {
        fun update(name: String, sellIn: Int, quality: Int): Item =
            advance(sellIn).let { newSellIn ->
                improve(quality)
                    .let { if (newSellIn < 0) improve(it) else it }
                    .let { Item(name, newSellIn, it) }
            }
    }

    object BackstagePass : ExtendedItem() {
        fun update(name: String, sellIn: Int, quality: Int) =
            advance(sellIn).let { newSellIn ->
                (if (newSellIn >= 0)
                    quality
                        .let { improve(it) }
                        .let { if (newSellIn < 10) improve(it) else it }
                        .let { if (newSellIn < 5) improve(it) else it }
                else writeOff()).let { Item(name, newSellIn, it) }
            }
    }

    object Sulfuras : ExtendedItem() {
        fun update(name: String, sellIn: Int, quality: Int) = Item(name, sellIn, quality)
    }
}
