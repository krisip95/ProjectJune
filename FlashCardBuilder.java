package ProjectFlashcards;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class FlashCardBuilder {
	
	//Instance variables
	private JTextArea word;
	private JTextArea definition;
	private ArrayList<FlashCard> cardList;
	private JFrame frame;
	
	
	public FlashCardBuilder() {
		
		//Setuo UI
		frame = new JFrame("Flash Card");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create a JPanel to hold everything
		JPanel mainPanel = new JPanel();
		
		//Font
		Font greatFont = new Font("Helvetica Neue", Font.BOLD, 22);
		word = new JTextArea(6, 20);
		word.setLineWrap(true);
		word.setWrapStyleWord(true);
		word.setFont(greatFont);
		
		//Word area
		JScrollPane wJScrollPane = new JScrollPane(word);
		wJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		wJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//Answer area
		definition = new JTextArea(6, 20);
		definition.setLineWrap(true);
		definition.setWrapStyleWord(true);
		definition.setFont(greatFont);
		
		//JscrollPane
		JScrollPane dJScrollPane = new JScrollPane(definition);
		dJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		dJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		JButton nextButton = new JButton("Next Card");
		
		cardList = new ArrayList<FlashCard>();
		
		//Create a few labels
		JLabel wJLabel = new JLabel("Word");
		JLabel dJLabel = new JLabel("Definiton");
		
		
		//Add components to mainPanel
		mainPanel.add(wJLabel);
		mainPanel.add(wJScrollPane);
		mainPanel.add(dJLabel);
		mainPanel.add(dJScrollPane);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());
		
		//MenuBar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		
		menuBar.add(fileMenu); //dobavq new i save kum file
		
		
		//Add eventListeners
		newMenuItem.addActionListener(new NewMenuItemListener());
		saveMenuItem.addActionListener(new SaveMenuListener());
		
		frame.setJMenuBar(menuBar); //spetsifi4no mqsto kudeto trqbva da e file
		
		//Add to frame
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500, 600);
		frame.setVisible(true);
		
	
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new FlashCardBuilder(); //clear the deck
			}
		});
		
	}
	
	class NextCardListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO Auto-generated method stub
			
			//create a flashcard 
			FlashCard card = new FlashCard (word.getText(), definition.getText());
			cardList.add(card);
			
			System.out.println("Size of ArrayList" + cardList.size());
			
			clearCard();
		}

	}
			
		class NewMenuItemListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		}
		
		class SaveMenuListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub\
				
				FlashCard card = new FlashCard(word.getText(), definition.getText());
				cardList.add(card);
				
				//Create a file dialog with file chooser
				JFileChooser fileSave = new JFileChooser();
				fileSave.showSaveDialog(frame);
				saveFile(fileSave.getSelectedFile());
				
			}

			
		}
		
		private void clearCard() { //clear the entire screen
			word.setText("");
			definition.setText("");
			word.requestFocus();
			
		}
		
		private void saveFile(File selectedFile) {
			//TODO Auto-generated stub
			
			try {
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
				
				Iterator<FlashCard> cardIterator = cardList.iterator();
				while (cardIterator.hasNext()) {
					FlashCard card = (FlashCard)cardIterator.next();
					writer.write(card.getWord() + "/");
					writer.write(card.getDefinition() + "\n");
					
				}
				writer.close();
				
			} catch (Exception e) {
				//TODO: handle exception
				System.out.println("Couldn't write to file");
			}
			
		}
}
