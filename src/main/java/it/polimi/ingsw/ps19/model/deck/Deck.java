package it.polimi.ingsw.ps19.model.deck;

import java.util.Random;

import it.polimi.ingsw.ps19.Period;
import it.polimi.ingsw.ps19.model.card.DevelopmentCard;

/**
 * @author Mirko
 *
 */
public abstract class Deck<T extends DevelopmentCard> {

	private T[] cards;

	/**
	 * This method shuffles the deck mantaining the Periods' order (FIRST Period
	 * in from 0 to cards.length-1 etc. The code is similar to the one used by
	 * Collections, see Collections.shuffle
	 */
	public void shuffleDeck() {
		Random rnd = new Random();

		for (int i = cards.length / Period.values().length; i > 1; i--)
			swap(cards, i - 1, rnd.nextInt(i));
		for (int i = 2 * cards.length / Period.values().length; i > cards.length / Period.values().length + 1; i--)
			swap(cards, i - 1, rnd.nextInt(i));
		for (int i = cards.length; i > 2 * cards.length / Period.values().length + 1; i--)
			swap(cards, i - 1, rnd.nextInt(i));
	}

	/**
	 * @param arr
	 * @param i
	 * @param j
	 *            swaps cards in index i and j
	 */
	private static void swap(Object[] arr, int i, int j) {
		Object tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public int length(){
		return this.cards.length;
	}
	
	public void printCardInfo(int i){
		System.out.println(this.cards[i].getId());
		
	}
	
	
	/**
	 * @param i
	 * @return dynamic type T extends DevelopmentCard
	 */
	public T getCard(int i) {  //perchè non va cazzo?
//		System.out.println(this.cards[i]);
//		this.cards[i];
		return this.cards[i];
	}
	
}
