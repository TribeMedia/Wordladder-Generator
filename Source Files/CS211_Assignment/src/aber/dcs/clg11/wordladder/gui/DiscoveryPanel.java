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
 * Contains the GUI elements utilised by the user to run the 'Discovery' mode of the application.
 * Extends from {@link aber.dcs.clg11.wordladder.gui.GenericPanel}.
 * 
 * @author Connor Goddard (clg11)
 */
@SuppressWarnings("serial")
public class DiscoveryPanel extends GenericPanel {

	/** Button used to run the Breadth first search algorithm. */
	private JButton buttonDisc;

	/** The component title labels. */
	private JLabel startTitle, targetTitle;

	/** The two word input textboxes (start word and target word) */
	private JTextField textStart, textTarget;


	/**
	 * Instantiates a new DiscoveryPanel.
	 */
	public DiscoveryPanel() {

		//Create a tiled border around the panel
		this.setBorder(BorderFactory.createTitledBorder("Discovery"));

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
		startTitle = new JLabel("Start:");
		targetTitle = new JLabel("Target:");

		//Set maximum size of amount text box to 15 characters
		textStart = new JTextField(15);
		textTarget = new JTextField(15);

		//Create new instance of JButton with specified display text
		buttonDisc = new JButton("Run Search");

		//Link action listener to the button component
		buttonDisc.addActionListener(this);

		//Create new instance of JTextArea (used for output window)
		outputArea = new JTextArea();

		//Prevent the user from editing this text area
		outputArea.setEditable(false);

		//Create new instance of scroll-able window which overlays the 'outputArea' window
		outputScroll = new JScrollPane(outputArea);
		outputScroll.setPreferredSize(new Dimension(400, 100));

		//Add the components to the panel
		this.add(startTitle);
		this.add(targetTitle);
		this.add(textStart);
		this.add(textTarget);
		this.add(buttonDisc);
		this.add(outputScroll);
	}

	/**
	 *  Configures the 'SpringLayout' layout manager to organise all components on the panel.
	 */
	@Override
	public void setUpLayout() {

		//Set NORTH edge of 'startTitle' label, 10 pixels down from NORTH edge of panel 
		layout.putConstraint(SpringLayout.NORTH,startTitle,10,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,startTitle,10,SpringLayout.WEST, this); 

		layout.putConstraint(SpringLayout.NORTH,textStart,10,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,textStart,10,SpringLayout.EAST, startTitle);

		layout.putConstraint(SpringLayout.NORTH,targetTitle,10,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,targetTitle,10,SpringLayout.EAST, textStart); 

		layout.putConstraint(SpringLayout.NORTH,textTarget,10,SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,textTarget,10,SpringLayout.EAST, targetTitle);

		layout.putConstraint(SpringLayout.NORTH,buttonDisc,10,SpringLayout.SOUTH, textStart);
		layout.putConstraint(SpringLayout.WEST,buttonDisc,180,SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,outputScroll,10,SpringLayout.SOUTH, buttonDisc);
		layout.putConstraint(SpringLayout.WEST,outputScroll,35,SpringLayout.WEST, this);

	}

	/**
	 * @see aber.dcs.clg11.wordladder.gui.GenericPanel#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();

		//If the button contained within the panel is clicked..
		if (actionCommand.equals("Run Search")) {

			//Check if any of the textboxes are empty
			if (textStart.getText().equals("") || textTarget.getText().equals("")) {

				//If they are, display an error message box informing the user
				JOptionPane.showMessageDialog(new JFrame(), "Please ensure all fields are completed.", "WordLadder | Generator", JOptionPane.ERROR_MESSAGE);

				//Otherwise..
			} else {

				//Create new instance of an OutputStack
				OutputStack output = null;

				//Check that a breadth first search has successfully run..
				if (graph.breadthFirstSearch(textStart.getText(), textTarget.getText())) {

					//If it has, set the local stack to the OutputStack contained within the Graph object
					output = graph.getOutputStack();

					//Retrieve the total size of the stack (i.e the size of the shortest path word ladder)
					int length = output.sizeOf();

					//Initialise output window display string
					String ladder = "Word ladder: ";

					//Update the GUI output window informing the user that a shortest path between the two specified words has been retrieved
					//and display the size of the shortest path
					updateDisplay("SUCCESS: Shortest path between " + textStart.getText() + " and " + textTarget.getText() + " located in " + length + " steps.");

					//For all elements contained within the stack
					while (! output.isEmpty()) {

						//Retrieve the top element and append that to the word ladder display string
						ladder = ladder + " --> " + output.getHead();

						//Remove this top element from the stack
						output.pop();
					} 

					//Update the GUI output window with the complete word ladder
					updateDisplay(ladder);

					//Otherwise if the BFS returns false (i.e it cannot locate the shortest path between the two nodes)
				} else {

					//Update the GUI output window informing that a path cannot be retrieved.
					updateDisplay("FAILURE: Shortest path cannot be established.");

				}
			}

		}

	}

}
