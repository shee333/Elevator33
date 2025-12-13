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

		MessageHandler.sendConsole("&b___________ __                       __                ");
		MessageHandler.sendConsole("&b\\_   _____/|  |   _______  _______ _/  |_  ___________ ");
		MessageHandler.sendConsole("&b |    __)_ |  | _/ __ \\  \\/ /\\__  \\\\   __\\/  _ \\_  __ \\");
		MessageHandler.sendConsole("&b |        \\|  |_\\  ___/\\   /  / __ \\|  | (  <_> )  | \\/");
		MessageHandler.sendConsole("&b/_______  /|____/\\___  >\\_/  (____  /__|  \\____/|__|   ");
		MessageHandler.sendConsole("&b        \\/           \\/           \\/                   ");
		MessageHandler.sendConsole("");
		MessageHandler.sendConsole("&bElevator v" + getPluginMeta().getVersion());
		MessageHandler.sendConsole("&bRunning on " + getServer().getName());
		MessageHandler.sendConsole("&bAuthor: Baktus_79");
		MessageHandler.sendConsole("&8&n_______________________________________________________");
		MessageHandler.sendConsole("");

		Config.initialize();
		createDatafile();
		Permissions.register();

		TeleporterData.createSection();
		HookManager.initialize();
		MessageHandler.sendConsole("&8&n_______________________________________________________");

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
				MessageHandler.sendConsole("&7-----------------------");
				MessageHandler.sendConsole("&7[Elevator] Version " + getLatestVersion() + " is now available!");
				MessageHandler.sendConsole("&7[Elevator] Download the update at https://modrinth.com/plugin/" + getProjectSlug());
				MessageHandler.sendConsole("&7-----------------------");
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
