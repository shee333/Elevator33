package no.vestlandetmc.elevator.handler;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class Permissions {

	private static final Map<String, Boolean> childrenAdmin = new HashMap<>();
	private static final Map<String, Boolean> childrenTeleporterBasic = new HashMap<>();

	public static Permission
			ELEVATOR_USE,
			ADD,
			LINK,
			UNLINK,
			REMOVE,
			LIST,
			TP_USE,
			BYPASS,
			BASIC,
			ADMIN;

	public static void register() {
		final PluginManager pm = Bukkit.getPluginManager();

		ELEVATOR_USE = new Permission("elevator.use", "For elevator usage.", PermissionDefault.OP);
		ADD = new Permission("elevator.teleporter.add", "Use of the /teleporter add command.", PermissionDefault.OP);
		LINK = new Permission("elevator.teleporter.link", "Use of the /teleporter link command.", PermissionDefault.OP);
		UNLINK = new Permission("elevator.teleporter.unlink", "Use of the /teleporter unlink command.", PermissionDefault.OP);
		REMOVE = new Permission("elevator.teleporter.remove", "Use of the /teleporter remove command.", PermissionDefault.OP);
		LIST = new Permission("elevator.teleporter.list", "Use of the /teleporter list command.", PermissionDefault.OP);
		TP_USE = new Permission("elevator.teleporter.use", "For teleporter usage.", PermissionDefault.OP);
		BYPASS = new Permission("elevator.teleporter.bypass", "Bypasses the elevator.teleporter.xx node. Unlimited teleporters.", PermissionDefault.OP);

		BASIC = new Permission("elevator.teleporter.basic", "Give all basic teleporter permissions.", PermissionDefault.OP, childrenTeleporterBasic);
		ADMIN = new Permission("elevator.admin", "Give you access to everything.", PermissionDefault.OP, childrenAdmin);

		childrenAdmin.put("elevator.teleporter.basic", true);
		childrenAdmin.put("elevator.use", true);
		childrenAdmin.put("bfc.list", true);
		childrenAdmin.put("elevator.bypass", true);

		childrenTeleporterBasic.put("elevator.teleporter.add", true);
		childrenTeleporterBasic.put("elevator.teleporter.link", true);
		childrenTeleporterBasic.put("elevator.teleporter.unlink", true);
		childrenTeleporterBasic.put("elevator.teleporter.remove", true);
		childrenTeleporterBasic.put("elevator.teleporter.list", true);
		childrenTeleporterBasic.put("elevator.teleporter.use", true);

		pm.addPermissions(List.of(
				ELEVATOR_USE,
				ADD,
				LINK,
				UNLINK,
				REMOVE,
				LIST,
				TP_USE,
				BYPASS,
				BASIC,
				ADMIN
		));
	}
}
