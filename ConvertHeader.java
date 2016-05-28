import java.io.*;
import java.util.regex.Pattern;

public class ConvertHeader {
    public static void main(String[] args) {
	if(args.length != 2){
	    System.out.println("引数が不適切です。");
	    System.exit(0);
	}
	String InputFile=args[0],OutputFile=args[1];
	try{
	    BufferedReader reader = new BufferedReader(new FileReader(InputFile));
	    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(OutputFile)));
	    String line;
	    while((line = reader.readLine()) != null){
		Pattern p = Pattern.compile("[\\s]+");
		String[] field = p.split(line);
		int i;
		long[] two,num = {Long.parseLong(field[0]),Long.parseLong(field[1]),Long.parseLong(field[2]),Long.parseLong(field[3]),Long.parseLong(field[4]),Long.parseLong(field[5])};

		field = new String[] {"","","","","",""};

		for(int j=0;j<2;j++){
		    two = new long[35];
		    for(i=0;i<31;i++) {
			two[i] = num[j]%2;
			num[j] = num[j] >> 1;
		    }
		    two[i] = num[j];
		    while(i>=0){
			if(two[i]==1)
			    field[j] += '1';
			else
			    field[j] += '0';
			i--;
		    }
		}
		for(int j=2;j<4;j++){
		    two = new long[35];
		    for(i=0;i<15;i++) {
			two[i] = num[j]%2;
			num[j] = num[j] >> 1;
		    }
		    two[i] = num[j];
		    while(i>=0){
			if(two[i]==1)
			    field[j] += '1';
			else
			    field[j] += '0';
			i--;
		    }
		}
		for(int j=4;j<6;j++){
		    two = new long[35];
		    if(j==4){
			for(i=0;i<7;i++) {
			    two[i] = num[j]%2;
			    num[j] = num[j] >> 1;
			}
			two[i] = num[j];
		    }
		    else{
			for(i=0;i<31;i++) {
			    two[i] = num[j]%2;
			    num[j] = num[j] >> 1;
			}
			two[i] = num[j];
		    }
		    while(j==4 ? i>=0 : i>=16){
			if(two[i]==1)
			    field[j] += '1';
			else
			    field[j] += '0';
			i--;
		    }
		}
		for(i=0;i<6;i++)
		    writer.print(field[i]);
		writer.println("");
	    }
	    reader.close();
	    writer.close();
	}catch (FileNotFoundException e) {
	    System.out.println(InputFile + "が見つかりません。");
	}catch (IOException e){
	    System.out.println(e);
	}
    }
}
