package no.vestlandetmc.elevator;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import no.vestlandetmc.elevator.Listener.ElevatorListener;
import no.vestlandetmc.elevator.Listener.TeleporterListener;
import no.vestlandetmc.elevator.commands.ElevatorCommand;
import no.vestlandetmc.elevator.commands.TeleporterCommand;
import no.vestlandetmc.elevator.config.Config;
import no.vestlandetmc.elevator.config.TeleporterData;
import no.vestlandetmc.elevator.handler.MessageHandler;
import no.vestlandetmc.elevator.handler.Permissions;
import no.vestlandetmc.elevator.handler.UpdateNotification;
import no.vestlandetmc.elevator.hooks.HookManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ElevatorPlugin extends JavaPlugin {

	@Getter
	private static ElevatorPlugin plugin;

	private FileConfiguration data;

	@Override
	public void onEnable() {
		plugin = this;

		MessageHandler.sendConsole("<aqua>___________ __                       __                ");
		MessageHandler.sendConsole("<aqua>\\_   _____/|  |   _______  _______ _/  |_  ___________ ");
		MessageHandler.sendConsole("<aqua> |    __)_ |  | _/ __ \\  \\/ /\\__  \\\\   __\\/  _ \\_  __ \\");
		MessageHandler.sendConsole("<aqua> |        \\|  |_\\  ___/\\   /  / __ \\|  | (  <_> )  | \\/");
		MessageHandler.sendConsole("<aqua>/_______  /|____/\\___  >\\_/  (____  /__|  \\____/|__|   ");
		MessageHandler.sendConsole("<aqua>        \\/           \\/           \\/                   ");
		MessageHandler.sendConsole("");
		MessageHandler.sendConsole("<aqua>Elevator v" + getPluginMeta().getVersion());
		MessageHandler.sendConsole("<aqua>Running on " + getServer().getName());
		MessageHandler.sendConsole("<aqua>Author: Baktus_79");
		MessageHandler.sendConsole("<dark_gray><u>_______________________________________________________");
		MessageHandler.sendConsole("");

		Config.initialize();
		createDatafile();
		Permissions.register();

		TeleporterData.createSection();
		HookManager.initialize();
		MessageHandler.sendConsole("<dark_gray><u>_______________________________________________________");

		this.getServer().getPluginManager().registerEvents(new ElevatorListener(), this);
		this.getServer().getPluginManager().registerEvents(new TeleporterListener(), this);

		this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
			commands.registrar().register(
					"elevator",
					"Shows plugin help and reload options.",
					new ElevatorCommand());

			commands.registrar().register(
					"teleporter",
					"Manage teleporters.",
					new TeleporterCommand());
		});

		new UpdateNotification("blockelevator") {

			@Override
			public void onUpdateAvailable() {
				MessageHandler.sendConsole("<red>-----------------------");
				MessageHandler.sendConsole("<red>[Elevator] Version " + getLatestVersion() + " is now available!");
				MessageHandler.sendConsole("<red>[Elevator] Download the update at https://modrinth.com/plugin/" + getProjectSlug());
				MessageHandler.sendConsole("<red>-----------------------");
			}
		}.runTaskAsynchronously(this);

		final int pluginId = 22614;
		final Metrics metrics = new Metrics(this, pluginId);
	}

	public void reload() {
		Config.initialize();
	}

	public FileConfiguration getDataFile() {
		return this.data;
	}

	public void createDatafile() {
		File dataFile = new File(this.getDataFolder(), "data.dat");
		if (!dataFile.exists()) {
			dataFile.getParentFile().mkdirs();
			try {
				dataFile.createNewFile();
			} catch (final IOException e) {
				getLogger().severe(e.getMessage());
			}
		}

		data = new YamlConfiguration();
		try {
			data.load(dataFile);
		} catch (IOException | InvalidConfigurationException e) {
			getLogger().severe(e.getMessage());
		}
	}
}
