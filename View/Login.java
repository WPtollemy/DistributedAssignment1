package View;
import java.awt.*;
import javax.swing.*;

public class Login extends JFrame 
{
	private JTextField passIn, userIn;
    private Controller.Login loginController;

    public Login()
    {
        this.loginController = new Controller.Login();
        initComponents();
        this.setSize(450,150);
    }

	private void initComponents ()
    {
		setTitle ("Login");
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

		JLabel userLabel = new JLabel();
		userLabel.setText ("Username: ");
		jPanel2.add (userLabel);

		userIn = new JTextField (12);
		userIn.setText ("");
		jPanel2.add (userIn);

		JLabel passLabel = new JLabel();
		passLabel.setText ("Password: ");
		jPanel2.add (passLabel);

		passIn = new JTextField (12);
		passIn.setText ("");
		jPanel2.add (passIn);

		JButton loginButton = new JButton();
		loginButton.setText("Login");
		loginButton.addActionListener (new java.awt.event.ActionListener () {
			public void actionPerformed (java.awt.event.ActionEvent evt) {
                login(evt);
			}
		}  );

		jPanel2.add(loginButton);

        //South Panel to add comments
		JPanel jPanel3 = new JPanel();
		jPanel3.setLayout (new FlowLayout ());


        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
	}

	private void login(java.awt.event.ActionEvent evt)
    {
        String username = userIn.getText();
        String password = passIn.getText();

        if (!this.loginController.validUser(username, password)){
            return;
        }

        this.loginController.loginUser();
        this.setVisible(false);
    }

	public static void main(String[] args) {
		new Login().setVisible(true);
	}
}
