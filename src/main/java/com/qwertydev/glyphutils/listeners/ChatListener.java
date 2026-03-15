package com.qwertydev.glyphutils.listeners;

import com.qwertydev.glyphutils.GlyphUtils;
import com.qwertydev.glyphutils.inventory.impl.GradientMakerGUI;
import com.qwertydev.glyphutils.inventory.impl.SmallCapsGUI;
import com.qwertydev.glyphutils.session.PlayerSession;
import com.qwertydev.glyphutils.session.SessionType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    
    private final GlyphUtils plugin;
    
    public ChatListener(GlyphUtils plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        
        if (!plugin.getSessionManager().hasActiveSession(player.getUniqueId())) {
            return;
        }
        
        event.setCancelled(true);
        
        PlayerSession session = plugin.getSessionManager().getSession(player.getUniqueId());
        String message = event.getMessage();
        
        if (message.equalsIgnoreCase("cancel")) {
            session.reset();
            String cancelMsg = ChatColor.translateAlternateColorCodes('&', 
                plugin.getConfig().getString("messages.cancelled"));
            player.sendMessage(cancelMsg);
            return;
        }
        
        SessionType type = session.getSessionType();
        
        if (type == SessionType.SMALL_CAPS_INPUT) {
            session.setInputText(message);
            session.setWaitingForInput(false);
            
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                SmallCapsGUI gui = new SmallCapsGUI(plugin);
                plugin.getGuiManager().openGUI(gui, player);
            });
            
        } else if (type == SessionType.GRADIENT_TEXT_INPUT) {
            session.setInputText(message);
            session.setWaitingForInput(false);
            
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                GradientMakerGUI gui = new GradientMakerGUI(plugin);
                plugin.getGuiManager().openGUI(gui, player);
            });
            
        } else if (type == SessionType.GRADIENT_COLOR1_INPUT) {
            if (isValidHexColor(message)) {
                session.setColor1(formatHexColor(message));
                session.setWaitingForInput(false);
                
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    GradientMakerGUI gui = new GradientMakerGUI(plugin);
                    plugin.getGuiManager().openGUI(gui, player);
                });
            } else {
                player.sendMessage(ChatColor.RED + "Invalid hex color! Use format: &#RRGGBB or #RRGGBB");
            }
            
        } else if (type == SessionType.GRADIENT_COLOR2_INPUT) {
            if (isValidHexColor(message)) {
                session.setColor2(formatHexColor(message));
                session.setWaitingForInput(false);
                
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    GradientMakerGUI gui = new GradientMakerGUI(plugin);
                    plugin.getGuiManager().openGUI(gui, player);
                });
            } else {
                player.sendMessage(ChatColor.RED + "Invalid hex color! Use format: &#RRGGBB or #RRGGBB");
            }
        }
    }
    
    private boolean isValidHexColor(String input) {
        String hex = input.replace("&#", "").replace("#", "");
        return hex.matches("[0-9A-Fa-f]{6}");
    }
    
    private String formatHexColor(String input) {
        String hex = input.replace("&#", "").replace("#", "");
        return "&#" + hex.toUpperCase();
    }
}