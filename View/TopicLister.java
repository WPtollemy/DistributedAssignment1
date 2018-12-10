package View;
import javax.swing.Timer;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.*;
import net.jini.core.event.*;
import net.jini.core.lease.Lease;
import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;
import net.jini.space.JavaSpace;
import res.WPTopic;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TopicLister extends JFrame implements RemoteEventListener
{
    //JSwing components
    private JList<String> topicList;

    //Other vars
    private Controller.TopicLister topicListerController;
    private JavaSpace space;
    private RemoteEventListener theStub;
    private static int FIFTEEN_MINUTES = 15000 * 60;

    public TopicLister()
    {
        topicListerController = new Controller.TopicLister();
        
        // find the space
        space = Controller.SpaceUtils.getSpace();
        if (space == null){
            System.err.println("Failed to find the javaspace");
            System.exit(1);
        }
        setupNotification();

        initComponents();
        pack();
        setVisible(true);
        updateTopicList();
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
            WPTopic template = new WPTopic();
            space.notify(template, null, this.theStub, FIFTEEN_MINUTES, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this is the method called when we are notified
    // of an object of interest
    public void notify(RemoteEvent ev)
    {
            updateTopicList();
            JFrame f = new JFrame();
            f.setMinimumSize(new Dimension(100,100));
            final JDialog dialog = new JDialog(f, "Test", true);
            Timer timer = new Timer(2000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.setVisible(false);
                    dialog.dispose();
                }
            });
            timer.setRepeats(false);
            timer.start();

            dialog.setVisible(true); // if modal, application will pause here
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

    private String[] getTopicList()
    {
        ArrayList<String> listTopics = topicListerController.getTopicList();

        String[] topics = new String[listTopics.size()];
        topics = listTopics.toArray(topics);
        return topics;
    }

    public static void main(String[] args) {
        new TopicLister();
    }
}
