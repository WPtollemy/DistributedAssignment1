import net.jini.space.*;
import net.jini.core.lease.*;
import java.awt.*;
import javax.swing.*;

public class TopicCreator extends JFrame
{
    private static int THREE_MINUTES = 3000 * 60;
    private JavaSpace space;

    private JTextField titleIn, topicTitleIn;
    private JTextField printerNameIn;

    public TopicCreator()
    {
        space = SpaceUtils.getSpace();
        if (space == null){
            System.err.println("Failed to find the javaspace");
            System.exit(1);
        }

        initComponents ();
        pack ();
    }

    private void initComponents()
    {
        setTitle ("Topic Creator");
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

        JLabel jobLabel = new JLabel();
        jobLabel.setText ("Title: ");
        jPanel1.add (jobLabel);

        titleIn = new JTextField (12);
        titleIn.setText ("");
        jPanel1.add (titleIn);

        //Center panel
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout (new FlowLayout ());

        JLabel printerNameLabel = new JLabel();
        printerNameLabel.setText ("Topic Owner ");
        jPanel2.add (printerNameLabel);

        topicTitleIn = new JTextField (12);
        topicTitleIn.setText ("");
        jPanel2.add (topicTitleIn);

        //South panel
        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout (new FlowLayout ());

        JButton addJobButton = new JButton();
        addJobButton.setText("Create Topic");
        addJobButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                createTopic (evt);
            }
        }  );

        jPanel3.add(addJobButton);

        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
    }

    private void createTopic(java.awt.event.ActionEvent evt)
    {
        String topicTitle     = titleIn.getText();
        String topicOwnerName = topicTitleIn.getText();   //  Added to get the destination printer name
        WPTopic testing       = new WPTopic(topicTitle, topicOwnerName);

        try {
            space.write( testing, null, THREE_MINUTES);
        }  catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TopicCreator().setVisible(true);
    }
}
