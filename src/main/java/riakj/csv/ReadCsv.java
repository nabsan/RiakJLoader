package main.java.riakj.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ReadCsv {
	
    public ArrayList<SampleCsvBean> readCSV(File inputfile){
    	
    	ArrayList<SampleCsvBean> resObj= new ArrayList<SampleCsvBean>();
        try {
            //ファイルを読み込む
            FileReader fr = new FileReader(inputfile);
            BufferedReader br = new BufferedReader(fr);

            //読み込んだファイルを１行ずつ処理する
            String line;
            StringTokenizer token;
            
            while ((line = br.readLine()) != null) {
                //区切り文字","で分割する
                token = new StringTokenizer(line, ",");
                String hddt1=token.nextToken();
                String hddt2=token.nextToken();
                String hddt3=token.nextToken();
                //分割した文字を画面出力する
                HashMap nMap = new HashMap();
                while (token.hasMoreTokens()) {
                    //System.out.println(token.nextToken());
                	String col = token.nextToken();
                	String cdata =token.nextToken();
                	nMap.put(col, cdata);
                }
                SampleCsvBean cb =new SampleCsvBean(hddt1,hddt2,hddt3, nMap);
                resObj.add(cb);

                System.out.println("**********");
            }
            
            System.out.println();

            //終了処理
            br.close();

        } catch (IOException ex) {
            //例外発生時処理
            ex.printStackTrace();
        }
        return resObj;
    }
    	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
