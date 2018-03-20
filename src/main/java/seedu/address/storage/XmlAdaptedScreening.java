package seedu.address.storage;

import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.screening.Screening;

/**
 * JAXB-friendly adapted version of the Screening.
 */
public class XmlAdaptedScreening {

    @XmlElement(required = true, name = "movie")
    private String movieName;
    @XmlElement(required = true, name = "startDateTime")
    private String screeningDateTime;
    @XmlElement(required = true, name = "endDateTime")
    private String screeningEndDateTime;

    /**
     * Constructs an XmlAdaptedScreening.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedScreening() {}

    /**
     * Constructs a {@code XmlAdaptedScreening} with the given {@code theaternum}.
     */
    public XmlAdaptedScreening(String movieName, String screeningDateTime, String screeningEndDateTime) {
        this.movieName = movieName;
        this.screeningDateTime = screeningDateTime;
        this.screeningEndDateTime = screeningEndDateTime;
    }

    /**
     * Converts a given Screening into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedScreening(Screening source) {
        movieName = source.getMovie().getName().toString();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        screeningDateTime = source.getScreeningDateTime().format(dtf);
        screeningEndDateTime = source.getScreeningEndDateTime().format(dtf);
    }
}
