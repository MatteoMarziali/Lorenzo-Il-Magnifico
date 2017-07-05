package it.polimi.ingsw.ps19.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import it.polimi.ingsw.ps19.Dice;
import it.polimi.ingsw.ps19.FamilyMember;
import it.polimi.ingsw.ps19.Player;
import it.polimi.ingsw.ps19.model.area.Board;
import it.polimi.ingsw.ps19.model.card.CardType;
import it.polimi.ingsw.ps19.model.resource.Resource;
import it.polimi.ingsw.ps19.model.resource.ResourceType;

/**
 * The content of the entire frame (MyFrame) during the GamePlay
 * 
 * @author Mirko
 *
 */
public class GamePanel extends JPanel implements ActionListener, MouseListener {

	protected static Dimension screenDim;

	private transient Toolkit toolkit = Toolkit.getDefaultToolkit();
	private JTextField textField;
	private BoardPanel boardPanel;

	private JButton sendChat;
	private JButton showPersonalBoard;
	private JButton strategyEditorButton;
	private JButton quitGameButton;
	private JButton showLeaderCardsButton;

	private MarketButton firstMarket;
	private MarketButton secondMarket;
	private MarketButton thirdMarket;
	private MarketButton fourthMarket;
	private CouncilButton councilButton;
	private SingleHarvestButton singleHarvestButton;
	private SingleProductionButton singleProductionButton;
	private MultipleHarvestButton multipleHarvestButton;
	private MultipleProductionButton multipleProductionButton;

	private JTextArea textArea;
	private PlayerResources playerResources;
	private final Font buttonsFont = new Font("SansSerif", Font.BOLD, 16);

	private List<CardButton> cards;

	private Container actionContentPane;

	private ActionPanel actionPanel;
	private ChooseAction chooseAction;
	private StrategyEditor strategyEditor;
	private EndOrDiscardPanel endOrDiscardPanel;
	private ChoosePrivilegePanel choosePrivilegePanel;

	private ArrayList<String> actionConstructor;

	private GraphicalUserInterface GUI;

	private Component currentActionPanel;

	private List<OrderMarkerDisk> orderMarkers;
	private Map<String, VictoryPointMarkerDisk> victoryMarkers;
	private Map<String, MilitaryPointMarkerDisk> militaryMarkers;
	private Map<String, FaithPointMarkerDisk> faithMarkers;
	private Map<String, FamilyMemberPawn> familiars; // the key is
														// diceColor+playerColor

	public GamePanel(String playerColor) {

		cards = new ArrayList<CardButton>();

		screenDim = toolkit.getScreenSize();
		// this.set(true);
		// setExtendedState(Frame.MAXIMIZED_BOTH);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(100, 100, 450, 300);
		// contentPane = new JPanel();
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new BorderLayout(0, 0));

		// BOARD

		boardPanel = new BoardPanel();
		add(boardPanel, BorderLayout.WEST);
		boardPanel.setLayout(null);

		// JButton btnNewButton = new JButton("New button");
		// btnNewButton.setContentAreaFilled(false);
		// btnNewButton.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		// null, null, null));
		// btnNewButton.setBounds(72, 72, 105, 176);
		// boardPanel.add(btnNewButton);

		// JButton btnNewButton_2 = new
		// CardButton(boardPanel.getPreferredSize(),1,0,55);
		// btnNewButton_2.setBounds(268, 256, 105, 170);
		// boardPanel.add(btnNewButton_2);

		// MarketButton market=new MarketButton();
		// market.setBounds(200,200,50,50);
		// boardPanel.add(market);

		System.out.println("BoardPanel preferredSize: " + boardPanel.getPreferredSize().getHeight() + " "
				+ boardPanel.getPreferredSize().getWidth());

		// RIGHT SCROLLBAR

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// scrollPane.setSize(new
		// Dimension(screenDim.width-panel.getPreferredSize().width, 100));
		scrollPane.setPreferredSize(new Dimension(screenDim.width - boardPanel.getPreferredSize().width, 500));
		add(scrollPane, BorderLayout.EAST);

		// PANEL CONTAINED IN THE SCROLLBAR

