package View;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import res.WPTopic;

public class TopicViewer extends JFrame
{
    private JComboBox topicList;
	private JTextField newComment;
    private JTextArea jobList;
    private String topicName;
    private Controller.TopicViewer topicViewerController;

    public TopicViewer()
    {
        topicViewerController = new Controller.TopicViewer();

        this.topicName = "Enter a topic";
        initComponents ();
        pack ();
        setVisible(true);
    }

    public TopicViewer(String topicTitle)
    {
        this.topicName = topicTitle;
        initComponents ();
        pack ();
        setVisible(true);
    }

    private void initComponents () {
        setTitle (this.topicName);

        addWindowListener (new java.awt.event.WindowAdapter () {
            public void windowClosing (java.awt.event.WindowEvent evt) {
                System.exit(0);
            }
        }   );

        Container cp = getContentPane();
        cp.setLayout (new BorderLayout ());

        //North Panel to select topics
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout());

		JLabel jobLabel = new JLabel();
		jobLabel.setText ("Topic: ");
		jPanel1.add (jobLabel);

        topicList = new JComboBox(getTopicList());
        jPanel1.add(topicList);

        JButton showTopicButton = new JButton();
        showTopicButton.setText("Show Topic");
        showTopicButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                showTopic (evt);
            }
        }  );

        jPanel1.add(showTopicButton);

        //Center Panel to view comments
		JPanel jPanel2 = new JPanel();
		jPanel2.setLayout (new FlowLayout ());

        jobList = new JTextArea(30,30);
        jPanel2.add(jobList);

        //South Panel to add comments
		JPanel jPanel3 = new JPanel();
		jPanel3.setLayout (new FlowLayout ());

		JLabel printerNameLabel = new JLabel();
		printerNameLabel.setText ("Add Comment: ");
		jPanel3.add (printerNameLabel);

		newComment = new JTextField (12);
		newComment.setText ("");
		jPanel3.add (newComment);

        JButton addMessageButton = new JButton();
        addMessageButton.setText("Add");
        addMessageButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                addMessage (evt);
            }
        }  );

        jPanel3.add(addMessageButton);

        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
    }

    private void showTopic(java.awt.event.ActionEvent evt)
    {
        this.topicName = (String)topicList.getSelectedItem();
        WPTopic topic = topicViewerController.findTopic(this.topicName);

        //Handle errors
        if (null == topic) {
            System.out.println("null topic found: " + this.topicName);
            return;
        }

        setTitle ("Topic: " + this.topicName);

        for(String message : this.getMessageList()) {
            jobList.append(new String(message + "\n"));
        }
        jobList.update(jobList.getGraphics());
    }

    private void addMessage(java.awt.event.ActionEvent evt)
    {
        this.topicName = (String)topicList.getSelectedItem();
        topicViewerController.addMessage(this.topicName, newComment.getText());

        for(String message : this.getMessageList()) {
            jobList.append(new String(message + "\n"));
        }
        jobList.update(jobList.getGraphics());
    }

    private String[] getTopicList()
    {
        ArrayList<String> listTopics = topicViewerController.getTopicList();

        String[] topics = new String[listTopics.size()];
        topics = listTopics.toArray(topics);
        return topics;
    }

    private String[] getMessageList()
    {
        ArrayList<String> listMessages = topicViewerController.getMessageList(this.topicName);

        String[] messages = new String[listMessages.size()];
        messages = listMessages.toArray(messages);
        return messages;
    }

    public static void main(String[] args) {
        new TopicViewer();
    }
}
