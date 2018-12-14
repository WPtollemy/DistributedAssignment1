package View;
import java.awt.*;
import javax.swing.*;

public class Register extends JFrame
{
    //Instance
    private static Register instance = new Register();

    //JSwing components
    private JTextField userIn;
    private JPasswordField passIn;
    private JLabel errorLabel;

    //Other vars
    private Controller.Register registerController;

    //Only have one register instance at any time
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

        //North panel to hold the error
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout (new FlowLayout ());

        errorLabel = new JLabel();
        errorLabel.setText ("");
        jPanel1.add (errorLabel);

        //Center panel to take user and pass fields
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

        passIn = new JPasswordField (12);
        passIn.setText ("");
        jPanel2.add (passIn);

        //South panel to contain the register button
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
        String password = new String(passIn.getPassword());

        //Ensure details entered are valid and don't exist already
        if (!registerController.validUser(username, password)) {
            this.errorLabel.setText("Invalid User");            
            this.errorLabel.setForeground(Color.red);
            pack();
            return;
        }

        registerController.registerUser(username, password);
        JOptionPane.showMessageDialog(null, "Account Created");
        instance.setVisible(false);
    }
}
