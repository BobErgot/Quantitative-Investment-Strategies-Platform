package controller;

/**
 * Mock model util class to provide additional functionality to mock objects.
 */
class MockModelUtil {

  public static String merge(String... log) {
    StringBuilder sb = new StringBuilder();
    for (String element : log) {
      sb.append(element);
      sb.append(",");
    }
    return sb.substring(0, sb.length() - 1);
  }

  public static boolean compareStringContents(String a, String b) {
    return a.replaceAll("\\s+", "")
            .equalsIgnoreCase(b.replaceAll("\\s+", ""));
  }
}