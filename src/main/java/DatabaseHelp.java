import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseHelp {
    public File createFile(String fileName){
        File file;
        try
        {
            file = new File(fileName+".txt");
            if(!file.exists()){
                file.createNewFile();
            }
        }
        catch(Exception e)
        {
            return null;
        }
        return file;
    }

    public boolean writeFile(File currentFile, String currentValues){
        try{
            FileWriter fileWriter = new FileWriter(currentFile.getName(), true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(currentValues);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean readFile(File currentFile){
        try{
            FileReader fileReader = new FileReader(currentFile.getName());
            BufferedReader reader = new BufferedReader(fileReader);
            String currentLine;
            while ((currentLine = reader.readLine())!=null){
                System.out.println(currentLine);
            }
            reader.close();
        }
        catch(Exception e){

        }
        return true;
    }

    public String valueCreation(ArrayList<String> currentValues){
        String values = null;
        for(String i: currentValues){
            if(values == null){
                values = i;
            }
            else{
                values = values.concat(","+i);
            }
        }
        return values;
    }

    public String[] fetchUserData(String currentUserID, File dbFile){
        try{
            FileReader fileReader = new FileReader(dbFile.getName());
            BufferedReader reader = new BufferedReader(fileReader);
            String currentLine;
            while((currentLine = reader.readLine())!=null){
                String[] userInfo = currentLine.split(",",4);
                if(userInfo[0].equals(currentUserID)){
                    return userInfo;
                }
            }
            reader.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public String fetchTableData(File dbFile){
        try{
            FileReader fileReader = new FileReader(dbFile.getName());
            BufferedReader reader = new BufferedReader(fileReader);
            String temp = reader.readLine();
            reader.close();
            return temp;
        }
        catch (Exception e){
            return null;
        }
    }

    File updateValues(File tableFile, Integer index, String currentVal, ArrayList<Integer> indexes, ArrayList<String> values){
        File temp = createFile("temp");
        try{
            FileReader fileReader = new FileReader(tableFile.getName());
            BufferedReader reader = new BufferedReader(fileReader);
            //PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(dbFile.getName())));
            String currentLine;
            while((currentLine = reader.readLine())!= null){
                String[] breaks = currentLine.split(",");
                if(breaks[index].equals(currentVal)){

                    for(int i: indexes){
                        for(String j: values){
                            //breaks[i] = j;
                            currentLine = currentLine.replaceAll(breaks[i],j);
                        }
                    }
                }
                writeFile(temp,currentLine);

            }
            reader.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return temp;

    }

    File deleteValues(File tableFile, Integer index, String currentVal){
        File temp = createFile("temp");
        try{
            FileReader fileReader = new FileReader(tableFile.getName());
            BufferedReader reader = new BufferedReader(fileReader);
            //PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(dbFile.getName())));
            String currentLine;
            while((currentLine = reader.readLine())!= null){
                String[] breaks = currentLine.split(",");
                if(breaks[index].equals(currentVal)==false){
                    writeFile(temp,currentLine);
                }
            }
            reader.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return temp;
    }

    public boolean displayValues(File tableFile, ArrayList<Integer> indexes){
        try{
            FileReader fileReader = new FileReader(tableFile.getName());
            BufferedReader reader = new BufferedReader(fileReader);
            String currentLine;
            while((currentLine = reader.readLine())!= null){
                String newLine = null;
                String[] breaks = currentLine.split(",");
                for( int i: indexes){
                    if(newLine == null){
                        newLine = breaks[i];
                    }
                    else {
                        newLine = newLine.concat(",");
                        newLine = newLine.concat(breaks[i]);
                    }
                }
                System.out.println(newLine);
            }
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean displayValuesWhere(File tableFile, Integer index, String currentVal,ArrayList<Integer> indexes){
        try{
            FileReader fileReader = new FileReader(tableFile.getName());
            BufferedReader reader = new BufferedReader(fileReader);
            String currentLine;
            while((currentLine = reader.readLine())!= null){
                String newLine = null;
                String[] breaks = currentLine.split(",");
                if(breaks[index].equals(currentVal)){
                    for(int i: indexes){
                        if(newLine == null){
                            newLine = breaks[i];
                        }
                        else {
                            newLine = newLine.concat(",");
                            newLine = newLine.concat(breaks[i]);
                        }
                    }
                    System.out.println(newLine);
                }
            }
        }
        catch (Exception e){
            return false;
        }
        return true;
    }


    ArrayList<Integer> getIndexes(ArrayList<String> columnName, String[] table){
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i = 0; i<table.length; i++){
            for(int j=0; j<columnName.size();j++){
                if(columnName.get(j).equals(table[i])){
                    indexes.add(i); //stores index values of data we need to update
                }
            }
        }
        return indexes;
    }

}
