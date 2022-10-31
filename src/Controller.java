import java.util.function.Predicate;

public interface Controller {
  void createPortfolio();
  void viewPortfolio();
  // Filter function
  <T> void conditionalViewPortfolio(Predicate<T> filter);
}
