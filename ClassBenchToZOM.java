import java.io.*;
import java.util.*;


public class ClassBenchToZOM {
    public static void main(String[] args){
	
	if (args.length != 2) {
	    System.out.println("Arguments Error!\nUsage: $ java ClassBenchToZOM <rulelist> <outputfile>");
	    //printf("Arguments Error!\nUsage: $ ./a.out <rulelist> <packetlist>.\n");
	    System.exit(1);
	}
	try{
	    List<String> origin_list = new ArrayList<String>();
	    File input = new File(args[0]);
	    BufferedReader br = new BufferedReader(new FileReader(input));
	    File output = new File(args[1]);
	    BufferedWriter bw = new BufferedWriter(new FileWriter(output));
	    String str;
	    while((str = br.readLine()) != null){
		List<String> slist = new ArrayList<String>();
		List<String> dlist = new ArrayList<String>();
		String[] result = str.split("\\s+|\\t");
		StringBuilder sb = new StringBuilder(result[0]);
		sb.deleteCharAt(0);
		result[0]=sb.toString();
		String SA = CIDRToZOM(result[0]);
		String DA = CIDRToZOM(result[1]);
		int[] sport = {Integer.parseInt(result[2]),Integer.parseInt(result[4])};
		int[] dport = {Integer.parseInt(result[5]),Integer.parseInt(result[7])};
		slist = RangeToZOM.rangeTozom(16,0,65535,sport[0],sport[1]);
		dlist = RangeToZOM.rangeTozom(16,0,65535,dport[0],dport[1]);
		String promask = prmsTozom(result[8]);
		String flagmask = fgmsTozom(result[9]);
		
		for(String sp : slist){
		    for(String dp : dlist){
			origin_list.add(SA +" "+ DA +" "+ sp +" "+ dp +" "+ promask +" "+ flagmask);
		    }
		}		
		
	    }
	    for(String ZOM : origin_list){//0,1,*のリストの表示
		//System.out.println(ZOM);
		bw.write(ZOM);
		bw.newLine();
	    }
	    br.close();
	    bw.close();
	}catch(FileNotFoundException e){
	    System.out.println(e);
	}catch(IOException e){
	    System.out.println(e);
	}
    }

    public static String prmsTozom(String promask){
	
	String[] pm = promask.split("/");
	return (tenTotwo(Integer.decode(pm[0]),8) + tenTotwo(Integer.decode(pm[1]),8));

    }
    public static String fgmsTozom(String flagmask){

	String[] fm = flagmask.split("/");
	return (tenTotwo(Integer.decode(fm[0]),16) + tenTotwo(Integer.decode(fm[1]),16));

    }


    public static String tenTotwo(int num, int numwidth){//10進表記を2進表記に変換する
	int[] two;
	int i;
	String returnBits = "";
	two = new int[65535];

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

public static String CIDRToZOM(String CIDR)
{
    String[] ZO = CIDR.split("\\.|/") ;
  int i; 

  for(i=0;i<4;i++){
      ZO[i]=tenTotwo(Integer.parseInt(ZO[i]),8);   
  }
  StringBuilder ZOM = new StringBuilder(ZO[0] + ZO[1] + ZO[2] + ZO[3]);
  int plefix=Integer.parseInt(ZO[4]);

  while(plefix < 32){
      ZOM.setCharAt(plefix,'*');
      plefix++;
  }
  return ZOM.toString();
}
}
