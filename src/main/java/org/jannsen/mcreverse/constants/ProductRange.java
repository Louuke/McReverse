package org.jannsen.mcreverse.constants;

import java.util.AbstractMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ProductRange {

    public static final Map<Pattern, String> products = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(Pattern.compile("McRib"), "Mäc Rib"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("McFlurry"), "Mäc Flurry"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("Filet-o-Fish"), "Filet Fish"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("Happy Meal"), "(Un)happy Meal"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("McNuggets"), "Mäc Nuggets"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("McPlant"), "Mäc Plant"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("Big Mac"), "Big Mäc"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("Big Tasty"), "Tasty"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("McChicken"), "Mäc Chicken"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("McWrap"), "Mäc Wrap"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("McCrispy"), "Mäc Crispy"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("McSundae"), "Mäc Sundae"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("McDouble"), "Mäc Double"),
            new AbstractMap.SimpleEntry<>(Pattern.compile("McFreezy"), "Mäc Freezy")
    );
}
