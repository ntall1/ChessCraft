package me.desht.chesscraft.commands;

import me.desht.chesscraft.chess.ChessGame;
import me.desht.chesscraft.chess.ChessGameManager;
import me.desht.chesscraft.exceptions.ChessException;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class InvitePlayerCommand extends ChessAbstractCommand {

	public InvitePlayerCommand() {
		super("chess invite", 0, 1);
		setPermissionNode("chesscraft.commands.invite");
		setUsage("/chess invite [<player-name>]");
	}

	@Override
	public boolean execute(Plugin plugin, CommandSender player, String[] args) throws ChessException {
		notFromConsole(player);

		ChessGame game = ChessGameManager.getManager().getCurrentGame(player.getName(), true);
		String invitee = args.length > 0 ? args[0] : null;
		game.invitePlayer(player.getName(), invitee);

		return true;
	}

	@Override
	public List<String> onTabComplete(Plugin plugin, CommandSender sender, String[] args) {
		switch (args.length) {
		case 1:
			return getPlayerCompletions(plugin, sender, args[0], false);
		default:
			return noCompletions(sender);
		}
	}

}
