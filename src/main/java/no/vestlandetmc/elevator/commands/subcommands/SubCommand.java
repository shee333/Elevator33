package no.vestlandetmc.elevator.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.Collection;
import java.util.Collections;

public interface SubCommand {

	void execute(CommandSender sender, String[] args);

	String getName();

	String getDescription();

	Permission getPermission();

	default Collection<String> suggest(CommandSender sender, String[] args) {
		return Collections.emptyList();
	}

}