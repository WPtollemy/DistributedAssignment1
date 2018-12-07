package Controller;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class MyTextListener implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("MyTextProperty")) {
            System.out.println(event.getNewValue().toString());
        }
    }
}
