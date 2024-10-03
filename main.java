import java.util.Scanner;  
public class main {
    public static void main(String[] args){
        System.out.println("Bienvenue sur X !");
        System.out.println("Veuillez vous connecter!");
        System.out.println("Username :");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        String mdp = "";
        switch (username) {
            case "hamid":
                System.out.println("Mot de passe user : ");
                mdp = scanner.nextLine();
                break;
            case "admin":
                System.out.println("Mot de passe admin : ");
                mdp = scanner.nextLine();
                break;
            default:
                System.out.println("nope ya pas ce username chef");
                break;
        }
        if (mdp.equals("")){
            switch (username) {
                case "test1234":
                    System.out.println("Hello user : ");
                    break;
                case "admin":
                    System.out.println("Hello admin : ");
                    break;
                default:
                    System.out.println("nope ya pas ce password chef");
                    break;
            }
        }
        


        System.out.println("1) Cas  numéro 1");
        System.out.println("2) Cas  numéro 2");
        System.out.println("3) Cas  numéro 3");
        System.out.println("Veuillez choisir un des cas : ");
        String cas = scanner.nextLine();  
        
        switch (cas) {
            case "1":
                System.out.println("cas choisi = " + cas);
                break;
            case "2":
                System.out.println("cas choisi = " + cas);
                break;
            case "3":
                System.out.println("cas choisi = " + cas);
                break;
            default:
                System.out.println("nope ya pas chef");
                break;
        }
    }
}
