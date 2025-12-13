package no.vestlandetmc.elevator.commands.subcommands;

import no.vestlandetmc.elevator.config.Config;
import no.vestlandetmc.elevator.config.TeleporterData;
import no.vestlandetmc.elevator.handler.*;
import no.vestlandetmc.elevator.hooks.HookManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class TeleporterAdd implements SubCommand {

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

		if (HookManager.isGriefPreventionLoaded()) {
			if (!GPHandler.haveTrust(player)) return;
		}

		if (HookManager.isGriefDefenderLoaded()) {
			if (!GDHandler.haveTrust(player)) return;
		}

		if (HookManager.isWorldGuardLoaded()) {
			if (!WGHandler.haveTrust(player)) return;
		}

		if (TeleporterData.checkTpPerms(player)) {
			TeleporterData.setTeleporter(player);
		} else {
			MessageHandler.sendMessage(player, Config.TP_LOCALE_PERMBLOCK);
		}
	}

	@Override
	public String getName() {
		return "add";
	}

	@Override
	public String getDescription() {
		return "Creates a new teleporter at your location.";
	}

	@Override
	public Permission getPermission() {
		return Permissions.ADD;
	}
}
