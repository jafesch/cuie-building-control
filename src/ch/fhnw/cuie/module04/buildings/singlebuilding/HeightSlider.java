package ch.fhnw.cuie.module04.buildings.singlebuilding;

import ch.fhnw.cuie.module04.buildings.BuildingPM;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;

/**
 * Height slider which shows the height in relation to a building.
 */
public class HeightSlider extends GridPane {
	private final BuildingPM building;

	StackPane sp;
	Slider buildingSlider;

	private Label height_mLabel;
	private TextField height_mField;

	public HeightSlider(BuildingPM building) {
		this.building = building;
		initializeSelf();
		initializeParts();
		layoutParts();
		setupBindings();
	}

	private void initializeSelf() {
		setPadding(new Insets(10));
		setHgap(20);
		setVgap(10);

		ColumnConstraints cc = new ColumnConstraints();
		cc.setMinWidth(200);
		cc.setHgrow(Priority.ALWAYS);

		getColumnConstraints().addAll(new ColumnConstraints(), cc);
	}

	private void initializeParts() {
		height_mLabel = new Label("Height (m)");
		height_mField = new TextField();

		buildingSlider = new  Slider(0, 1000, 100);
		buildingSlider.getStylesheets().add(getClass().getResource("buildingSlider.css").toExternalForm());
	}

	private void layoutParts() {
		GridPane valueGrid = new GridPane();
		valueGrid.addRow(0, height_mLabel, height_mField);
		valueGrid.setHgap(20);
		valueGrid.setVgap(10);

		this.addRow(0, valueGrid, buildingSlider);
	}

	private void setupBindings() {
		StringConverter<Number> doubleStringConverter = new StringConverter<Number>() {
			@Override
			public String toString(Number n) {
				return n.toString();
			}

			@Override
			public Number fromString(String string) {
				try {
					return Double.valueOf(string);
				}
				catch (NumberFormatException ex){
					return 0.0;
				}
			}
		};

		Bindings.bindBidirectional(height_mField.textProperty(), building.height_mProperty(), doubleStringConverter);
		Bindings.bindBidirectional(building.height_mProperty(), buildingSlider.valueProperty());
	}
}
