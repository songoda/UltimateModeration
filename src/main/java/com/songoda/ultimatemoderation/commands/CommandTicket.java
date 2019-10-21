package com.songoda.ultimatemoderation.commands;

import com.songoda.core.commands.AbstractCommand;
import com.songoda.ultimatemoderation.UltimateModeration;
import com.songoda.ultimatemoderation.gui.GUITicketManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandTicket extends AbstractCommand {

    private UltimateModeration instance;

    public CommandTicket(UltimateModeration instance) {
        super(CommandType.PLAYER_ONLY, "Ticket");
        this.instance = instance;
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        Player senderP = ((Player) sender);

        new GUITicketManager(instance, senderP, senderP);
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "um.ticket";
    }

    @Override
    public String getSyntax() {
        return "/ticket";
    }

    @Override
    public String getDescription() {
        return "Opens the ticket interface.";
    }

}