		JPanel rightScrollbarContainer = new JPanel();
		rightScrollbarContainer
				.setMaximumSize(new Dimension(screenDim.width - boardPanel.getPreferredSize().width, 20000));
		rightScrollbarContainer
				.setPreferredSize(new Dimension(screenDim.width - boardPanel.getPreferredSize().width, 1000));
		scrollPane.setViewportView(rightScrollbarContainer);
		rightScrollbarContainer.setLayout(new BoxLayout(rightScrollbarContainer, BoxLayout.Y_AXIS));

		// CHAT INTERNALFRAME

		JInternalFrame internalFrame = new JInternalFrame("Chat");
		internalFrame.setBackground(SystemColor.controlHighlight);
		rightScrollbarContainer.add(internalFrame);
		internalFrame.setPreferredSize(
				new Dimension(screenDim.width - boardPanel.getPreferredSize().width, screenDim.height / 3));
		internalFrame.setMaximumSize(
				new Dimension(screenDim.width - boardPanel.getPreferredSize().width, screenDim.height / 3));

		// CHAT TEXTAREA

		textArea = new JTextArea();
		textArea.setMargin(new Insets(1, 1, 1, 1));
		textArea.setText("ciaooo");
		textArea.setEditable(true);
		textArea.setLineWrap(true);
		textArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		textArea.setBackground(new Color(245, 200, 86));
		textArea.setFont(new Font("Consolas", 0, 20));

		// SCROLLPANE FOR CHAT

		JScrollPane scrPane = new JScrollPane(textArea);
		Border border = BorderFactory.createLoweredBevelBorder();
		scrPane.setBorder(border);

		JScrollBar vertical = scrPane.getVerticalScrollBar();
		vertical.setPreferredSize(new Dimension(0, 0));

		internalFrame.getContentPane().add(scrPane, BorderLayout.CENTER);

		JPanel chatInputPanel = new JPanel();
		internalFrame.getContentPane().add(chatInputPanel, BorderLayout.SOUTH);
		chatInputPanel.setLayout(new BorderLayout(0, 0));

		// CHAT INPUT BUTTON

		sendChat = new JButton("Send");
		sendChat.setForeground(new Color(255, 255, 255));
		sendChat.setBackground(new Color(102, 51, 51));
		sendChat.setFont(buttonsFont);
		sendChat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		sendChat.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		chatInputPanel.add(sendChat, BorderLayout.EAST);

		// TEXTFIELD

		JPanel textFieldOuterPanel = new JPanel();
		textFieldOuterPanel.setBackground(new Color(160, 82, 45));
		chatInputPanel.add(textFieldOuterPanel, BorderLayout.CENTER);
		textFieldOuterPanel.setLayout(new BorderLayout(0, 0));

		textField = new JTextField();
		textField.setPreferredSize(new Dimension(100, 26));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldOuterPanel.add(textField);
		textField.setColumns(50);

		// PLAYER RESOURCES PANEL

		playerResources = new PlayerResources(screenDim.width - boardPanel.getPreferredSize().width, playerColor);
		// playerResources.setPreferredSize(new
		// Dimension(screenDim.width-panel.getPreferredSize().width,400));
		// playerResources.setMaximumSize(new
		// Dimension(screenDim.width-panel.getPreferredSize().width,800));

		rightScrollbarContainer.add(playerResources);

		// ACTIONS INTERNAL FRAME

		JInternalFrame actionsInternalFrame = new JInternalFrame("Game actions");
		actionsInternalFrame.setMaximizable(true);
		actionsInternalFrame.getContentPane().setBackground(new Color(160, 82, 45));
		actionsInternalFrame.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		actionsInternalFrame.setResizable(true);
		// internalFrame_1.setIconifiable(true);
		// internalFrame_1.setClosable(true);
		// internalFrame_1.setResizable(true);

		// setBorder(val ? null : border);

		// internalFrame_1.setRootPaneCheckingEnabled(false);
		// internalFrame_1.getUI().setNorthPane(val ? null : northPane);
		// internalFrame_1.setRootPaneCheckingEnabled(true);
		actionsInternalFrame.setBounds(new Rectangle(0, 0, 500, 0));
		rightScrollbarContainer.add(actionsInternalFrame);
		actionsInternalFrame.getContentPane().setLayout(new BorderLayout(0, 0));

