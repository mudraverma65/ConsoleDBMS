import java.io.File;
import java.util.ArrayList;

public class Query {
    DatabaseHelp helperClass = new DatabaseHelp();
    public boolean createDatabase(String database, String dbName){
        File tableFile = new File(database+".txt");
        if(tableFile.exists()){
            System.out.println("Database limit exceeded");
            return false;
        }
        else{
            tableFile = helperClass.createFile(database);
            helperClass.writeFile(tableFile,dbName);
            System.out.println("Database Created");
        }
        return true;
    }

    public boolean createTable(String currentQuery){
        try{
            ArrayList<String> tableData = new ArrayList<>();
            String[] twoParts = currentQuery.split("\\(",2);
            File tableFile = helperClass.createFile(twoParts[0]);
            String[] secondPart = twoParts[1].split(",");
            for(int i = 0;i<secondPart.length;i++){
                secondPart[i] = secondPart[i].stripLeading();
                String[] name = secondPart[i].split(" ",2);
                name[0] = name[0].replaceAll("\\)|;","");
                tableData.add(name[0]);
            }
            String values = helperClass.valueCreation(tableData);
            Boolean b1 = helperClass.writeFile(tableFile, values);
        }
        catch (Exception e){
            System.out.println("Syntax error");
            return false;
        }
        System.out.println("Table created");
        return true;
    }

    public boolean selectAll(String dbFile){
        try{
            File tableFile = helperClass.createFile(dbFile);
            helperClass.readFile(tableFile);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean select(String rowDisplay, String currentQuery){
        try{
            int whereV = 0;
            ArrayList<String> columnName = new ArrayList<>();
            ArrayList<Integer> indexes = new ArrayList<>();
            currentQuery = currentQuery.replaceAll("\\s+|;",""); //Reference: https://www.scaler.com/topics/remove-whitespace-from-string-in-java/
            String[] firstPart = currentQuery.split("from",2);
            String[] secondPart = firstPart[1].split("where",2);
            File tableFile = helperClass.createFile(secondPart[0]);

            String[] displayRows = rowDisplay.split(",");
            for(String s:displayRows){
                columnName.add(s);
            }

            String tableInfo = helperClass.fetchTableData(tableFile); //returns first line of table that contains name of its columns
            String[] table = tableInfo.split(",");
            indexes = helperClass.getIndexes(columnName,table);

            //where clause
            //secondPart[0] = file name
            //aQuery[1] = rows to display
            //secondPart[1] = where clause

            if(currentQuery.contains("where")){
                String[] partTwo = secondPart[1].split("=",2);
                for(int m = 0; m<table.length;m++){
                    if(table[m].equals(partTwo[0])){
                        whereV = m;
                    }
                }
                helperClass.displayValuesWhere(tableFile,whereV,partTwo[1],indexes);
            }
            else{
                helperClass.displayValues(tableFile,indexes);
            }
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean insert(String currentQuery){
        try{
            String[] twoParts = currentQuery.split(" ",2);
            File tableFile = helperClass.createFile(twoParts[0]);
            String[] tableValues = twoParts[1].split("values",2);
            tableValues[1] = tableValues[1].replaceAll("\\(|\\)|;","");
            tableValues[1] = tableValues[1].replaceAll("\\s+",""); //Reference: https://www.scaler.com/topics/remove-whitespace-from-string-in-java/
            Boolean b1 = helperClass.writeFile(tableFile, tableValues[1]);
        }
        catch (Exception e){
            System.out.println("Syntax Error");
            return false;
        }
        System.out.println("Values Inserted");
        return true;
    }

    public boolean update(String dbFile,String currentQuery){
        try{
            Integer whereV = null;
            ArrayList<String> columnName = new ArrayList<>();
            ArrayList<String> columnValue = new ArrayList<>();
            ArrayList<Integer> indexes = new ArrayList<>();
            File tableFile = helperClass.createFile(dbFile);
            String[] queryParts = currentQuery.split("where",2);
            queryParts[0] = queryParts[0].replaceAll("set",""); //removing word set
            String[] updateValues = queryParts[0].split(",");
            for(int i=0;i< updateValues.length;i++){
                String[] partOne = updateValues[i].split("=",2);
                partOne[0] = partOne[0].strip();
                columnName.add(partOne[0]);
                partOne[1] = partOne[1].strip();
                partOne[1] = partOne[1].replaceAll("'","");
                columnValue.add(partOne[1]);
            }

            String tableInfo = helperClass.fetchTableData(tableFile); //returns first line of table that contains name of its columns
            String[] table = tableInfo.split(",");
            indexes = helperClass.getIndexes(columnName,table);

            //Where clause
            queryParts[1] = queryParts[1].replaceAll("\\s+|;","");
            String[] partTwo = queryParts[1].split("=",2);
            for(int m = 0; m<table.length;m++){
                if(table[m].equals(partTwo[0])){
                    whereV = m;
                }
            }

            File newFile = helperClass.updateValues(tableFile,whereV,partTwo[1],indexes,columnValue);
            File realName = new File(tableFile.getPath());
            realName.delete();
            newFile.renameTo(realName);
        }
        catch (Exception e){
            System.out.println("Syntax error");
            return false;
        }
        System.out.println("Record updated");
        return true;
    }

    public boolean delete(String dbFile,String currentQuery){
        try{
            File tableFile = helperClass.createFile(dbFile);
            Integer whereV = 0;
            String[] queryParts = currentQuery.split("=",2);
            queryParts[1] = queryParts[1].replaceAll("'","");

            String tableInfo = helperClass.fetchTableData(tableFile); //returns first line of table that contains name of its columns
            String[] table = tableInfo.split(",");

            for(int m = 0; m<table.length;m++){
                if(table[m].equals(queryParts[0])){
                    whereV = m;
                }
            }

            File newFile = helperClass.deleteValues(tableFile,whereV,queryParts[1]);
            File realName = new File(tableFile.getPath());
            realName.delete();
            newFile.renameTo(realName);
        }
        catch (Exception e){
            System.out.println("Syntax error");
            return false;
        }
        System.out.println("Records deleted");
        return true;
    }

    public boolean deleteAll(String dbFile){
        try{
            File tableFile = helperClass.createFile(dbFile);
            String tableInfo = helperClass.fetchTableData(tableFile);
            File temp = helperClass.createFile("temp");
            helperClass.writeFile(temp,tableInfo);
            File realName = new File(tableFile.getPath());
            realName.delete();
            temp.renameTo(realName);
        }
        catch (Exception e){
            System.out.println("Syntax Error");
            return false;
        }
        System.out.println("Records deleted");
        return true;
    }

}
