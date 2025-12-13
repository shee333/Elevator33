package no.vestlandetmc.elevator.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import no.vestlandetmc.elevator.commands.subcommands.ElevatorHelp;
import no.vestlandetmc.elevator.commands.subcommands.ElevatorReload;
import no.vestlandetmc.elevator.commands.subcommands.SubCommand;
import no.vestlandetmc.elevator.handler.Permissions;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class ElevatorCommand implements BasicCommand {

	private final Map<String, SubCommand> subcommands = new HashMap<>();

	public ElevatorCommand() {
		register(new ElevatorHelp());
		register(new ElevatorReload());
	}

	private void register(SubCommand subCommand) {
		subcommands.put(subCommand.getName().toLowerCase(), subCommand);
	}

	@Override
	public void execute(@NonNull CommandSourceStack commandSourceStack, String[] args) {
		if (args.length == 0) args = new String[]{"help"};
		String label = args[0].toLowerCase();
		SubCommand sub = subcommands.get(label);

		if (sub != null) {
			sub.execute(commandSourceStack.getSender(), Arrays.copyOfRange(args, 1, args.length));
		}
	}

	@Override
	public @NonNull Collection<String> suggest(@NonNull CommandSourceStack commandSourceStack, String @NonNull [] args) {
		if (args.length == 0) {
			return subcommands.keySet().stream().toList();
		} else if (args.length == 1) {
			return subcommands.keySet().stream()
					.filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
					.toList();
		} else {
			SubCommand sub = subcommands.get(args[0].toLowerCase());
			if (sub != null) {
				String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
				return sub.suggest(commandSourceStack.getSender(), subArgs);
			}
		}

		return Collections.emptyList();
	}

	@Override
	public @Nullable String permission() {
		return Permissions.ADMIN.getName();
	}
}
