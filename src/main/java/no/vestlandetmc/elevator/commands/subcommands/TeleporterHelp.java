package no.vestlandetmc.elevator.commands.subcommands;

import no.vestlandetmc.elevator.config.Config;
import no.vestlandetmc.elevator.handler.MessageHandler;
import no.vestlandetmc.elevator.handler.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class TeleporterHelp implements SubCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof Player player)) {
			MessageHandler.sendConsole("&cYou must use this command as a player in-game");
			return;
		}

		if (!player.hasPermission(getPermission())) {
			MessageHandler.sendMessage(player, Config.ML_LOCALE_PERMISSION);
			return;
		}

		MessageHandler.sendMessage(player, Config.TP_LOCALE_HELPHEADER);
		MessageHandler.sendMessage(player, Config.TP_LOCALE_HELPADD);
		MessageHandler.sendMessage(player, Config.TP_LOCALE_HELPHELP);
		MessageHandler.sendMessage(player, Config.TP_LOCALE_HELPLINK);
		MessageHandler.sendMessage(player, Config.TP_LOCALE_HELPLIST);
		MessageHandler.sendMessage(player, Config.TP_LOCALE_HELPREMOVE);
		MessageHandler.sendMessage(player, Config.TP_LOCALE_HELPUNLINK);
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "Shows help for teleporter commands.";
	}

	@Override
	public Permission getPermission() {
		return Permissions.TP_USE;
	}
}
