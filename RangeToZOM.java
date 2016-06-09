import java.io.*;
import java.util.*;


public class RangeToZOM {
    public static void main(String[] args) {
	//String fileName = "ret_file1.txt";
	//	if(args.length>0){
	//	fileName = args[0];
	//	}
	//String lines = "@95.105.142.0/23 195.170.0.0/16 0 : 65535 0 : 65535 0x06/0xFF";

	//Convert10to2 con = new Convert10to2();
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	List<String> list = new ArrayList<String>();
	//FileReader fr;


	try {
	    String low = reader.readLine();
	    String high = reader.readLine();
	    int numl = Integer.parseInt(low);
	    int origin=numl,pos;
	    int numh = Integer.parseInt(high);
	    String zoml = new String();
	    String zomh = new String();
	    int i=0,j=0;

	    if(numl==0&&numh==65535)
		list.add("****************");
	    else if((numl & (numl-1))==0&&(numh&(numh+1))==0){
		    
		zomh=tenToTwo(numh,16);
		while(numl!=(numh+1)){
		    zoml=tenToTwo(numl,16);
		    list.add(zoml);
		    i++;
		    numl=numl << 1;	
		}
		i--;
		while(i>1){
		    StringBuilder x = new StringBuilder(list.get(i));
		    pos=x.indexOf("1");
		    pos++;
		    while(pos<16){    	
			x.setCharAt(pos,'*');
			pos++;		    
		    }
		    list.set(i,x.toString());
		    i--;
		}
	    }
	    
	    else if(((numl+1)&numl)==0){
		zoml=tenToTwo(numl,16);
		
			
	    }
	    else if(((numh+1)&numh)==0){



	    }

	    else{


	    }
	}catch (IOException e) {
	    System.out.println("エラー");
	}
    }

    for(int i=0;)
    println("");

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
    
}


void rangeTozom(int low,int high){

    int MAX_RANGE=65535;

    
    if(low==high)
      list.add(low);


    else if((low&low(low-1))==0 && (high&(high+1))==0){//low=00010 : high=01111
	if(low==0){
	    num=log(high-low+1)/log(2);
	    list.add(twoToZom(tenTotwo(32,high),num));
	}
	else if(low < 2^(int)(log(high)/log(2))){//一つの0，1，*のルールで表現できない時
	    
	    rangeTozom(low,2^(int)(log(high)/log(2))-1);//一つの0，1，*のルールで表現できる形に分ける
	    rangeTozom(2^((int)(log(high)/log(2))),high);//一つの0，1，*のルールで表現できる形に分ける
	    
	}
	else{//一つの0，1，*のルールで表現できる時
	    num=log(high-low+1)/log(2);
	    list.add(twoToZom(tenTotwo(32,high),num));	    
	}
    }

    else if((low|(low-1))==MAX_RANGE && (high|(high+1))==MAX_RANGE){//low=10000 : high=10111
	if(high==MAX_RANGE){
	    num=log(high+1-low)/log(2);
	    list.add(twoToZom(tenTotwo(32,high),num));
	}
	else if(low < 2^(int)(log(high)/log(2))){
	    
	    rangeTozom(low,2^(int)(log(high)/log(2))-1);
	    rangeTozom(2^((int)(log(high)/log(2))),high);
	    
	}
	else{
	    num=log(high-low+1)/log(2);
	    list.add(twoToZom(tenTotwo(32,high),num));	    
	}
    }

    
    else if(low < 2^(int)(log(high)/log(2))){
	
    rangeTozom(low,2^(int)(log(high)/log(2))-1);
    rangeTozom(2^((int)(log(high)/log(2))),high);

    }
    else{//numは後ろに付け加える＊の数
	int num,ban=0,bin=0;
	num=log(high-low+1)/log(2);

	while(num>=0){
	    list.add(twoToZom(tenTotwo(32,high),num));
	    high=high-2^num;
	    num--;
	}
    }




}
