package com.gildedrose

open class Item(var name: String, var sellIn: Int, var quality: Int) {
    override fun toString(): String {
        return this.name + ", " + this.sellIn + ", " + this.quality
    }

    open fun advance(): Item {
        sellIn -= 1
        return this
    }


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
}