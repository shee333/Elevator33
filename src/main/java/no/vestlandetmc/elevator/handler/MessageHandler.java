package no.vestlandetmc.elevator.handler;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import no.vestlandetmc.elevator.ElevatorPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler {

	public final static ArrayList<String> spamMessageClaim = new ArrayList<>();
	public final static ArrayList<String> spamMessageCooldown = new ArrayList<>();
	private final static MiniMessage mm = MiniMessage.miniMessage();

	public static void sendAction(Player player, String miniMessage) {
		final Component text = mm.deserialize(miniMessage);
		player.sendActionBar(text);
	}

	public static void sendMessage(Player player, List<String> miniMessages) {
		for (String s : miniMessages) {
			sendMessage(player, s);
		}
	}

	public static void sendMessage(Player player, String miniMessage) {
		final Component text = mm.deserialize(miniMessage);
		player.sendMessage(text);
	}

	public static void sendConsole(List<String> miniMessages) {
		for (String m : miniMessages) {
			sendConsole(m);
		}
	}

	public static void sendConsole(String miniMessage) {
		final Component text = mm.deserialize(miniMessage);
		ElevatorPlugin.getPlugin().getServer().getConsoleSender().sendMessage(text);
	}

	public static String placeholders(String message, String time, String tpName, String tp1, String tp2) {
		return message.
				replaceAll("%time%", time).
				replaceAll("%tpname%", tpName).
				replaceAll("%teleport1%", tp1).
				replaceAll("%teleport2%", tp2);

	}
}
