package main.java.riakj.csv;


import java.util.HashMap;




public class SampleCsvBean {

	private String title = "";
	private String subtitle ="";
	private String datestr ="";
	private HashMap<String,String> colMap= new HashMap<String,String>();
	
	
	public SampleCsvBean(){}
	
	public SampleCsvBean(String title, String subtitle, String datestr,
			HashMap<String,String> cols) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.datestr = datestr;
		this.colMap = cols;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getSubtitle() {
		return subtitle;
	}



	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}



	public String getDatestr() {
		return datestr;
	}



	public void setDatestr(String datestr) {
		this.datestr = datestr;
	}



	public HashMap<String,String> getColMap() {
		return colMap;
	}



	public void setColMap(HashMap<String,String> colMap) {
		this.colMap = colMap;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	


}
