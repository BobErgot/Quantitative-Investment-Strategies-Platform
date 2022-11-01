package utility;

import java.nio.file.FileSystems;
import java.time.LocalDate;

public final class Constants {

  public static final String HOME = System.getProperty("user.home");
  public final static String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
  public static final String STOCK_DIRECTORY = "stock";
  public static final String PORTFOLIO_DIRECTORY = "portfolio";
  public static final String STOCK_ENDPOINT = "https://www.alphavantage.co/query";
  public static final String STOCK_API_KEY = "T5770I9RBI1FTSXJ";

  public static final String DATE_FORMAT = "YYYY-MM-DD";

  public static final String PORTFOLIO_NOT_FOUND = "Portfolio was not found!";
  private Constants() {
    // Restrict constructor access
  }

}