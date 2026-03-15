package com.qwertydev.glyphutils.inventory.impl;

import com.qwertydev.glyphutils.GlyphUtils;
import com.qwertydev.glyphutils.inventory.InventoryButton;
import com.qwertydev.glyphutils.inventory.InventoryGUI;
import com.qwertydev.glyphutils.util.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainMenuGUI extends InventoryGUI {
    
    private final GlyphUtils plugin;
    
    public MainMenuGUI(GlyphUtils plugin) {
        this.plugin = plugin;
    }
    
    @Override
    protected Inventory createInventory() {
        String title = ChatColor.translateAlternateColorCodes('&', 
            plugin.getConfig().getString("messages.main-menu-title"));
        return Bukkit.createInventory(null, 27, title);
    }
    
    @Override
    public void decorate(Player player) {
        addButton(11, new InventoryButton()
            .creator(p -> new ItemBuilder(XMaterial.PAPER)
                .name("&b&lSmall Caps Converter")
                .lore("&7Convert your text into", "&7small caps Unicode characters.", "", "&eClick to open!")
                .build())
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                SmallCapsGUI smallCapsGUI = new SmallCapsGUI(plugin);
                plugin.getGuiManager().openGUI(smallCapsGUI, clicker);
            })
        );
        
        addButton(13, new InventoryButton()
            .creator(p -> new ItemBuilder(XMaterial.WRITABLE_BOOK)
                .name("&d&lGradient Text Maker")
                .lore("&7Create beautiful gradient", "&7colored text with custom colors.", "", "&eClick to open!")
                .build())
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                GradientMakerGUI gradientGUI = new GradientMakerGUI(plugin);
                plugin.getGuiManager().openGUI(gradientGUI, clicker);
            })
        );
        
        addButton(15, new InventoryButton()
            .creator(p -> new ItemBuilder(XMaterial.NETHER_STAR)
                .name("&e&lEmoji & Symbol Copier")
                .lore("&7Browse and copy emojis", "&7and special symbols.", "", "&eClick to open!")
                .build())
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                SymbolCopierGUI symbolGUI = new SymbolCopierGUI(plugin);
                plugin.getGuiManager().openGUI(symbolGUI, clicker);
            })
        );
        
        super.decorate(player);
    }
}