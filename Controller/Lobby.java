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
        TopicCreator topicCreator = TopicCreator.getInstance();
        topicCreator.setVisible(true);
    }

    public void findTopic()
    {
        TopicViewer topicViewer = TopicViewer.getInstance();
        topicViewer.setVisible(true);
    }
}
