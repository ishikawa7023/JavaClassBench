import java.io.*;


public class main {
	public static void main(String[] args) {
		String fileName = "ret_file1.txt";
		if(args.length>0){
			fileName = args[0];
		}
		String lines = "@95.105.142.0/23 195.170.0.0/16 0 : 65535 0 : 65535 0x06/0xFF";

		Convert10to2 con = new Convert10to2();

		FileReader fr;
		try {

			fr = new FileReader(fileName);

			File file = new File("ret_file2.txt");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			BufferedReader br = new BufferedReader(fr);
			String line;

		    ConvertInterface convert = new ConvertBinary();
			convert = new ConvertIptables();
			while((line=br.readLine()) != null){
				String fooLine = convert.convert(line);
				pw.println(fooLine);
			}
			br.close();
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
