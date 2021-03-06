package me.desht.chesscraft.controlpanel;

import me.desht.chesscraft.ChessCraft;
import me.desht.chesscraft.chess.ChessGame;
import me.desht.chesscraft.enums.GameState;
import me.desht.chesscraft.util.EconomyUtil;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class StakeButton extends AbstractSignButton {

	public StakeButton(ControlPanel panel) {
		super(panel, "stakeBtn", "stake", 7, 1);
	}

	@Override
	public void execute(PlayerInteractEvent event) {
		double stakeIncr;
		if (event.getPlayer().isSneaking()) {
			stakeIncr = ChessCraft.getInstance().getConfig().getDouble("stake.smallIncrement"); //$NON-NLS-1$
		} else {
			stakeIncr = ChessCraft.getInstance().getConfig().getDouble("stake.largeIncrement"); //$NON-NLS-1$
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			stakeIncr = -stakeIncr;
		}

		getGame().adjustStake(event.getPlayer(), stakeIncr);

		repaint();
	}

	@Override
	public boolean isEnabled() {
		return getGame() != null && EconomyUtil.enabled();
	}

	@Override
	public boolean isReactive() {
		ChessGame game = getGame();
		return game != null && !getView().getLockStake() && game.getState() == GameState.SETTING_UP && !game.isFull();

	}

	@Override
	protected String[] getCustomSignText() {
		String[] res = getSignText();

		ChessGame game = getGame();
		double stake = game == null ? getView().getDefaultStake() : game.getStake();
		String[] s =  EconomyUtil.formatStakeStr(stake).split(" ", 2);
		res[2] = getIndicatorColour() + s[0];
		res[3] = s.length > 1 ? getIndicatorColour() + s[1] : "";

		return res;
	}
}
