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
                    item.quality.depreciate(if (sellIn >= 0) 1 else 2)
                        .let { Item(item.name, sellIn, it) }
                }
    }

    object Brie {
        fun update(item: Item): Item =
            (item.sellIn - 1)
                .let { sellIn ->
                    item.quality.improve(if (sellIn >= 0) 1 else 2)
                        .let { Item(item.name, sellIn, it) }
                }
    }

    object BackstagePass {
        fun update(item: Item) =
            (item.sellIn - 1)
                .let { sellIn ->
                    (if (sellIn < 0) 0
                    else
                        item.quality.improve(if (sellIn < 5) 3 else if (sellIn < 10) 2 else 1)
                            )
                        .let { Item(item.name, sellIn, it) }
                }
    }

    object Sulfuras {
        fun update(item: Item) = Item(item.name, item.sellIn, item.quality)
    }

}

fun Int.depreciate(step: Int = 1) = (this - step).takeIf { it >= 0 } ?: this
fun Int.improve(step: Int = 1) = (this + step).takeIf { it <= 50 } ?: this
