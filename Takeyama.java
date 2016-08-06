import java.io.*;
import java.util.*;

class Ingredient{
    public static List<String> opt;
    public static List<String> rule;
    public static int[][] dep;
    public static int[] eval; //評価パケット数を格納する配列
}

public class Takeyama{
    public static void main(String[] args){

	if(args.length != 2){
	    System.out.println("Arguments Error!\nUsage: $ java Takeyama <AdjacencyListFile> <OutputFile>");
	    System.exit(1);
	}
	try{
	    List<String> opt = new ArrayList<String>();
	    Ingredient.rule = new ArrayList<String>();
	    File rInput = new File(args[0]);
	    BufferedReader br = new BufferedReader(new FileReader(rInput));//入力ファイル
	    File output = new File(args[1]);
	    BufferedWriter bw = new BufferedWriter(new FileWriter(output));//出力ファイル
	    String str;
	    
	    while((str = br.readLine()) != null)
		Ingredient.rule.add(str);

	    opt = useTakeyamaMethod(Ingredient.rule);

	    for(String r : opt){
		//System.out.println(r);
		bw.write(r);
		bw.newLine();
	    }

	    br.close();
	    bw.close();
	}catch(FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}


    }
public static List<String> useTakeyamaMethod(List<String> rule){

    int rSize = rule.size();
    int place;
    Ingredient.eval = new int[rSize]; //評価パケット数を格納する配列
    String[][] sdep = new String[rSize][]; //従属関係を格納する配列
    Ingredient.dep = new int[rSize][]; //従属関係を格納する配列
    
    for(int i = 0;i < rSize; i++){
	place = rule.get(i).indexOf(" ");
	System.out.println(place);
	if(place!=-1){
	    Ingredient.eval[i] = Integer.parseInt(rule.get(i).substring(0,place));
	    sdep[i] = rule.get(i).substring(place+1).split(",");
	}
	else{
	    Ingredient.eval[i] = Integer.parseInt(rule.get(i));
	    sdep[i][] =new int[1];
	    sdep[i][0] = "0";
	}
	for(int j = 0; j < sdep[i].length; j++)
	    Ingredient.dep[i][j] = Integer.parseInt(sdep[i][j])-1;
		    

    }
    while(rSize != Ingredient.opt.size()){
	int base = -1;
	int index = -1;
	
	for(int i = 0; i < rSize; i++){
	    if(Ingredient.eval[i] > base){
		base = Ingredient.eval[i];
		index = i;
	    }
	    }

	subProcedure(Ingredient.dep[index]);
	Ingredient.opt.add(Ingredient.rule.get(index));
	Ingredient.eval[index] = -1;	
    }
    return Ingredient.opt;
}

public static void subProcedure(int[] subDep){
    int base = -1;
    int index = -1;
    
    for(int i = 0; i < subDep.length; i++){
	if(Ingredient.eval[subDep[i]] > base){
	    base = Ingredient.eval[subDep[i]];
	    index = subDep[i];
	}
    } 
    while(base != -1){
	subProcedure(Ingredient.dep[index]);
	Ingredient.opt.add(Ingredient.rule.get(index));
	Ingredient.eval[index] = -1;
	base = -1;
	for(int i = 0; i < subDep.length; i++){
	    if(Ingredient.eval[subDep[i]] > base){
		base = Ingredient.eval[subDep[i]];
		index = subDep[i];
	    }
	}
    }
}
}
