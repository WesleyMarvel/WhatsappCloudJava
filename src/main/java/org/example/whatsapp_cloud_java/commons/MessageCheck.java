package org.example.whatsapp_cloud_java.commons;
import org.apache.commons.text.similarity.JaroWinklerDistance;

public class MessageCheck {
    public static boolean isGreeting(String str) {
        String[] greetings = {"Hello", "Hi", "Hey", "Good morning", "Good afternoon", "Good evening"};
        JaroWinklerDistance jwd = new JaroWinklerDistance();

        for (String greeting : greetings) {
            if (jwd.apply(str, greeting) > 0.8) {
                return true;
            }
        }
        return false;
    }
}
