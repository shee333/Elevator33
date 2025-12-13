package no.vestlandetmc.elevator.commands.subcommands;

import no.vestlandetmc.elevator.config.Config;
import no.vestlandetmc.elevator.handler.MessageHandler;
import no.vestlandetmc.elevator.handler.Permissions;
import no.vestlandetmc.elevator.handler.UpdateNotification;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public class ElevatorHelp implements SubCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		boolean isConsole = !(sender instanceof Player);

		if (!isConsole && !sender.hasPermission(getPermission())) {
			MessageHandler.sendMessage((Player) sender, Config.ML_LOCALE_PERMISSION);
			return;
		}

		List<String> messages = new ArrayList<>();
		messages.add("&3---------------------------------");
		messages.add("&bElevator is running version: &3v" + UpdateNotification.getCurrentVersion());

		if (UpdateNotification.isUpdateAvailable()) {
			messages.add("&aUpdate is available! New version is: &3" + UpdateNotification.getLatestVersion());
			messages.add("&aGet the new update at https://modrinth.com/plugin/" + UpdateNotification.getProjectSlug());
		} else {
			messages.add("&aYou are running the latest version!");
		}

		messages.add("&bRun &3/elevator reload &bto reload the plugin.");
		messages.add("&3---------------------------------");

		if (isConsole) MessageHandler.sendConsole(messages);
		else MessageHandler.sendMessage((Player) sender, messages);
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "Displays plugin information and commands.";
	}

	@Override
	public Permission getPermission() {
		return Permissions.ADMIN;
	}
}