		actionContentPane = actionsInternalFrame.getContentPane();

		// BUTTONS: Market, council, production and harvest

		firstMarket = new MarketButton(1);
		firstMarket.setBounds(580, 1180, 50, 50); // non ci devono essere i
													// setbounds qui
		firstMarket.addActionListener(this);
		boardPanel.add(firstMarket);

		secondMarket = new MarketButton(2);
		secondMarket.setBounds(650, 1200, 50, 50);
		secondMarket.addActionListener(this);
		boardPanel.add(secondMarket);

		thirdMarket = new MarketButton(3);
		thirdMarket.setBounds(720, 1220, 50, 50);
		thirdMarket.addActionListener(this);
		boardPanel.add(thirdMarket);

		fourthMarket = new MarketButton(4);
		fourthMarket.setBounds(800, 1250, 50, 50);
		fourthMarket.addActionListener(this);
		boardPanel.add(fourthMarket);

		councilButton = new CouncilButton();
		councilButton.setBounds(500, 800, 200, 90);
		councilButton.addActionListener(this);
		boardPanel.add(councilButton);

		singleHarvestButton = new SingleHarvestButton();
		singleHarvestButton.addActionListener(this);
		boardPanel.add(singleHarvestButton);

		singleProductionButton = new SingleProductionButton();
		singleProductionButton.addActionListener(this);
		boardPanel.add(singleProductionButton);

		multipleHarvestButton = new MultipleHarvestButton();
		multipleHarvestButton.addActionListener(this);
		boardPanel.add(multipleHarvestButton);

		multipleProductionButton = new MultipleProductionButton();
		multipleProductionButton.addActionListener(this);
		boardPanel.add(multipleProductionButton);

		// ACTION PANELS

		actionPanel = new ActionPanel(this);
		actionPanel.setBackground(new Color(204, 153, 51));
		actionPanel.setVisible(false);
		// actionContentPane.add(actionPanel);

		chooseAction = new ChooseAction(this);
		chooseAction.setBackground(new Color(204, 153, 51));
		chooseAction.setVisible(false);
		// actionContentPane.add(chooseAction);
		// actionsInternalFrame.getContentPane().add(actionPanel);

		strategyEditor = new StrategyEditor();
		strategyEditor.setVisible(false);
		// actionContentPane.add(strategyEditor);

		endOrDiscardPanel = new EndOrDiscardPanel(this);
		endOrDiscardPanel.setBackground(new Color(204, 153, 51));
		endOrDiscardPanel.setVisible(false);

		choosePrivilegePanel = new ChoosePrivilegePanel(screenDim.width - boardPanel.getPreferredSize().width, this);
		choosePrivilegePanel.setBackground(new Color(204, 153, 51));
		choosePrivilegePanel.setVisible(false);
		// FINAL BUTTONS PANEL

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(new Color(210, 180, 140));
		rightScrollbarContainer.add(buttonsPanel);
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		showLeaderCardsButton = new JButton("Show Leader Cards");
		showLeaderCardsButton.setFont(buttonsFont);
		showLeaderCardsButton.setForeground(new Color(255, 255, 255));
		showLeaderCardsButton.setBackground(new Color(102, 51, 51));
		showLeaderCardsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(showLeaderCardsButton);

		showPersonalBoard = new JButton("Show Personal Board");
		showPersonalBoard.setFont(buttonsFont);
		showPersonalBoard.setBackground(new Color(102, 51, 51));
		showPersonalBoard.setForeground(new Color(255, 255, 255));
		showPersonalBoard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(showPersonalBoard);

		strategyEditorButton = new JButton("Strategy editor");
		strategyEditorButton.setFont(buttonsFont);
		strategyEditorButton.setBackground(new Color(102, 51, 51));
		strategyEditorButton.setForeground(new Color(255, 255, 255));
		strategyEditorButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		strategyEditorButton.addActionListener(this);
		buttonsPanel.add(strategyEditorButton);

