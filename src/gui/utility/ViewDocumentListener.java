package gui.utility;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * View document listener that checks the change in the text field.
 */
@FunctionalInterface
public interface ViewDocumentListener extends DocumentListener {

  void update(DocumentEvent e);

  @Override
  default void insertUpdate(DocumentEvent e) {
    update(e);
  }

  @Override
  default void removeUpdate(DocumentEvent e) {
    update(e);
  }

  @Override
  default void changedUpdate(DocumentEvent e) {
    update(e);
  }
}