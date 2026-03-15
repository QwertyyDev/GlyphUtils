package com.qwertydev.glyphutils.inventory.impl;

import com.qwertydev.glyphutils.GlyphUtils;
import com.qwertydev.glyphutils.inventory.InventoryButton;
import com.qwertydev.glyphutils.inventory.InventoryGUI;
import com.qwertydev.glyphutils.session.PlayerSession;
import com.qwertydev.glyphutils.util.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ColorPaletteGUI extends InventoryGUI {
    
    private final GlyphUtils plugin;
    private final boolean isFirstColor;
    
    private static final String[][] COLORS = {
        {"&#FF0000", "&#FF3300", "&#FF6600", "&#FF9900", "&#FFCC00", "&#FFFF00", "&#CCFF00", "&#99FF00", "&#66FF00"},
        {"&#33FF00", "&#00FF00", "&#00FF33", "&#00FF66", "&#00FF99", "&#00FFCC", "&#00FFFF", "&#00CCFF", "&#0099FF"},
        {"&#0066FF", "&#0033FF", "&#0000FF", "&#3300FF", "&#6600FF", "&#9900FF", "&#CC00FF", "&#FF00FF", "&#FF00CC"},
        {"&#FF0099", "&#FF0066", "&#FF0033", "&#FFFFFF", "&#CCCCCC", "&#999999", "&#666666", "&#333333", "&#000000"}
    };
    
    public ColorPaletteGUI(GlyphUtils plugin, boolean isFirstColor) {
        this.plugin = plugin;
        this.isFirstColor = isFirstColor;
    }
    
    @Override
    protected Inventory createInventory() {
        String title = ChatColor.translateAlternateColorCodes('&', 
            plugin.getConfig().getString("messages.color-palette-title"));
        return Bukkit.createInventory(null, 54, title);
    }
    
    @Override
    public void decorate(Player player) {
        int slot = 10;
        for (int row = 0; row < COLORS.length; row++) {
            for (int col = 0; col < COLORS[row].length; col++) {
                final String color = COLORS[row][col];
                XMaterial material = getWoolColor(row, col);
                
                addButton(slot, new InventoryButton()
                    .creator(p -> new ItemBuilder(material)
                        .name("&f" + color)
                        .lore("&7Click to select this color")
                        .build())
                    .consumer(event -> {
                        Player clicker = (Player) event.getWhoClicked();
                        PlayerSession session = plugin.getSessionManager().getSession(clicker.getUniqueId());
                        
                        if (isFirstColor) {
                            session.setColor1(color);
                        } else {
                            session.setColor2(color);
                        }
                        
                        GradientMakerGUI gradientGUI = new GradientMakerGUI(plugin);
                        plugin.getGuiManager().openGUI(gradientGUI, clicker);
                    })
                );
                
                slot++;
            }
            slot += 2;
        }
        
        addButton(49, new InventoryButton()
            .creator(p -> new ItemBuilder(XMaterial.ARROW)
                .name("&c&lBack")
                .build())
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                GradientMakerGUI gradientGUI = new GradientMakerGUI(plugin);
                plugin.getGuiManager().openGUI(gradientGUI, clicker);
            })
        );
        
        super.decorate(player);
    }
    
    private XMaterial getWoolColor(int row, int col) {
        if (row == 0) {
            if (col < 3) return XMaterial.RED_WOOL;
            if (col < 6) return XMaterial.ORANGE_WOOL;
            return XMaterial.YELLOW_WOOL;
        } else if (row == 1) {
            if (col < 3) return XMaterial.LIME_WOOL;
            if (col < 6) return XMaterial.GREEN_WOOL;
            return XMaterial.CYAN_WOOL;
        } else if (row == 2) {
            if (col < 3) return XMaterial.LIGHT_BLUE_WOOL;
            if (col < 6) return XMaterial.BLUE_WOOL;
            return XMaterial.PURPLE_WOOL;
        } else {
            if (col < 3) return XMaterial.PINK_WOOL;
            if (col < 6) return XMaterial.WHITE_WOOL;
            return XMaterial.BLACK_WOOL;
        }
    }
}