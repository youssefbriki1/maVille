import java.util.Scanner;  
public class main {
    public static void main(String[] args){
        System.out.println("Bienvenue sur X !");
        System.out.println("Veuillez vous connecter!");
        System.out.println("Username :");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        String mdp = "";
        Boolean valide = false;
        Boolean valido = false;
        do {
        switch (username) {
            case "hamid":
                System.out.println("Mot de passe user : ");
                valido = true;
                mdp = scanner.nextLine();
                
                break;
            case "admin":
                System.out.println("Mot de passe admin : ");
                valido = true;
                mdp = scanner.nextLine();
                
                break;
            default:
                System.out.print("nope ya pas ce username chef, reessaye : ");
                username = scanner.nextLine();
                break;
                
        } 
        } while (!valido);
            
        do{
        if (mdp.equals("") == false){
            switch (mdp) {
                case "test1234":
                    System.out.println("Hello user : ");
                    valide = true;
                    break;
                case "admin":
                    System.out.println("Hello admin : ");
                    valide = true;
                    break;
                default:
                    System.out.print("nope ya pas ce password chef, reessaye : ");
                    mdp = scanner.nextLine();
                    break;
            }
        }
        } while(!valide);
        
    
        System.out.println("1) Cas  numéro 1");
        System.out.println("2) Cas  numéro 2");
        System.out.println("3) Cas  numéro 3");
        System.out.println("Veuillez choisir un des cas : ");
        String cas = scanner.nextLine();  
        
        Boolean casv = false; 
        do{
        switch (cas) {
            case "1":
                System.out.println("cas choisi = " + cas);
                casv = true;
                break;
            case "2":
                System.out.println("cas choisi = " + cas);
                casv = true;
                break;
            case "3":
                System.out.println("cas choisi = " + cas);
                casv = true;
                break;
            default:
                System.out.print("nope ya pas chef, reessaye : ");
                cas = scanner.nextLine();
                break;
        }
        } while(!casv);

        
        scanner.close();
        
        
    }
}
