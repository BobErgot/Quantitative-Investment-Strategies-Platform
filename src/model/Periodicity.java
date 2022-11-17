package model;

/**
 *  Possible filters for performance graph.
 *  @see #DAY
 *  @see #MONTH
 *  @see #YEAR
 */
public enum Periodicity {
  /**
   * Day divides the y-axis of graph into day points.
   */
  DAY,

  /**
   * Month divides the y-axis of graph into month points.
   */
  MONTH,

  /**
   * Year divides the y-axis of graph into year points.
   */
  YEAR
}