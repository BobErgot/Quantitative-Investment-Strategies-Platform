package utility;

import java.nio.file.FileSystems;

public final class Constants {
  public final static String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();

  public final static String LINE_BREAKER = System.getProperty("line.separator");
  public static final String HOME = System.getProperty("user.dir");
  public static final String RELATIVE_PATH = "~";
  public static final String STOCK_DIRECTORY =  "stocker" + FILE_SEPARATOR + "stock";
  public static final String PORTFOLIO_DIRECTORY = "stocker" + FILE_SEPARATOR + "portfolio";
  public static final String TICKER_DIRECTORY = "stocker" + FILE_SEPARATOR + "ticker";
  public static final String STOCK_ENDPOINT = "https://www.alphavantage.co/query";
  public static final String STOCK_API_KEY = "T5770I9RBI1FTSXJ";
  public static final String DATE_FORMAT = "YYYY-MM-DD";
  public static final String PORTFOLIO_NOT_FOUND = "Portfolio was not found!";

  private Constants() {
    // Restrict constructor access
  }
}