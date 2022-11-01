package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utility.Constants.STOCK_API_KEY;
import static utility.Constants.STOCK_DIRECTORY;
import static utility.Constants.STOCK_ENDPOINT;

public class WebAPI implements APIInterface {
  protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());
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
    } catch (UnsupportedEncodingException exception) {
      LOGGER.log(Level.SEVERE, "Error occurred while querying: ", exception);
      return -1;
    }
    java.net.URLConnection connection = null;
    try {
      LOGGER.info(url + "?" + query);
      connection = new URL(url + "?" + query).openConnection();
      connection.setRequestProperty("Accept-Charset", charset);
    } catch (IOException ioException) {
      LOGGER.log(Level.SEVERE, "Error occurred while querying: ", ioException);
      return -1;
    }
    int responseCode = 0;
    String responseMessage;
    if (connection instanceof HttpURLConnection) {
      try {
        HttpURLConnection httpConnection = (HttpURLConnection) connection;
        responseCode = httpConnection.getResponseCode();
        responseMessage = httpConnection.getResponseMessage();
        LOGGER.info("API response: " + responseMessage);
      } catch (IOException ioException) {
        LOGGER.log(Level.SEVERE, "Error occurred during connection: ", ioException);
        return -1;
      }
    } else {
      LOGGER.log(Level.SEVERE, "Error occurred during connection");
      return -1;
    }
    if (responseCode == HttpURLConnection.HTTP_OK) { // success
      HttpURLConnection httpConnection = (HttpURLConnection) connection;
      BufferedReader in = null;
      try {
        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, "Error occurred during getting result stream");
        return -1;
      }
      String inputLine;
      StringBuilder response = new StringBuilder();

      while (true) {
        try {
          if ((inputLine = in.readLine()) == null) break;
        } catch (IOException e) {
          LOGGER.log(Level.SEVERE, "Error occurred during getting result stream");
          return -1;
        }
        if (inputLine.length() >= 10 && inputLine.substring(0, 10).equals(date.toString())) {
          String [] inputLineData = inputLine.split(",");
          double high = Double.parseDouble(inputLineData[2]);
          double low = Double.parseDouble(inputLineData[3]);
          return low;
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
      LOGGER.log(Level.SEVERE, "Failure: GET request did not work");
      return -1;
    }
    return -1;
  }
}
