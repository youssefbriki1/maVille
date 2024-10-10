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
        int x = 0;
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
                x = x+1;
                System.out.print("Adresse mail incorrecte. Tentatives restantes : "+ (3-x) +". Veuillez réessayer : ");
                username = scanner.nextLine();
                if (x==2){
                    System.out.println("Nombre de tentatives dépassé.");
                    System.exit(0);
                }
                break;
                
        } 
        } while (!valido);
        x = 0;  
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
                x = x+1;
                System.out.print("Mot de passe incorrect. Tentatives restantes : "+ (3-x) +". Veuillez réessayer : ");
                mdp = scanner.nextLine();
                if (x==2){
                    System.out.println("Nombre de tentatives dépassé.");
                    System.exit(0);
                }
                break;
        }
        } while(!valide);
        System.out.println();
    
        
        
        
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
                                PrintUpR(scanner);
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
                    PrintUpR(scanner);
                    nice = true;
                    break;
                    
                default:
                    System.out.print("Cas inexistant.. Veuillez réessayer : ");
                    a = scanner.nextLine();
                    break;
            }
        } while(!nice);

    }
    public static void ConsulterI(String a, Scanner scanner){
        Boolean nice = false;
        System.out.println();
        System.out.println("[1] Afficher la liste des requêtes de travaux");
        System.out.println("[2] Filtrer les requêtes");
        System.out.println("[3] Soumettre votre candidature et les dates pour un projet");
        System.out.println("[4] Retourner au menu princpal");
        System.out.print("Veuillez selectionner parmis un des cas : ");
        a = scanner.nextLine();
        do{
            switch (a) {
                case "1":
                    System.out.println("cas choisi = " + a);
                    nice = true;
                    break;
                case "2":
                    System.out.println();
                    System.out.println("[1] Filtrer par type");
                    System.out.println("[2] Filtrer par quartier");
                    System.out.println("[3] Filtrer par date de début");
                    System.out.println("[4] Revenir au menu princpal");
                    System.out.print("Veuillez selectionner parmis un des cas : ");
                    Boolean varia = false;
                    a = scanner.nextLine();
                    do{
                        switch (a) {
                            case "1":
                                System.out.println("cas choisi = " + a);
                                varia = true;
                                break;
                            case "2":
                                System.out.println("cas choisi = " + a);
                                varia = true;
                                break;
                            case "3":
                                System.out.println("cas choisi = " + a);
                                varia = true;
                                break;
                            case "4":
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
                case "3":
                    System.out.println("cas choisi = " + a);
                    nice = true;
                    break;
                case "4":
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
    public static void SoumettreI(String a, Scanner scanner){
        Boolean nice = false;
        System.out.println();
        System.out.println("[1] Soumettre un nouveau projet de travaux");
        System.out.println("[2] Consulter les préférences des résidents");
        System.out.println("[3] Mettre à jour les informations d'un chantier");
        System.out.println("[4] Retourner au menu princpal");
        System.out.print("Veuillez selectionner parmis un des cas : ");
        a = scanner.nextLine();
        do{
            switch (a) {
                case "1":
                    System.out.println();
                    System.out.print("Titre du projet : ");
                    a = scanner.nextLine();
                    System.out.print("Description du projet : ");
                    a = scanner.nextLine();
                    System.out.print("Type de travaux : ");
                    a = scanner.nextLine();
                    System.out.println("Quartiers affectés");
                    System.out.println("[1] Côtes des neiges");
                    System.out.println("[2] Downtown");
                    System.out.println("[3] Montréal-Nord");
                    System.out.println("[4] Autre (Précisez)");
                    System.out.print("Veuillez selectionner parmis un ou plusieurs quartier : ");
                    a = scanner.nextLine();
                    System.out.print("Rues affectées : ");
                    a = scanner.nextLine();
                    System.out.print("Date de début : ");
                    a = scanner.nextLine();
                    System.out.print("Date de fin : ");
                    a = scanner.nextLine();
                    System.out.print("Horaire des travaux : ");
                    a = scanner.nextLine();
                    System.out.print("Projet soumis ! Selectionnez 1 pour retourner au menu princpal : ");
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
                    System.out.println("Jean : Pas de travaux le soir");
                    System.out.println("Michelle : Pas de travaux entre 18h et 23h");
                    nice = true;
                    break;
                case "3":
                    System.out.print("Selectionnez le projet à modifier : ");
                    nice = true;
                    break;

                case "4":
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

    public static void PrintUpI(Scanner scanner){
        Boolean casv = false; 
        System.out.println("Choix intervenant :");
        System.out.println("[1] Informations sur votre profil");
        System.out.println("[2] Consulter la liste des requêtes de travaux");
        System.out.println("[3] Projets de travaux");
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
                        ConsulterI(cas, scanner);
                        casv = true;
                        break;
                    case "3":
                        SoumettreI(cas, scanner);
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
        System.out.println("[2] Services travaux");
        System.out.println("[3] Soumettre une requête de travail");
        System.out.println("[4] Préférences et Avis");
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
                        ServicesR(scanner);
                        casv = true;
                        break;
                    case "3":
                        System.out.println("cas choisi = " + cas);
                        casv = true;
                        break;
                    case "4":
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
    public static void ServicesR(Scanner scanner){
        Boolean nice = false;
        System.out.println();
        System.out.println("[1] Rechercher un projet");
        System.out.println("[2] Consulter les travaux en cours/à venir");
        System.out.println("[3] S'abonner aux notifications d'un projet");
        System.out.println("[4] Retourner au menu princpal");
        System.out.print("Veuillez selectionner parmis un des cas : ");
        String a = scanner.nextLine();
        do{
            switch (a) {
                case "1":
                    FiltrerR(scanner);
                    
                    
                    nice = true;
                    break;
                case "2":
                    FiltrerR(scanner);
                    nice = true;
                    break;
                case "3":
                    FiltrerR(scanner);
                    nice = true;
                    break;
                case "4":
                    System.out.println();
                    PrintUpR(scanner);
                    nice = true;
                    break;
                    
                default:
                    System.out.print("Cas inexistant.. Veuillez réessayer : ");
                    a = scanner.nextLine();
                    break;
            }
        } while(!nice);

    }
    public static void FiltrerR(Scanner scanner){
        System.out.println();
        System.out.println("[1] Entrer le nom d'un projet");
        System.out.println("[2] Filtrer les travaux");
        System.out.println("[3] Revenir au menu principal");
        System.out.print("Selectionnez une des options : ");
        Boolean nice = false;
        String a = scanner.nextLine();
        do{
            switch (a) {
                case "1":
                    System.out.println();
                    System.out.print("Veuillez écrire le nom du projet : ");
                    Boolean varia = false;
                    a = scanner.nextLine();
                    do{
                        switch (a) {
                            default:
                                System.out.println("Projet introuvable. Veuillez contacter l'administrateur.");
                                varia = true;

                                break;
                        }
                    }while (!varia);
                    
                    nice = true;
                    break;
                case "2":
                    System.out.println();
                    System.out.println("[1] Filtrer par Quartier");
                    System.out.println("[2] Filtrer par type de travaux ");
                    System.out.println("[3] Filtrer par rue");
                    System.out.println("[4] Revenir au menu princpal");
                    System.out.print("Selectionnez une des options : ");
                    Boolean varial = false;
                    a = scanner.nextLine();
                    do{
                        switch (a) {
                            case "1":
                                System.out.println("À Implémenter :P");
                                varial = true;
                                break;
                            case "2":
                                System.out.println("À Implémenter :P");
                                varial = true;
                                break;
                            case "3":
                                System.out.println("À Implémenter :P");
                                varial = true;
                                break;
                            case "4":
                                varial = true;
                                System.out.println();
                                PrintUpR(scanner);
                                break;
                        
                            default:
                                System.out.print("Veuillez réessayer : ");
                                a = scanner.nextLine();
                                break;
                        }
                    }while (!varial);
                    
                    
                    nice = true;
                    break;
                case "3":
                    System.out.println();
                    PrintUpR(scanner);
                    nice = true;
                    break;
                    
                default:
                    System.out.print("Cas inexistant.. Veuillez réessayer : ");
                    a = scanner.nextLine();
                    break;
            }
        } while(!nice);

    }
}
