package gui.utility;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Common constants that views might require to validate and give messages to users.
 */
public final class ViewConstants {

  public static final String INVALID_TICKER = "You've entered an invalid ticker symbol!";
  public static final String INVALID_STOCKS = "You've entered invalid number of stocks!";
  public static final String PORTFOLIO_EXISTS = "Portfolio already exists!";
  public static final String PORTFOLIO_INVALID = "Portfolio name is invalid";
  public static final String PORTFOLIO_CREATED = "Portfolio has been successfully created";
  public static final String INVALID_DATE = "Invalid date input!";

  public static final String SHARE_NUMBER_EXCEEDS =
      "The number of shares that you're attempting to " + "sell exceeds what is available!";
  public static final String STOCK_INVALID = "Stock entered is not present in portfolio!";
  public static final String BOUGHT_FOR = "Stocks bought for:\t";

  public static final String SOLD_FOR = "Stocks sold for:\t";

  public static final Set<String> SUPPORTED_FILE_EXTENSION =
      new HashSet<>(Collections.singletonList("csv"));
  public static final String FILE_UPLOAD_FAIL_EXTENSION = "File upload failed as file extension " +
      "is not supported. ";
  public static final String SUPPORTED_FILES = "Supported file extensions: ";
  public static final String UPLOAD_ANOTHER_FILE = "Try to upload another file!";
  public static final String NO_FILES_SELECTED = "No Files Selected";
  public static final String FILE_UPLOAD_SUCCESS = "File uploaded successfully!";
  public static final String FILE_UPLOAD_FAIL_FORMAT = "Data in file is not in correct format. ";
  public static final String FILE_UPLOAD_FAIL_NOT_FOUND = "Unable to locate the file at specified" +
      " path. ";
  public static final String FILE_UPLOAD_ANOTHER = "Browse and upload another file";
  public static final String INPUT_FIELD_EMPTY = "Field cannot be empty!";
  public static final String DATE_IS_IN_PAST = "Invalid date as it is in past!";
}
