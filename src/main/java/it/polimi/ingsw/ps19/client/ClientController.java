package it.polimi.ingsw.ps19.client;


import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps19.command.PlayerMoveCommand;
import it.polimi.ingsw.ps19.command.toserver.ChosenLeaderCardCommand;
import it.polimi.ingsw.ps19.command.toserver.ChosenPrivilegeCommand;
import it.polimi.ingsw.ps19.command.toserver.ClientToServerCommand;
import it.polimi.ingsw.ps19.command.toserver.InvalidInputCommand;
import it.polimi.ingsw.ps19.command.toserver.PlaceIntoCouncilPalaceCommand;
import it.polimi.ingsw.ps19.command.toserver.PlaceIntoMarketCommand;
import it.polimi.ingsw.ps19.command.toserver.TakeCardCommand;
import it.polimi.ingsw.ps19.model.card.CardType;
import it.polimi.ingsw.ps19.network.NetworkInterface;
import it.polimi.ingsw.ps19.view.InputObserver;
import it.polimi.ingsw.ps19.view.UserInterface;
/**
 * @author matteo
 *
 *  this is the game controller of the MVC pattern
 */
public class ClientController implements InputObserver{

	private UserInterface userInterface;
	private NetworkInterface networkInterface;
	private ClientCommandHandler commandHandler;
	
	public ClientController(NetworkInterface networkInterface) {
		this.networkInterface = networkInterface;
	}
	

	
	private void sendCommand(ClientToServerCommand command){
		try {
			networkInterface.sendCommand(command);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void notifyName(String name) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void notifyPassword(String password){
		// TODO Auto-generated method stub
		
	}



	@Override
	public void notifyChosenLeaderCard(String leaderCardName) {
		sendCommand(new ChosenLeaderCardCommand(leaderCardName));
	}
	
	@Override
	public void notifyMove(String move){
		sendCommand(new PlayerMoveCommand(move));
	}
	
	@Override
	public void notifyChosenPrivileges(String choices){
		char[] charArray = choices.toCharArray();
		ArrayList<Integer> commandConstructor = new ArrayList<Integer>();
		for(int i = 0; i<charArray.length; i+=2){
			if(Character.getNumericValue(charArray[i]) != -1)
				commandConstructor.add(Character.getNumericValue(charArray[i]));
			else{
				userInterface.invalidInput();
				notifyInvalidInput();
				return;
			}
		}
		sendCommand(new ChosenPrivilegeCommand(commandConstructor));
	}

	

	public void setCommandHandler(ClientCommandHandler handler) {
		this.commandHandler = handler;
	}

	public void setUserInterface(UserInterface userInterface) {
		this.userInterface = userInterface;
	}



	@Override
	public void notifyInvalidInput() {
		sendCommand(new InvalidInputCommand());		
	}



	@Override
	public void notifyCouncilPalace(List<String> actionConstructor) {
		sendCommand(new PlaceIntoCouncilPalaceCommand(actionConstructor.get(0), Integer.parseInt(actionConstructor.get(1))));
	}



	@Override
	public void notifyTakeCardAction(List<String> actionConstructor) {
		TakeCardCommand takeCardCommand = new TakeCardCommand(actionConstructor.get(0),Integer.parseInt(actionConstructor.get(4)), Integer.parseInt(actionConstructor.get(2)), CardType.values()[Integer.parseInt(actionConstructor.get(4))-1]);
		sendCommand(takeCardCommand);
	}



	@Override
	public void notifyMarket(List<String> actionConstructor) {
		PlaceIntoMarketCommand placeIntoMarketCommand = new PlaceIntoMarketCommand(actionConstructor.get(0), actionConstructor.get(3), Integer.parseInt(actionConstructor.get(1)));
		sendCommand(placeIntoMarketCommand);
	}



	@Override
	public void notifyHarvest(List<String> actionConstructor) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void notifyProduction(List<String> actionConstructor) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void notifyDiscardedLeaderCard(String dicardedLeaderCard) {
		// TODO Auto-generated method stub
		
	}

}
