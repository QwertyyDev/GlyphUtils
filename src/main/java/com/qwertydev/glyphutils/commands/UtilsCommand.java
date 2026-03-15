package com.qwertydev.glyphutils.commands;

import com.qwertydev.glyphutils.GlyphUtils;
import com.qwertydev.glyphutils.inventory.impl.MainMenuGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilsCommand implements CommandExecutor {
    
    private final GlyphUtils plugin;
    
    public UtilsCommand(GlyphUtils plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }
        
        Player player = (Player) sender;
        MainMenuGUI mainMenu = new MainMenuGUI(plugin);
        plugin.getGuiManager().openGUI(mainMenu, player);
        
        return true;
    }
}