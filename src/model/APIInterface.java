package model;

import java.time.LocalDate;

public interface APIInterface {

  double getShareValueByGivenDate(String stockSymbol, LocalDate date);

}
