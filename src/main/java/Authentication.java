import java.io.File;
import java.util.*;

public class Authentication {

    File dbFile;

    DatabaseHelp dbHelp = new DatabaseHelp();
    public boolean login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID: ");
        String currentUserID = sc.nextLine();
        dbFile = dbHelp.createFile("userData");
        String[] userInfo = dbHelp.fetchUserData(currentUserID, dbFile);
        if(userInfo == null){
            System.out.println("User ID incorrect");
            return false;
        }
        System.out.println("Enter Password: ");
        if(sc.nextLine().equals(userInfo[1]) == false){
            System.out.println("Incorrect Password");
            return false;
        }
        System.out.println("Question: "+userInfo[2]);
        System.out.println("Enter Answer");
        if(sc.nextLine().equals(userInfo[3]) == false){
            System.out.println("Incorrect Answer");
            return false;
        }
        System.out.println("Login Successful");
        return true;
    }
    public boolean signUp(){
        try{
            ArrayList<String> currentUser = new ArrayList<>();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter ID: ");
            currentUser.add(sc.nextLine());
            System.out.println("Enter Password: ");
            currentUser.add(sc.nextLine());
            System.out.println("Enter Question: ");
            currentUser.add(sc.nextLine());
            System.out.println("Enter Answer: ");
            currentUser.add(sc.nextLine());
            dbFile = dbHelp.createFile("userData");
            String values = dbHelp.valueCreation(currentUser);
            dbHelp.writeFile(dbFile, values);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean twoFactor(){
        Scanner scan = new Scanner(System.in);
        System.out.println("1. Login\n2. Signup");
        if(scan.nextInt() == 1){
            if(login()==true){
                System.out.println("User Logged In");
            }
            else {
                System.out.println("Error");
                return false;
            }
        }
        else{
            if(signUp() == true){
                System.out.println("User Created. Login please");
                if(login()==false){
                    return false;
                }
            }
            else {
                System.out.println("Error");
                return false;
            }
        }
        return true;
    }
}
