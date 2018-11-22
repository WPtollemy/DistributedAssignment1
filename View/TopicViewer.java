package View;
import javax.swing.*;
import java.awt.*;
import res.WPTopic;

public class TopicViewer extends JFrame {

    private static final long TWO_SECONDS = 2 * 1000;  // two thousand milliseconds

	private JTextField topicTitleIn, newComment;
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

		topicTitleIn = new JTextField (12);
		topicTitleIn.setText ("");
		jPanel1.add (topicTitleIn);

        JButton findTopicButton = new JButton();
        findTopicButton.setText("Find Topic");
        findTopicButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                findTopic (evt);
            }
        }  );

        jPanel1.add(findTopicButton);

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
        addMessageButton.setText("Find Topic");
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

    private void findTopic(java.awt.event.ActionEvent evt)
    {
        this.topicName = topicTitleIn.getText();
        WPTopic topic = topicViewerController.findTopic(this.topicName);

        //Handle errors
        if (null == topic) {
            System.out.println("null topic found");
            return;
        }

        setTitle ("Topic: " + this.topicName);

        while(true)
        {
            try {
                String topicPost = topicViewerController.findTopicMessage(topic);
                if (topicPost == null ||
                        jobList.getText().toLowerCase().contains(topicPost.toLowerCase())
                   ) {
                    // no print job was found, so sleep for a couple of seconds and try again
                    Thread.sleep(TWO_SECONDS);
                } else {
                    jobList.append(new String("Title: " + topicPost + "\n"));
                    jobList.update(jobList.getGraphics());
                }
            }  catch ( Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addMessage(java.awt.event.ActionEvent evt)
    {
        this.topicName = topicTitleIn.getText();
        topicViewerController.addMessage(this.topicName, newComment.getText());
    }

    public static void main(String[] args) {
        new TopicViewer();
    }
}
