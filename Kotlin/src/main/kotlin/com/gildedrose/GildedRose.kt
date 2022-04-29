package com.gildedrose

import java.lang.Integer.*

private const val QUALITY_MIH = 0
private const val QUALITY_MAX = 50
private fun Int.degrade(step: Int = 1) = min(max(this - step, QUALITY_MIH), QUALITY_MAX)

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

    abstract class AbstractStrategy {
        open fun Item.advance() = sellIn - 1
        open fun updateQuality(item: Item) =
            item.advance()
                .takeIf { sellIn -> sellIn != item.sellIn }
                ?.let { Item(item.name, it, item.quality.degrade(degradation(it))) }
                ?: item

        open fun degradation(sellIn: Int) = 0
    }

    private object Normal : AbstractStrategy() {
        override fun degradation(sellIn: Int) = if (sellIn >= 0) 1 else 2
    }

    private object Brie : AbstractStrategy() {
        override fun degradation(sellIn: Int) = if (sellIn >= 0) -1 else -2
    }

    private object BackstagePass : AbstractStrategy() {
        override fun updateQuality(item: Item) =
            item.advance().let {
                Item(item.name, it, if (it < 0) 0 else item.quality.degrade(degradation(it)))
            }

        override fun degradation(sellIn: Int) = when {
            sellIn < 5 -> -3
            sellIn < 10 -> -2
            else -> -1
        }
    }

    private object Sulfuras : AbstractStrategy() {
        override fun Item.advance() = 0
    }

    private object Conjured : AbstractStrategy() {
        override fun degradation(sellIn: Int) = if (sellIn >= 0) 2 else 4
    }
}
