package com.songoda.ultimatemoderation.listeners;

import com.songoda.ultimatemoderation.UltimateModeration;
import com.songoda.ultimatemoderation.staffchat.StaffChatManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class BlockListener implements Listener {

    private UltimateModeration instance;
    private StaffChatManager chat = UltimateModeration.getInstance().getStaffChatManager();

    public BlockListener(UltimateModeration ultimateModeration) {
        this.instance = ultimateModeration;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material material = block.getType();

        List<String> blocks = instance.getConfig().getStringList("Main.Notify Blocks List");

        for (String broken : blocks) {
            if (!broken.equalsIgnoreCase(material.name())) continue;

            if (player.hasPermission("um.trackblockbreaks") && instance.getConfig().getBoolean("Main.Notify Blocks")) {
                chat.getChat("notify").messageAll("&7[UM] &a" + Bukkit.getPlayer(player.getUniqueId()).getDisplayName()
                        + UltimateModeration.getInstance().getLocale().getMessage("notify.block.main").getMessage().replace("%material%", material.name()) + "(" + block.getX() + ", " + block.getY() + ", " + block.getZ() + ")&a!");
            }
        }
    }

}
