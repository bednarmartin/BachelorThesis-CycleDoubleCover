package application.states;

import application.WindowBorderPane;
import application.bottom.BottomHBox;
import application.center.vboxes.ChooseOutputVBox;
import application.sides.SideVBox;
import application.sides.left.LeftSideVBox;
import application.sides.right.RightSideVBox;
import application.top.TopHBox;
import io.github.palexdev.materialfx.controls.enums.ButtonType;

/**
 * Class representing a state of window for choosing the output.
 */
public class ChooseOutputWindowState implements WindowState {
    /**
     * @inheritDoc
     */
    @Override
    public void update(WindowBorderPane windowBorderPane) {
        TopHBox topHbox = new TopHBox();
        topHbox.setText("Specify the output file");
        windowBorderPane.setTop(topHbox);
        SideVBox rightSideVBox = new RightSideVBox(windowBorderPane);
        rightSideVBox.getButton().setText("Test");
        rightSideVBox.getButton().setStyle("-fx-background-color: lightgreen");
        rightSideVBox.getButton().setButtonType(ButtonType.RAISED);
        SideVBox leftSideVBox = new LeftSideVBox(windowBorderPane);
        rightSideVBox.activateButton(new ComputationWindowState());
        ChooseOutputVBox chooseOutputVBox = new ChooseOutputVBox(windowBorderPane, rightSideVBox);
        windowBorderPane.setCenter(chooseOutputVBox);
        leftSideVBox.activateButton(new ChoosePrintWindowState());
        windowBorderPane.setRight(rightSideVBox);
        windowBorderPane.setLeft(leftSideVBox);
        windowBorderPane.setBottom(new BottomHBox(windowBorderPane));
    }
}
