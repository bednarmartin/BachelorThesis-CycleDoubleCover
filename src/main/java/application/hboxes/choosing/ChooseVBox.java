package application.hboxes.choosing;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Class for VBox used to choose between options
 */
public class ChooseVBox extends VBox {
    /**
     * List of Checkboxes
     */
    private final List<RadioButton> radioButtonList;
    /**
     * Associated continue button
     */
    private MFXButton associatedContinueButton;

    /**
     * Whether there is an associated button
     */
    private boolean isAssociatedButton;


    /**
     * Constructor
     */
    public ChooseVBox(RadioButton... radioButtons) {
        radioButtonList = List.of(radioButtons);
        setSpacing(30);
        setAlignment(Pos.BASELINE_CENTER);
        for (RadioButton radioButton : radioButtonList) {
            getChildren().add(radioButton);
        }
        isAssociatedButton = false;
        makeSureOnlyOneChosen();
    }

    /**
     * This method allows setting associated continue button.
     *
     * @param button continue button to be associated
     */
    public void setAssociatedContinueButton(MFXButton button) {
        isAssociatedButton = true;
        associatedContinueButton = button;
        associatedContinueButton.setDisable(true);
    }

    /**
     * This method allows updating status of associated continue button.
     *
     * @param disable true if button should be disabled.
     */
    private void updateAssociatedContinueButton(boolean disable) {
        associatedContinueButton.setDisable(disable);
    }

    /**
     * This method makes sure that only one option can be chosen.
     */
    protected void makeSureOnlyOneChosen() {
        for (RadioButton radioButton : radioButtonList) {
            radioButton.addEventHandler(ActionEvent.ACTION, event -> {
                for (RadioButton otherRadioButton : radioButtonList) {
                    if (radioButton.isSelected()) {
                        if (!otherRadioButton.equals(radioButton)) {
                            otherRadioButton.setDisable(true);
                            otherRadioButton.setSelected(false);
                            if (isAssociatedButton) {
                                updateAssociatedContinueButton(false);
                            }

                        }
                    } else {
                        if (!otherRadioButton.equals(radioButton)) {
                            otherRadioButton.setDisable(false);
                            otherRadioButton.setSelected(false);
                            if (isAssociatedButton) {
                                updateAssociatedContinueButton(true);
                            }
                        }
                    }
                }
            });
        }
    }
}

