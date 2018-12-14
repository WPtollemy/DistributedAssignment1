package View;
import java.awt.*;
import javax.swing.*;

public class TopicCreator extends JFrame
{
    //Instance
    private static TopicCreator instance = new TopicCreator();

    //JSwing components
    private JTextField titleIn;

    //Other vars
    private Controller.TopicCreator topicCreatorController;

    //Only have one topic creator instance at a time
    public static TopicCreator getInstance()
    {
        return instance;
    }

    private TopicCreator()
    {
        topicCreatorController = new Controller.TopicCreator();

        initComponents ();
        pack ();
    }

    private void initComponents()
    {
        setTitle ("Topic Creator");

        Container cp = getContentPane();
        cp.setLayout (new BorderLayout ());

        //North panel to take the title
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
        String topicTitle = titleIn.getText();
        boolean createdTopic = this.topicCreatorController.createTopic(topicTitle);
        String infoText = "Topic Created";

        //Override display text if failed to create topic
        if (!createdTopic) {
            infoText = "Topic already exists!";
        }

        JOptionPane.showMessageDialog(null, infoText);
        instance.setVisible(false);
    }
}
