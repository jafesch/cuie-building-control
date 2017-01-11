package ch.fhnw.cuie.buildings.singlebuilding;

import ch.fhnw.cuie.buildings.BuildingPM;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;

/**
 * Height slider which shows the height in relation to a building.
 * @author Roman Fritschi, Julian Fisch
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

	/**
	 * Sets up all parts used for the height slider.
	 */
	private void initializeParts() {
		height_mLabel = new Label("Height (m)");
		height_mField = new TextField();

		//stack pane which contains slider and rectangle as color overlay
		sp = new StackPane();
		sp.setAlignment(Pos.TOP_LEFT);
		sp.setMaxWidth(200);

		//rectangle to highlight selected area
		Pane p = new Pane();
		Rectangle rect = new Rectangle(30, 0, 170, 500);
		rect.setFill(Color.BLACK);
		rect.setOpacity(0.35);
		p.getChildren().add(rect);

		buildingSlider = new Slider(0, 1000, 100);
		buildingSlider.getStylesheets().add(getClass().getResource("buildingSlider.css").toExternalForm());

		sp.getStyleClass().add("my-stack-pane");
		sp.getStylesheets().add(getClass().getResource("buildingSlider.css").toExternalForm());
		sp.getChildren().addAll(p, buildingSlider);

		//Adapt highlighting rectangle if slider value has changed
		buildingSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
								Number oldValue, Number newValue) {

				if (newValue == oldValue) return;

				int sliderPos = (int) ((1000 - (double) newValue) / 2);

				//correction value to adapt rectangle to background position of the slider track element
				sliderPos += (int) ((double)(newValue) - 500) / 50;

				rect.setY(sliderPos);
				rect.setHeight(500 - sliderPos);
			}
		});

		buildingSlider.setValue(200);
	}

	private void layoutParts() {
		GridPane valueGrid = new GridPane();
		valueGrid.addRow(0, height_mLabel, height_mField);
		valueGrid.setHgap(20);
		valueGrid.setVgap(10);

		this.addRow(0, valueGrid, sp);
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

		//bind slider to input and vice versa
		Bindings.bindBidirectional(height_mField.textProperty(), building.height_mProperty(), doubleStringConverter);
		Bindings.bindBidirectional(building.height_mProperty(), buildingSlider.valueProperty());
	}
}
