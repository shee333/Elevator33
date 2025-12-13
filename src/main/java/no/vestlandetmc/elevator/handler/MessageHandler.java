package no.vestlandetmc.elevator.handler;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import no.vestlandetmc.elevator.ElevatorPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler {

	public static ArrayList<String> spamMessageClaim = new ArrayList<>();
	public static ArrayList<String> spamMessageCooldown = new ArrayList<>();

	public static void sendAction(Player player, String message) {
		final Component text = colorize(message);
		player.sendActionBar(text);
	}

	public static void sendMessage(Player player, List<String> messages) {
		for (String s : messages) {
			sendMessage(player, s);
		}
	}

	public static void sendMessage(Player player, String message) {
		final Component text = colorize(message);
		player.sendMessage(text);
	}

	public static void sendConsole(List<String> messages) {
		for (String m : messages) {
			sendConsole(m);
		}
	}

	public static void sendConsole(String message) {
		final Component text = colorize(message);
		ElevatorPlugin.getPlugin().getServer().getConsoleSender().sendMessage(text);
	}

	public static Component colorize(String message) {
		return LegacyComponentSerializer.legacy('&').deserialize(message);
	}

	public static String placeholders(String message, String time, String tpName, String tp1, String tp2) {
		return message.
				replaceAll("%time%", time).
				replaceAll("%tpname%", tpName).
				replaceAll("%teleport1%", tp1).
				replaceAll("%teleport2%", tp2);

	}
}
