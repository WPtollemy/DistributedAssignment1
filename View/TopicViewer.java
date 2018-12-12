package View;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import net.jini.core.event.*;
import net.jini.core.lease.Lease;
import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;
import net.jini.space.JavaSpace;
import res.WPMessage;

public class TopicViewer extends JFrame implements RemoteEventListener
{
    //JSwing components
	private JTextField newComment;
    private JList<String> messageList;
    private JCheckBox privateBox;

    //Space vars
    private Controller.TopicViewer topicViewerController;
    private JavaSpace space;
    private RemoteEventListener theStub;

    //Other vars
    private String topicName;
    private static int FIFTEEN_MINUTES = 15000 * 60;

    public TopicViewer(String topicTitle)
    {
        topicViewerController = new Controller.TopicViewer();

        topicName = topicTitle;
        this.setTitle (topicViewerController.findTopicOwner(topicName) + " - " + topicName.trim());

        // find the space
        space = Controller.SpaceUtils.getSpace();
        if (space == null){
            System.err.println("Failed to find the javaspace");
            System.exit(1);
        }
        setupNotification();

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

        JButton subscribeButton = new JButton();
        subscribeButton.setText("Subscribe");
        subscribeButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                subscribe (evt);
            }
        }  );

        jPanel3.add(subscribeButton);

        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
    }

    private void addMessage(java.awt.event.ActionEvent evt)
    {
        topicViewerController.addMessage(topicName, newComment.getText(), privateBox.isSelected());
    }

    private void subscribe(java.awt.event.ActionEvent evt)
    {
        //Add subscribe functionality
        topicViewerController.subscribeToTopic(topicName);
    }

    private void setupNotification()
    {
        // create the exporter
        Exporter myDefaultExporter =
            new BasicJeriExporter(TcpServerEndpoint.getInstance(0),
                    new BasicILFactory(), false, true);

        try {
            // register this as a remote object
            // and get a reference to the 'stub'
            theStub = (RemoteEventListener) myDefaultExporter.export(this);

            // add the listener
            WPMessage template = new WPMessage();
            template.topic     = topicName;
            space.notify(template, null, this.theStub, FIFTEEN_MINUTES, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notify(RemoteEvent ev)
    {
        updateMessageList();
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
        new TopicViewer("TEST");
    }
}
