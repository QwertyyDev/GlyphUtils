package com.qwertydev.glyphutils.inventory.impl;

import com.qwertydev.glyphutils.GlyphUtils;
import com.qwertydev.glyphutils.inventory.InventoryButton;
import com.qwertydev.glyphutils.inventory.InventoryGUI;
import com.qwertydev.glyphutils.session.PlayerSession;
import com.qwertydev.glyphutils.session.SessionType;
import com.qwertydev.glyphutils.util.ItemBuilder;
import com.qwertydev.glyphutils.util.SmallCapsConverter;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SmallCapsGUI extends InventoryGUI {
    
    private final GlyphUtils plugin;
    
    public SmallCapsGUI(GlyphUtils plugin) {
        this.plugin = plugin;
    }
    
    @Override
    protected Inventory createInventory() {
        String title = ChatColor.translateAlternateColorCodes('&', 
            plugin.getConfig().getString("messages.small-caps-title"));
        return Bukkit.createInventory(null, 27, title);
    }
    
    @Override
    public void decorate(Player player) {
        PlayerSession session = plugin.getSessionManager().getSession(player.getUniqueId());
        
        addButton(11, new InventoryButton()
            .creator(p -> new ItemBuilder(XMaterial.WRITABLE_BOOK)
                .name("&a&lEnter Text")
                .lore("&7Click to type your text", "&7in the chat.", "", "&eClick to input!")
                .build())
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.closeInventory();
                
                PlayerSession playerSession = plugin.getSessionManager().getSession(clicker.getUniqueId());
                playerSession.setWaitingForInput(true);
                playerSession.setSessionType(SessionType.SMALL_CAPS_INPUT);
                
                String enterMsg = ChatColor.translateAlternateColorCodes('&', 
                    plugin.getConfig().getString("messages.enter-text"));
                String cancelMsg = ChatColor.translateAlternateColorCodes('&', 
                    plugin.getConfig().getString("messages.type-cancel"));
                clicker.sendMessage(enterMsg);
                clicker.sendMessage(cancelMsg);
            })
        );
        
        if (session.getInputText() != null) {
            String converted = SmallCapsConverter.convert(session.getInputText());
            
            addButton(13, new InventoryButton()
                .creator(p -> new ItemBuilder(XMaterial.PAPER)
                    .name("&b&lConverted Text")
                    .lore("&7Original: &f" + session.getInputText(), 
                          "&7Result: &f" + converted, 
                          "", 
                          "&eClick to copy!")
                    .build())
                .consumer(event -> {
                    Player clicker = (Player) event.getWhoClicked();
                    clicker.closeInventory();
                    
                    String copyMsg = ChatColor.translateAlternateColorCodes('&', 
                        plugin.getConfig().getString("messages.text-copied"));
                    clicker.sendMessage(copyMsg);
                    clicker.sendMessage(converted);
                })
            );
        }
        
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