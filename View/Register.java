package View;
import java.awt.*;
import javax.swing.*;
import res.WPTopic;

public class Register extends JFrame
{
    private static Register instance = new Register();

    private Controller.Register registerController;

    private JTextField userIn, passIn;
    private JLabel errorLabel;

    public static Register getInstance()
    {
        return instance;
    }

    private Register()
    {
        registerController = new Controller.Register();

        initComponents ();
        this.setSize(300,150);
        pack ();
    }

    private void initComponents()
    {
        setTitle ("Create an account");

        Container cp = getContentPane();
        cp.setLayout (new BorderLayout ());

        //North panel
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout (new FlowLayout ());

        errorLabel = new JLabel();
        errorLabel.setText ("");
        jPanel1.add (errorLabel);

        //Center panel
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout (new GridLayout (2, 2, 5, 5));

        JLabel userLabel = new JLabel();
        userLabel.setText ("Username: ");
        jPanel2.add (userLabel);

        userIn = new JTextField (12);
        userIn.setText ("");
        jPanel2.add (userIn);

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

        if (!registerController.validUser(username, password)) {
            // TODO Get actual error messages
            this.errorLabel.setText("Invalid User");            
            this.errorLabel.setForeground(Color.red);
            pack();
            return;
        }

        registerController.registerUser(username, password);
        JOptionPane.showMessageDialog(null, "Account Created");
        this.instance.setVisible(false);
    }

    public static void main(String[] args) {
        new Register().setVisible(true);
    }
}
