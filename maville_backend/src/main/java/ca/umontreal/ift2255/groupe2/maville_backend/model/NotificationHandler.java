package ca.umontreal.ift2255.groupe2.maville_backend.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Interface définissant les méthodes pour gérer les notifications.
 * Fournit des fonctionnalités pour accéder aux notifications nouvelles ou anciennes
 * et pour formater une liste de notifications.
 */
public interface NotificationHandler{
    /**
     * Récupère la liste des nouvelles notifications.
     * 
     * @return Une liste d'objets {@link Notification} marqués comme nouveaux.
     */
    List<Notification> getNewNotifications();

    /**
     * Récupère la liste des anciennes notifications.
     * 
     * @return Une liste d'objets {@link Notification} qui ne sont plus marqués comme nouveaux.
     */
    List<Notification> getOldNotifications();

    /**
     * Formate une liste de notifications en une liste de Maps.
     * Chaque notification est représentée sous forme de Map contenant ses attributs clés-valeurs.
     * 
     * @param notifications La liste d'objets {@link Notification} à formater.
     * @return Une liste de Maps, où chaque Map représente une notification avec ses champs formatés.
     */
    public static List<Map<String, String>> formatNotificationList(List<Notification> notifications){
        List<Map<String,String>> formatedResponse = new ArrayList<>();
        for (Notification notification : notifications){
            formatedResponse.add(notification.toDict());

        }
        return formatedResponse;

    };

}
