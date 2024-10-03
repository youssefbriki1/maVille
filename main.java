import java.util.Scanner;  
public class main {
    public static void main(String[] args){
        System.out.println("Bienvenue sur X !");
        System.out.println("Veuillez vous connecter!");
        System.out.print("Username : ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        String mdp = "";
        Boolean valide = false;
        Boolean valido = false;
        do {
        switch (username) {
            case "hamid":
                System.out.print("Mot de passe de " +"\'" + username +  "\' : ");
                valido = true;
                mdp = scanner.nextLine();
                
                break;
            case "admin":
                System.out.print("Mot de passe de " +"\'" + username +  "\' : ");
                valido = true;
                mdp = scanner.nextLine();
                
                break;
            default:
                System.out.print("Nom d'utilisateur incorrect. Veuillez réessayer : ");
                username = scanner.nextLine();
                break;
                
        } 
        } while (!valido);
            
        do{
        switch (mdp) {
            case "test1234":
                System.out.println("Bienvenue "+ username +  "!" );
                valide = true;
                break;
            case "admin":
                System.out.println("Bienvenue "+ username +  "!");
                valide = true;
                break;
            default:
                System.out.print("Mot de passe incorrect. Veuillez réessayer : ");
                mdp = scanner.nextLine();
                break;
        }
        } while(!valide);
        
    
        System.out.println("1) Cas  numéro 1");
        System.out.println("2) Cas  numéro 2");
        System.out.println("3) Cas  numéro 3");
        System.out.print("Veuillez selectionner parmis un des cas : ");
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
                System.out.print("Cas inexistant. Veuillez réessayer : ");
                cas = scanner.nextLine();
                break;
        }
        } while(!casv);

        
        scanner.close();
        
        
    }
}
