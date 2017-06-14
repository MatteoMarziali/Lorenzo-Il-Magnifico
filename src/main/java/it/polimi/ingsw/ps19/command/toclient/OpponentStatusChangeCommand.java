package it.polimi.ingsw.ps19.command.toclient;

import it.polimi.ingsw.ps19.Player;
import it.polimi.ingsw.ps19.client.ClientCommandHandler;
import it.polimi.ingsw.ps19.command.ServerToClientCommand;

/**
 * @author Mirko
 *
 */
public class OpponentStatusChangeCommand extends ServerToClientCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3016399299916946675L;
	
	/**
	 * Note: since it's an opponent we have to send a masked clone of the player
	 */
	Player maskedPlayer;

	

	public OpponentStatusChangeCommand(Player maskedClone) {
		this.maskedPlayer=maskedClone;
	}

	@Override
	public void processCommand(ClientCommandHandler clientCommandHandler) {
		// TODO Auto-generated method stub

	}
	
	public Player getMaskedPlayer() {
		return maskedPlayer;
	}

}
