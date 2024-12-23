package ca.umontreal.ift2255.groupe2.maville_backend.model;
import java.util.*;


public interface NotificationHandler{
    
    List<Notification> getNewNotifications();
    List<Notification> getOldNotifications();
    public static List<Map<String, String>> formatNotificationList(List<Notification> notifications){
        List<Map<String,String>> formatedResponse = new ArrayList<>();
        for (Notification notification : notifications){
            formatedResponse.add(notification.toDict());

        }
        return formatedResponse;

    };

}
