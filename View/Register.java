package View;
import java.awt.*;
import javax.swing.*;
import res.WPTopic;

public class Register extends JFrame
{
    private static int THREE_MINUTES = 3000 * 60;
    private Controller.Register registerController;

    private JTextField userIn, passIn;

    public Register()
    {
        registerController = new Controller.Register();

        initComponents ();
        pack ();
    }

    private void initComponents()
    {
        setTitle ("Create an account");
        addWindowListener (new java.awt.event.WindowAdapter () {
            public void windowClosing (java.awt.event.WindowEvent evt) {
                System.exit (0);
            }
        }   );

        Container cp = getContentPane();
        cp.setLayout (new BorderLayout ());

        //North panel
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout (new FlowLayout ());

        JLabel userLabel = new JLabel();
        userLabel.setText ("Username: ");
        jPanel1.add (userLabel);

        userIn = new JTextField (12);
        userIn.setText ("");
        jPanel1.add (userIn);

        //Center panel
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout (new FlowLayout ());

        JLabel passLabel = new JLabel();
        passLabel.setText ("Password ");
        jPanel2.add (passLabel);

        passIn = new JTextField (12);
        passIn.setText ("");
        jPanel2.add (passIn);

        //South panel
        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout (new FlowLayout ());

        JButton addJobButton = new JButton();
        addJobButton.setText("Register");
        addJobButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                register (evt);
            }
        }  );

        jPanel3.add(addJobButton);

        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
    }

    private void register(java.awt.event.ActionEvent evt)
    {
        String username = userIn.getText();
        String password = passIn.getText();

        registerController.validUser(username, password);
        registerController.registerUser(username, password);
    }

    public static void main(String[] args) {
        new Register().setVisible(true);
    }
}
