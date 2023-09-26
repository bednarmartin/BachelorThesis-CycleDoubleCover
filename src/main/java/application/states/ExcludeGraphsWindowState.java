package application.states;

import application.bottom.BottomHBox;
import application.WindowBorderPane;
import application.center.vboxes.FilterGraphsVBox;
import application.top.TopHBox;
import application.sides.SideVBox;
import application.sides.left.LeftSideVBox;
import application.sides.right.RightSideVBox;

/**
 * Class representing a window state when graphs are excluded.
 */
public class ExcludeGraphsWindowState implements WindowState {
    /**
     * @inheritDoc
     */
    @Override
    public void update(WindowBorderPane windowBorderPane) {
        TopHBox topHbox = new TopHBox();
        topHbox.setText("Exclude the graphs with:");
        windowBorderPane.setTop(topHbox);
        SideVBox sideVBox = new RightSideVBox(windowBorderPane);
        sideVBox.activateButton(new InformationOfFileWindowState());
        SideVBox leftsideVBox = new LeftSideVBox(windowBorderPane);
        sideVBox.activateButton(new ChoosePrintWindowState());
        windowBorderPane.setCenter(FilterGraphsVBox.getInstance(windowBorderPane, sideVBox));
        windowBorderPane.setRight(sideVBox);
        windowBorderPane.setLeft(leftsideVBox);
        windowBorderPane.setBottom(new BottomHBox(windowBorderPane));
    }
}
