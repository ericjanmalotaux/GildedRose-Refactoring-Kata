package com.gildedrose

class GildedRose(var items: Array<Item>) {

    val strategies = mapOf(
        "Aged Brie" to Brie::update,
        "Backstage passes to a TAFKAL80ETC concert" to BackstagePass::update,
        "Sulfuras, Hand of Ragnaros" to Sulfuras::update
    )

    fun updateQuality() {
        items = items
            .map { (strategies[it.name] ?: Normal::update).invoke(it) }
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
        fun update(item: Item) =
            advance(item.sellIn)
                .let { sellIn ->
                    depreciate(item.quality)
                        .let { if (sellIn < 0) depreciate(it) else it }
                        .let { Item(item.name, sellIn, it) }
                }
    }

    object Brie : ExtendedItem() {
        fun update(item: Item): Item =
            advance(item.sellIn)
                .let { sellIn ->
                    improve(item.quality)
                        .let { if (sellIn < 0) improve(it) else it }
                        .let { Item(item.name, sellIn, it) }
                }
    }

    object BackstagePass : ExtendedItem() {
        fun update(item: Item) =
            advance(item.sellIn)
                .let { sellIn ->
                    (if (sellIn >= 0)
                        item.quality
                            .let { improve(it) }
                            .let { if (sellIn < 10) improve(it) else it }
                            .let { if (sellIn < 5) improve(it) else it }
                    else writeOff()).let { Item(item.name, sellIn, it) }
                }
    }

    object Sulfuras : ExtendedItem() {
        fun update(item: Item) = Item(item.name, item.sellIn, item.quality)
    }
}
