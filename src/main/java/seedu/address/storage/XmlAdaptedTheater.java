package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cinema.Theater;

/**
 * JAXB-friendly adapted version of the Theater.
 */
public class XmlAdaptedTheater {

    @XmlValue
    private String theaterNumber;

    /**
     * Constructs an XmlAdaptedTheater.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTheater() {}

    /**
     * Constructs a {@code XmlAdaptedTheater} with the given {@code theaterNumber}.
     */
    public XmlAdaptedTheater(String theaterNumber) {
        this.theaterNumber = theaterNumber;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTheater(Theater source) {
        theaterNumber = String.valueOf(source.getTheaterNumber());
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted cinema
     */
    public Theater toModelType() throws IllegalValueException {
        if (!Theater.isValidTheater(Integer.parseInt(theaterNumber), "4", "M")) {
            throw new IllegalValueException(Theater.MESSAGE_THEATER_CONSTRAINTS);
        }
        return new Theater(Integer.parseInt(theaterNumber));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTheater)) {
            return false;
        }

        return theaterNumber.equals(((XmlAdaptedTheater) other).theaterNumber);
    }
}
