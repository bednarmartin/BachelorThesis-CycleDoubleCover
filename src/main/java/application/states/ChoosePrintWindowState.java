package application.states;

import application.WindowBorderPane;
import application.bottom.BottomHBox;

import application.center.vboxes.PrintChoosingVBox;
import application.sides.SideVBox;
import application.sides.left.LeftSideVBox;
import application.sides.right.RightSideVBox;
import application.top.TopHBox;

/**
 * Class representing a state of window with options to print.
 */
public class ChoosePrintWindowState implements WindowState {
    /**
     * @inheritDoc
     */
    @Override
    public void update(WindowBorderPane windowBorderPane) {
        TopHBox topHbox = new TopHBox();
        topHbox.setText("Print:");
        SideVBox rightSideVBox = new RightSideVBox(windowBorderPane);
        SideVBox leftSideVBox = new LeftSideVBox(windowBorderPane);
        leftSideVBox.activateButton(new ExcludeGraphsWindowState());
        windowBorderPane.setTop(topHbox);
        PrintChoosingVBox printChoosingVBox = new PrintChoosingVBox(windowBorderPane, rightSideVBox);
        windowBorderPane.setCenter(printChoosingVBox);
        rightSideVBox.activateButton(new ChooseOutputWindowState());
        printChoosingVBox.setAssociatedContinueButton(rightSideVBox.getButton());
        windowBorderPane.setRight(rightSideVBox);
        windowBorderPane.setLeft(leftSideVBox);
        windowBorderPane.setBottom(new BottomHBox(windowBorderPane));
    }
}
