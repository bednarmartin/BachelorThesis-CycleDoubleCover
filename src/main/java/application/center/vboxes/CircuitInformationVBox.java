package application.center.vboxes;

import algorithm.graph.Circuit;
import algorithm.reading.CircuitFileAnalysis;
import algorithm.reading.iterator.CircuitIterator;
import application.WindowBorderPane;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Text;

/**
 * Class representing CenterVBox with the analysis of the file with the circuits.
 */
public class CircuitInformationVBox extends CenterVBox {
    /**
     * instance of the class
     */
    private static CircuitInformationVBox instance;

    /**
     * Constructor
     *
     * @param parent WindowBorderPane
     */
    public CircuitInformationVBox(WindowBorderPane parent) {
        super(parent);
        setAlignment(Pos.BASELINE_CENTER);
        setPadding(new Insets(10));
        CircuitFileAnalysis circuitFileAnalysis = parent.getCircuitFileAnalysis();
        StringBuilder stringBuilder = new StringBuilder();
        CircuitIterator circuitIterator = circuitFileAnalysis.getCircuitIterator();
        try {
            int counter = 1;
            while (circuitIterator.hasNext()) {
                Circuit circuit = circuitIterator.next();
                stringBuilder.append(counter);
                stringBuilder.append(". Circuit: ");
                stringBuilder.append(circuit);
                if (circuit.isInduced(parent.getGraphFileAnalysis().getGraphIterator().next())) {
                    stringBuilder.append("\nCircuit is induced\n\n");
                } else {
                    stringBuilder.append("\nCircuit is not induced\n\n");
                }
                counter++;
            }
        } catch (Exception e) {
            stringBuilder.append("SOMETHING WENT WRONG\n");
            stringBuilder.append(e);
        }
        MFXScrollPane mfxScrollPane = new MFXScrollPane();
        Text text = new Text(stringBuilder.toString());
        mfxScrollPane.setContent(text);
        getChildren().add(mfxScrollPane);
    }

    /**
     * This method grants access to the instance of the class.
     *
     * @param parent WindowBorderPane
     * @return instance of the class.
     */
    public static CircuitInformationVBox getInstance(WindowBorderPane parent) {
        if (instance == null) {
            instance = new CircuitInformationVBox(parent);
        }
        return instance;
    }

    /**
     * This method nulls the instance.
     */
    public static void clear() {
        instance = null;
    }
}
