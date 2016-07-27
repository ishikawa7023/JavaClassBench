import java.io.*;
import java.util.*;

class RelatedRule //評価パケット数と従属関係を求めるのに共通するルールの値を格納
{
    public static int rSize;
    
    public static String[][] rField;


    public static void setRule(List<String> rule){

	 rField = new String[rSize][11];
       	
	for(int i=0; i < rSize; i++){
	    rField[i] = rule.get(i).split("\\s+|\\t+");
	    rField[i][0] = rField[i][0].substring(1);

	    String[] num1 = rField[i][0].split("\\.|/") ;
	    String[] num2 = rField[i][1].split("\\.|/") ;
	    int plefix1 = (Integer.parseInt(num1[4]));
	    int plefix2 = (Integer.parseInt(num2[4]));
	    String ZOM1 = (ConvertFilter.tenTotwo(Long.decode(num1[0]),8)) + (ConvertFilter.tenTotwo(Long.decode(num1[1]),8)) + (ConvertFilter.tenTotwo(Long.decode(num1[2]),8)) + (ConvertFilter.tenTotwo(Long.decode(num1[3]),8));
	    String ZOM2 = (ConvertFilter.tenTotwo(Long.decode(num2[0]),8)) + (ConvertFilter.tenTotwo(Long.decode(num2[1]),8)) + (ConvertFilter.tenTotwo(Long.decode(num2[2]),8)) + (ConvertFilter.tenTotwo(Long.decode(num2[3]),8));
	    
	    StringBuilder sb1 = new StringBuilder(ZOM1);
	    for(int j = plefix1; j < 32; j++)
		sb1.setCharAt(j,'*');
	    
	    StringBuilder sb2 = new StringBuilder(ZOM2);
	    for(int j = plefix2; j < 32; j++)
		sb2.setCharAt(j,'*');
	    
	    RelatedRule.rField[i][0] = sb1.toString();
	    RelatedRule.rField[i][1] = sb2.toString();
	    
	}
	
	
    }
}

