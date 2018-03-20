package seedu.address.storage;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cinema.Theater;
import seedu.address.model.screening.Screening;

/**
 * JAXB-friendly adapted version of the Theater.
 */
public class XmlAdaptedTheater {

    @XmlAttribute(name = "number")
    private int theaterNumber;
    @XmlElement(name = "screening")
    private ArrayList<XmlAdaptedScreening> screenings = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTheater.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTheater() {}

    /**
     * Constructs a {@code XmlAdaptedTheater} with the given {@code theaterNumber}.
     */
    public XmlAdaptedTheater(int theaterNumber, ArrayList<XmlAdaptedScreening> screenings) {
        this.theaterNumber = theaterNumber;
        if (screenings != null) {
            this.screenings = new ArrayList<>(screenings);
        }
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTheater(Theater source) {
        this.theaterNumber = source.getTheaterNumber();

        for (Screening screening : source.getScreeningList()) {
            screenings.add(new XmlAdaptedScreening(screening));
        }
    }

    /**
     * Converts this jaxb-friendly adapted theater object into the model's Theater object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted cinema
     */
    public Theater toModelType() throws IllegalValueException {
        if (!Theater.isValidTheater(theaterNumber)) {
            throw new IllegalValueException(Theater.MESSAGE_THEATER_CONSTRAINTS);
        }
        return new Theater(theaterNumber);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTheater)) {
            return false;
        }

        return theaterNumber == ((XmlAdaptedTheater) other).theaterNumber;
    }
}
