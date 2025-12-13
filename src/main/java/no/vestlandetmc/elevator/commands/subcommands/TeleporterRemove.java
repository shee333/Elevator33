package no.vestlandetmc.elevator.commands.subcommands;

import no.vestlandetmc.elevator.config.Config;
import no.vestlandetmc.elevator.config.TeleporterData;
import no.vestlandetmc.elevator.handler.MessageHandler;
import no.vestlandetmc.elevator.handler.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.Collection;

public class TeleporterRemove implements SubCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof Player player)) {
			MessageHandler.sendConsole("&cYou must use this command as a player in-game");
			return;
		}

		if (args.length != 1) {
			MessageHandler.sendMessage(player, Config.TP_LOCALE_SPECIFYTP);
			return;
		}

		if (!player.hasPermission(getPermission())) {
			MessageHandler.sendMessage(player, Config.ML_LOCALE_PERMISSION);
			return;
		}

		TeleporterData.deleteTeleporter(player, args[0].toUpperCase());
	}

	@Override
	public String getName() {
		return "remove";
	}

	@Override
	public String getDescription() {
		return "Removes a teleporter.";
	}

	@Override
	public Permission getPermission() {
		return Permissions.REMOVE;
	}

	@Override
	public Collection<String> suggest(CommandSender sender, String[] args) {
		String input = args.length >= 1 ? args[args.length - 1].toLowerCase() : "";

		return TeleporterData.tabCompleteTp((Player) sender).stream()
				.filter(tp -> tp.toLowerCase().startsWith(input))
				.sorted(String.CASE_INSENSITIVE_ORDER)
				.toList();
	}
}
