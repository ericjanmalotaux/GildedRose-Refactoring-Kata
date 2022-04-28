package com.gildedrose

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    private val gildedRose = GildedRose(
        arrayOf(
            Item("+5 Dexterity Vest", 10, 20), //
            Item("Aged Brie", 2, 0), //
            Item("Elixir of the Mongoose", 5, 7), //
            Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            Item("Sulfuras, Hand of Ragnaros", -1, 80),
            Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            Item("Conjured Mana Cake", 3, 6)
        )
    )

    @Test
    fun allItemsHaveASellInValue() {
        gildedRose.items.forEach {
            assertNotNull(it.sellIn)
        }

    }

    @Test
    fun qualityIsNeverNegative() {
        (gildedRose.items.map { it.sellIn }.maxOrNull()!! downTo -3).forEach {
            gildedRose.updateQuality()
            gildedRose.items.forEach {
                assertTrue(it.quality >= 0, "The quality of an item is never negative")
            }
        }
    }

    @Test
    fun qualityIsNeverMoreThanFifty() {
        val notSulfuras = gildedRose.items.filterNot { it.name.startsWith("Sulfuras") }
        (gildedRose.items.map { it.sellIn }.maxOrNull()!! downTo -3).forEach {
            gildedRose.updateQuality()
            assertFalse(notSulfuras.map { it.quality }.maxOrNull()!! > 50)
        }
    }

    @Test
    fun ordinaryItemsDecreaseInSellIn() {
        gildedRose.elixir
        while (gildedRose.elixir.sellIn >= 0) {
            val sellIn = gildedRose.elixir.sellIn
            gildedRose.updateQuality()
            assertEquals(sellIn - 1, gildedRose.elixir.sellIn)
        }
    }

    @Test
    fun ordinaryItemsDecreaseInQualityUntilZero() {
        while (gildedRose.elixir.quality != 0) {
            val quality = gildedRose.elixir.quality
            gildedRose.updateQuality()
            assertTrue(gildedRose.elixir.quality < quality)
        }
        gildedRose.updateQuality()
        assertEquals(0, gildedRose.elixir.quality)
    }

    @Test
    fun afterSellByDateHasPassedQualityDecreasesTwiceAsFase() {
        while (gildedRose.elixir.sellIn > 0) {
            val quality = gildedRose.elixir.quality
            gildedRose.updateQuality()
            assertEquals(quality - 1, gildedRose.elixir.quality)
        }
        val quality = gildedRose.elixir.quality
        gildedRose.updateQuality()
        assertEquals(quality - 2, gildedRose.elixir.quality)
        while (gildedRose.elixir.sellIn > -3) {
            gildedRose.updateQuality()
            assertEquals(0, gildedRose.elixir.quality)
        }
    }

    @Test
    fun agedBrieIncreasesInQuality() {
        while (gildedRose.brie.quality < 50) {
            val quality = gildedRose.brie.quality
            gildedRose.updateQuality()
            assertTrue(gildedRose.brie.quality > quality)
        }
        gildedRose.updateQuality()
        assertTrue(gildedRose.brie.quality == 50)
    }

    @Test
    fun sulfurasNeverHasToBeSoldOrDecreasesInQuality() {
        val sulfuras = gildedRose.sulfuras
        val quality = sulfuras.quality
        val sellIn = sulfuras.sellIn
        gildedRose.updateQuality()
        assertEquals(quality, gildedRose.sulfuras.quality)
        assertEquals(sellIn, gildedRose.sulfuras.sellIn)
    }

    private val GildedRose.sulfuras get() = items.first { it.name.startsWith("Sulfuras") }

    @Test
    fun backstagePassesAreDifferent() {
        gildedRose.backstagePass
        while (gildedRose.backstagePass.sellIn > 10) {
            val quality = gildedRose.backstagePass.quality
            gildedRose.updateQuality()
            assertEquals(quality + 1, gildedRose.backstagePass.quality)
        }
        while (gildedRose.backstagePass.sellIn > 5) {
            val quality = gildedRose.backstagePass.quality
            gildedRose.updateQuality()
            assertEquals(quality + 2, gildedRose.backstagePass.quality)
        }
        while (gildedRose.backstagePass.sellIn > 0) {
            val quality = gildedRose.backstagePass.quality
            gildedRose.updateQuality()
            assertEquals(quality + 3, gildedRose.backstagePass.quality)
        }
        gildedRose.updateQuality()
        assertEquals(-1, gildedRose.backstagePass.sellIn)
        assertEquals(0, gildedRose.backstagePass.quality)
    }

    private val GildedRose.backstagePass get() = items.first { it.name.startsWith("Backstage") }
    private val GildedRose.brie get() = items.first { it.name == "Aged Brie" }
    private val GildedRose.elixir get() = items.first { it.name.startsWith("Elixir") }
}
