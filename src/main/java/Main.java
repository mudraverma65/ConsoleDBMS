/**
 *Refered to https://www.geeksforgeeks.org/ for basic java syntax along with https://docs.oracle.com/en/java/
 * */
public class Main {

    public static void main(final String[] args) {
        QueryIdentification q1 = new QueryIdentification();
        Authentication a1 = new Authentication();
        if(a1.twoFactor()==true){
            System.out.println("E for exit");
            q1.getQuery();
        }
    }
}
