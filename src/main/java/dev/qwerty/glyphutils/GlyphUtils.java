package com.qwertydev.glyphutils;

import com.qwertydev.glyphutils.commands.UtilsCommand;
import com.qwertydev.glyphutils.inventory.gui.GUIListener;
import com.qwertydev.glyphutils.inventory.gui.GUIManager;
import com.qwertydev.glyphutils.listeners.ChatListener;
import com.qwertydev.glyphutils.session.SessionManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class GlyphUtils extends JavaPlugin {
    
    private GUIManager guiManager;
    private SessionManager sessionManager;
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        this.guiManager = new GUIManager();
        this.sessionManager = new SessionManager();
        
        GUIListener guiListener = new GUIListener(guiManager);
        Bukkit.getPluginManager().registerEvents(guiListener, this);
        
        ChatListener chatListener = new ChatListener(this);
        Bukkit.getPluginManager().registerEvents(chatListener, this);
        
        getCommand("utils").setExecutor(new UtilsCommand(this));
        
        getLogger().info("GlyphUtils has been enabled!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("GlyphUtils has been disabled!");
    }
}