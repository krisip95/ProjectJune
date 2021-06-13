package ProjectFlashcards;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class FlashCardPlayer {

	private JTextArea display;
	private JTextArea defninition;
	private ArrayList<FlashCard> cardList;
	private Iterator<FlashCard> cardIterator;
	private FlashCard currentCard;
	private int currentCarsIndex;
	private JButton showDefinition;
	private JFrame frame;
	private boolean isShowDefinition;

	public FlashCardPlayer() {

		// Build User Interface
		frame = new JFrame("Flash Card Player");
		JPanel mainPanel = new JPanel();
		Font mFont = new Font("Helevatica Neue", Font.BOLD, 22);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		display = new JTextArea(10, 20);
		display.setFont(mFont);

		JScrollPane wJScrollPane = new JScrollPane(display);
		wJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		wJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		showDefinition = new JButton("Show Definition");

		mainPanel.add(wJScrollPane);
		mainPanel.add(showDefinition);
		showDefinition.addActionListener(new NextCardListener());

		// Add Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load Card Set");
		loadMenuItem.addActionListener(new OpenMenuListener());

		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);

		// Add to frame
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(640, 500);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new FlashCardPlayer();
			}
		});

	}

	class NextCardListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (isShowDefinition) {
				display.setText(currentCard.getDefinition());
				showDefinition.setText("NextCard");
				isShowDefinition = false;

			} else {

				// show next question
				if (cardIterator.hasNext()) {
					showNextCard();
				} else {
					// no more cards
					display.setText("That was the last card.");
					showDefinition.setEnabled(false);
				}
			}

		}

	}

	class OpenMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());

		}

	}

	private void loadFile(File selectedFile) {
		// TODO Auto-generated method stub

		cardList = new ArrayList<FlashCard>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
			String line = null;

			while ((line = reader.readLine()) != null) { // we want this to happen until we find a null
				makeCard(line);

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Couldn't read file");
			e.printStackTrace();
		}

		// show the first card
		cardIterator = cardList.iterator();
		showNextCard();
	}

	private void makeCard(String lineToParse) { // vzima zapazenoto ot reader v file

		StringTokenizer result = new StringTokenizer(lineToParse, "/");
		if (result.hasMoreTokens()) {
			FlashCard card = new FlashCard(result.nextToken(), result.nextToken());
			cardList.add(card);
		}
		// String[] result = lineToParse.split("/"); // [word -> index 0, definition ->
		// index 1]

		// FlashCard card = new FlashCard(result[0], result[1]);
		// cardList.add(card);
		// System.out.println("Made a card");
	}

	private void showNextCard() {
		// TODO Auto-generated method stub
		currentCard = (FlashCard) cardIterator.next();

		display.setText(currentCard.getWord());
		showDefinition.setText("Show Definition");
		isShowDefinition = true;

	}
}