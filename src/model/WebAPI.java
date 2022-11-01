package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.concurrent.TimeoutException;

import static utility.Constants.STOCK_API_KEY;
import static utility.Constants.STOCK_DIRECTORY;
import static utility.Constants.STOCK_ENDPOINT;

public class WebAPI implements APIInterface {

  @Override
  public double getShareValueByGivenDate(String stockSymbol, LocalDate date) {
    String url = STOCK_ENDPOINT;
    String charset = "UTF-8";

    String query = null;
    try {
      query = String.format("function=%s",
              URLEncoder.encode("TIME_SERIES_DAILY", charset));
      query = query + "&" + String.format("symbol=%s",
              URLEncoder.encode(stockSymbol, charset));
      query = query + "&" + String.format("apikey=%s",
              URLEncoder.encode(STOCK_API_KEY, charset));
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
    if (connection instanceof HttpURLConnection httpConnection) {
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
    } else {
      System.err.println("error!");
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
          if ((inputLine = in.readLine()) == null) break;
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        System.out.println(date.toString());
        if (inputLine.substring(0, 10).equals(date.toString())) {
          System.out.println(inputLine);
        }
        response.append(inputLine).append(System.getProperty("line.separator"));
      }
      try {
        in.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      CSVFile fileDatabase = new CSVFile();
      fileDatabase.writeToFile(STOCK_DIRECTORY, stockSymbol, response.toString().getBytes());
    } else {
      System.out.println("Failure: GET request did not work.");
    }
    return 0;
  }

  public Share getShare (String id) throws TimeoutException {
    return  null;
  }
}
