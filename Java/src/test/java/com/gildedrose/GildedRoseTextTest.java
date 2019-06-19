package com.gildedrose;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class GildedRoseTextTest {


    private static GildedRose app = new GildedRose(
            new Item("+5 Dexterity Vest", 10, 20), //
            new Item("Aged Brie", 2, 0), //
            new Item("Elixir of the Mongoose", 5, 7), //
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            new Item("Conjured Mana Cake", 3, 6));

    public static void main(String... args) throws IOException, URISyntaxException {
        Files.write(Paths.get(new URL(GildedRoseTextTest.class.getResource("/"), "gold.txt").toURI()), createOutput(app).getBytes());
    }

    @Test
    public void regression() throws URISyntaxException, IOException {
        String output = createOutput(app);
        String gold = new String(Files.readAllBytes(Paths.get(getClass().getResource("/gold.txt").toURI())));
        assertThat(output).isEqualTo(gold);
    }

    private static String createOutput(GildedRose app) {
        StringBuilder output = new StringBuilder();
        IntStream.range(0, 32).forEach(i -> {
            output
                    .append("-------- day ").append(i).append(" --------\n")
                    .append("name, sellIn, quality\n")
                    .append(Arrays.stream(app.items).map(Item::toString).collect(Collectors.joining("\n")))
                    .append("\n");
            app.updateQuality();
        });
        return output.toString();
    }


}
