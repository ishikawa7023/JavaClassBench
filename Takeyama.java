import java.io.*;
import java.util.*;
import graphAlgo.Vertex;

public class Takeyama {
    LinkedList<Vertex> takeyamaList;
    
    public Takeyama(LinkedList<Vertex> list) {
	takeyamaList = new LinkedList<Vertex>();
	LinkedList<Vertex> copyList = new LinkedList<Vertex>(list);
	MainProcedure(takeyamaList, copyList);	
    }

 void printList() {
	System.out.println("==== TAKEYAMA ====");
	for (int i = 0; i < takeyamaList.size(); ++i) {
	    System.out.println(takeyamaList.get(i));
	}
	System.out.println("L(R) = " + getLatency() + "\n");
    }
    int getLatency() {
	int latency = 0;
	for (int i = 0; i < takeyamaList.size(); ++i) {
	    latency += (i+1) * (takeyamaList.get(i)).getRuleWeight();
	}
	latency -= (takeyamaList.get(takeyamaList.size()-1)).getRuleWeight();

	return latency;
    }
    
    void MainProcedure(LinkedList<Vertex> R,LinkedList<Vertex> H){
	while(!H.isEmpty()){
	    int base = -1;
	    int index = -1;
	    int ruleNum = -1;
	    
	    for(int i = 0; i < H.size(); i++){
		if((H.get(i)).getRuleWeight() > base){//max heap H に存在していて最大の評価パケット数を探す
		    base = (H.get(i)).getRuleWeight();
		    index = i;
		    ruleNum = (H.get(i)).getRuleNum();
		}
	    }
	    SubProcedure(H.get(index),R,H);
	    for(int i = 0; i < H.size(); i++){
		if(ruleNum == (H.get(i)).getRuleNum()){
		    index = i;
		    break;
		}
	    }
	    R.add(H.get(index));
	    H.remove(index);
	}
    }
      
    void SubProcedure(Vertex r,LinkedList<Vertex> R,LinkedList<Vertex> H){
	if(r.getAddjanceyList().isEmpty())
	    return;
	int base = -1;
	int index = -1;
	int ruleNum = -1;
	
	for(int i = 0; i < r.getAddjanceyList().size(); i++){
	    for(int j = 0; j < H.size(); j++){		
		if(r.getAddjanceyList().get(i) == (H.get(j)).getRuleNum() && (H.get(j)).getRuleWeight() > base){
		    base = (H.get(j)).getRuleWeight();
		    index = j;
		    ruleNum = (H.get(j)).getRuleNum();
		    break;
		}
	    }
	}
	while(base != -1){
	    SubProcedure(H.get(index),R,H);
	    for(int i = 0; i < H.size(); i++){
		if(ruleNum == (H.get(i)).getRuleNum()){
		    index = i;
		    break;
		}
	    }
	    R.add(H.get(index));
	    H.remove(index);
	    base = -1;
	    for(int i = 0; i < r.getAddjanceyList().size(); i++){
		for(int j = 0; j < H.size(); j++){		
		    if(r.getAddjanceyList().get(i) == (H.get(j)).getRuleNum() && (H.get(j)).getRuleWeight() > base){
			base = (H.get(j)).getRuleWeight();
			index = j;
			ruleNum = (H.get(j)).getRuleNum();
			break;
		    }
		}
	    }
	}
    }
}

/*
class Ingredient{
    public static List<String> opt;
    public static List<String> rule;//R
    public static Map<String,String> map; //max heap H
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
	Ingredient.map = new HashMap<String,String>(); //max heap H
	for(int i = 0; i < rSize; i++){
	    Ingredient.map.put( String.valueOf(i) , rule.get(i) );
	}
	int place;
	Ingredient.eval = new int[rSize]; //評価パケット数を格納する配列
	String[][] sdep = new String[rSize][]; //従属関係を格納する配列
	Ingredient.dep = new int[rSize][]; //従属関係を格納する配列
	for(int i = 0;i < rSize ; i++){
	    place = rule.get(i).indexOf(" ");
	    //System.out.println(place);
	    if(place!=-1){
		Ingredient.eval[i] = Integer.parseInt(rule.get(i).substring(0,place));
		sdep[i] = rule.get(i).substring(place+1).split("," );
		Ingredient.dep[i] = new int[sdep[i].length];
		for(int j = 0; j < sdep[i].length; j++){
		    //		System.out.println(sdep[i].length + " " +  sdep[i][0] +" " + sdep[i][1]);
		    Ingredient.dep[i][j] = Integer.parseInt(sdep[i][j])-1;
		}
	    }
	    else{
		Ingredient.eval[i] = Integer.parseInt(rule.get(i));
		//		sdep[i][] =new int[1];
		//		sdep[i][0] = "0";
	    }
	}
	Ingredient.opt = new ArrayList<String>();
	while(!(Ingredient.map.isEmpty()) ){
	    int base = -1;
	    int index = -1;
	    for(int i = 0; i < rSize; i++){
		//	       System.out.println(Ingredient.eval[i]);
		if(Ingredient.map.containsKey(String.valueOf(i)) && Ingredient.eval[i] > base){//max heap H に存在していて最大の評価パケット数を探す
		    base = Ingredient.eval[i];
		    index = i;
		}
	    }
	    subProcedure(Ingredient.dep[index]);
	    Ingredient.opt.add( (index+1) +"|" + Ingredient.rule.get(index));
	    Ingredient.map.remove(String.valueOf(index));
	    //	    Ingredient.eval[index] = -1;
	}
	return Ingredient.opt;
    }
    public static void subProcedure(int[] subDep){
	if(subDep == null)
	    return;
	//	Ingredient.opt = new ArrayList<String>(); //最適なリスト
	int base = -1;
	int index = -1;
	for(int i = 0; i < subDep.length; i++){
	    //	    System.out.println(subDep[i]);
	    if(Ingredient.map.containsKey(String.valueOf(subDep[i])) && Ingredient.eval[subDep[i]] > base){
		base = Ingredient.eval[subDep[i]];
		index = subDep[i];
	    }
	}

	while(base != -1){
	    subProcedure(Ingredient.dep[index]);
	    Ingredient.opt.add( (index+1) +"|" + Ingredient.rule.get(index));
	    Ingredient.map.remove(String.valueOf(index));
	    //	    Ingredient.eval[index] = -1;
	    
	    //	    for(int i=0;i<5;i++)
	    //	System.out.println(Ingredient.map.get(String.valueOf(i)));

	    base = -1;
	    for(int i = 0; i < subDep.length; i++){
		//		System.out.println(Ingredient.map.containsKey(String.valueOf(subDep[i])));
		if(Ingredient.map.containsKey(String.valueOf(subDep[i])) && Ingredient.eval[subDep[i]] > base ){
		    base = Ingredient.eval[subDep[i]];
		    index = subDep[i];
		}
	    }
	    //	System.out.println(base);
	}
    }
}
*/
