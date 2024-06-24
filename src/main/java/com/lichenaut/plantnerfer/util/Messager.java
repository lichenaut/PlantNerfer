package com.lichenaut.plantnerfer.util;

import com.lichenaut.plantnerfer.Main;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.apache.logging.log4j.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Getter
public class Messager {

    private final Logger logger;
    private final Main main;
    private final String locale;
    private final String separator;
    private BaseComponent[] prefix;
    private BaseComponent[] helpCommand;
    private BaseComponent[] invalidCommand;
    private BaseComponent[] reloadCommand;
    private BaseComponent[] farmlandIntoDirt;
    private BaseComponent[] plantDroppedNothing;
    private BaseComponent[] plantNeedsSky;
    private BaseComponent[] cannotFollowingBiomes;
    private BaseComponent[] cannotDark;
    private BaseComponent[] cannotBright;
    private BaseComponent[] cannotBelow;
    private BaseComponent[] cannotAbove;
    private BaseComponent[] boneMealSuccessRate;
    private BaseComponent[] boneMealSuccessRateDark;

    public void loadLocaleMessages() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(
                new File(main.getDataFolder(), "locales" + separator + locale + ".properties"))) {
            properties.load(inputStream);
        }
        prefix = getColoredMessage("prefix", properties);
        helpCommand = getColoredMessage("helpCommand", properties);
        invalidCommand = getColoredMessage("invalidCommand", properties);
        reloadCommand = getColoredMessage("reloadCommand", properties);
        farmlandIntoDirt = getColoredMessage("farmlandIntoDirt", properties);
        plantDroppedNothing = getColoredMessage("plantDroppedNothing", properties);
        plantNeedsSky = getColoredMessage("plantNeedsSky", properties);
        cannotFollowingBiomes = getColoredMessage("cannotFollowingBiomes", properties);
        cannotDark = getColoredMessage("cannotDark", properties);
        cannotBright = getColoredMessage("cannotBright", properties);
        cannotBelow = getColoredMessage("cannotBelow", properties);
        cannotAbove = getColoredMessage("cannotAbove", properties);
        boneMealSuccessRate = getColoredMessage("boneMealSuccessRate", properties);
        boneMealSuccessRateDark = getColoredMessage("boneMealSuccessRateDark", properties);
    }

    private BaseComponent[] getColoredMessage(String key, Properties properties) {
        String message = properties.getProperty(key);
        if (message == null) {
            logger.error("Missing message key: {} in locale: {}", key, locale);
            return new BaseComponent[]{new TextComponent("")};
        }

        Pattern pattern = Pattern.compile("<([^>]+)>(.*?)\\s*(?=<[^>]+>|\\z)");
        Matcher matcher = pattern.matcher(message);
        ArrayList<String> resultList = new ArrayList<>();
        while (matcher.find()) {
            resultList.add(matcher.group(2));
            resultList.add(matcher.group(1));
        }
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

    public void sendMsg(CommandSender sender, BaseComponent[] message, boolean includePrefix) {
        if (sender instanceof Player) {
            if (prefix == null || !includePrefix) {
                sender.spigot().sendMessage(message);
            } else {
                sender.spigot().sendMessage(concatArrays(prefix, message));
            }

            return;
        }

        infoLog(message);
    }

    private void infoLog(BaseComponent[] message) {
        logger.info(new TextComponent(message).toLegacyText().replaceAll("§[0-9a-fA-FklmnoKLMNO]", ""));
    }

    public BaseComponent[] concatArrays(BaseComponent[]... arrays) {
        ArrayList<BaseComponent> resultList = new ArrayList<>();
        for (BaseComponent[] array : arrays) {
            resultList.addAll(Arrays.asList(array));
        }

        return resultList.toArray(new BaseComponent[0]);
    }

    public ChatColor getLastColor(BaseComponent[] components) {
        for (int i = components.length - 1; i >= 0; i--) {
            BaseComponent component = components[i];
            if (component.getColor() != null) {
                return component.getColor();
            }
        }

        return ChatColor.WHITE;
    }

    public BaseComponent[] combineMessage(BaseComponent[] message, String messageText) {
        if (messageText == null || messageText.isEmpty()) {
            return message;
        }

        BaseComponent[] textComponent = ComponentSerializer.parse(messageText);
        if (textComponent == null || textComponent.length == 0) {
            return message;
        }

        ChatColor lastColor = getLastColor(message);
        if (lastColor == null) {
            lastColor = ChatColor.WHITE;
        }

        for (BaseComponent component : textComponent) {
            component.setColor(lastColor);
        }
        BaseComponent[] combined = new BaseComponent[message.length + textComponent.length];
        System.arraycopy(message, 0, combined, 0, message.length);
        System.arraycopy(textComponent, 0, combined, message.length, textComponent.length);
        return combined;
    }
}
