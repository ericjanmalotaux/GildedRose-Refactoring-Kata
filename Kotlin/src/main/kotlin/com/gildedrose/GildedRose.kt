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

    object Normal {
        fun update(item: Item) =
            (item.sellIn - 1)
                .let { sellIn ->
                    depreciate(item.quality)
                        .let { if (sellIn < 0) depreciate(it) else it }
                        .let { Item(item.name, sellIn, it) }
                }
    }

    object Brie {
        fun update(item: Item): Item =
            (item.sellIn - 1)
                .let { sellIn ->
                    improve(item.quality)
                        .let { if (sellIn < 0) improve(it) else it }
                        .let { Item(item.name, sellIn, it) }
                }
    }

    object BackstagePass {
        fun update(item: Item) =
            (item.sellIn - 1)
                .let { sellIn ->
                    (if (sellIn < 0) 0
                    else
                        item.quality
                            .let { improve(it) }
                            .let { if (sellIn < 10) improve(it) else it }
                            .let { if (sellIn < 5) improve(it) else it }
                            )
                        .let { Item(item.name, sellIn, it) }
                }
    }

    object Sulfuras {
        fun update(item: Item) = Item(item.name, item.sellIn, item.quality)
    }

}

fun depreciate(i: Int) = if (i > 0) i - 1 else i
fun improve(i: Int) = if (i < 50) i + 1 else i
