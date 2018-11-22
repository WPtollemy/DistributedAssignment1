package View;
import java.awt.*;
import javax.swing.*;

public class Lobby extends JFrame 
{
    private Controller.Lobby lobbyController;
	private	JButton createTopicButton = new JButton();
	private	JButton searchTopicButton = new JButton();

    public Lobby()
    {
        this.lobbyController = new Controller.Lobby();
        initComponents();
        this.setSize(500,500);
    }

	private void initComponents ()
    {
		setTitle ("Main Menu");
		addWindowListener (new java.awt.event.WindowAdapter () {
			public void windowClosing (java.awt.event.WindowEvent evt) {
				System.exit (0);
			}
		}   );

		Container cp = getContentPane();
		cp.setLayout (new BorderLayout ());

        //North Panel to select topics
		JPanel jPanel1 = new JPanel();
		jPanel1.setLayout (new FlowLayout ());

        //Center Panel to view comments
		JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.Y_AXIS));

		createTopicButton.setText("Create Topic");
		createTopicButton.addActionListener (new java.awt.event.ActionListener () {
			public void actionPerformed (java.awt.event.ActionEvent evt) {
			   createTopic(evt); 
			}
		}  );

		jPanel2.add(createTopicButton);

		searchTopicButton.setText("Find Topic");
		searchTopicButton.addActionListener (new java.awt.event.ActionListener () {
			public void actionPerformed (java.awt.event.ActionEvent evt) {
                searchTopic(evt);
			}
		}  );

		jPanel2.add(searchTopicButton);

        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
    }

	private void createTopic(java.awt.event.ActionEvent evt)
    {
        this.lobbyController.createTopic();
        this.createTopicButton.setEnabled(false);
    }

	private void searchTopic(java.awt.event.ActionEvent evt)
    {
        this.lobbyController.findTopic();
        this.searchTopicButton.setEnabled(false);
    }

	public static void main(String[] args) {
		new Lobby().setVisible(true);
	}
}
