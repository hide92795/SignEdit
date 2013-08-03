package hide92795.bukkit.plugin.signedit;

import hide92795.bukkit.plugin.corelib.Localizable;

public enum Type implements Localizable {
	RELOADED_SETTING("ReloadedSetting"), ERROR_RELOAD_SETTING("ErrorReloadSetting"), LINE_NUMBER("LineNumber"), USAGE_EDIT("UsageEdit"), STRING(
			"String"), USAGE_CANCEL("UsageCancel"), RELOAD_SETTING("ReloadSetting"), CANCELED("Canceled"), ALREADY_CANCELED("AlreadyCanceled"), CLICKING_SIGN(
			"ClickingSign"), EDITED("Edited");
	private final String type;

	private Type(String type) {
		this.type = type;
	}

	@Override
	public String getName() {
		return type;
	}
}
