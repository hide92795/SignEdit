package hide92795.bukkit.plugin.signedit.listener;

import hide92795.bukkit.plugin.signedit.Request;
import hide92795.bukkit.plugin.signedit.SignEdit;
import hide92795.bukkit.plugin.signedit.Type;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {
	private SignEdit plugin;

	public PlayerListener(SignEdit plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		switch (event.getAction()) {
		case LEFT_CLICK_BLOCK:
		case RIGHT_CLICK_BLOCK:
			Block b = event.getClickedBlock();
			Player player = event.getPlayer();
			String name = player.getName();
			if (b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST) {
				if (plugin.requests.containsKey(name)) {
					event.setCancelled(true);
					Sign sign = (Sign) event.getClickedBlock().getState();
					Request request = plugin.requests.get(name);
					plugin.requests.remove(player.getName());
					sign.setLine(request.line, request.string);
					sign.update();
					player.sendMessage(plugin.localize.getString(Type.EDITED));
				}
			}
			break;
		default:
			break;
		}
	}
}
