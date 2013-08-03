package hide92795.bukkit.plugin.signedit;

import hide92795.bukkit.plugin.corelib.CoreLib;
import hide92795.bukkit.plugin.corelib.Localize;
import hide92795.bukkit.plugin.corelib.Tool;
import hide92795.bukkit.plugin.corelib.Usage;
import hide92795.bukkit.plugin.signedit.listener.PlayerListener;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SignEdit extends JavaPlugin {
	public HashMap<String, Request> requests;
	private Logger logger;
	public Localize localize;
	private Tool tool;
	private Usage usage;

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
		logger = getLogger();
		localize = new Localize(this);
		tool = CoreLib.getCoreLib().getTool();
		requests = new HashMap<>();
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		logger.info("SignEdit enabled!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (command.getName().toLowerCase()) {
		case "signedit":
			if (sender instanceof Player) {
				Player player = (Player) sender;
				switch (args.length) {
				case 1:
					if (args[0].equalsIgnoreCase("cancel")) {
						requests.remove(player.getName());
						player.sendMessage(localize.getString(Type.CANCELED));
					} else {
						player.sendMessage(usage.toString());
					}
					break;
				case 2:
					String line_s = args[0];
					String str = args[1];
					int line = 0;
					try {
						line = Integer.parseInt(line_s);
					} catch (Exception e) {
						player.sendMessage(usage.toString());
						break;
					}
					editRequest(player, line, str);
					break;
				default:
					player.sendMessage(usage.toString());
					break;
				}
			} else {
				sender.sendMessage("[SignEdit] This command is only for player!");
			}
			break;
		case "signedit-reload":
			try {
				reload();
				sender.sendMessage(localize.getString(Type.RELOADED_SETTING));
				logger.info("Reloaded successfully.");
			} catch (Exception e) {
				sender.sendMessage(localize.getString(Type.ERROR_RELOAD_SETTING));
			}
			break;
		default:
			break;
		}
		return true;
	}

	private void editRequest(Player player, int line, String str) {
		if (requests.containsKey(player.getName())) {
			player.sendMessage(localize.getString(Type.ALREADY_CANCELED));
		} else {
			Request request = new Request(player.getName(), line, tool.replaceThings(str));
			requests.put(player.getName(), request);
			player.sendMessage(localize.getString(Type.CLICKING_SIGN));
		}

	}

	private void reload() throws Exception {
		reloadConfig();
		try {
			localize.reload(getConfig().getString("Language"));
		} catch (Exception e1) {
			logger.severe("Can't load language file.");
			try {
				localize.reload("jp");
				logger.severe("Loaded default language file.");
			} catch (Exception e) {
				throw e;
			}
		}
		createUsage();
	}

	private void createUsage() {
		usage = new Usage(this);
		usage.addCommand("/signedit <" + localize.getString(Type.LINE_NUMBER) + "> <" + localize.getString(Type.STRING) + ">",
				localize.getString(Type.USAGE_EDIT));
		usage.addCommand("/signedit cancel", localize.getString(Type.USAGE_CANCEL));
		usage.addCommand("/signedit-reload", localize.getString(Type.RELOAD_SETTING));
	}
}
