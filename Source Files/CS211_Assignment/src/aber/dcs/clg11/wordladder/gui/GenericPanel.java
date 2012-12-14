package aber.dcs.clg11.wordladder.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import aber.dcs.clg11.wordladder.model.Graph;


/**
 * Abstract class extended by {@link aber.dcs.clg11.wordladder.gui.GeneratePanel} and 
 * {@link aber.dcs.clg11.wordladder.gui.DiscoveryPanel}.
 * Used to contain shared variables and method utilised by both sub-panels.
 * 
 * @author Connor Goddard (clg11)
 */
@SuppressWarnings("serial")
public abstract class GenericPanel extends JPanel implements ActionListener {

	/** {@link aber.dcs.clg11.wordladder.model.Graph} object passed from 
	 * {@link aber.dcs.clg11.wordladder.gui.WordLadderFrame}.*/
	protected Graph graph;

	/** The layout manager used by the panel. */
	protected SpringLayout layout;

	/** The JTextArea output window used in both sub-panels. */
	protected JTextArea outputArea;

	/** The JScrollPane overlay used by both sub-panel output windows. */
	protected JScrollPane outputScroll;

	/**
	 * Instantiates a new GenericPanel.
	 */
	public GenericPanel() {

		//Initalise the layout manager
		layout = new SpringLayout();
		this.setLayout(layout);

		//Define the size of the panel
		this.setPreferredSize(new Dimension(480, 210));
	}

	/**
	 * Sets the local {@link aber.dcs.clg11.wordladder.model.Graph} object 
	 * to a new object. 
	 *
	 * @param newGraph New {@link aber.dcs.clg11.wordladder.model.Graph} object.
	 */
	public void setGraph(Graph newGraph) {
		this.graph = newGraph;
	}

	/**
	 * Appends and updates the JTextPane output window with new information.
	 *
	 * @param update The new message to be appended to the output window.
	 */
	public void updateDisplay(String update) {

		//Appends the new message to the output window before adding a new line.
		this.outputArea.append(update + "\n");

	}

	/**
	 * Abstract method implemented by {@link aber.dcs.clg11.wordladder.gui.GeneratePanel} and 
	 * {@link aber.dcs.clg11.wordladder.gui.DiscoveryPanel}.
	 * Initialises and adds the GUI components to the sub-panel.
	 */
	public abstract void initComponents();

	/**
	 * Abstract method implemented by {@link aber.dcs.clg11.wordladder.gui.GeneratePanel} and 
	 * {@link aber.dcs.clg11.wordladder.gui.DiscoveryPanel}.
	 * Configures the layout manager to organise all components on the panel.
	 */
	public abstract void setUpLayout();

	/**
	 * Abstract listener for actions from sub-panel buttons, to allow operations to be run when clicked.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * @param evt - ActionEvent called from button components in the sub-panels that require an action to be performed.
	 */
	@Override
	public abstract void actionPerformed(ActionEvent evt);

}