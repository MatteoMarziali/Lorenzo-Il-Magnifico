package it.polimi.ingsw.ps19;

import it.polimi.ingsw.ps19.Dice;

/**
 * @author matteo
 *
 */
public class FamilyMember {
	
	private Dice dice; 
	private Player player;
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public FamilyMember(Dice d){
		
		this.dice = d;
	}	
	
	public Dice getDice(){
		
		return this.dice;
	}
	
	public int getActionValue(){
		return this.dice.getUpperFaceValue();
	}
	
	

}