public class ConvertFilter {//ClassBench形式のルールリストを評価パケット数と従属関係のルールリスト(竹山法やヒキン法)に変換する
    public static void main(String[] args) {
	if(args.length != 3){
	    System.out.println("Arguments Error!\nUsage: $ java ClassBenchToZOM <rulelist> <headerlist> <outputfile>");
	    System.exit(1);
	}
	try {
	    List<String> rule = new ArrayList<String>();
	    List<String> header = new ArrayList<String>();
		   
	    File rInput = new File(args[0]);
	    BufferedReader br1 = new BufferedReader(new FileReader(rInput));//入力ファイル
	    File hInput = new File(args[1]);
	    BufferedReader br2 = new BufferedReader(new FileReader(hInput));//入力ファイル
	    File output = new File(args[2]);
	    BufferedWriter bw = new BufferedWriter(new FileWriter(output));//出力ファイル
	    String str;
		    
	    while((str = br1.readLine()) != null)
		rule.add(str);
		    	  
	    while((str = br2.readLine()) != null)
		header.add(str);

	    RelatedRule.rSize = rule.size();
	    RelatedRule.setRule(rule);
	  
	    int[] eval = makeEvaluation(header);
	    List<String>[] dep = makeDependence();
	    

	    for(int i = 0; i < dep.length; i++){   //結果の表示
		//	System.out.print(eval[i] + "\t");
		bw.write(String.valueOf(eval[i]));
	    	if(0 != dep[i].size()){
		    bw.write(" ");
	    	    for(int j = dep[i].size()-1;0 <= j; j--){
			//	System.out.print(dep[i].get(j));
			bw.write(dep[i].get(j));
	    	    if(j == 0){
			//	System.out.println("");
			bw.newLine();
			break;
	    	    }
		    //   System.out.print(",");
		    bw.write(",");
		    }
	    	}
	    	else{
		    // System.out.println("");
		    bw.newLine();
		}
		}

	    
	    
	    br1.close();
	    br2.close();
	    bw.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
   }
    
    public static List<String>[] makeDependence(){ 

	//	int rSize = rule.size();
	ArrayList<String>[] dep = new ListString[RelatedRule.rSize]; //従属関係を格納する配列
	for(int i = 0; i < dep.length; i++)
	    dep[i] = new ListString();
	//	List<List<String>> dep = new ArrayList<List<String>>();

	for(int i=RelatedRule.rSize-1; 0<=i; i--){
	    for(int j=i-1; 0<=j; j--){
		//if(i=j)
		//  continue;
		if(RelatedRule.rField[i][10].equals(RelatedRule.rField[j][10]))
		    continue;
		if(includeSP(RelatedRule.rField[i][2],RelatedRule.rField[i][4],RelatedRule.rField[j][2],RelatedRule.rField[j][4]) && includeDP(RelatedRule.rField[i][5],RelatedRule.rField[i][7],RelatedRule.rField[j][5],RelatedRule.rField[j][7]) && includePROT(RelatedRule.rField[i][8],RelatedRule.rField[j][8]) && includeFLAG(RelatedRule.rField[i][9],RelatedRule.rField[j][9]) && includeSA(RelatedRule.rField[i][0],RelatedRule.rField[j][0]) && includeDA(RelatedRule.rField[i][1],RelatedRule.rField[j][1])){
		    
		    // System.out.println((i+1) +" "+ (j+1));
		    
		    dep[i].add(String.valueOf(j+1));
		    //   System.out.println(j+1);
		}
	    }
	}

	// for(ArrayList<String> d : dep)
	//     for(String s : d)
	// 	System.out.println(d);
	return dep;	       
    }
    
    public static boolean includeSA(String rule1,String rule2){
	// String[] num1 = rule1.split("\\.|/") ;
	// String[] num2 = rule2.split("\\.|/") ;
	// String ZOM1 = (tenTotwo(Long.decode(num1[0]),16)) + (tenTotwo(Long.decode(num1[1]),16)) + (tenTotwo(Long.decode(num1[2]),16)) + (tenTotwo(Long.decode(num1[3]),16));;
	// String ZOM2 = (tenTotwo(Long.decode(num2[0]),16)) + (tenTotwo(Long.decode(num2[1]),16)) + (tenTotwo(Long.decode(num2[2]),16)) + (tenTotwo(Long.decode(num2[3]),16));
	// int plefix1 = Integer.parseInt(num1[4]);
	// int plefix2 = Integer.parseInt(num2[4]);
	// int min = Math.min(plefix1,plefix2);
	
	for(int i=0; i<32; i++){
	    if(rule1.charAt(i)=='*' || rule2.charAt(i)=='*')
		break;
	    else if(rule1.charAt(i)!=rule2.charAt(i))
		return false;
		}
    return true;	
    
}
    public static boolean includeDA(String rule1,String rule2){
	// String[] num1 = rule1.split("\\.|/") ;
	// String[] num2 = rule2.split("\\.|/") ;
	// String ZOM1 = (tenTotwo(Long.decode(num1[0]),16)) + (tenTotwo(Long.decode(num1[1]),16)) + (tenTotwo(Long.decode(num1[2]),16)) + (tenTotwo(Long.decode(num1[3]),16));
	// String ZOM2 = (tenTotwo(Long.decode(num2[0]),16)) + (tenTotwo(Long.decode(num2[1]),16)) + (tenTotwo(Long.decode(num2[2]),16)) + (tenTotwo(Long.decode(num2[3]),16));
	// int plefix1 = Integer.parseInt(num1[4]);
	// int plefix2 = Integer.parseInt(num2[4]);
	// int min = Math.min(plefix1,plefix2);
	
	for(int i=0; i<32; i++){
	    if(rule1.charAt(i)=='*' || rule2.charAt(i)=='*')
		break;	    
	    if(rule1.charAt(i)!=rule2.charAt(i))
		return false;
		}
    return true;	
	
    }
    public static boolean includeSP(String sr1_1,String sr1_2,String sr2_1,String sr2_2){
	int rule1_1 = Integer.parseInt(sr1_1);
	int rule1_2 = Integer.parseInt(sr1_2);
	int rule2_1 = Integer.parseInt(sr2_1);
	int rule2_2 = Integer.parseInt(sr2_2);
	if(rule1_2 < rule2_1)
	    return false;
	else if(rule2_2 < rule1_1)
	    return false;
	return true;
    }
    public static boolean includeDP(String sr1_1,String sr1_2,String sr2_1,String sr2_2){
	int rule1_1 = Integer.parseInt(sr1_1);
	int rule1_2 = Integer.parseInt(sr1_2);
	int rule2_1 = Integer.parseInt(sr2_1);
	int rule2_2 = Integer.parseInt(sr2_2);
	if(rule1_2 < rule2_1)
	    return false;
	else if(rule2_2 < rule1_1)
	    return false;
	return true;
    }
    public static boolean includePROT(String rule1,String rule2){
	String[] pm1 = rule1.split("/");
	String[] pm2 = rule2.split("/");
	if(pm1[1].equals("0x00") || pm2[1].equals("0x00"))
	    return true;
	else if(pm1[0].equals(pm2[0]))
	    return true;
	return false;
	    
    }
    public static boolean includeFLAG(String rule1,String rule2){
	 String[] fm1 = rule1.split("/");
	 String[] fm2 = rule2.split("/");
	 if(fm1[1].equals("0x0000") || fm2[1].equals("0x0000"))
	     return true;

	     	String FLAG1 = (tenTotwo(Long.decode(fm1[0]),16));
		String MASK1 = (tenTotwo(Long.decode(fm1[1]),16));
		String FLAG2 = (tenTotwo(Long.decode(fm2[0]),16));
		String MASK2 = (tenTotwo(Long.decode(fm2[1]),16));

		for(int i=0; i<16; i++){
		    if( MASK1.charAt(i)=='1' && MASK2.charAt(i)=='1' && FLAG1.charAt(i)!=FLAG2.charAt(i) )
			return false;
			    }
		return true;
    }
    
    public static int[] makeEvaluation(List<String> header){
	
	int hSize = header.size();	
	//	int rSize = rule.size();
	int[] eval = new int[RelatedRule.rSize];//評価パケット数を格納する配列

	//	String[][] RelatedRule.rField = new String[RelatedRule.rSize][11];

	// for(int i = 0; i < RelatedRule.rSize; i++){
	//     RelatedRule.rField[i] = rule.get(i).split("\\s+|\\t+");
	//     RelatedRule.rField[i][0] = RelatedRule.rField[i][0].substring(1);			    
	// }

	// for(int i=0;i<4;i++)
	//     System.out.println(RelatedRule.rField[i][0]+" "+RelatedRule.rField[i][1]+" "+RelatedRule.rField[i][2]+ " "+ RelatedRule.rField[i][4]+ " " + RelatedRule.rField[i][5]+ " " +RelatedRule.rField[i][7]+" "+RelatedRule.rField[i][8]+" "+RelatedRule.rField[i][9]);

	
	for(int j=0; j < hSize; j++){
	    String[] hField = header.get(j).split("\\s+|\\t+");
	    
	    for(int i=0; i < RelatedRule.rSize; i++){
		
	      	if(isMatchSP(RelatedRule.rField[i][2],RelatedRule.rField[i][4],hField[2]) && isMatchDP(RelatedRule.rField[i][5],RelatedRule.rField[i][7],hField[3]) && isMatchPROT(RelatedRule.rField[i][8],hField[4]) && isMatchFLAG(RelatedRule.rField[i][9],hField[5]) && isMatchSA(RelatedRule.rField[i][0],hField[0]) && isMatchDA(RelatedRule.rField[i][1],hField[1])){    
		    eval[i]++;
		    break;
		}
	    }
	}
	return eval;
    }
	public static boolean isMatchSA(String rule,String header){
	    // String[] ZO = rule.split("\\.|/") ;
	    // for(int i=0; i<4; i++ )
	    // 	ZO[i]=tenTotwo(Long.parseLong(ZO[i]),8);
	    //	    String ZOM = ZO[0] + ZO[1] + ZO[2] + ZO[3];
	    // if(ZO[4].equals("0"))
	    // 	return true;
	    // if(!ZO[4].equals("32"))
	    // 	ZOM = ZOM.substring(0,Integer.parseInt(ZO[4]));

	    
	    header = tenTotwo(Long.parseLong(header),32);
	    
	    for(int i=0; i<32; i++){
		if(rule.charAt(i)=='*')
		    break;
		else if(rule.charAt(i)!=header.charAt(i))
		    return false;
	    }
	    return true;
	}
	
	public static boolean isMatchDA(String rule,String header){
	   // String[] ZO = rule.split("\\.|/") ;
	   //  for(int i=0; i<4; i++ )
	   // 	ZO[i]=tenTotwo(Long.parseLong(ZO[i]),8);
	   //  String ZOM = ZO[0] + ZO[1] + ZO[2] + ZO[3];
	   //  if(ZO[4].equals("0"))
	   // 	return true;		    
	   //  if(!ZO[4].equals("32"))
	   // 	ZOM = ZOM.substring(0,Integer.parseInt(ZO[4]));
	   //  if(header.startsWith(ZOM))
	   // 	return true;

	    header = tenTotwo(Long.parseLong(header),32);
	    
	    for(int i=0; i<32; i++){
		if(rule.charAt(i)=='*')
		    break;
		else if(rule.charAt(i)!=header.charAt(i))
		    return false;
	    }
	    return true;
	}
	
	public static boolean isMatchSP(String low,String high,String header){

	    //System.out.println(low + " : " + high +" "+ header);
	    if(Integer.parseInt(low)<=Integer.parseInt(header) && Integer.parseInt(header) <= Integer.parseInt(high))
		return true;
	    else
		return false;
	    
	}
	
	public static boolean isMatchDP(String low,String high,String header){
	    
	    //System.out.println(low + " : " + high +" "+ header);
	    if(Integer.parseInt(low)<=Integer.parseInt(header) && Integer.parseInt(header) <= Integer.parseInt(high))
		return true;
	    else
		return false;

	}
	
	public static boolean isMatchPROT(String rule,String header){//プロトコルが合致しているかの比較
	    String[] pm = rule.split("/");

	    //System.out.println(rule +" "+ header);
	    
	    if(pm[1].equals("0x00"))
		return true;

	    if(pm[1].equals("0xFF") && Integer.decode(pm[0]) == Integer.parseInt(header))
		return true;

	    return false;
	}
	
	public static boolean isMatchFLAG(String rule,String header){//フラグが合致しているかの比較
	    String[] fm = rule.split("/");

	    // System.out.println(rule +" "+ header);

	    if(fm[1].equals("0x0000"))
		return true;
	    else {
		String FLAG = (tenTotwo(Long.decode(fm[0]),16));
		String MASK = (tenTotwo(Long.decode(fm[1]),16));
		String HEADER = (tenTotwo(Long.parseLong(header),32));//.subString(0,16);
		for(int i = 0; i < 16; i++){
		    if(MASK.charAt(i)=='1' && FLAG.charAt(i) != HEADER.charAt(i))
			return false;
		}
		return true;
	    }
	    //   return false;    
	}

     
    public static String tenTotwo(long num, int numwidth){//10進表記を2進表記に変換する
	long[] two;
	int i;
	String returnBits = "";
	two = new long[65535];

	for(i=0;i<(numwidth-1);i++) {
	    two[i] = num%2;
	    num = num >> 1;
	}
	two[i] = num;
	while(i>=0){
	    if(two[i]==1)
		returnBits += '1';
	    else
		returnBits += '0';
	    i--;
	}
	return returnBits;
    }







    


}

