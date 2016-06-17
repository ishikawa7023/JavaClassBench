import java.io.*;
import java.util.*;


public class RangeToZOM {
    public static void main(String[] args){

	List<String> origin_list = new ArrayList<String>();

	    int low =  Integer.parseInt(args[0]);
	    int high =  Integer.parseInt(args[1]);
	    int BITS =  Integer.parseInt(args[2]);
	    int bl = 0;
	    int bh=(int)(Math.pow(2,BITS))-1;
	   
	    origin_list=rangeTozom(BITS,bl,bh,low,high);

	    for(int i=0;i<origin_list.size();++i){
		System.out.println(origin_list.get(i));
	    }
    }

    public static String twoTozom(String bit,int mask,int BITS){

	StringBuilder sb = new StringBuilder(bit);
	int num=BITS-mask;
	
	while(num<BITS){
	    //   System.out.println(sb);
	    sb.setCharAt(num,'*');
	    num++;
	}
	return sb.toString();
    }
    
    public static String tenTotwo(int num, int numwidth){
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

    public static List<String> rangeTozom(int BITS,int bl,int bh,int low,int high){


    List<String> list = new ArrayList<String>();
    
    if(bh==high && bl==low){

	int num=(int)(Math.log(bh-bl+1)/Math.log(2));//numは後ろに付け加えられる*の数
	list.add(twoTozom(tenTotwo(high,BITS),num,BITS));	    
	return list;
	
    }

    else if(high <= (bl+bh-1)/2)
	{
	    list.addAll(rangeTozom(BITS,bl,(bl+bh-1)/2,low,high));
	    return list;
	}
    
    else if((bl+bh+1)/2<=low){
	list.addAll(rangeTozom(BITS,(bl+bh+1)/2,bh,low,high));
	return list;
    }

    else /*if(low<=(bl+bh-1)/2 && (bl+bh+1)/2<=high)*/{

	list.addAll(rangeTozom(BITS,bl,(bl+bh-1)/2,low,(bl+bh-1)/2));
	list.addAll(rangeTozom(BITS,(bl+bh+1)/2,bh,(bl+bh+1)/2,high));
	return list;
    }

    }
}
