import net.jini.space.JavaSpace;
import javax.swing.*;
import java.awt.*;
import res.WPTopic;

public class TopicViewer extends JFrame {

    private static final long TWO_SECONDS = 2 * 1000;  // two thousand milliseconds

    private JavaSpace space;
	private JTextField topicTitleIn, newComment;
    private JTextArea jobList;
    private String topicName;

    public TopicViewer() {
        /**/
           space = SpaceUtils.getSpace();
           if (space == null){
           System.err.println("Failed to find the javaspace");
           System.exit(1);
           }
        /**/

        initComponents ();
        pack ();
        setVisible(true);
        processPrintJobs();
    }

    private void initComponents () {
        topicName = "Selected Topic";
        setTitle ("Topic " + topicName);  // Updated to show printer name in title bar

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

        //Center Panel to view comments
		JPanel jPanel2 = new JPanel();
		jPanel2.setLayout (new FlowLayout ());

        //South Panel to add comments
		JPanel jPanel3 = new JPanel();
		jPanel3.setLayout (new FlowLayout ());

		JLabel printerNameLabel = new JLabel();
		printerNameLabel.setText ("Add Comment: ");
		jPanel3.add (printerNameLabel);

		newComment = new JTextField (12);
		newComment.setText ("");
		jPanel3.add (newComment);

        jobList = new JTextArea(30,30);
        jPanel2.add(jobList);

        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
    }

    public void processPrintJobs(){
	    /**/
        while(true){
            try {
                WPTopic topic = new WPTopic();
                topic.topicOwner = "WP"; 
                WPTopic nextJob = (WPTopic)space.take(topic,null, TWO_SECONDS);

                if (nextJob == null) {
                    System.out.println("No topic");
                    // no print job was found, so sleep for a couple of seconds and try again
                    Thread.sleep(TWO_SECONDS);
                } else {
                    System.out.println("Got topic");
                    // we have a job to process
                    String nextJobName = nextJob.title;
                    jobList.append("Title: " + nextJobName + "\n");
                }
            }  catch ( Exception e) {
                e.printStackTrace();
            }
        }
	/**/
    }

    public static void main(String[] args) {
        new TopicViewer();
    }
}
