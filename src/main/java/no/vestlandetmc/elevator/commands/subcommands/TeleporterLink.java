package no.vestlandetmc.elevator.commands.subcommands;

import no.vestlandetmc.elevator.config.Config;
import no.vestlandetmc.elevator.config.TeleporterData;
import no.vestlandetmc.elevator.handler.MessageHandler;
import no.vestlandetmc.elevator.handler.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.Collection;
import java.util.Collections;

public class TeleporterLink implements SubCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof Player player)) {
			MessageHandler.sendConsole("&cYou must use this command as a player in-game");
			return;
		}

		if (args.length != 2) {
			MessageHandler.sendMessage(player, Config.TP_LOCALE_SPECIFYMORETP);
			return;
		}

		String tpName1 = args[0].toUpperCase();
		String tpName2 = args[1].toUpperCase();

		if (!player.hasPermission(getPermission())) {
			MessageHandler.sendMessage(player, Config.ML_LOCALE_PERMISSION);
			return;
		}

		if (tpName1.equals(tpName2)) {
			MessageHandler.sendMessage(player, Config.TP_LOCALE_LINKSELF);
			return;
		}

		TeleporterData.linkTeleporter(player, tpName1, tpName2);
	}

	@Override
	public String getName() {
		return "link";
	}

	@Override
	public String getDescription() {
		return "Links two teleporters together.";
	}

	@Override
	public Permission getPermission() {
		return Permissions.LINK;
	}

	@Override
	public Collection<String> suggest(CommandSender sender, String[] args) {
		if (args.length > 2) return Collections.emptyList();
		String input = args.length >= 1 ? args[args.length - 1].toLowerCase() : "";

		return TeleporterData.tabCompleteTp((Player) sender).stream()
				.filter(tp -> tp.toLowerCase().startsWith(input))
				.sorted(String.CASE_INSENSITIVE_ORDER)
				.toList();
	}
}
