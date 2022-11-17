package model;

import java.time.LocalDate;

/**
 * This interface represents all the API operations to be supported by the concrete implementation.
 */
interface APIInterface {

  /**
   * Make a GET API call to the stock data endpoint set in the Constants class.
   *
   * @param stockSymbol company ticker for which the data needs to be fetched
   * @param date        date for which the data needs to be fetched
   * @return api response if status code is 200 or return null
   */
  String getData(String stockSymbol, LocalDate date);
}