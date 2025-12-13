package no.vestlandetmc.elevator.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import no.vestlandetmc.elevator.ElevatorPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;

public abstract class UpdateNotification extends BukkitRunnable {

	@Getter
	private static String projectSlug;
	@Getter
	private static String latestVersion = "";

	public UpdateNotification(String projectSlug) {
		UpdateNotification.projectSlug = projectSlug;
	}

	@Override
	public void run() {
		try {
			URI uri = new URI("https://api.modrinth.com/v2/project/" + projectSlug + "/version");
			URLConnection con = uri.toURL().openConnection();
			con.setRequestProperty("User-Agent", "Elevator/1.0");

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				JsonElement element = JsonParser.parseReader(reader);

				if (!element.isJsonArray()) return;

				JsonArray array = element.getAsJsonArray();
				for (JsonElement e : array) {
					JsonObject version = e.getAsJsonObject();

					if (!version.get("version_type").getAsString().equalsIgnoreCase("release")) {
						continue;
					}

					latestVersion = version.get("version_number").getAsString();
					break;
				}
			}

			if (isUpdateAvailable()) {
				onUpdateAvailable();
			}

		} catch (Exception e) {
			ElevatorPlugin.getPlugin().getLogger().severe(e.getMessage());
		}
	}

	public abstract void onUpdateAvailable();

	public static boolean isUpdateAvailable() {
		return !latestVersion.equals(ElevatorPlugin.getPlugin().getPluginMeta().getVersion());
	}

	public static String getCurrentVersion() {
		return ElevatorPlugin.getPlugin().getPluginMeta().getVersion();
	}
}