package com.songoda.ultimatemoderation.utils;

import com.songoda.ultimatemoderation.UltimateModeration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AbstractChatConfirm implements Listener {

    private static final List<UUID> registered = new ArrayList<>();

    private Inventory inventory;

    private final Player player;
    private final ChatConfirmHandler handler;

    public AbstractChatConfirm(Player player, ChatConfirmHandler hander) {
        this.player = player;
        this.handler = hander;
        this.inventory = player.getOpenInventory() == null ? null : player.getOpenInventory().getTopInventory();
        player.closeInventory();
        initializeListeners(UltimateModeration.getInstance());
        registered.add(player.getUniqueId());
    }

    private static boolean listenersInitialized = false;

    public void initializeListeners(JavaPlugin plugin) {
        if (listenersInitialized) return;

        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onChat(AsyncPlayerChatEvent event) {
                Player player = event.getPlayer();
                if (!AbstractChatConfirm.isRegistered(player)) return;

                AbstractChatConfirm.unregister(player);
                event.setCancelled(true);

                ChatConfirmEvent chatConfirmEvent = new ChatConfirmEvent(player, event.getMessage());

                handler.onChat(chatConfirmEvent);

                if (inventory != null) player.openInventory(inventory);
            }

            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event) {
                AbstractChatConfirm.unregister(event.getPlayer());
            }
        }, plugin);
        listenersInitialized = true;
    }

    public static boolean isRegistered(Player player) {
        return registered.contains(player.getUniqueId());
    }

    public static boolean unregister(Player player) {
        return registered.remove(player.getUniqueId());
    }

    public interface ChatConfirmHandler {
        void onChat(ChatConfirmEvent event);
    }

    public class ChatConfirmEvent {

        private final Player player;
        private final String message;

        public ChatConfirmEvent(Player player, String message) {
            this.player = player;
            this.message = message;
        }

        public Player getPlayer() {
            return player;
        }

        public String getMessage() {
            return message;
        }
    }

}