		quitGameButton = new JButton("Quit Game");
		quitGameButton.setFont(buttonsFont);
		quitGameButton.setBackground(new Color(20, 20, 0));
		quitGameButton.setForeground(new Color(255, 255, 255));
		quitGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		quitGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUI.notifyCloseGame();
			}
		});
		buttonsPanel.add(quitGameButton);

		actionsInternalFrame.setVisible(true);
		internalFrame.setVisible(true);

		orderMarkers = new ArrayList<OrderMarkerDisk>();
		victoryMarkers = new HashMap<String, VictoryPointMarkerDisk>();
		militaryMarkers = new HashMap<String, MilitaryPointMarkerDisk>();
		faithMarkers = new HashMap<String, FaithPointMarkerDisk>();
		familiars = new HashMap<String, FamilyMemberPawn>();
	}

	public void addCard(int tower, int floor, int id, String descr) {
		if (tower == 1)
			tower = 2;
		else if (tower == 2)
			tower = 1;
		CardButton card = new CardButton(boardPanel.getPreferredSize(), tower, floor, id);
		// btnNewButton_2.setBounds(268, 256, 105, 170);
		card.addActionListener(this);
		card.setToolTipText(descr);
		boardPanel.add(card);
		cards.add(card);

	}

	public JButton getSendChat() {
		return sendChat;
	}

	public JButton getShowPersonalBoard() {
		return showPersonalBoard;
	}

	public String getAndDeleteChatInput() {
		String m = this.textField.getText();
		this.textField.setText("");
		return m;

	}

	public void addMessageToConsole(String message) {
		message = "\n" + message;
		this.textArea.append(message);
		this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
	}

	private void writeGameMessage(String string) {
		addMessageToConsole("\n<-GAME-> " + string + "\n");
	}

	public void setMarkerOrder(ArrayList<OrderMarkerDisk> markers) {
		for (OrderMarkerDisk m : markers) {
			paint(m.getGraphics());
			m.setAlignmentX((float) 782.23);
			m.setAlignmentY(792);
		}
	}

	public void setMarkerVictoryPoints() {

	}

	public void setMarkerMilitaryPoint() {

	}

	public void setChurchMarker() {

	}

	public void addResourceToPlayerStatus(Resource resourceInChest) {
		playerResources.refreshResource(resourceInChest);
	}

	public void addFamilyMembersToPlayerStatus(HashMap<it.polimi.ingsw.ps19.Color, FamilyMember> familyMembers) {
		playerResources.refreshFamilyMembers(familyMembers);
	}

	public void removeCards() {
		cards.forEach(card -> boardPanel.remove(card));

	}

	public BoardPanel getBoardPanel() {
		return boardPanel;
	}

	private void showActionPanel(Component panel) {
		// rimuovo tutti gli altri panels
		// chooseAction.setVisible(false);
		// actionContentPane.remove(chooseAction);
		setEveryoneInvisible(this.actionContentPane.getComponents());
		this.actionContentPane.removeAll();
		this.actionContentPane.add(panel);
		panel.setVisible(true);
	}

	private void setEveryoneInvisible(Component[] components) {
		for (int i = 0; i < components.length; i++)
			components[i].setVisible(false);
	}

	private void removeActionPanel() {
		this.actionContentPane.removeAll();
		actionContentPane.repaint();
	}

	public void showChooseAction() {
		System.out.println("show choose action");
		this.currentActionPanel = chooseAction;
		this.showActionPanel(chooseAction);

	}

	public void setObserver(GraphicalUserInterface GUI) {
		this.GUI = GUI;
	}

	public void notifyActionClick() {
		writeGameMessage("Choose the family member and the amount of servants "
				+ "you intend to use in this action, then press the area"
				+ "you want to place your family member into");
		this.currentActionPanel = actionPanel;
		this.showActionPanel(actionPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof CardButton) {
			CardButton card = (CardButton) e.getSource();
			constructAction(card);
			this.GUI.notifyTakeCard(actionConstructor);

		} else if (e.getSource() == strategyEditorButton) {
			if (!strategyEditor.isVisible())
				showActionPanel(strategyEditor);
			else
				backToCurrentAction();

		} else if (e.getSource() instanceof MarketButton) {
			String market = ((MarketButton) e.getSource()).getName();
			constructAction(market);
			if (actionConstructor.size() == 4) {
				this.GUI.notifyMarketAction(actionConstructor);
			} else {
				System.out.println("gamepanel: market actionconstructor non con 4 elementi");
			}

		} else if (e.getSource() instanceof CouncilButton) {
			constructCouncilAction();
			 this.GUI.notifyCouncilAction(actionConstructor);

		} else if (e.getSource() instanceof SingleHarvestButton) {
			addFamServToConstructor();
			actionConstructor.add("harvest");
			actionConstructor.add("1");
			this.GUI.notifyHarvest(actionConstructor);

		} else if (e.getSource() instanceof SingleProductionButton) {
			addFamServToConstructor();
			actionConstructor.add("production");
			actionConstructor.add("1");
			this.GUI.notifyProduction(actionConstructor);

		} else if (e.getSource() instanceof MultipleHarvestButton) {
			addFamServToConstructor();
			actionConstructor.add("harvest");
			actionConstructor.add("2");
			this.GUI.notifyHarvest(actionConstructor);

		} else if (e.getSource() instanceof MultipleProductionButton) {
			addFamServToConstructor();
			actionConstructor.add("production");
			actionConstructor.add("2");
			this.GUI.notifyProduction(actionConstructor);

		}

	}

	private void addFamServToConstructor() {
		actionConstructor = new ArrayList<String>();
		String familyMember;
		String servants; // number
		
		familyMember = actionPanel.getFamilyMember();

		if (familyMember == "none") {
			invalidInputMessage("Select a family member");
			return;
		}

		servants = actionPanel.getServants();
		
		actionConstructor.add(familyMember);
		actionConstructor.add(servants);
	}

	private void constructCouncilAction() {
		addFamServToConstructor();

	}

	private void constructAction(String market) {
		System.out.println("gamepanel:constructing marketaction");
		actionConstructor = new ArrayList<String>();

		String familyMember;
		String servants; // number
		String actionSpace = market; // FIRST,SECOND,THIRD,FOURTH as the place
										// of the marker spot from the left to
										// the
		// right on the board
		familyMember = actionPanel.getFamilyMember();

		if (familyMember == "none") {
			invalidInputMessage("Select a family member");
			return;
		}

		servants = actionPanel.getServants();

		// I have to adapt the order because it was made that way in the
		// ClietController
		// order: 0-family member, 1-servants, 2-doesn't matter (actiontype),
		// 3-actionspace

		actionConstructor.add(familyMember);
		actionConstructor.add(servants);
		actionConstructor.add("market");
		actionConstructor.add(actionSpace);

	}

	private void backToCurrentAction() {
		showActionPanel(currentActionPanel);
	}

	private void constructAction(CardButton card) {
		// order; 0-member, 1-servants, 2-unused, 3-cardtype,4-floor
		actionConstructor = new ArrayList<String>();

		String familyMember;
		String floor; // number
		String servants; // number
		int tower;
		String cardType; // number
		familyMember = actionPanel.getFamilyMember();

		if (familyMember == "none") {
			invalidInputMessage("Select a family member");
			return;
		}

		floor = "" + card.getFloor();
		tower = card.getTower();

		if (tower == 1)
			tower = 2;
		else if (tower == 2)
			tower = 1;

		tower = tower + 1; // to use the conventions of CLI...

		servants = actionPanel.getServants();
		cardType = "" + tower; // scambio 2 torri centrali

		System.out.println("GamePanel: costructAction: member:" + familyMember + " servants:" + servants
				+ " cardtype/tower:" + cardType + " floor:" + floor);

		actionConstructor.add(familyMember);
		actionConstructor.add(servants);
		actionConstructor.add("takecard");
		actionConstructor.add(cardType);
		actionConstructor.add(floor);
	}

	private void invalidInputMessage(String string) {
		this.addMessageToConsole("--INVALID INPUT--: " + string);

	}

	public void notifyEndRound() {
		writeGameMessage("Your round has ended");
		this.removeActionPanel();
		GUI.notifyEndRound();
	}

	public void showEndOrDiscard() {
		this.currentActionPanel = endOrDiscardPanel;
		this.showActionPanel(endOrDiscardPanel);
	}

	public void showChoosePrivilege() {
		this.currentActionPanel = choosePrivilegePanel;
		this.showActionPanel(choosePrivilegePanel);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof JResource) {
			String chosenP = ((JResource) e.getSource()).getName();
			// choices.add(Integer.parseInt(((JResource)e.getSource()).getName()));
			System.out.println("choicepriv:" + chosenP);
			this.removeActionPanel();
			this.GUI.notifyChosenPrivilege(chosenP);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void createMarkers(Board board) {
		if (orderMarkers.isEmpty()) {
			for (int i = 0; i < board.getPlayerOrder().size(); i++) {
				orderMarkers.add(new OrderMarkerDisk(board.getPlayerOrder().get(i)));
				boardPanel.add(orderMarkers.get(i));
				victoryMarkers.put(board.getPlayerOrder().get(i),
						new VictoryPointMarkerDisk(board.getPlayerOrder().get(i)));
				militaryMarkers.put(board.getPlayerOrder().get(i),
						new MilitaryPointMarkerDisk(board.getPlayerOrder().get(i)));
				faithMarkers.put(board.getPlayerOrder().get(i),
						new FaithPointMarkerDisk(board.getPlayerOrder().get(i)));
				boardPanel.add(victoryMarkers.get(board.getPlayerOrder().get(i)));
				boardPanel.add(militaryMarkers.get(board.getPlayerOrder().get(i)));
				boardPanel.add(faithMarkers.get(board.getPlayerOrder().get(i)));
			}
		}
	}

	public void PlaceFamiliars(Board board) {

		ArrayList<FamilyMemberPawn> familiars = new ArrayList<FamilyMemberPawn>();

		for (int i = 0; i < board.getCouncilPalace().getMembers().size(); i++) {
			familiars.add(this.familiars.get(board.getCouncilPalace().getMembers().get(i).getColor().toString()
					+ board.getCouncilPalace().getMembers().get(i).getPlayer().getColor()));
			boardPanel.add(familiars.get(i));
			familiars.get(i).PlaceFamiliarIntoCouncilPalace();
		}
		for (FamilyMemberPawn f : familiars) {
			f.ResetAllCounters();
		}
		familiars.clear();
		
		for(int i=1; i < board.getPlayerOrder().size(); i++){
			if(board.getMarket().getMarktActionSpace(String.valueOf(i)).getFamilyMember()!=null){
			familiars.add(this.familiars.get(board.getMarket().getMarktActionSpace(String.valueOf(i)).getFamilyMember().getColor().toString() +
					board.getMarket().getMarktActionSpace(String.valueOf(i)).getFamilyMember().getPlayer().getColor()));
			boardPanel.add(familiars.get(i));
			familiars.get(i-1).PlaceFamiliarIntoMarket(i);
			} else familiars.add(null);
		}
		for(FamilyMemberPawn f:familiars){
			if(f!=null)
				f.ResetAllCounters();
		}
		familiars.clear();

		for (int i = 0; i < board.getHarvestArea().getMultipleActionSpace().getMembers().size(); i++) {
			familiars.add(this.familiars.get(
					board.getHarvestArea().getMultipleActionSpace().getMembers().get(i).getColor().toString() + board
							.getHarvestArea().getMultipleActionSpace().getMembers().get(i).getPlayer().getColor()));
			boardPanel.add(familiars.get(i));
			familiars.get(i).PlaceFamiliarIntoHarvestArea("2");
		}
		for (FamilyMemberPawn f : familiars) {
			f.ResetAllCounters();
		}
		familiars.clear();
		if (board.getHarvestArea().getSingleActionSpace().getFamilyMember() != null) {
			familiars.add(this.familiars
					.get(board.getHarvestArea().getSingleActionSpace().getFamilyMember().getColor().toString()
							+ board.getHarvestArea().getSingleActionSpace().getFamilyMember().getPlayer().getColor()));
			boardPanel.add(familiars.get(0));
			familiars.get(0).PlaceFamiliarIntoHarvestArea("1");
			for (FamilyMemberPawn f : familiars) {
				f.ResetAllCounters();
			}
			familiars.clear();
		}
		for (int i = 0; i < board.getProductionArea().getMultipleActionSpace().getMembers().size(); i++) {
			familiars.add(this.familiars.get(
					board.getProductionArea().getMultipleActionSpace().getMembers().get(i).getColor().toString() + board
							.getProductionArea().getMultipleActionSpace().getMembers().get(i).getPlayer().getColor()));
			boardPanel.add(familiars.get(i));
			familiars.get(i).PlaceFamiliarIntoProductionArea("2");
		}
		for (FamilyMemberPawn f : familiars) {
			f.ResetAllCounters();
		}
		familiars.clear();
		if (board.getProductionArea().getSingleActionSpace().getFamilyMember() != null) {
			familiars.add(this.familiars.get(
					board.getProductionArea().getSingleActionSpace().getFamilyMember().getColor().toString() + board
							.getProductionArea().getSingleActionSpace().getFamilyMember().getPlayer().getColor()));
			boardPanel.add(familiars.get(0));
			familiars.get(0).PlaceFamiliarIntoProductionArea("1");
			for (FamilyMemberPawn f : familiars) {
				f.ResetAllCounters();
			}
			familiars.clear();
		}
		for (CardType c : CardType.values()) {
			if (c != CardType.ANY) {
				for (int i = 0; i < board.getTower(c).getFloors().size(); i++) {
					if (board.getFloor(c, i).getActionSpace().getFamilyMember() != null) {
						familiars.add(this.familiars.get(
								board.getFloor(c, i).getActionSpace().getFamilyMember().getColor().toString() + board
										.getFloor(c, i).getActionSpace().getFamilyMember().getPlayer().getColor()));
						boardPanel.add(familiars.get(i));
						familiars.get(i).PlaceFamiliarInTower(c.toString().toLowerCase(Locale.ROOT), i);
					}
					familiars.add(null);
				}
				for(FamilyMemberPawn f:familiars){
					if(f != null){
					f.ResetAllCounters();
					}
				}
				familiars.clear();
			}
		}

	}

	victoryMarkers.get(p.getColor()).setVictoryPointsAmount(
			p.getResourceChest().getResourceInChest(ResourceType.VICTORYPOINT).getAmount());
	militaryMarkers.get(p.getColor()).setMilitaryPointsAmount(
			p.getResourceChest().getResourceInChest(ResourceType.MILITARYPOINT).getAmount());
		faithMarkers.get(p.getColor()).setFaithPointsAmount(
				p.getResourceChest().getResourceInChest(ResourceType.FAITHPOINT).getAmount());
	}

	public void updateOrder(Board board) {
		for (int i = 0; i < board.getPlayerOrder().size(); i++) {
			for (int j = 0; j < orderMarkers.size(); j++) {
				if (orderMarkers.get(j).getSrc() == board.getPlayerOrder().get(i)) {
					OrderMarkerDisk flag = orderMarkers.get(i);
					OrderMarkerDisk old = orderMarkers.get(j);
					orderMarkers.set(j, flag);
					orderMarkers.set(i, old);
				}
			}

		}
	}

	public void populateFamiliars(Board board) {
		if (familiars.isEmpty()) {
			for (String s : board.getPlayerOrder()) {
				for (Dice d : Dice.values()) {

					familiars.put(d.getColor().toString() + s, new FamilyMemberPawn(d.getColor().toString(), s));
					System.out.println("ho creato una pawn " + d.getColor().toString() + s);

				}
			}
		}
	}

	public ArrayList<OrderMarkerDisk> getOrderMarkers() {
		return (ArrayList<OrderMarkerDisk>) orderMarkers;
	}

	public Map<String, VictoryPointMarkerDisk> getVictoryMarkers() {
		return victoryMarkers;
	}

	public Map<String, MilitaryPointMarkerDisk> getMilitaryMarkers() {
		return militaryMarkers;
	}

	public Map<String, FaithPointMarkerDisk> getFaithMarkers() {
		return faithMarkers;
	}

}
