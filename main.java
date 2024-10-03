import java.util.Scanner;  
public class main {
    public static void main(String[] args){
        System.out.println("Enter username");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String userName = myObj.nextLine();  // Read user input
        
        switch (userName) {
            case "2":
                System.out.println("lol");
                break;
        
            default:
                System.out.println("hello " + userName);
                break;
        }
    }
}
