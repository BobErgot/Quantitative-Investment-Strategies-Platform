import java.util.function.Predicate;

public interface ControllerInterface {
  void createPortfolio();
  void viewPortfolio();
  // Filter function
  <T> void conditionalViewPortfolio(Predicate<T> filter);
}
