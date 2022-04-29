package com.gildedrose

private const val QUALITY_MIH = 0
private const val QUALITY_MAX = 50
private fun Int.degrade(step: Int = 1) = (this - step).takeIf { it >= QUALITY_MIH } ?: QUALITY_MIH
private fun Int.improve(step: Int = 1) = (this + step).takeIf { it <= QUALITY_MAX } ?: QUALITY_MAX

class GildedRose(var items: Array<Item>) {

    private val strategies = mapOf(
        "Aged Brie" to Brie::updateQuality,
        "Backstage passes to a TAFKAL80ETC concert" to BackstagePass::updateQuality,
        "Sulfuras, Hand of Ragnaros" to Sulfuras::updateQuality,
        "Conjured Mana Cake" to Conjured::updateQuality,
    )

    fun updateQuality() {
        items = items.map { (strategies[it.name] ?: Normal::updateQuality).invoke(it) }.toTypedArray()
    }

    private object Normal {
        fun updateQuality(item: Item) =
            (item.sellIn - 1).let { Item(item.name, it, item.quality.degrade(if (it >= QUALITY_MIH) 1 else 2)) }
    }

    private object Brie {
        fun updateQuality(item: Item) =
            (item.sellIn - 1).let { Item(item.name, it, item.quality.improve(if (it >= QUALITY_MIH) 1 else 2)) }
    }

    private object BackstagePass {
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

    private object Sulfuras {
        fun updateQuality(item: Item) = item
    }

    private object Conjured {
        fun updateQuality(item: Item) =
            (item.sellIn - 1).let { Item(item.name, it, item.quality.degrade(if (it >= QUALITY_MIH) 2 else 4)) }
    }
}
