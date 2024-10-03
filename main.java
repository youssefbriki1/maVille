import java.util.Scanner;  
public class main {
    public static void main(String[] args){
        System.out.println("Bienvenue sur X !");
        System.out.println("1) Cas  numéro 1");
        System.out.println("2) Cas  numéro 2");
        System.out.println("3) Cas  numéro 3");
        System.out.println("Veuillez choisir un des cas : ");
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        String cas = scanner.nextLine();  // Read user input
        
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
