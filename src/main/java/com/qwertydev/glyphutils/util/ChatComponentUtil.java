package com.qwertydev.glyphutils.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class ChatComponentUtil {
    
    public static TextComponent createCopyableText(String text, String displayText) {
        TextComponent component = new TextComponent(displayText);
        component.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
            new Text(ChatColor.GREEN + "Click to copy!")));
        return component;
    }
    
    public static TextComponent createColorSquare(String hexColor, String command) {
        String cleanHex = hexColor.replace("&#", "#");
        ChatColor color = ChatColor.of(cleanHex);
        
        TextComponent square = new TextComponent("█ ");
        square.setColor(color);
        square.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        square.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
            new Text(ChatColor.WHITE + "Color: " + hexColor + "\n" + ChatColor.GRAY + "Click to select")));
        
        return square;
    }
    
    public static TextComponent createClickableSymbol(String symbol, String copyText) {
        TextComponent component = new TextComponent(symbol + " ");
        component.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, copyText));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
            new Text(ChatColor.GREEN + "Click to copy: " + ChatColor.WHITE + symbol)));
        return component;
    }
    
    public static void sendColorPalette(org.bukkit.entity.Player player, boolean isFirstColor) {
        String[][] colors = {
            {"&#FF0000", "&#FF3300", "&#FF6600", "&#FF9900", "&#FFCC00", "&#FFFF00", "&#CCFF00", "&#99FF00", "&#66FF00"},
            {"&#33FF00", "&#00FF00", "&#00FF33", "&#00FF66", "&#00FF99", "&#00FFCC", "&#00FFFF", "&#00CCFF", "&#0099FF"},
            {"&#0066FF", "&#0033FF", "&#0000FF", "&#3300FF", "&#6600FF", "&#9900FF", "&#CC00FF", "&#FF00FF", "&#FF00CC"},
            {"&#FF0099", "&#FF0066", "&#FF0033", "&#FFFFFF", "&#CCCCCC", "&#999999", "&#666666", "&#333333", "&#000000"}
        };
        
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Select a color by clicking:");
        player.sendMessage("");
        
        for (String[] row : colors) {
            ComponentBuilder builder = new ComponentBuilder();
            for (String color : row) {
                String cmd = "/glyphutils-selectcolor " + (isFirstColor ? "1" : "2") + " " + color;
                builder.append(createColorSquare(color, cmd));
            }
            player.spigot().sendMessage(builder.create());
        }
        
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "Or type the hex code directly in chat (e.g., #FF5555)");
    }
    
    public static void sendSymbolList(org.bukkit.entity.Player player, java.util.List<String> symbols, String title) {
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + title);
        player.sendMessage(ChatColor.GRAY + "Click any symbol to copy it:");
        player.sendMessage("");
        
        ComponentBuilder builder = new ComponentBuilder();
        int count = 0;
        
        for (String symbol : symbols) {
            builder.append(createClickableSymbol(symbol, symbol));
            count++;
            
            if (count % 15 == 0) {
                player.spigot().sendMessage(builder.create());
                builder = new ComponentBuilder();
            }
        }
        
        if (count % 15 != 0) {
            player.spigot().sendMessage(builder.create());
        }
        
        player.sendMessage("");
    }
}