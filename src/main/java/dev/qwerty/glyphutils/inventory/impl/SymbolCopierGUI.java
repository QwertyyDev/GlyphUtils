package com.qwertydev.glyphutils.inventory.impl;

import com.qwertydev.glyphutils.GlyphUtils;
import com.qwertydev.glyphutils.inventory.InventoryButton;
import com.qwertydev.glyphutils.inventory.InventoryGUI;
import com.qwertydev.glyphutils.util.ItemBuilder;
import com.qwertydev.glyphutils.util.SymbolRegistry;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import java.util.List;

public class SymbolCopierGUI extends InventoryGUI {
    
    private final GlyphUtils plugin;
    private final int page;
    private static final int SYMBOLS_PER_PAGE = 45;
    
    public SymbolCopierGUI(GlyphUtils plugin, int page) {
        this.plugin = plugin;
        this.page = page;
    }
    
    @Override
    protected Inventory createInventory() {
        String title = ChatColor.translateAlternateColorCodes('&', 
            plugin.getConfig().getString("messages.symbol-copier-title"));
        return Bukkit.createInventory(null, 54, title + " §8(Page " + (page + 1) + ")");
    }
    
    @Override
    public void decorate(Player player) {
        List<String> allSymbols = SymbolRegistry.getAllSymbols();
        int totalPages = (int) Math.ceil((double) allSymbols.size() / SYMBOLS_PER_PAGE);
        
        int startIndex = page * SYMBOLS_PER_PAGE;
        int endIndex = Math.min(startIndex + SYMBOLS_PER_PAGE, allSymbols.size());
        
        int slot = 0;
        for (int i = startIndex; i < endIndex; i++) {
            final String symbol = allSymbols.get(i);
            
            addButton(slot, new InventoryButton()
                .creator(p -> new ItemBuilder(XMaterial.PAPER)
                    .name("&e" + symbol)
                    .lore("&7Click to copy this symbol")
                    .build())
                .consumer(event -> {
                    Player clicker = (Player) event.getWhoClicked();
                    clicker.closeInventory();
                    
                    String copyMsg = ChatColor.translateAlternateColorCodes('&', 
                        plugin.getConfig().getString("messages.text-copied"));
                    clicker.sendMessage(copyMsg);
                    clicker.sendMessage(symbol);
                })
            );
            
            slot++;
        }
        
        if (page > 0) {
            addButton(48, new InventoryButton()
                .creator(p -> new ItemBuilder(XMaterial.ARROW)
                    .name("&a&lPrevious Page")
                    .build())
                .consumer(event -> {
                    Player clicker = (Player) event.getWhoClicked();
                    SymbolCopierGUI prevPage = new SymbolCopierGUI(plugin, page - 1);
                    plugin.getGuiManager().openGUI(prevPage, clicker);
                })
            );
        }
        
        addButton(49, new InventoryButton()
            .creator(p -> new ItemBuilder(XMaterial.BARRIER)
                .name("&c&lBack to Main Menu")
                .build())
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                MainMenuGUI mainMenu = new MainMenuGUI(plugin);
                plugin.getGuiManager().openGUI(mainMenu, clicker);
            })
        );
        
        if (page < totalPages - 1) {
            addButton(50, new InventoryButton()
                .creator(p -> new ItemBuilder(XMaterial.ARROW)
                    .name("&a&lNext Page")
                    .build())
                .consumer(event -> {
                    Player clicker = (Player) event.getWhoClicked();
                    SymbolCopierGUI nextPage = new SymbolCopierGUI(plugin, page + 1);
                    plugin.getGuiManager().openGUI(nextPage, clicker);
                })
            );
        }
        
        super.decorate(player);
    }
}