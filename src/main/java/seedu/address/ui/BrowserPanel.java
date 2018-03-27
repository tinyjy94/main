package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.DetailedDayView;
import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CinemaPanelSelectionChangedEvent;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Theater;
import seedu.address.model.screening.Screening;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private StackPane browserPane;

    public BrowserPanel() {
        super(FXML);
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        registerAsAnEventHandler(this);
    }

    private void loadCinemaSchedule(Cinema cinema) {

        DetailedDayView detailedDayView = new DetailedDayView();
        ArrayList<Theater> theaterList = cinema.getTheaters();

        CalendarSource theatersSchedule = new CalendarSource("Theaters");

        int count = 1;

        for (Theater t : theaterList) {
            Calendar c = new Calendar(Integer.toString(t.getTheaterNumber()));
            c.setShortName(Integer.toString(t.getTheaterNumber()));
            theatersSchedule.getCalendars().add(c);
            setColorStyle(c, count);
            c.setReadOnly(true);

            ArrayList<Screening> screeningList = t.getScreeningList();
            ArrayList<Entry<String>> entries = new ArrayList<>();
            for (Screening s : screeningList) {
                Entry<String> movieScreening = new Entry<>(s.getMovieName());
                movieScreening.setInterval(s.getScreeningDateTime(), s.getScreeningEndDateTime());
                c.addEntry(movieScreening);
            }
            count++;
        }

        detailedDayView.getCalendarSources().setAll(theatersSchedule);

        browserPane.getChildren().add(detailedDayView);
    }


    private void setColorStyle(Calendar c, int count) {
        switch (count % 7) {
        case 1:
            c.setStyle(Calendar.Style.STYLE1);
            break;
        case 2:
            c.setStyle(Calendar.Style.STYLE2);
            break;
        case 3:
            c.setStyle(Calendar.Style.STYLE3);
            break;
        case 4:
            c.setStyle(Calendar.Style.STYLE4);
            break;
        case 5:
            c.setStyle(Calendar.Style.STYLE5);
            break;
        case 6:
            c.setStyle(Calendar.Style.STYLE6);
            break;
        case 7:
            c.setStyle(Calendar.Style.STYLE7);
            break;
        default:
            c.setStyle(Calendar.Style.STYLE1);
        }
    }

    private void setUpDayView(DetailedDayView detailedDayView) {
        detailedDayView.setLayout(DateControl.Layout.SWIMLANE);
        detailedDayView.setShowAllDayView(false);
        detailedDayView.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"),
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Subscribe
    private void handleCinemaPanelSelectionChangedEvent(CinemaPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPane.getChildren().clear();
        loadCinemaSchedule(event.getNewSelection().cinema);
    }
}
