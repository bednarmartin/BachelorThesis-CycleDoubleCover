package application.states;

import application.WindowBorderPane;

/**
 * Interface for states of window - determine what shows on Window Border Pane.
 */
public interface WindowState {
    /**
     * Updates the window border pane.
     *
     * @param windowBorderPane window to be updated
     */
    void update(WindowBorderPane windowBorderPane);
}
