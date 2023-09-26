package application.states;

import application.WindowBorderPane;
import application.bottom.BottomHBox;
import application.center.vboxes.CircuitInformationVBox;
import application.sides.left.LeftSideVBox;
import application.sides.right.RightSideVBox;
import application.top.TopHBox;

/**
 * Class representing a window state with the information of imported circuits
 */
public class InformationCircuitFileWindowState implements WindowState {
    /**
     * @inheritDoc
     */
    @Override
    public void update(WindowBorderPane windowBorderPane) {
        RightSideVBox rightSideVBox = new RightSideVBox(windowBorderPane);
        rightSideVBox.activateButton(new ExcludeGraphsWindowState());
        LeftSideVBox leftSideVBox = new LeftSideVBox(windowBorderPane);
        leftSideVBox.activateButton(new InformationOfFileWindowState());
        windowBorderPane.setTop(new TopHBox());
        windowBorderPane.setCenter(CircuitInformationVBox.getInstance(windowBorderPane));
        windowBorderPane.setLeft(leftSideVBox);
        windowBorderPane.setRight(rightSideVBox);
        windowBorderPane.setBottom(new BottomHBox(windowBorderPane));

    }
}
