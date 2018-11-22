package Controller;
import View.TopicCreator;
import View.TopicViewer;

public class Lobby 
{
    public Lobby()
    {
    }

    public void createTopic()
    {
        TopicCreator topicCreator = new TopicCreator();
        topicCreator.setVisible(true);
    }

    public void findTopic()
    {
        TopicViewer topicViewer = new TopicViewer();
        topicViewer.setVisible(true);
    }
}
