package View;
import java.awt.*;
import javax.swing.*;

public class Login extends JFrame
{
    //JSwing components
    private JTextField userIn;
    private JPasswordField passIn;
    private Controller.Login loginController;

    public Login()
    {
        this.loginController = new Controller.Login();
        initComponents();
        this.setSize(450,150);
        pack();
    }

    private void initComponents ()
    {
        setTitle ("Login");

        Container cp = getContentPane();
        cp.setLayout (new BorderLayout ());

        //North Panel to select topics
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout (new FlowLayout ());

        //Center Panel to view comments
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

        //South Panel to add comments
        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout (new FlowLayout ());

        JButton loginButton = new JButton();
        loginButton.setText("Login");
        loginButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                login(evt);
            }
        }  );

        jPanel3.add(loginButton);

        JButton registerButton = new JButton();
        registerButton.setText("Register");
        registerButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                register(evt);
            }
        }  );

        jPanel3.add(registerButton);


        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
    }

    private void login(java.awt.event.ActionEvent evt)
    {
        String username = userIn.getText();
        String password = new String(passIn.getPassword());

        if (!this.loginController.validUser(username, password)){
            return;
        }

        this.loginController.loginUser(username, password);
        this.setVisible(false);
    }

    private void register(java.awt.event.ActionEvent evt)
    {
        this.loginController.registerUser();
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
