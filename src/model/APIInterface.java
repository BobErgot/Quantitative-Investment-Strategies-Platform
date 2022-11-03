package model;

import java.time.LocalDate;
import java.util.List;

public interface APIInterface {
  String getData(String stockSymbol, LocalDate date);
}