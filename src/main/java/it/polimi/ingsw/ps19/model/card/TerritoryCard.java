package it.polimi.ingsw.ps19.model.card;

import it.polimi.ingsw.ps19.model.Period;
import it.polimi.ingsw.ps19.model.effect.Effect;
import it.polimi.ingsw.ps19.model.resource.ResourceChest;

/**
 * A card of type Territory
 *
 * @author Mirko
 */
public class TerritoryCard extends DevelopmentCard {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1065227820762540803L;
	
	/** The harvest activation cost. */
	private int harvestActivationCost;

	/**
	 * Instantiates a new territory card.
	 *
	 * @param id the id
	 * @param name the name
	 * @param period the period
	 * @param immediateEffect the immediate effect
	 * @param permanentEffect the permanent effect
	 * @param harvestActivationCost the harvest activation cost
	 */
	public TerritoryCard(int id, String name, Period period, Effect immediateEffect,
		Effect permanentEffect,int harvestActivationCost) {
		super(id, name, period, new ResourceChest(), immediateEffect, permanentEffect);
		this.cardType=CardType.TERRITORY;
		this.harvestActivationCost=harvestActivationCost;
	}

    /**
     * Can activate harvest with a certain production value.
     *
     * @param harvestValue the harvest value
     * @return true, if the harvest effect of this card can be activated with the given production Value
     */
    public boolean canActivateHarvestWith(int harvestValue){
    	return harvestValue>harvestActivationCost;
		
	}
    
     /* (non-Javadoc)
      * @see it.polimi.ingsw.ps19.model.card.DevelopmentCard#toString()
      */
     @Override
    public String toString() {
    	StringBuilder string = new StringBuilder();
    	string.append(super.toString() + "\nHarvest cost: " + harvestActivationCost + "\nHarvest effect: ");
    	if(this.permanentEffect!=null)
    		string.append(permanentEffect.toString());
    	string.append("\n\n");
    	return string.toString();
    }

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps19.model.card.DevelopmentCard#getActivationCost()
	 */
	@Override
	public int getActivationCost() {
		return this.harvestActivationCost;
	}
	

}
