package algorithm.reading.format.circuit;

import algorithm.graph.Graph;
import algorithm.reading.format.Format;
import algorithm.reading.iterator.CircuitIterator;
import algorithm.reading.iterator.VerticesFormatCircuitIterator;
import application.center.vboxes.CircuitAnalysisVBox;
import application.sides.left.LeftSideVBox;
import application.sides.right.RightSideVBox;
import application.states.InformationCircuitFileWindowState;
import javafx.scene.paint.Paint;


import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class representing a circuit format of series of vertices.
 */
public class VerticesCircuitFormat implements CircuitFormat {
    /**
     * @inheritDoc
     */
    @Override
    public CircuitIterator getCircuitIterator(String path, Graph graph) throws IOException {
        return new VerticesFormatCircuitIterator(path, graph);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateAnalysisVBox(CircuitAnalysisVBox circuitAnalysisVBox) {
        try {
            Format.setResult(circuitAnalysisVBox, "./bin/good.png", Paint.valueOf("#45d6a9"), toString());
            RightSideVBox rightSideVBox = new RightSideVBox(circuitAnalysisVBox.getWindowBorderPane());
            rightSideVBox.activateButton(new InformationCircuitFileWindowState());
            circuitAnalysisVBox.getWindowBorderPane().setRight(rightSideVBox);
            circuitAnalysisVBox.getWindowBorderPane().setLeft(new LeftSideVBox(circuitAnalysisVBox.getWindowBorderPane()));
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }


    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "Supported Circuit Format";
    }

}
