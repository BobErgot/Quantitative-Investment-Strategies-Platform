package utility;

import java.nio.file.FileSystems;

/**
 * Class with all the constants that will be used across the different classes in this codebase
 * and functions as a configuration file.
 */
public final class Constants {
  public static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
  public static final String LINE_BREAKER = System.getProperty("line.separator");
  public static final String HOME = System.getProperty("user.dir");
  public static final String RECORD_FIELD_SEPERATOR = ",";
  public static final String VALUE_SEPERATOR = ":";
  public static final String RELATIVE_PATH = "~";
  public static final String STOCK_DIRECTORY =  "stocker" + FILE_SEPARATOR + "stock";
  public static final String PORTFOLIO_DIRECTORY = "stocker" + FILE_SEPARATOR + "portfolio";
  public static final String PORTFOLIO_FILENAME = "portfolio";

  public static final String STRATEGY_FILENAME = "strategy-";
  public static final String TICKER_DIRECTORY = "stocker" + FILE_SEPARATOR + "ticker";
  public static final String STOCK_ENDPOINT = "https://www.alphavantage.co/query";
  public static final String STOCK_API_KEY = "T5770I9RBI1FTSXJ";
  public static final String DATE_FORMAT = "YYYY-MM-DD";
  public static final String PORTFOLIO_NOT_FOUND = "Portfolio was not found!";
  public static final int STOCK_COLUMNS_COUNT = 6;

  // Purchase = cost_per_share + broker_fees/num_shares
  // Sell = cost_per_share * num_shares - broker_fees
  public static final float BROKER_FEES = 20;

  public static final String MUTABLE = ",mutable";

  /**
   * Constructor is explicitly defined and made private to restrict access to the object of this
   * class.
   */
  private Constants() {
    // Restrict constructor access
  }
}