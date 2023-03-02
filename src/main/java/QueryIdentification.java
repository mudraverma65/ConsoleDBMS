/**
 * Used https://www.w3schools.com/sql/ to refer to all basic SQL syntax.
 * */
import java.util.Scanner;

public class QueryIdentification {
    String firstWord;
    String secondWord;
    int iterate = 0;
    Query queryClass = new Query();
    public void getQuery(){
        Scanner scan = new Scanner(System.in);
        while (iterate == 0)
        {
            System.out.println(">> Enter Query: ");
            String currentQuery = scan.nextLine();
            queryIdentification(currentQuery);
        }
    }

    public boolean queryIdentification(String currentQuery){
        if((currentQuery.equals("E"))==true){
            iterate = 1;
            return true;
        }

        String[] aQuery = currentQuery.split(" ", 3);

        firstWord = aQuery[0];
        secondWord = aQuery[1];


        if(firstWord.equals("create") && secondWord.equals("database")){
            queryClass.createDatabase("database",aQuery[2]);
            //Database creation
        }

        if(firstWord.equals("create") && secondWord.equals("table")){
            queryClass.createTable(aQuery[2]);
            //Table creation
        }

        if(firstWord.equals("select") && secondWord.equals("*") ){
            aQuery[2] = aQuery[2].replaceAll("from","");
            aQuery[2] = aQuery[2].replaceAll("\\s+|;","");
            queryClass.selectAll(aQuery[2]);
            //Select Statement
        }

        if(firstWord.equals("select") && secondWord.equals("*")==false){
            queryClass.select(aQuery[1],aQuery[2]);
            //select statement with conditions
        }

        if(firstWord.equals("insert") ){
            queryClass.insert(aQuery[2]);
            //insert statement
        }

        if(firstWord.equals("update")){
            queryClass.update(aQuery[1],aQuery[2]);
            //Update Statement
        }

        if(firstWord.equals("delete")){
            String[] parts = new String[5];
            try{
                //Delete Statement
                aQuery[2] = aQuery[2].replaceAll("\\s+|;","");
                parts = aQuery[2].split("where",2);
                queryClass.delete(parts[0],parts[1]);
            }
            catch (ArrayIndexOutOfBoundsException e){
                queryClass.deleteAll(parts[0]);
            }
        }
        return true;
    }
}
