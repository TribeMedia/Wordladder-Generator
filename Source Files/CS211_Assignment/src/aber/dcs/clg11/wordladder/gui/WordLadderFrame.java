package aber.dcs.clg11.wordladder.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


import aber.dcs.clg11.wordladder.fileIO.ReadFile;
import aber.dcs.clg11.wordladder.model.Graph;

/**
 * Main JFrame for displaying program GUI. Responsible for initialising the 
 * main data structure class {@link aber.dcs.clg11.wordladder.model.Graph}, as well as
 * the two GUI sub-panels {{@link aber.dcs.clg11.wordladder.gui.GeneratePanel} & 
 * {@link aber.dcs.clg11.wordladder.gui.DiscoveryPanel}. Passes the new Graph object to the base panels
 * as a parameter to allow access to the data model from the sub-panels.
 */
@SuppressWarnings("serial")
public class WordLadderFrame extends JFrame {

	/** The new GeneratePanel component. */
	private GeneratePanel generatePan;

	/** The new DiscoveryPanel component. */
	private DiscoveryPanel discoveryPan;
	
	/** FileChooser dialog object used to select external dictionary files. */
	private JFileChooser fc = new JFileChooser();

	/** The {@link aber.dcs.clg11.wordladder.model.Graph} data structure object. */
	private Graph graph;
	
	/** The {@link aber.dcs.clg11.wordladder.fileIO.ReadFile} object used to parse external files. */
	private ReadFile fileIO = new ReadFile();


	/**
	 * Instantiates a new WordLadderFrame
	 */
	public WordLadderFrame() {

		//Initialise and set up frame
		this.setTitle("WordLadder - By Connor Goddard (clg11)");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Prevent user resizing frame
		this.setResizable(false);

		//Initialise the two sub-panels
		generatePan = new GeneratePanel();
		discoveryPan = new DiscoveryPanel();

		//Add the two panels to the north and south of the frame respectively.
		this.add(generatePan, BorderLayout.NORTH);
		this.add(discoveryPan, BorderLayout.SOUTH);

		//Fit frame to ensure all panels/components are visible
		this.pack();

		//Determine centre of user's screen and position frame accordingly
		Toolkit k=Toolkit.getDefaultToolkit();
		Dimension d=k.getScreenSize();
		this.setLocation(d.width/2-this.getWidth()/2,d.height/2-this.getHeight()/2);

		//Display frame on screen
		this.setVisible(true);

		//Update both output window displays and inform user that the system is currently genertaing the graph
		updateDisplays("Welcome to the Word Ladder App - By Connor Goddard (clg11)");
		updateDisplays("*********************************************************************");
		updateDisplays("Generating graph...");

		//Run method to load dictionary file and generate graph
		loadFile();

		if (graph.getGraphSize() > 0) {

			updateDisplays("Graph created successfully. Total nodes: " + graph.getGraphSize());
		}

	}


	/**
	 * Update displays.
	 *
	 * @param update the update
	 */
	public void updateDisplays(String update) {

		generatePan.updateDisplay(update);
		discoveryPan.updateDisplay(update);

	}

	/**
	 * Load file.
	 */
	public void loadFile() {

		//Display dialog asking user if they wish to use a internal dictionary file, 
		//or load a new external file
		int exit = JOptionPane.YES_NO_CANCEL_OPTION;
		exit = JOptionPane.showConfirmDialog(null, "Would you like to use an internal dictionary file?", "WordLadder | Start-Up", JOptionPane.YES_NO_CANCEL_OPTION);

		//If user selects "No"..
		if (exit == JOptionPane.NO_OPTION) {

			//Ensure user can only select files (not directories)
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			//Removes any existing file filters
			for (FileFilter f : fc.getChoosableFileFilters()){
				fc.removeChoosableFileFilter(f);
			}
			
			//Create and add pre-defined file filters
			fc.addChoosableFileFilter(new FileNameExtensionFilter("DAT files", "dat"));
			fc.addChoosableFileFilter(new FileNameExtensionFilter("TXT files", "txt"));
			
			//Remove the "All Files" filter
			fc.setAcceptAllFileFilterUsed(false);

			//Display the file chooser dialog
			int returnVal = fc.showOpenDialog(null);

			//Check if user has approved save
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
				//Generate a new Graph object using data obtained from the selected external file.
				graph = new Graph(fileIO.readIn(fc.getSelectedFile().toString()));

				//If the generated graph is empty
				if (graph.getGraphSize() == 0) {

					//Display dialog asking user to select a file to load
					JOptionPane.showMessageDialog(new JFrame(), "Please ensure a file is selected to continue.", "WordLadder | Start-Up", JOptionPane.ERROR_MESSAGE);
					
					//Return to original loading dialog
					loadFile();

				}

				//Otherwise update sub-panel Graph objects with newly generated graph
				generatePan.setGraph(graph);
				discoveryPan.setGraph(graph);

				//Otherwise of the user cancels/closes the file chooser dialog
			} else {

				//Terminate the program as no graph has been generated
				System.exit(0);
			}

		//If the user selects the "Yes" button when  asked if they wish to load an internal dictionary file
		} else if (exit == JOptionPane.YES_OPTION) {
			
			try {

			//Initialise and display checkbox input dialog
			Object[] possibilities = {"4 Letters", "5 Letters", "6 Letters"};
			String s = (String)JOptionPane.showInputDialog(null,"Select a word length:","Select Dictionary File | WordLadder",JOptionPane.PLAIN_MESSAGE,null, possibilities, "WordLadder | Start-Up");

			//If the user selects the "4 letters" option
			if (s.equals("4 Letters")) {

				//Create a graph using the 4 letter dictionary file
				graph = new Graph(fileIO.readIn("dict4.dat"));

			} else if (s.equals("5 Letters")) {

				graph = new Graph(fileIO.readIn("dict5.dat"));

			} else if (s.equals("6 Letters")) {
				
				graph = new Graph(fileIO.readIn("dict6.dat"));

			}
			
			//If the internal file cannot be located for any reason
			} catch (NullPointerException nP) {
				
				//Display message box asking the user to manually select a dictionary file
				JOptionPane.showMessageDialog(new JFrame(), "Internal file not located - please manually select dictionary file.", "WordLadder | Start-Up", JOptionPane.ERROR_MESSAGE);

				//Return to original loading dialog
				loadFile();
			}

			//Otherwise update sub-panel Graph objects with newly generated graph
			generatePan.setGraph(graph);
			discoveryPan.setGraph(graph);


			//Otherwise if attempts to close the dialog box or clicks "Cancel"
		} else {

			//Terminate the program
			System.exit(0);
		}

	}
	
}
