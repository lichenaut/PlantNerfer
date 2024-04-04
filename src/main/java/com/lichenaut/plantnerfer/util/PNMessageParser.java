package com.lichenaut.plantnerfer.util;

import com.lichenaut.plantnerfer.PlantNerfer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PNMessageParser {

    private final PlantNerfer plugin;
    private String locale;
    private BaseComponent[] helpCommand;
    private BaseComponent[] invalidCommand;
    private BaseComponent[] reloadCommand;
    private BaseComponent[] farmlandIntoDirt;
    private BaseComponent[] plantDroppedNothing;
    private BaseComponent[] cannotPlaceAnyBiome;
    private BaseComponent[] tryOtherBiomes;
    private BaseComponent[] plantNeedsSky;
    private BaseComponent[] cannotPlaceFollowingBiomes;
    private BaseComponent[] tryFollowingBiomes;
    private BaseComponent[] cannotPlaceDark;
    private BaseComponent[] cannotPlaceBright;
    private BaseComponent[] cannotPlaceBelow;
    private BaseComponent[] cannotPlaceAbove;
    private BaseComponent[] cannotPlaceSpecific;
    private BaseComponent[] boneMealSuccessRate;
    private BaseComponent[] boneMealSuccessRateDark;

    public PNMessageParser(PlantNerfer plugin) throws IOException {this.plugin = plugin;}

    public void loadLocaleMessages() throws IOException {
        locale = plugin.getConfig().getString("locale");
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(new File(plugin.getDataFolder(), "locales" + PNSep.getSep() + locale + ".properties"))) {properties.load(inputStream);}
        helpCommand = getColoredMessage("helpCommand", properties);
        invalidCommand = getColoredMessage("invalidCommand", properties);
        reloadCommand = getColoredMessage("reloadCommand", properties);
        farmlandIntoDirt = getColoredMessage("farmlandIntoDirt", properties);
        plantDroppedNothing = getColoredMessage("plantDroppedNothing", properties);
        cannotPlaceAnyBiome = getColoredMessage("cannotPlaceAnyBiome", properties);
        tryOtherBiomes = getColoredMessage("tryOtherBiomes", properties);
        plantNeedsSky = getColoredMessage("plantNeedsSky", properties);
        cannotPlaceFollowingBiomes = getColoredMessage("cannotPlaceFollowingBiomes", properties);
        tryFollowingBiomes = getColoredMessage("tryFollowingBiomes", properties);
        cannotPlaceDark = getColoredMessage("cannotPlaceDark", properties);
        cannotPlaceBright = getColoredMessage("cannotPlaceBright", properties);
        cannotPlaceBelow = getColoredMessage("cannotPlaceBelow", properties);
        cannotPlaceAbove = getColoredMessage("cannotPlaceAbove", properties);
        cannotPlaceSpecific = getColoredMessage("cannotPlaceSpecific", properties);
        boneMealSuccessRate = getColoredMessage("boneMealSuccessRate", properties);
        boneMealSuccessRateDark = getColoredMessage("boneMealSuccessRateDark", properties);
    }

    private BaseComponent[] getColoredMessage(String key, Properties properties) {
        String message = properties.getProperty(key);
        if (message == null) {
            plugin.getLog().warning("Missing message key: " + key + " in locale: " + locale);
            return new BaseComponent[]{new TextComponent("")};
        }

        Pattern pattern = Pattern.compile("<([^>]+)>(.*?)\\s*(?=<[^>]+>|\\z)");
        Matcher matcher = pattern.matcher(message);
        ArrayList<String> resultList = new ArrayList<>();
        while (matcher.find()) {resultList.add(matcher.group(2));resultList.add(matcher.group(1));}
        String[] resultArray = resultList.toArray(new String[0]);

        ComponentBuilder builder = new ComponentBuilder("");
        for (String part : resultArray) {
            switch (part.toLowerCase()) {
                case "aqua":
                    builder.color(ChatColor.AQUA);
                    break;
                case "black":
                    builder.color(ChatColor.BLACK);
                    break;
                case "blue":
                    builder.color(ChatColor.BLUE);
                    break;
                case "bold":
                    builder.bold(true);
                    break;
                case "dark_aqua":
                    builder.color(ChatColor.DARK_AQUA);
                    break;
                case "dark_blue":
                    builder.color(ChatColor.DARK_BLUE);
                    break;
                case "dark_gray":
                    builder.color(ChatColor.DARK_GRAY);
                    break;
                case "dark_green":
                    builder.color(ChatColor.DARK_GREEN);
                    break;
                case "dark_purple":
                    builder.color(ChatColor.DARK_PURPLE);
                    break;
                case "dark_red":
                    builder.color(ChatColor.DARK_RED);
                    break;
                case "gold":
                    builder.color(ChatColor.GOLD);
                    break;
                case "gray":
                    builder.color(ChatColor.GRAY);
                    break;
                case "green":
                    builder.color(ChatColor.GREEN);
                    break;
                case "italic":
                    builder.italic(true);
                    break;
                case "light_purple":
                    builder.color(ChatColor.LIGHT_PURPLE);
                    break;
                case "magic":
                    builder.obfuscated(true);
                    break;
                case "red":
                    builder.color(ChatColor.RED);
                    break;
                case "reset":
                    builder.reset();
                    break;
                case "strikethrough":
                    builder.strikethrough(true);
                    break;
                case "underline":
                    builder.underlined(true);
                    break;
                case "white":
                    builder.color(ChatColor.WHITE);
                    break;
                case "yellow":
                    builder.color(ChatColor.YELLOW);
                    break;
                default:
                    builder.append(part);
                    break;
            }
        }
        builder.append(" ");
        return builder.create();
    }

    public BaseComponent[] getHelpCommand() {return helpCommand;}
    public BaseComponent[] getInvalidCommand() {return invalidCommand;}
    public BaseComponent[] getReloadCommand() {return reloadCommand;}
    public BaseComponent[] getFarmlandIntoDirt() {return farmlandIntoDirt;}
    public BaseComponent[] getPlantDroppedNothing() {return plantDroppedNothing;}
    public BaseComponent[] getCannotPlaceAnyBiome() {return cannotPlaceAnyBiome;}
    public BaseComponent[] getTryOtherBiomes() {return tryOtherBiomes;}
    public BaseComponent[] getPlantNeedsSky() {return plantNeedsSky;}
    public BaseComponent[] getCannotPlaceFollowingBiomes() {return cannotPlaceFollowingBiomes;}
    public BaseComponent[] getTryFollowingBiomes() {return tryFollowingBiomes;}
    public BaseComponent[] getCannotPlaceDark() {return cannotPlaceDark;}
    public BaseComponent[] getCannotPlaceBright() {return cannotPlaceBright;}
    public BaseComponent[] getCannotPlaceBelow() {return cannotPlaceBelow;}
    public BaseComponent[] getCannotPlaceAbove() {return cannotPlaceAbove;}
    public BaseComponent[] getCannotPlaceSpecific() {return cannotPlaceSpecific;}
    public BaseComponent[] getBoneMealSuccessRate() {return boneMealSuccessRate;}
    public BaseComponent[] getBoneMealSuccessRateDark() {return boneMealSuccessRateDark;}
    public net.md_5.bungee.api.ChatColor getLastColor(BaseComponent[] components) {
        for (int i = components.length - 1; i >= 0; i--) {
            BaseComponent component = components[i];
            if (component.getColor() != null) return component.getColor();
        }
        return net.md_5.bungee.api.ChatColor.WHITE;
    }

    public BaseComponent[] combineMessage(BaseComponent[] message, String messageText) {
        if (messageText == null || messageText.isEmpty()) {return message;}

        BaseComponent[] textComponent = TextComponent.fromLegacyText(messageText);
        if (textComponent == null || textComponent.length == 0) return message;

        net.md_5.bungee.api.ChatColor lastColor = getLastColor(message);
        if (lastColor == null) lastColor = net.md_5.bungee.api.ChatColor.WHITE;
        for (BaseComponent component : textComponent) component.setColor(lastColor);
        BaseComponent[] combined = new BaseComponent[message.length + textComponent.length];
        System.arraycopy(message, 0, combined, 0, message.length);
        System.arraycopy(textComponent, 0, combined, message.length, textComponent.length);
        return combined;
    }
}
