import java.util.Scanner;  
public class main {
    public static void main(String[] args){
        String textArt = """
             ______  _______          ____    ____      ____  ____  ____         ____             ______   
            |      \\/       \\    ____|\\   \\  |    |    |    ||    ||    |       |    |        ___|\\     \\  
           /          /\\     \\  /    /\\    \\ |    |    |    ||    ||    |       |    |       |     \\     \\ 
          /     /\\   / /\\     ||    |  |    ||    |    |    ||    ||    |       |    |       |     ,_____/|
         /     /\\ \\_/ / /    /||    |__|    ||    |    |    ||    ||    |  ____ |    |  ____ |     \\--'\\_|/
        |     |  \\|_|/ /    / ||    .--.    ||    |    |    ||    ||    | |    ||    | |    ||     /___/|  
        |     |       |    |  ||    |  |    ||\\    \\  /    /||    ||    | |    ||    | |    ||     \\____|\\ 
        |\\____\\       |____|  /|____|  |____|| \\ ___\\/___ / ||____||____|/____/||____|/____/||____ '     /|
        | |    |      |    | / |    |  |    | \\ |   ||   | / |    ||    |     |||    |     |||    /_____/ |
         \\|____|      |____|/  |____|  |____|  \\|___||___|/  |____||____|_____|/|____|_____|/|____|     | /
            \\(          )/       \\(      )/      \\(    )/      \\(    \\(    )/     \\(    )/     \\( |_____|/ 
             '          '         '      '        '    '        '     '    '       '    '       '    )/    
                                                                                                     '      
        """;

        // Display the text art
        System.out.print(textArt);
        String hashLine = "#".repeat(100);
        System.out.println(hashLine);
        System.out.println();
        System.out.println("Bienvenue sur MaVille !");
        System.out.println();
        System.out.println("Veuillez vous connecter!");
        System.out.print("Username : ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        String mdp = "";
        Boolean valide = false;
        Boolean valido = false;
        Boolean type = false;
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
                type = true;
                break;
            case "admin":
                System.out.println("Bienvenue "+ username +  "!");
                valide = true;
                type = false;
                break;
            default:
                System.out.print("Mot de passe incorrect. Veuillez réessayer : ");
                mdp = scanner.nextLine();
                break;
        }
        } while(!valide);
        System.out.println();
    
        
        
        Boolean casv = false; 
        if (type){
            PrintUpR();
            String cas = scanner.nextLine();  
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
            }
        else{
            PrintUpI(scanner); 
            
        }
        
        
        

        
        scanner.close();
        
    
    }
    public static void infoCompte(String a, Scanner scanner){
        Boolean nice = false;
        System.out.println();
        System.out.println("[1] Afficher vos informations");
        System.out.println("[2] Modifier vos informations");
        System.out.println("[3] Retourner au menu princpal");
        System.out.print("Veuillez selectionner parmis un des cas : ");
        a = scanner.nextLine();
        do{
            switch (a) {
                case "1":
                    System.out.println();
                    System.out.println("Nom complet : Admin Admin");
                    System.out.println("Adresse courriel : admin@admin.com");
                    System.out.println("Type de compte : Intervenant");
                    System.out.println("Indentifiant de la ville : XXXXXXXX ");
                    System.out.print("Entrez '1' pour revenir au menu princpal : ");
                    Boolean varia = false;
                    a = scanner.nextLine();
                    do{
                        switch (a) {
                            case "1":
                                varia = true;
                                System.out.println();
                                PrintUpI(scanner);
                                break;
                        
                            default:
                                System.out.print("Veuillez réessayer : ");
                                a = scanner.nextLine();
                                break;
                        }
                    }while (!varia);
                    
                    
                    nice = true;
                    break;
                case "2":
                    System.out.println("cas choisi = " + a);
                    nice = true;
                    break;
                case "3":
                    System.out.println();
                    PrintUpI(scanner);
                    nice = true;
                    break;
                    
                default:
                    System.out.print("Cas inexistant.. Veuillez réessayer : ");
                    a = scanner.nextLine();
                    break;
            }
        } while(!nice);

    }
    public static void PrintUpR(){
        System.out.println("Choix résident :");
        System.out.println("[1] Informations sur votre profile");
        System.out.println("[2] Cas  numéro 2");
        System.out.println("[3] Cas  numéro 3");
        
        System.out.print("Veuillez selectionner parmis un des cas : ");

    }
    public static void PrintUpI(Scanner scanner){
        Boolean casv = false; 
        System.out.println("Choix intervenant :");
        System.out.println("[1] Informations sur votre profile");
        System.out.println("[2] Cas  numéro 2");
        System.out.println("[3] Cas  numéro 3");
        System.out.print("Veuillez selectionner parmis un des cas : ");
        String cas = scanner.nextLine(); 
            
            do{
                switch (cas) {
                    case "1":
                        infoCompte(cas, scanner);
                        
                        // System.out.println("cas choisi = " + cas);
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
                        System.out.print("Cas inexistant... Veuillez réessayer : ");
                        cas = scanner.nextLine();
                        break;
                }
            } while(!casv);

    }
}
