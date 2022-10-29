package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class WebAPI implements APIInterface{
  private static String STOCK_ENDPOINT ="https://www.alphavantage.co/query";

  @Override
  public double getShareValueByGivenDate(String stockSymbol, Date date) {
    String url = STOCK_ENDPOINT;
    String charset = "UTF-8";

    String query = null;
    try {
      query = String.format("function=%s",
              URLEncoder.encode("TIME_SERIES_DAILY", charset));
      query = query + "&" + String.format("symbol=%s",
              URLEncoder.encode(stockSymbol, charset));
      query = query + "&" + String.format("apikey=%s",
              URLEncoder.encode("T5770I9RBI1FTSXJ", charset));
      query = query + "&" + String.format("datatype=%s",
              URLEncoder.encode("csv", charset));
      query = query + "&" + String.format("outputsize=%s",
              URLEncoder.encode("full", charset));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    java.net.URLConnection connection = null;
    try {
      System.out.println(url + "?" + query);
      connection = new URL(url + "?" + query).openConnection();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    connection.setRequestProperty("Accept-Charset", charset);
    int responseCode = 0;
    String responseMessage;
    if ( connection instanceof HttpURLConnection)
    {
      HttpURLConnection httpConnection = (HttpURLConnection) connection;
      try {
        responseCode = httpConnection.getResponseCode();
        System.out.println(responseCode);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      try {
        responseMessage = httpConnection.getResponseMessage();
        System.out.println(responseMessage);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    else
    {
      System.err.println ("error!");
    }
    if (responseCode == HttpURLConnection.HTTP_OK) { // success
      HttpURLConnection httpConnection = (HttpURLConnection) connection;
      BufferedReader in = null;
      try {
        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      String inputLine;
      StringBuffer response = new StringBuffer();

      while (true) {
        try {
          if (!((inputLine = in.readLine()) != null)) break;
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        System.out.println(date.toString());
        if(inputLine.substring(0,10).equals(date.toString())){
          System.out.println(inputLine);
        }
        response.append(inputLine);
      }
      try {
        in.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      FileDatabase fileDatabase = new FileDatabase();
      fileDatabase.writeToFile(stockSymbol, "stocks", response.toString());
    } else {
      System.out.println("Failure: GET request did not work.");
    }
    return 0;
  }
}
