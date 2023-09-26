package algorithm.reading.format.graph;

import algorithm.reading.format.Format;
import application.center.vboxes.AnalysisVBox;
import application.sides.left.LeftSideVBox;
import application.sides.right.RightSideVBox;
import application.states.InformationOfFileWindowState;
import javafx.scene.paint.Paint;

import java.io.FileNotFoundException;

/**
 * Abstract class representing a good graph format.
 */
public abstract class GoodGraphFormat implements GraphFormat {

    /**
     * @inheritDoc
     */
    @Override
    public void updateAnalysisVBox(AnalysisVBox analysisVBox) {
        try {
            Format.setResult(analysisVBox, "bin/good.png", Paint.valueOf("#45d6a9"), toString());
            RightSideVBox rightSideVBox = new RightSideVBox(analysisVBox.getWindowBorderPane());
            rightSideVBox.activateButton(new InformationOfFileWindowState());
            analysisVBox.getWindowBorderPane().setRight(rightSideVBox);
            analysisVBox.getWindowBorderPane().setLeft(new LeftSideVBox(analysisVBox.getWindowBorderPane()));
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "Supported Graph Format";
    }
}
