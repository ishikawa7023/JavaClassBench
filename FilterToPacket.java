import java.io.*;


public class FilterToPacket {
    public static void main(String[] args){
  
if (args.length != 2) {
	    System.out.println("Arguments Error!\nUsage: $ java FilterToPacket <rulelist> <outputfile>");
	    //printf("Arguments Error!\nUsage: $ ./a.out <rulelist> <packetlist>.\n");
	    System.exit(1);
	}
try{
   File input = new File(args[0]);
   BufferedReader br = new BufferedReader(new FileReader(input));
   File output = new File(args[1]);
   BufferedWriter bw = new BufferedWriter(new FileWriter(output));
   String rule;
   while((rule = br.readLine()) != null){
       bw.write(createpacket(rule));
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

public static String createpacket(String rule){

    StringBuilder ZOM = new StringBuilder(rule);
    int len=ZOM.length();
    for(int i=0;i<len;i++){
	int ran=(int)(Math.random()*100)+1; 
	if(ZOM.charAt(i)=='*'){
	    if(ran > 50)
		ZOM.setCharAt(i,'1');
	    else
		ZOM.setCharAt(i,'0');  
	}
    }
    return ZOM.toString();
}
}


