package application.center.vboxes;

import application.WindowBorderPane;
import application.sides.SideVBox;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing CenterVBox with options for printing.
 */
public class PrintChoosingVBox extends CenterVBox {
    /**
     * list of ToggleButtons
     */
    private final List<MFXToggleButton> mfxCheckBoxList;
    /**
     * associated continue button
     */
    private MFXButton associatedButton;

    /**
     * Constructor
     *
     * @param parent WindowBorderPane
     */
    public PrintChoosingVBox(WindowBorderPane parent, SideVBox sideVBox) {
        super(parent);
        setSpacing(20);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER_LEFT);
        setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(0,0,0,85));
        mfxCheckBoxList = new ArrayList<>();
        MFXToggleButton sixCDCCheckBox = new MFXToggleButton("if a semi-induced 6-CDC exists");
        MFXToggleButton inducedCDCCheckBox = new MFXToggleButton("if an induced 7-CDC exists");
        MFXToggleButton numberOfStrongEdgesCheckBox = new MFXToggleButton("possible numbers of strong edges\n in circuit double covers");

        mfxCheckBoxList.addAll(List.of(numberOfStrongEdgesCheckBox, sixCDCCheckBox, inducedCDCCheckBox));
        vBox.getChildren().addAll(numberOfStrongEdgesCheckBox, sixCDCCheckBox, inducedCDCCheckBox);
        getChildren().add(vBox);
        for (MFXToggleButton mfxCheckBox : mfxCheckBoxList) {
            mfxCheckBox.addEventHandler(ActionEvent.ACTION, event -> {
                boolean isAnySelected = false;
                if (mfxCheckBox.isSelected()) {
                    isAnySelected = true;
                } else {
                    for (MFXToggleButton othermfxToggleButton : mfxCheckBoxList) {
                        if (!othermfxToggleButton.equals(mfxCheckBox)) {
                            if (othermfxToggleButton.isSelected()) {
                                isAnySelected = true;
                                break;
                            }
                        }
                    }

                }
                updateAssociatedContinueButton(!isAnySelected);
            });
        }
        sideVBox.getButton().addEventHandler(ActionEvent.ACTION, event -> {
            parent.setPrintCountOfStrongEdges(numberOfStrongEdgesCheckBox.isSelected());
            parent.setPrintFound6CDC(sixCDCCheckBox.isSelected());
            parent.setPrintFoundInduced6CDC(inducedCDCCheckBox.isSelected());
        });
    }

    /**
     * This method associate the continue button.
     *
     * @param mfxButton continue button.
     */
    public void setAssociatedContinueButton(MFXButton mfxButton) {
        mfxButton.setDisable(true);
        this.associatedButton = mfxButton;
    }

    /**
     * This method allows to disable the continue button.
     *
     * @param disable whether to disable the contunue button
     */
    public void updateAssociatedContinueButton(boolean disable) {
        this.associatedButton.setDisable(disable);
    }


}
