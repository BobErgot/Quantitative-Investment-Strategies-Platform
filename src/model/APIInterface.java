package model;

import java.time.LocalDate;
import java.util.List;

public interface APIInterface {
  List<String> getData(String stockSymbol, LocalDate date);
}