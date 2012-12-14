package aber.dcs.clg11.wordladder.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import aber.dcs.clg11.wordladder.model.OutputStack;

/**
 * Contains the GUI elements utilised by the user to run the 'Generation' mode of the application.
 * Extends from {@link aber.dcs.clg11.wordladder.gui.GenericPanel}.
 * 
 * @author Connor Goddard (clg11)
 */
@SuppressWarnings("serial")
public class GeneratePanel extends GenericPanel {

	/** Button used to run the Breadth first search algorithm. */
	private JButton buttonGen;

	/** The component title labels. */
	private JLabel inputTitle, lengthTitle;

	/** The two input text boxes (start node and word ladder length) */
	private JTextField textWord, textLength;


	/**
	 * Instantiates a new GeneratePanel.
	 */
	public GeneratePanel() {

		//Create a titled border around the panel
		this.setBorder(BorderFactory.createTitledBorder("Generation"));

		//Initialise all panel components
		this.initComponents();

		//Define panel layout settings for layout manager
		this.setUpLayout();

	}

	/**
	 * Initialises the panel components (including linking components to listeners where required) before
	 * adding the components to the panel.
	 */
	@Override
	public void initComponents() {

		//Create new instance of JLabel with specified display text
		inputTitle = new JLabel("Search term:");
		lengthTitle = new JLabel("Length:");

		//Set maximum size of text box characters
		textWord = new JTextField(7);
		textLength = new JTextField(3);

		//Create new instance of JButton with specified display text
		buttonGen = new JButton("Generate Ladder");

		//Link action listener to the button component
		buttonGen.addActionListener(this);

		//Create new instance of JTextArea (used for output window)
		outputArea = new JTextArea();

		//Prevent the user from editing this text area
		outputArea.setEditable(false);

		//Create new instance of scroll-able window which overlays the 'outputArea' window
		outputScroll = new JScrollPane(outputArea);
		outputScroll.setPreferredSize(new Dimension(400, 100));

		//Add the components to the panel
		this.add(inputTitle);
		this.add(textWord);
		this.add(lengthTitle);
		this.add(textLength);
		this.add(buttonGen);
		this.add(outputScroll);
	}

	/**
	 * Configures the 'SpringLayout' layout manager to organise all components on the panel.
	 */
	@Override
	public void setUpLayout() {

		//Set NORTH edge of 'inputTitle' label, 10 pixels down from NORTH edge of panel 
		layout.putConstraint(SpringLayout.NORTH,inputTitle,10,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,inputTitle,10,SpringLayout.WEST, this); 

		layout.putConstraint(SpringLayout.NORTH,textWord,10,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,textWord,10,SpringLayout.EAST, inputTitle);

		layout.putConstraint(SpringLayout.NORTH,lengthTitle,10,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,lengthTitle,10,SpringLayout.EAST, textWord); 

		layout.putConstraint(SpringLayout.NORTH,textLength,10,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,textLength,10,SpringLayout.EAST, lengthTitle);

		layout.putConstraint(SpringLayout.NORTH,buttonGen,5,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,buttonGen,10,SpringLayout.EAST, textLength);

		layout.putConstraint(SpringLayout.NORTH,outputScroll,30,SpringLayout.NORTH, textWord);
		layout.putConstraint(SpringLayout.WEST,outputScroll,35,SpringLayout.WEST, this);

	}

	/**
	 * @see aber.dcs.clg11.wordladder.gui.GenericPanel#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();

		//If the button contained within the panel is clicked..
		if (actionCommand.equals("Generate Ladder")) {	

			try {

				//Check if any of the input textboxes are empty..
				if (textWord.getText().equals("") || textLength.getText().equals("")) {

					//If they are, display an error message box informing the user of this
					JOptionPane.showMessageDialog(new JFrame(), "Please ensure all fields are completed.", "WordLadder | Generator", JOptionPane.ERROR_MESSAGE);

					//Otherwise verify that the graph has been generated..
				} else if (graph == null) {

					//If it has not, inform the user of this by displaying a message box
					JOptionPane.showMessageDialog(new JFrame(), "Graph is still generating - please wait.", "WordLadder | Generator", JOptionPane.ERROR_MESSAGE);

					//Otherwise..
				} else {

					//Retrieve the maximum word ladder length entered by the user
					//Deduct one from the value to account for the start node within the ladder
					int iterate = (Integer.parseInt(textLength.getText()) - 1);

					//If the depth limited search was successful (using the data entered by the user)
					if (graph.depthSearch(this.textWord.getText(), 0, iterate)) {

						//If it was, initialise a new local stack populated by the OutputStack contained within the Graph object
						OutputStack output = graph.getOutputStack();

						//Obtain the total size of the generated word ladder
						int length = output.sizeOf();

						//Initialise output window display string
						String ladder = "Word ladder: " + textWord.getText();

						//For all elements contained within the stack
						while (! output.isEmpty()) {

							//Retrieve the top element and append that to the word ladder display string
							ladder = ladder + " --> " + output.getHead();

							//Remove this top element from the stack
							output.pop();
						} 

						//Check if a word ladder was able to be produced
						if (length == 0) {

							//If not, display a message to the GUI output window informing the user
							updateDisplay("FAILURE: No words link from " + textWord.getText() + ".");

							//Otherwise check if the maximum possible length of the word ladder < the specified ladder length
						} else if (length < iterate) {

							//If this is the case, inform the user of this fact before outputting the generated word ladder
							updateDisplay("WARNING: Maximum ladder length available - " + (length + 1) + " words.");
							updateDisplay(ladder);

							//Otherwise, if a word ladder of the speified length was able to the generated
						} else {

							//Display message to GUI output window informing user
							updateDisplay("SUCCESS: Generation completed successfully.");	

							//Display the generated word ladder in the GUI output window
							updateDisplay(ladder);
						}


						//Otherwise if the DLS returned false (i.e. the entered word does not exist within the graph)
					} else {

						//Display message to user via GUI output window informing them of this
						updateDisplay("FAILURE: Ladder cannot be generated - entered value does not exist.");
					}

				}

				//If the user enters a sting into the 'word ladder length' text box..
			} catch (NumberFormatException nF) {

				//Display error message dialog requesting them to change the value
				JOptionPane.showMessageDialog(new JFrame(), "Please enter an integer value for ladder length", "WordLadder | Generator", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

}
