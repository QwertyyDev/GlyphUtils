package com.qwertydev.glyphutils.commands;

import com.qwertydev.glyphutils.GlyphUtils;
import com.qwertydev.glyphutils.inventory.impl.GradientMakerGUI;
import com.qwertydev.glyphutils.session.PlayerSession;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ColorSelectCommand implements CommandExecutor {
    
    private final GlyphUtils plugin;
    
    public ColorSelectCommand(GlyphUtils plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        
        if (args.length != 2) {
            return true;
        }
        
        Player player = (Player) sender;
        PlayerSession session = plugin.getSessionManager().getSession(player.getUniqueId());
        
        String colorNumber = args[0];
        String hexColor = args[1];
        
        if (colorNumber.equals("1")) {
            session.setColor1(hexColor);
        } else if (colorNumber.equals("2")) {
            session.setColor2(hexColor);
        }
        
        session.setWaitingForInput(false);
        
        GradientMakerGUI gui = new GradientMakerGUI(plugin);
        plugin.getGuiManager().openGUI(gui, player);
        
        return true;
    }
}