package View;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import res.WPTopic;

public class TopicViewer extends JFrame
{
    //Instance
    private static TopicViewer instance = new TopicViewer();

    //JSwing components
    private JComboBox<String> topicList;
	private JTextField newComment;
    private JList<String> messageList;
    private JCheckBox privateBox;

    //Other vars
    private static String topicName;
    private Controller.TopicViewer topicViewerController;

    public static TopicViewer getInstance(String topicTitle)
    {
        topicName = topicTitle;
        instance.setTitle (topicName.trim());

        return instance;
    }

    private TopicViewer()
    {
        topicViewerController = new Controller.TopicViewer();

        initComponents ();
        pack ();
        setVisible(true);
    }

    private void initComponents ()
    {
        Container cp = getContentPane();
        cp.setLayout (new BorderLayout ());

        //North Panel to select topics
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout());

        //Center Panel to view comments
		JPanel jPanel2 = new JPanel();
		jPanel2.setLayout (new BoxLayout (jPanel2, BoxLayout.PAGE_AXIS));

        messageList = new JList<String>();
        messageList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        messageList.setLayoutOrientation(JList.VERTICAL);
        messageList.setVisibleRowCount(12);

        JScrollPane listScroller = new JScrollPane(messageList);
        jPanel2.add(listScroller, BorderLayout.CENTER);
        jPanel2.revalidate();
        jPanel2.repaint();

        //South Panel to add comments
		JPanel jPanel3 = new JPanel();
		jPanel3.setLayout (new FlowLayout ());

		JLabel addCommentLabel = new JLabel();
		addCommentLabel.setText ("Add Comment: ");
		jPanel3.add (addCommentLabel);

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

        privateBox = new JCheckBox("Private");
        jPanel3.add(privateBox);

        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
    }

    private void showTopic(java.awt.event.ActionEvent evt)
    {
        topicName = (String)topicList.getSelectedItem();
        WPTopic topic = topicViewerController.findTopic(topicName);

        //Handle errors
        if (null == topic) {
            System.out.println("null topic found: " + topicName);
            return;
        }

        setTitle ("Topic: " + topicName);

        updateMessageList();
    }

    private void addMessage(java.awt.event.ActionEvent evt)
    {
        topicName = (String)topicList.getSelectedItem();
        topicViewerController.addMessage(topicName, newComment.getText(), privateBox.isSelected());
        updateMessageList();
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
        ArrayList<String> listMessages = topicViewerController.getMessageList(topicName);

        String[] messages = new String[listMessages.size()];
        messages = listMessages.toArray(messages);
        return messages;
    }

    private void updateMessageList()
    {
        //Clear message list then update
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        for(String message : this.getMessageList()) {
            listModel.addElement(new String(message + "\n"));
        }
        messageList.update(messageList.getGraphics());
        messageList.updateUI();
        messageList.setVisibleRowCount(listModel.size());
        messageList.setModel(listModel);
    }

    public static void main(String[] args) {
        new TopicViewer();
    }
}
