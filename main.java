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
        Boolean choix = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("[1] Se connecter");
        System.out.println("[2] Créer un compte");
        System.out.print("Selectionnez une des deux options : ");
        String choisi = scanner.nextLine();
        do{
            switch (choisi) {
                case "1":
                    System.out.println("Veuillez vous connecter!");
                    System.out.print("Adresse mail : ");
                    choix = true;
                    break;
                case "2":
                    System.out.println("Veuillez contacter l'administrateur.");
                    choix = true;
                    System. exit(0);
                    break;
                default:
                    System.out.print("Cas inexistant. Veuillez réessayer : ");
                    choisi = scanner.nextLine();
                    break;
                    
            }
        }while (!choix);


        
        
        String username = scanner.nextLine();
        String mdp = "";
        Boolean valide = false;
        Boolean valido = false;
        Boolean type = false;
        do {
        switch (username) {
            case "resident1@gmail.com":
                System.out.print("Mot de passe de " +"\'" + username +  "\' : ");
                valido = true;
                mdp = scanner.nextLine();
                
                break;
            case "intervenant1@gmail.com":
                System.out.print("Mot de passe de " +"\'" + username +  "\' : ");
                valido = true;
                mdp = scanner.nextLine();
                
                break;
            default:
                System.out.print("Adresse mail incorrecte. Veuillez réessayer : ");
                username = scanner.nextLine();
                break;
                
        } 
        } while (!valido);  
        do{
        switch (mdp) {
            case "maville2024":
                System.out.println("Bienvenue  Isabelle!" );
                valide = true;
                type = true;
                break;
            case "ameliore2024@":
                System.out.println("Bienvenue Marcel!");
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
            PrintUpR(scanner);
            }
        else{
            PrintUpI(scanner); 
            
        }
        
        
        

        
        scanner.close();
        
    
    }
    public static void infoCompteI(String a, Scanner scanner){
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
                    System.out.println("Nom complet : Marcel Nasraoui");
                    System.out.println("Adresse courriel : intervenant1@gmail.com");
                    System.out.println("Type de compte : Entreprise public");
                    System.out.println("Indentifiant de la ville : 40202494 ");
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
    public static void infoCompteR(String a, Scanner scanner){
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
                    System.out.println("Nom complet : Isabelle Belanger");
                    System.out.println("Date de naissance : 15/03/1994 ");
                    System.out.println("Adresse courriel : resident1@gmail.com");
                    System.out.println("Numéro de téléphone : +1 (514) 693 3204 ");
                    System.out.println("Adresse : 4117 St Laurent Blvd, Montreal, Quebec H2W 1Y7, Canada ");
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

    public static void PrintUpRR(){
        System.out.println("Choix résident :");
        System.out.println("[1] Informations sur votre profil");
        System.out.println("[2] Cas  numéro 2");
        System.out.println("[3] Cas  numéro 3");
        
        System.out.print("Veuillez selectionner parmis un des cas : ");

    }
    public static void PrintUpI(Scanner scanner){
        Boolean casv = false; 
        System.out.println("Choix intervenant :");
        System.out.println("[1] Informations sur votre profil");
        System.out.println("[2] Cas  numéro 2");
        System.out.println("[3] Cas  numéro 3");
        System.out.print("Veuillez selectionner parmis un des cas : ");
        String cas = scanner.nextLine(); 
            
            do{
                switch (cas) {
                    case "1":
                        infoCompteI(cas, scanner);
                        
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
    public static void PrintUpR(Scanner scanner){
        Boolean casv = false; 
        System.out.println("Choix résident :");
        System.out.println("[1] Informations sur votre profil");
        System.out.println("[2] Cas  numéro 2");
        System.out.println("[3] Cas  numéro 3");
        System.out.print("Veuillez selectionner parmis un des cas : ");
        String cas = scanner.nextLine(); 
            
            do{
                switch (cas) {
                    case "1":
                        infoCompteR(cas, scanner);
                        
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
