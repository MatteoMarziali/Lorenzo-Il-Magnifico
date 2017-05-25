package it.polimi.ingsw.ps19.model.effect;

import it.polimi.ingsw.ps19.Player;

/**
 * This class implements the harvest effect that gives only resources,
 * hence a resource chest. The harvest effect is the same as an instantResourcesEffect
 * except for the fact that it has a condition that triggers it. 
 * 
 * @author Jimmy
 *
 */
public class HarvestEffect extends Effect {
	
	Effect instantEffect;
	
	/**
	 * class constructor
	 * 
	 * @param effectResourceChest  the chest that contains the rewarded resources or the council privilege effect
	 */
	public HarvestEffect(Effect instantEffect){
		this.instantEffect=instantEffect;
	
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps19.model.effect.Effect#applyEffect()
	 */
	public void applyEffect(Player p) {
		instantEffect.applyEffect(p);
	}
	
	@Override
	public String toString() {
		return instantEffect.toString();
	}

}
