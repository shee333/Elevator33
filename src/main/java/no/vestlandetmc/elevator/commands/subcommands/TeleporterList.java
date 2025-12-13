package no.vestlandetmc.elevator.commands.subcommands;

import no.vestlandetmc.elevator.config.Config;
import no.vestlandetmc.elevator.config.TeleporterData;
import no.vestlandetmc.elevator.handler.MessageHandler;
import no.vestlandetmc.elevator.handler.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class TeleporterList implements SubCommand {

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

		TeleporterData.listTP(player);
	}

	@Override
	public String getName() {
		return "list";
	}

	@Override
	public String getDescription() {
		return "Lists all available teleporters.";
	}

	@Override
	public Permission getPermission() {
		return Permissions.LIST;
	}
}
