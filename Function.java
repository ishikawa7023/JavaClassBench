import java.io.*;

public class Function {
    public static void main(String[] args){
	//public String tenTotwo(String num,int bit){
	// String[] str = ipv4.split("/");
	//String[] bits = str[0].split("[.]");
	//int subnetMask = Integer.parseInt(str[1]);
	//String returnBits="";
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	try{
	    int i;
	    int[]  two;
	    String bits = reader.readLine();
	    int num = Integer.parseInt(bits);
	    String width = reader.readLine();
	    int numwidth = Integer.parseInt(width);
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
	    //   s_two[j]='\0';

	    System.out.println(returnBits);
	 
	    // switch (numwidth){
	    // case 4:
	    // 	 returnBits = String.format("%04d ",num);
	    // 	break;
	    // case 8:
	    // 	 returnBits = String.format("%08d ",num);
	    // 	break;
	    // case 16:
	    // 	 returnBits = String.format("%016d ",num);
	    // 	break;
	    // case 32:
	    // 	 returnBits = String.format("%032d ",num);
	    // 	break;
	    // default:
	    // 	System.out.println("値が大きすぎます．");
	    // 	break;
	    // }
	 
	}catch (IOException e){
	    System.out.println(e);
	}catch (NumberFormatException e) {
	    System.out.println("エラー");
	    //	return returnBits;
	}
    }
}
