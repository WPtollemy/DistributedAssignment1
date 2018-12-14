package tests.Controller;
import junit.framework.*;
import org.junit.Test;
import Controller.TopicCreator;
import Controller.Main;
import Controller.SpaceController;
import res.*;

public class TopicCreatorControllerTest extends TestCase
{
    private TopicCreator topicCreatorController; 
    private SpaceController spaceController = new SpaceController();

    protected void setUp()
    {
        topicCreatorController = new TopicCreator();
    }

    @Test
    public void testNewTopicGetsCreated()
    {
        WPUser user = new WPUser("user", "pass");
        Main.logUserIn(user);

        assertTrue(topicCreatorController.createTopic("topicCreatorTest"));

        //Ensure topic doesn't keep from test
        WPTopic topic = new WPTopic("topicCreatorTest", Main.getLoggedUser().name);
        spaceController.deleteTopic(topic);
    }

    @Test
    public void testDuplicateTopicIsntCreated()
    {
        WPUser user = new WPUser("user", "pass");
        Main.logUserIn(user);

        WPTopic topic = new WPTopic("topicCreatorTest", Main.getLoggedUser().name);
        spaceController.writeTopic(topic);

        assertFalse(topicCreatorController.createTopic("topicCreatorTest"));

        //Ensure topic doesn't keep from test
        topic = new WPTopic("topicCreatorTest", Main.getLoggedUser().name);
        spaceController.deleteTopic(topic);
    }
}
