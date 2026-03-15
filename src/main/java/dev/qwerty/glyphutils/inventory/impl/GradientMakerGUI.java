package com.qwertydev.glyphutils.inventory.impl;

import com.qwertydev.glyphutils.GlyphUtils;
import com.qwertydev.glyphutils.inventory.InventoryButton;
import com.qwertydev.glyphutils.inventory.InventoryGUI;
import com.qwertydev.glyphutils.session.PlayerSession;
import com.qwertydev.glyphutils.session.SessionType;
import com.qwertydev.glyphutils.util.GradientGenerator;
import com.qwertydev.glyphutils.util.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GradientMakerGUI extends InventoryGUI {
    
    private final GlyphUtils plugin;
    
    public GradientMakerGUI(GlyphUtils plugin) {
        this.plugin = plugin;
    }
    
    @Override
    protected Inventory createInventory() {
        String title = ChatColor.translateAlternateColorCodes('&', 
            plugin.getConfig().getString("messages.gradient-maker-title"));
        return Bukkit.createInventory(null, 54, title);
    }
    
    @Override
    public void decorate(Player player) {
        PlayerSession session = plugin.getSessionManager().getSession(player.getUniqueId());
        
        addButton(10, new InventoryButton()
            .creator(p -> new ItemBuilder(XMaterial.WRITABLE_BOOK)
                .name("&a&lEnter Text")
                .lore("&7Click to type your text", "&7in the chat.", "", "&eClick to input!")
                .build())
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                clicker.closeInventory();
                
                PlayerSession playerSession = plugin.getSessionManager().getSession(clicker.getUniqueId());
                playerSession.setWaitingForInput(true);
                playerSession.setSessionType(SessionType.GRADIENT_TEXT_INPUT);
                
                String enterMsg = ChatColor.translateAlternateColorCodes('&', 
                    plugin.getConfig().getString("messages.enter-text"));
                String cancelMsg = ChatColor.translateAlternateColorCodes('&', 
                    plugin.getConfig().getString("messages.type-cancel"));
                clicker.sendMessage(enterMsg);
                clicker.sendMessage(cancelMsg);
            })
        );
        
        addButton(12, new InventoryButton()
            .creator(p -> {
                PlayerSession s = plugin.getSessionManager().getSession(p.getUniqueId());
                String color = s.getColor1() != null ? s.getColor1() : "Not Set";
                return new ItemBuilder(XMaterial.RED_WOOL)
                    .name("&c&lFirst Color")
                    .lore("&7Current: &f" + color, "", "&7Click to set manually", "&7or use color palette below")
                    .build();
            })
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                ColorPaletteGUI paletteGUI = new ColorPaletteGUI(plugin, true);
                plugin.getGuiManager().openGUI(paletteGUI, clicker);
            })
        );
        
        addButton(14, new InventoryButton()
            .creator(p -> {
                PlayerSession s = plugin.getSessionManager().getSession(p.getUniqueId());
                String color = s.getColor2() != null ? s.getColor2() : "Not Set";
                return new ItemBuilder(XMaterial.BLUE_WOOL)
                    .name("&9&lSecond Color")
                    .lore("&7Current: &f" + color, "", "&7Click to set manually", "&7or use color palette below")
                    .build();
            })
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                ColorPaletteGUI paletteGUI = new ColorPaletteGUI(plugin, false);
                plugin.getGuiManager().openGUI(paletteGUI, clicker);
            })
        );
        
        addButton(28, new InventoryButton()
            .creator(p -> {
                PlayerSession s = plugin.getSessionManager().getSession(p.getUniqueId());
                return new ItemBuilder(s.isBold() ? XMaterial.LIME_DYE : XMaterial.GRAY_DYE)
                    .name(s.isBold() ? "&a&lBold: ON" : "&7&lBold: OFF")
                    .lore("&7Click to toggle")
                    .build();
            })
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                PlayerSession s = plugin.getSessionManager().getSession(clicker.getUniqueId());
                s.setBold(!s.isBold());
                GradientMakerGUI gui = new GradientMakerGUI(plugin);
                plugin.getGuiManager().openGUI(gui, clicker);
            })
        );
        
        addButton(30, new InventoryButton()
            .creator(p -> {
                PlayerSession s = plugin.getSessionManager().getSession(p.getUniqueId());
                return new ItemBuilder(s.isItalic() ? XMaterial.LIME_DYE : XMaterial.GRAY_DYE)
                    .name(s.isItalic() ? "&a&oItalic: ON" : "&7&oItalic: OFF")
                    .lore("&7Click to toggle")
                    .build();
            })
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                PlayerSession s = plugin.getSessionManager().getSession(clicker.getUniqueId());
                s.setItalic(!s.isItalic());
                GradientMakerGUI gui = new GradientMakerGUI(plugin);
                plugin.getGuiManager().openGUI(gui, clicker);
            })
        );
        
        addButton(32, new InventoryButton()
            .creator(p -> {
                PlayerSession s = plugin.getSessionManager().getSession(p.getUniqueId());
                return new ItemBuilder(s.isUnderline() ? XMaterial.LIME_DYE : XMaterial.GRAY_DYE)
                    .name(s.isUnderline() ? "&a&nUnderline: ON" : "&7&nUnderline: OFF")
                    .lore("&7Click to toggle")
                    .build();
            })
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                PlayerSession s = plugin.getSessionManager().getSession(clicker.getUniqueId());
                s.setUnderline(!s.isUnderline());
                GradientMakerGUI gui = new GradientMakerGUI(plugin);
                plugin.getGuiManager().openGUI(gui, clicker);
            })
        );
        
        addButton(34, new InventoryButton()
            .creator(p -> {
                PlayerSession s = plugin.getSessionManager().getSession(p.getUniqueId());
                return new ItemBuilder(s.isStrikethrough() ? XMaterial.LIME_DYE : XMaterial.GRAY_DYE)
                    .name(s.isStrikethrough() ? "&a&mStrikethrough: ON" : "&7&mStrikethrough: OFF")
                    .lore("&7Click to toggle")
                    .build();
            })
            .consumer(event -> {
                Player clicker = (Player) event.getWhoClicked();
                PlayerSession s = plugin.getSessionManager().getSession(clicker.getUniqueId());
                s.setStrikethrough(!s.isStrikethrough());
                GradientMakerGUI gui = new GradientMakerGUI(plugin);
                plugin.getGuiManager().openGUI(gui, clicker);
            })
        );
        
        if (session.getInputText() != null && session.getColor1() != null && session.getColor2() != null) {
            String gradient = GradientGenerator.generate(
                session.getInputText(), 
                session.getColor1(), 
                session.getColor2(),
                session.isBold(),
                session.isItalic(),
                session.isUnderline(),
                session.isStrikethrough()
            );
            
            addButton(16, new InventoryButton()
                .creator(p -> new ItemBuilder(XMaterial.ENCHANTED_BOOK)
                    .name("&d&lGenerate Gradient")
                    .lore("&7Click to generate and", "&7copy your gradient text!", "", "&eClick to generate!")
                    .build())
                .consumer(event -> {
                    Player clicker = (Player) event.getWhoClicked();
                    clicker.closeInventory();
                    
                    String copyMsg = ChatColor.translateAlternateColorCodes('&', 
                        plugin.getConfig().getString("messages.gradient-generated"));
                    clicker.sendMessage(copyMsg);
                    clicker.sendMessage(gradient);
                })
            );
        }
        
        addButton(49, new InventoryButton()
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