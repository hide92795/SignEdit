package hide92795.bukkit.plugin.signedit;

public class Request {
	public final String player;
	public final int line;
	public final String string;

	public Request(String name, int line, String str) {
		this.player = name;
		this.line = line;
		this.string = str;
	}
}
