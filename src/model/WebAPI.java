package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utility.Constants.LINE_BREAKER;
import static utility.Constants.STOCK_API_KEY;
import static utility.Constants.STOCK_ENDPOINT;

/**
 * This class represents web API and its operations.
 */
class WebAPI implements APIInterface {
  protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());

  @Override
  public String getData(String stockSymbol, LocalDate date) {
    String url = STOCK_ENDPOINT;
    String charset = "UTF-8";

    String query;
    try {
      query = String.format("function=%s", URLEncoder.encode("TIME_SERIES_DAILY",
              charset));
      query = query + "&" + String.format("symbol=%s", URLEncoder.encode(stockSymbol, charset));
      query = query + "&" + String.format("apikey=%s", URLEncoder.encode(STOCK_API_KEY, charset));
      query = query + "&" + String.format("datatype=%s", URLEncoder.encode("csv", charset));
      query = query + "&" + String.format("outputsize=%s", URLEncoder.encode("full", charset));
    } catch (UnsupportedEncodingException exception) {
      LOGGER.log(Level.SEVERE, "Error occurred while querying: ", exception);
      return null;
    }
    URLConnection connection;
    try {
      LOGGER.log(Level.FINE, url + "?" + query);
      connection = new URL(url + "?" + query).openConnection();
      connection.setRequestProperty("Accept-Charset", charset);
    } catch (IOException ioException) {
      LOGGER.log(Level.SEVERE, "Error occurred while querying: ", ioException);
      return null;
    }
    int responseCode;
    String responseMessage;
    if (connection instanceof HttpURLConnection) {
      try {
        HttpURLConnection httpConnection = (HttpURLConnection) connection;
        responseCode = httpConnection.getResponseCode();
        responseMessage = httpConnection.getResponseMessage();
        LOGGER.log(Level.FINE, "API response: " + responseMessage);
      } catch (IOException ioException) {
        LOGGER.log(Level.SEVERE, "Error occurred during connection: ", ioException);
        return null;
      }
    } else {
      LOGGER.log(Level.SEVERE, "Error occurred during connection");
      return null;
    }
    if (responseCode == HttpURLConnection.HTTP_OK) {
      HttpURLConnection httpConnection = (HttpURLConnection) connection;
      BufferedReader in;
      try {
        in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, "Error occurred during getting result stream");
        return null;
      }
      String inputLine;
      StringBuilder response = new StringBuilder();

      while (true) {
        try {
          if ((inputLine = in.readLine()) == null) {
            break;
          }
        } catch (IOException e) {
          LOGGER.log(Level.SEVERE, "Error occurred during getting result stream");
          return null;
        }
        response.append(inputLine).append(LINE_BREAKER);
      }
      try {
        in.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return response.toString();
    } else {
      LOGGER.log(Level.SEVERE, "Failure: GET request did not work");
      return null;
    }
  }
}