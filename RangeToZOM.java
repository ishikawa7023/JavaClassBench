import java.io.*;
import java.util.*;


public class RangeToZOM {

    static int MAX_RANGE=65535;//任意のビット長の最大値
    static int BITS=(int)Math.floor(Math.log(MAX_RANGE+1)/Math.log(2));
    
    
    public static void main(String[] args) {
	//String fileName = "ret_file1.txt";
	//	if(args.length>0){
	//	fileName = args[0];
	//	}
	//String lines = "@95.105.142/23 195.170.0.0/16 0 : 65535 0 : 65535 0x06/0xFF";

	//Convert10to2 con = new Convert10to2();
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	List<String> origin_list = new ArrayList<String>();

	//FileReader fr;

	try {
	    String s_low = reader.readLine();
	    int low =  Integer.parseInt(s_low);
	    String s_high = reader.readLine();
	    int high =  Integer.parseInt(s_high);
	   
	    origin_list=rangeTozom(low,high);

	      for(int i=0;i<origin_list.size();++i){
		  System.out.println(origin_list.get(i));
	    }
	    
	}catch (IOException e){
	    System.out.println(e);
	}
    }
	    
    public static String twoTozom(String bit,int mask){

	StringBuilder sb = new StringBuilder(bit);
	int num=BITS-mask;
	
	while(num<BITS){
	    sb.setCharAt(num,'*');
	    num++;
	}
	return sb.toString();
    }
    
    public static String tenTotwo(int num, int numwidth){
	int[] two;
	int i;
	String returnBits = "";
	two = new int[MAX_RANGE];

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
    
public static List<String> rangeTozom(int low,int high){

    List<String> list = new ArrayList<String>();
    int num;
    
    if(low==high){
	list.add(tenTotwo(low,BITS));
	return list;
    }
    // else if((low&(low-1))==0 && (high&(high+1))==0){//low=00010 : high=01111
    else if(low==0 && (high&(high+1))==0){//lowが0でhighが２の累乗-1である時
	num=(int)Math.floor(Math.log(high)/Math.log(2));//numは後ろに付け加えられる*の数
	list.add(twoTozom(tenTotwo(high,BITS),num));
	return list;
    }
    else if(high <= MAX_RANGE/2){

	if(low < Math.pow(2,(int)(Math.floor(Math.log(high)/Math.log(2))))){//一つの0，1，*のルールで表現できない時

	    // List twolist = new ArrayList();
	    
	     list.addAll(rangeTozom(low,(int)Math.pow(2,(int)(Math.floor(Math.log(high)/Math.log(2))))-1));//一つの0，1，*のルールで表現できる形に分ける
	     list.addAll(rangeTozom((int)Math.pow(2,(Math.floor(Math.log(high)/Math.log(2)))),high));//一つの0，1，*のルールで表現できる形に分ける
	    
	    return list; 
	}
	else if(low == Math.pow(2,(int)(Math.log(high)/Math.log(2)))){//一つの0，1，*のルールで表現できる時
	    num=(int)Math.floor(Math.log(high)/Math.log(2));//numは後ろに付け加えられる*の数
	    list.add(twoTozom(tenTotwo(high,BITS),num));	    
	   return list;
	}
	else{
	    
	    int Ehigh=high-(int)Math.pow(2,(int)(Math.floor((Math.log(high)/Math.log(2)))));
	    int Elow=low-(int)Math.pow(2,(int)(Math.floor((Math.log(high)/Math.log(2)))));;
	    int pro=(int)Math.pow(2,(int)(Math.floor((Math.log(high)/Math.log(2)))));
	   
	    list=rangeTozom(Elow,Ehigh);
	    for(int i=0;i<list.size();++i){
		StringBuilder sb = new StringBuilder(list.get(i));
		sb.setCharAt(BITS-(pro+1),'1');
		list.set(i,sb.toString());
	    }
	    return list;
	}   
    }

    else if(low > MAX_RANGE/2){
	//   else if((low|(low-1))==MAX_RANGE && (high|(high+1))==MAX_RANGE){//low=10000 : high=10111
	if((low|(low-1))==MAX_RANGE && high==MAX_RANGE){//highがMAX_RANGEでlowがMAX_RANGE-(2の累乗)である時
	    num=(int)Math.floor(Math.log(high+1-low)/Math.log(2));//numは後ろに付け加えられる*の数
	    list.add(twoTozom(tenTotwo(high,BITS),num));
	    return list;
	}
	else if(high > ((int)Math.pow(2,(Math.floor(Math.log(low^MAX_RANGE)/Math.log(2))))^MAX_RANGE)){//一つの0，1，*のルールで表現できない時

	    //	     List twolist = new ArrayList();
	    
	     list.addAll(rangeTozom(low,(int)Math.pow(2,(Math.floor(Math.log(low^MAX_RANGE)/Math.log(2))))^MAX_RANGE));
	     list.addAll(rangeTozom(((int)Math.pow(2,(Math.floor(Math.log(low^MAX_RANGE)/Math.log(2))))^MAX_RANGE)+1,high));

	    return list;
	    
	}
	else if(high == ((int)(Math.pow(2,(Math.floor(Math.log(low^MAX_RANGE)/Math.log(2)))))^MAX_RANGE)){//一つの0，1，*のルールで表現できる時
	    num=(int)Math.floor(Math.log(high-low+1)/Math.log(2));//numは後ろに付け加えられる*の数
	    list.add(twoTozom(tenTotwo(high,BITS),num));
	    return list;
	}
	else{

	    int Ehigh=high-(int)Math.pow(2,(Math.floor(Math.log(high)/Math.log(2))));
	    int Elow=low-(int)Math.pow(2,(Math.floor(Math.log(high)/Math.log(2))));
	    int pro=(int)Math.pow(2,(Math.floor(Math.log(high)/Math.log(2))));
	   
	    list=rangeTozom(Elow,Ehigh);
	    for(int i=0;i<list.size();++i){
		StringBuilder sb = new StringBuilder(list.get(i));
		sb.setCharAt(BITS-(pro+1),'1');
		list.set(i,sb.toString());
	    }
	    return list;
	}   


    }

    else{
	List twolist = new ArrayList();

	twolist.addAll(rangeTozom(low,(int)MAX_RANGE/2));//一つの0，1，*のルールで表現できる形に分ける
	twolist.addAll(rangeTozom((int)(MAX_RANGE/2)+1,high));//一つの0，1，*のルールで表現できる形に分ける

	return twolist;

    }
    
}
    


}
