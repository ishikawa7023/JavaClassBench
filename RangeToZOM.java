import java.io.*;
import java.util.*;


public class RangeToZOM {

    int MAX_RANGE=65535;//任意のビット長の最大値
    int BITS=Math.log(MAX_RANGE+1)/Math.log(2.0);
    
    
    public static void main(String[] args) {
	//String fileName = "ret_file1.txt";
	//	if(args.length>0){
	//	fileName = args[0];
	//	}
	//String lines = "@95.105.142.0/23 195.170.0.0/16 0 : 65535 0 : 65535 0x06/0xFF";

	//Convert10to2 con = new Convert10to2();
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	List<StringBuilder> origin_list = new ArrayList<StringBuilder>();

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
	    
    public static String tewTozom(String bit,int mask){

	StringBuilder sb = new StringBuilder(bit);
	int num=BITS-mask;
	
	while(num<BITS){
	    sb.setCharAt(num,'*');
	    num++;
	}
	return sb.toString();
    }
    
    public static String tenToTwo(int num, int numwidth){
	int[] two;
	int i;
	String returnBits = "";
	two = new int[255];

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
    
public static List<StringBuilder> rangeTozom(int low,int high){

    List<StringBuilder> list = new ArrayList<StringBuilder>();

    
    if(low==high){
	list.add(low);
	return list;
    }
    // else if((low&(low-1))==0 && (high&(high+1))==0){//low=00010 : high=01111
    else if(low==0 && (high&(high+1))==0){//lowが0でhighが２の累乗である時
	num=(Math.log(high+1))/(Math.log(2.0));//numは後ろに付け加えられる*の数
	list.add(twoToZom(tenTotwo(BITS,high),num));
	return list;
    }
    else if(high <= MAX_RANGE/2){

	if(low < 2^(Math.floor((Math.log(high)/Math.log(2.0))))){//一つの0，1，*のルールで表現できない時
	    
	    list=rangeTozom(low,2^(Math.floor(Math.log(high)/Math.log(2.0)))-1);//一つの0，1，*のルールで表現できる形に分ける
	    int size=list.size();
	    list.set(size,rangeTozom(2^(Math.floor((Math.log(high)/Math.log(2.0)))),high));//一つの0，1，*のルールで表現できる形に分ける
	    
	    return list; 
	}
	else if(low == 2^(int)(Math.log(high)/Math.log(2.0))){//一つの0，1，*のルールで表現できる時
	    num=(Math.log(high+1))/(Math.log(2.0));//numは後ろに付け加えられる*の数
	   list.add(twoToZom(tenTotwo(BITS,high),num));	    
	   return list;
	}
	else{
	    
	    int Ehigh=high-2^(Math.floor((Math.log(high)/Math.log(2.0))));
	    int Elow=low-2^(Math.floor((Math.log(high)/Math.log(2.0))));;
	    int pro=2^(Math.floor((Math.log(high)/Math.log(2.0))));
	    StringBuilder sb = new StringBuilder();

	    list=rangeTozom(Elow,Ehigh);
	    for(int i=0;i<list.size();++i){
		sb=list.get(i);
		sb.setCharAt(BITS-(pro+1),'1');
		list.set(i,sb);
	    }
	    return list;
	}   
    }

    else if(low > MAX_RANGE/2){
	//   else if((low|(low-1))==MAX_RANGE && (high|(high+1))==MAX_RANGE){//low=10000 : high=10111
	if((low|(low-1))==MAX_RANGE && high==MAX_RANGE){//highがMAX_RANGEでlowがMAX_RANGE-(2の累乗)である時
	    num=Math.log(high+1-low)/Math.log(2.0);//numは後ろに付け加えられる*の数
	    list.add(twoToZom(tenTotwo(BITS,high),num));
	    return list;
	}
	else if(high > (2^(Math.floor(Math.log(low^MAX_RANGE)/Math.log(2.0))))^MAX_RANGE){//一つの0，1，*のルールで表現できない時
	    
	    list=rangeTozom(low,(2^(Math.floor(Math.log(low^MAX_RANGE)/Math.log(2.0))))^MAX_RANGE);
	    int size =list.size();
	    list.set(size,rangeTozom((2^(Math.floor(Math.log(low^MAX_RAMGE)/Math.log(2.0))))^MAX_RANGE)+1,high);

	    return list;
	    
	}
	else if(high == (2^(Math.floor(Math.log(low^MAX_RANGE)/Math.log(2.0))))^MAX_RANGE){//一つの0，1，*のルールで表現できる時
	    num=Math.log(high-low+1)/Math.log(2.0);//numは後ろに付け加えられる*の数
	    list.add(twoToZom(tenTotwo(BITS,high),num));
	    return list;
	}
	else{

	    int Ehigh=high-2^(Math.floor((Math.log(high)/Math.log(2.0))));
	     int Elow=low-2^(Math.floor((Math.log(high)/Math.log(2.0))));
	    int pro=2^(Math.floor((Math.log(high)/Math.log(2.0))));
	    StringBuilder sb = new StringBuilder();

	    list=rangeTozom(Elow,Ehigh);
	    for(int i=0;i<list.size();++i){
		sb=list.get(i);
		sb.setCharAt(BITS-(pro+1),'1');
		list.set(i,sb);
	    }
	    return list;
	}   


    }

    else{
	rangeTozom(low,MAX_RANGE/2);//一つの0，1，*のルールで表現できる形に分ける
	rangeTozom((MAX_RANGE/2)+1,high);//一つの0，1，*のルールで表現できる形に分ける
    }
    
}
    


}
