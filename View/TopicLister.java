package View;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

public class TopicLister extends JFrame
{
    //Instance
    private static TopicLister instance = new TopicLister();

    //JSwing components
    private JList<String> topicList;

    //Other vars
    private Controller.TopicLister topicListerController;

    public static TopicLister getInstance()
    {
        return instance;
    }

    private TopicLister()
    {
        topicListerController = new Controller.TopicLister();

        initComponents();
        pack();
        updateTopicList();
        setVisible(true);
    }

    private void initComponents()
    {
        setTitle ("Topics");

        Container cp = getContentPane();
        cp.setLayout (new BorderLayout ());

        //North Panel to handle buttons
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout());

        JButton createTopicButton = new JButton();
        createTopicButton.setText("Create Topic");
        createTopicButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                createTopic (evt);
            }
        }  );

        jPanel1.add(createTopicButton);

        JButton viewTopicButton = new JButton();
        viewTopicButton.setText("View Topic");
        viewTopicButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                viewTopic (evt);
            }
        }  );

        jPanel1.add(viewTopicButton);

        //Center Panel to view topics
		JPanel jPanel2 = new JPanel();
		jPanel2.setLayout (new BoxLayout (jPanel2, BoxLayout.PAGE_AXIS));

        topicList = new JList<String>();
        topicList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        topicList.setLayoutOrientation(JList.VERTICAL);
        topicList.setVisibleRowCount(12);

        JScrollPane listScroller = new JScrollPane(topicList);
        jPanel2.add(listScroller, BorderLayout.CENTER);
        jPanel2.revalidate();
        jPanel2.repaint();

        //South Panel to add comments
		JPanel jPanel3 = new JPanel();
		jPanel3.setLayout (new FlowLayout ());

        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
    }

    private void createTopic(java.awt.event.ActionEvent evt)
    {
        this.topicListerController.createTopic();
    }

    private void viewTopic(java.awt.event.ActionEvent evt)
    {
        String selectedTopic = topicList.getSelectedValue();

        if (selectedTopic == null)
            return;

        topicListerController.viewTopic(selectedTopic);
    }

    private String[] getTopicList()
    {
        ArrayList<String> listTopics = topicListerController.getTopicList();

        String[] topics = new String[listTopics.size()];
        topics = listTopics.toArray(topics);
        return topics;
    }

    private void updateTopicList()
    {
        //Clear topic list then update
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        for(String topic : this.getTopicList()) {
            listModel.addElement(new String(topic + "\n"));
        }
        topicList.setModel(listModel);
    }

    public static void main(String[] args) {
        new TopicLister();
    }
}
