package no.vestlandetmc.elevator.commands.subcommands;

import no.vestlandetmc.elevator.ElevatorPlugin;
import no.vestlandetmc.elevator.config.Config;
import no.vestlandetmc.elevator.handler.MessageHandler;
import no.vestlandetmc.elevator.handler.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class ElevatorReload implements SubCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		ElevatorPlugin.getPlugin().reload();
		if (sender instanceof Player player) {
			if (!player.hasPermission(getPermission())) {
				MessageHandler.sendMessage(player, Config.ML_LOCALE_PERMISSION);
				return;
			}
			MessageHandler.sendMessage(player, "&3[Elevator] The config has been reloaded");
		}
		MessageHandler.sendConsole("&3[Elevator] The config has been reloaded");
	}

	@Override
	public String getName() {
		return "reload";
	}

	@Override
	public String getDescription() {
		return "Reloads the plugin’s configuration files without restarting the server.";
	}

	@Override
	public Permission getPermission() {
		return Permissions.ADMIN;
	}
}
