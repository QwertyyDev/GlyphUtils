package com.qwertydev.glyphutils.inventory.impl;

import com.qwertydev.glyphutils.GlyphUtils;
import com.qwertydev.glyphutils.inventory.InventoryButton;
import com.qwertydev.glyphutils.inventory.InventoryGUI;
import com.qwertydev.glyphutils.util.ChatComponentUtil;
import com.qwertydev.glyphutils.util.ItemBuilder;
import com.qwertydev.glyphutils.util.SymbolRegistry;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SymbolCopierGUI extends InventoryGUI {
    
    private final GlyphUtils plugin;
    
    public SymbolCopierGUI(GlyphUtils plugin) {
        this.plugin = plugin;
    }
    
    @Override
    protected Inventory createInventory() {
        String title = ChatColor.translateAlternateColorCodes('&', 
            plugin.getConfig().getString("messages.symbol-copier-title"));
        return Bukkit.createInventory(null, 27, title);
    }
    
    @Override
    public void decorate(Player player) {
        addButton(11, new InventoryButton()
            .creator(p -> new ItemBuilder(XMaterial.SUNFLOWER)
                .name("&e&lEmojis")
                .lore("&7Browse and copy emojis", "&7directly from chat.", "", "&eClick to open!")
                .build())
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.closeInventory();
                
                clicker.sendMessage("");
                ChatComponentUtil.sendSymbolList(clicker, SymbolRegistry.getEmojis(), "Emojis");
                clicker.sendMessage(ChatColor.GRAY + "Type " + ChatColor.YELLOW + "/utils" + ChatColor.GRAY + " to return to the menu");
                clicker.sendMessage("");
            })
        );
        
        addButton(15, new InventoryButton()
            .creator(p -> new ItemBuilder(XMaterial.NETHER_STAR)
                .name("&b&lSymbols")
                .lore("&7Browse and copy special", "&7symbols from chat.", "", "&eClick to open!")
                .build())
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.closeInventory();
                
                clicker.sendMessage("");
                ChatComponentUtil.sendSymbolList(clicker, SymbolRegistry.getSymbols(), "Special Symbols");
                clicker.sendMessage(ChatColor.GRAY + "Type " + ChatColor.YELLOW + "/utils" + ChatColor.GRAY + " to return to the menu");
                clicker.sendMessage("");
            })
        );
        
        addButton(22, new InventoryButton()
            .creator(p -> new ItemBuilder(XMaterial.ARROW)
                .name("&c&lBack to Main Menu")
                .build())
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                MainMenuGUI mainMenu = new MainMenuGUI(plugin);
                plugin.getGuiManager().openGUI(mainMenu, clicker);
            })
        );
        
        super.decorate(player);
    }
}