package application.states;

import application.bottom.BottomHBox;
import application.WindowBorderPane;
import application.center.vboxes.InformationVBox;
import application.top.TopHBox;
import application.sides.left.LeftSideVBox;
import application.sides.right.RightSideVBox;

/**
 * Class representing a Windows state when file was analyses.
 */
public class InformationOfFileWindowState implements WindowState {
    /**
     * @inheritDoc
     */
    @Override
    public void update(WindowBorderPane windowBorderPane) {
        RightSideVBox rightSideVBox = new RightSideVBox(windowBorderPane);
        rightSideVBox.activateButton(new ExcludeGraphsWindowState());
        windowBorderPane.setTop(new TopHBox());
        windowBorderPane.setCenter(new InformationVBox(windowBorderPane, rightSideVBox));
        windowBorderPane.setLeft(new LeftSideVBox(windowBorderPane));
        windowBorderPane.setRight(rightSideVBox);
        windowBorderPane.setBottom(new BottomHBox(windowBorderPane));

    }
}
