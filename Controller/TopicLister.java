package Controller;
import java.util.ArrayList;
import View.TopicCreator;
import View.TopicViewer;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class TopicLister
{
    //JavaSpace
    private SpaceController spaceController = new SpaceController();

    //Notify flag needed to stop space logic in the view
    public boolean flag = false;
    protected PropertyChangeSupport propertyChangeSupport;

    public TopicLister()
    {
    }

    public void createTopic()
    {
        TopicCreator topicCreator = TopicCreator.getInstance();
        topicCreator.setVisible(true);
    }

    public void viewTopic(String selectedTopic)
    {
        TopicViewer topicViewer = TopicViewer.getInstance(selectedTopic);
        topicViewer.setVisible(true);
    }

    public ArrayList<String> getTopicList()
    {
        propertyChangeSupport.firePropertyChange("MyTextProperty","Old", "Text");
        return spaceController.getTopicList();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
