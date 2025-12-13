package no.vestlandetmc.elevator.hooks;

import lombok.Getter;
import no.vestlandetmc.elevator.ElevatorPlugin;
import no.vestlandetmc.elevator.handler.MessageHandler;

public class HookManager {

	@Getter
	private static boolean griefDefenderLoaded = false;
	@Getter
	private static boolean griefPreventionLoaded = false;
	@Getter
	private static boolean worldGuardLoaded = false;

	public static void initialize() {
		final ElevatorPlugin plugin = ElevatorPlugin.getPlugin();
		final String[] hooks = {
				"GriefDefender",
				"GriefPrevention",
				"WorldGuard",
				"CMI",
				"Essentials",
				"PremiumVanish",
				"SuperVanish"
		};

		for (String hook : hooks) {
			if (plugin.getServer().getPluginManager().getPlugin(hook) != null) {
				switch (hook) {
					case "GriefDefender" -> griefDefenderLoaded = true;
					case "GriefPrevention" -> griefPreventionLoaded = true;
					case "WorldGuard" -> worldGuardLoaded = true;
				}

				MessageHandler.sendConsole("&7Successfully hooked into &b" + hook);
			}
		}
	}
}
