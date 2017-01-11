package ch.fhnw.cuie.buildings.singlebuilding;

import ch.fhnw.cuie.buildings.BuildingPM;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * @author Dieter Holz
 */
public class SingleBuildingView extends VBox {
    private BuildingPM building;
    private Node customControlPane;
    private Node standardFormPane;
    private Node heightSlider;

    public SingleBuildingView(BuildingPM building) {
        this.building = building;
        initializeParts();
        layoutParts();
    }

    private void initializeParts() {
        customControlPane = new CustomControlPane(building);
        standardFormPane = new StandardFormPane(building);
        heightSlider = new HeightSlider(building);
    }

    private void layoutParts() {
        getChildren().addAll(customControlPane, standardFormPane, heightSlider);
    }
}
