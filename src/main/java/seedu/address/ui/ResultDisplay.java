package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.model.UserPrefs;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
        //if(UserPrefs.isEncryptedFileExist()) {
        //  showAlertDialogAndWait(AlertType.INFORMATION, "Encrypted File Found!", null, "Encrypted File found in directory! To decrypt the file, Enter \n         decrypt pw/<password>" );
        //    Alert alert = new Alert(AlertType.INFORMATION);
        //    alert.setTitle("Encrypted File Found!");
        //    alert.setHeaderText(null);
        //    alert.setContentText("Encrypted File found in directory! To decrypt the file, Enter \ndecrypt pw/<password>");
        //    alert.showAndWait();
         //}
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }

}
