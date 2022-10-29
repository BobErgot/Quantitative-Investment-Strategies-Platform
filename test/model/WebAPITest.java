package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;

import static org.junit.Assert.*;

public class WebAPITest {
  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void getShareValueByGivenDate() throws IOException {
    WebAPI webAPI = new WebAPI();
    webAPI.getShareValueByGivenDate("IBM", new Date());
//    URL url = new URL("");
//    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//    con.setRequestMethod("GET");
//    con.setRequestProperty("function", "TIME_SERIES_DAILY");
//    con.setRequestProperty("symbol", "IBM");
//    con.setRequestProperty("apikey", "T5770I9RBI1FTSXJ");
//    con.setRequestProperty("datatype", "csv");
//    con.setRequestProperty("outputsize", "full");
//    int responseCode = con.getResponseCode();
//    System.out.println("GET Response Code :: " + responseCode);
//    if (responseCode == HttpURLConnection.HTTP_OK) { // success
//      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//      String inputLine;
//      StringBuffer response = new StringBuffer();
//
//      while ((inputLine = in.readLine()) != null) {
//        response.append(inputLine);
//      }
//      in.close();
//
//      System.out.println(response.toString());
//    } else {
//      System.out.println("Failure: GET request did not work.");
//    }
  }
}