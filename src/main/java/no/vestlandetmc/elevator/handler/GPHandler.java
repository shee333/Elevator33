package no.vestlandetmc.elevator.handler;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import no.vestlandetmc.elevator.ElevatorPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class GPHandler {

	public static boolean haveTrust(Player player) {
		final Location loc = player.getLocation();
		final Claim claim = GriefPrevention.instance.dataStore.getClaimAt(loc, true, null);

		if (claim == null) {
			return true;
		}

		final @Nullable Supplier<String> accessDenied = claim.checkPermission(player, ClaimPermission.Access, null);
		if (accessDenied != null) {
			if (!MessageHandler.spamMessageClaim.contains(player.getUniqueId().toString())) {
				MessageHandler.sendMessage(player, "&c" + accessDenied);
				MessageHandler.spamMessageClaim.add(player.getUniqueId().toString());

				new BukkitRunnable() {
					@Override
					public void run() {
						MessageHandler.spamMessageClaim.remove(player.getUniqueId().toString());
					}

				}.runTaskLater(ElevatorPlugin.getPlugin(), 20L);
			}
			return false;
		} else {
			return true;
		}

	}

	public static boolean haveBuildTrust(Player player, Location loc) {
		final Claim claim = GriefPrevention.instance.dataStore.getClaimAt(loc, true, null);

		if (claim == null) {
			return true;
		}

		final @Nullable Supplier<String> accessDenied = claim.checkPermission(player, ClaimPermission.Build, null);
		return accessDenied == null;
	}

}
