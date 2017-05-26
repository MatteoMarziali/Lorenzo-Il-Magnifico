package it.polimi.ingsw.ps19.model.effect.leader;

import it.polimi.ingsw.ps19.Player;
import it.polimi.ingsw.ps19.model.effect.Effect;

/**
 * @author matteo
 *
 */
public class SantaRitaEffect extends Effect{

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Everytime you gain coins, woods, stones or servants from a development card"
				+ "effect, it is duplicate");
		return builder.toString();
	}

	@Override
	public void applyEffect(Player p) {
		p.getBonuses().setDoubleResourcesFromCards(true);
		
	}
	
	public void disapplyEffect(Player p){
		p.getBonuses().setDoubleResourcesFromCards(false);
	}

	
}
