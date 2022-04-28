package com.gildedrose

class GildedRose(var items: Array<Item>) {

    val strategies = mapOf(
        "Aged Brie" to Brie::updateQuality,
        "Backstage passes to a TAFKAL80ETC concert" to BackstagePass::updateQuality,
        "Sulfuras, Hand of Ragnaros" to Sulfuras::updateQuality
    )

    fun updateQuality() {
        items = items.map { (strategies[it.name] ?: Normal::updateQuality).invoke(it) }.toTypedArray()
    }

    object Normal {
        fun updateQuality(item: Item) =
            (item.sellIn - 1).let { Item(item.name, it, item.quality.depreciate(if (it >= 0) 1 else 2)) }
    }

    object Brie {
        fun updateQuality(item: Item) =
            (item.sellIn - 1).let { Item(item.name, it, item.quality.improve(if (it >= 0) 1 else 2)) }
    }

    object BackstagePass {
        fun updateQuality(item: Item) =
            (item.sellIn - 1).let {
                Item(
                    item.name, it, quality = when {
                        it < 0 -> 0
                        else -> item.quality.improve(
                            when {
                                it < 5 -> 3
                                it < 10 -> 2
                                else -> 1
                            }
                        )
                    }
                )
            }
    }

    object Sulfuras {
        fun updateQuality(item: Item) = item
    }

}

fun Int.depreciate(step: Int = 1) = (this - step).takeIf { it >= 0 } ?: this
fun Int.improve(step: Int = 1) = (this + step).takeIf { it <= 50 } ?: this
