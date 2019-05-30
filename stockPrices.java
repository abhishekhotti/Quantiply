package test;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class stockPrices {

	public static void main(String[] args) 
	{
		stockPrices x = new stockPrices();
		x.soln("22-April-2000", "11-January-2001");
	}
	public void soln(String firstDate, String lastDate) 
	{
		DateFormat formatter;
		Date fDate = null;
		Date eDate = null;
		formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			fDate = formatter.parse(firstDate);
			eDate = formatter.parse(lastDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DateFormat dayFormat=new SimpleDateFormat("d"); 
		DateFormat monthFormat=new SimpleDateFormat("MMMM"); 
		DateFormat yearFormat=new SimpleDateFormat("yyyy"); 
		while(fDate.getTime() <= eDate.getTime())
		{
			String startDat = dayFormat.format(fDate);
			String startMonth = monthFormat.format(fDate);
			String startYear = yearFormat.format(fDate);
			String val = startDat +"-"+startMonth+"-"+startYear;
			String url = "https://jsonmock.hackerrank.com/api/stocks/?date="+val;
			try 
			{
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				int responseCode = con.getResponseCode();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine=in.readLine();
				in.close();   
				JSONObject myresponse = new JSONObject(inputLine);
				if(myresponse.getInt("total") == 0) 
				{
					fDate = addDay(fDate);
				  	continue;
				}
				else
				{
					JSONArray data = myresponse.getJSONArray("data");
					String cd = data.getJSONObject(0).get("date")+"";
					String open = data.getJSONObject(0).get("open")+"";
					String close = data.getJSONObject(0).get("close")+"";

					System.out.println(cd+" "+open+" "+close);
				}
			} 
			catch(Exception e) 
			{
				System.out.println(e);
			}
			fDate = addDay(fDate);
		}
	}
		
		public Date addDay(Date d)
		{
			Calendar cal = Calendar.getInstance();
	        cal.setTime(d);
	        cal.add(Calendar.DATE, 1); //minus number would decrement the days
	        d = cal.getTime();
			return d;
		}
}
