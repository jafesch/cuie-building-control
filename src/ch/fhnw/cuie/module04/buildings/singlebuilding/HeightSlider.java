package ch.fhnw.cuie.module04.buildings.singlebuilding;

import ch.fhnw.cuie.module04.buildings.BuildingPM;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

/**
 * Created by Julian on 09-Jan-17.
 */
public class HeightSlider extends GridPane {
	private final BuildingPM building;

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

		System.out.println("Working Directory = " +
				System.getProperty("user.dir"));

		buildingSlider = new Slider(0, 1000, 100);
		buildingSlider.setMajorTickUnit(1);
		buildingSlider.setSnapToTicks(true);
		buildingSlider.setOrientation(Orientation.VERTICAL);
		buildingSlider.setPrefWidth(200);
		buildingSlider.setPrefHeight(800);

		buildingSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				if (newValue == null) return;
				Rectangle2D rect = new Rectangle2D(buildingSlider.getLayoutX(), buildingSlider.getLayoutY(), buildingSlider.getWidth(), buildingSlider.getMin());
			}
		});

		buildingSlider.getStylesheets().add(getClass().getResource("buildingSlider.css").toExternalForm());


		Image img = new Image("file:burjKhalifa.jpg");
		buildingSlider.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(200, 800, false, false, false, false))));


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
