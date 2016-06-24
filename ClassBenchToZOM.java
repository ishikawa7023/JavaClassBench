import java.io.*;
import java.util.*;


public class ClassBenchToZOM {
    public static void main(String[] args){

	try{
	    List<String> origin_list = new ArrayList<String>();
	    
	    File input = new File(args[0]);
	    BufferedReader br = new BufferedReader(new FileReader(input));
	    //File output = new FIle(args[2]);
	    //BufferedReader o_file = new BufferedReader(new FileReader(output));

	    String str;
	    while((str = br.readLine()) != null){
		List<String> slist = new ArrayList<String>();
		List<String> dlist = new ArrayList<String>();
	     
		String[] result = str.split("\\t");
		StringBuilder sb = new StringBuilder(result[0]);
		sb.deleteCharAt(0);
		result[0]=sb.toString();
		String SA = CIDRToZOM(result[0]);
		String DA = CIDRToZOM(result[1]);
		
		String[] ssport = result[2].split("\\s:\\s");
		int[] sport = {Integer.parseInt(ssport[0]),Integer.parseInt(ssport[1])};
		String[] sdport = result[3].split("\\s:\\s");
		int[] dport = {Integer.parseInt(sdport[0]),Integer.parseInt(sdport[1])};

		slist = RangeToZOM.rangeTozom(sport[0],sport[1],16);
		dlist = RangeToZOM.rangeTozom(dport[0],dport[1],16);
		
		String promask = prmsTozom(result[4]);
		String flagmask = fgmsTozom(result[5]);
		
		for(String sp : slist){
		    for(String dp : dlist){
			origin_list.add(SA + DA + sp + dp + promask + flagmask);
		    }
		}		
		
	    }

	    br.close();

	    for(String ZOM : origin_list){//0,1,*のリストの表示
		System.out.println(ZOM);
	    }
	}catch(FileNotFoundException e){
	    System.out.println(e);
	}catch(IOException e){
	    System.out.println(e);
	}
    }

    public static String prmsTozom(String promask){
	
	String[] pm = promask.split("/");
	return (tenTotwo(Integer.decode(pm[1]),8) + tenTotwo(Integer.decode(pm[3]),8));

    }
    public static String fgmsTozom(String flagmask){

	String[] fm = flagmask.split("/");
	return (tenTotwo(Integer.decode(fm[1]),16) + tenTotwo(Integer.decode(fm[3]),16));

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
