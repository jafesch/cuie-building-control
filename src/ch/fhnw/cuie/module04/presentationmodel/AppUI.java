package ch.fhnw.cuie.module04.presentationmodel;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class AppUI extends VBox {
    private final PresentationModelExample pm;

    private Label  isAdultLabel;
    private Label  ageLabel;
	private Slider ageSlider;

	public AppUI(PresentationModelExample pm) {
        this.pm = pm;
        initializeControls();
		layoutControls();
		addBindings();
	}

	private void initializeControls() {
        isAdultLabel = new Label("false");
        ageLabel = new Label("0");
        ageSlider = new Slider(0, 100, 0);
	}

	private void layoutControls() {
		setPadding(new Insets(10));
		setSpacing(10);

		getChildren().addAll(isAdultLabel, ageLabel, ageSlider);
	}

	private void addBindings() {

	}
}
