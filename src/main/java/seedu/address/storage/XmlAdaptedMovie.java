package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.movie.Movie;
import seedu.address.model.movie.MovieName;

/**
 * JAXB-friendly version of the Movie.
 */
public class XmlAdaptedMovie {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Movie's %s field is missing!";

    @XmlElement(required = true)
    private String name;

    /**
     * Constructs an XmlAdaptedMovie.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMovie() {}

    /**
     * Constructs an {@code XmlAdaptedCinema} with the given movie details.
     */
    public XmlAdaptedMovie(String name) {
        this.name = name;
    }

    /**
     * Converts a given Movie into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMovie
     */
    public XmlAdaptedMovie(Movie source) {
        name = source.getName().movieName;
    }

    /**
     * Converts this jaxb-friendly adapted movie object into the model's Movie object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted movie
     */
    public Movie toModelType() throws IllegalValueException {

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MovieName.class.getSimpleName()));
        }
        if (!MovieName.isValidName(this.name)) {
            throw new IllegalValueException(MovieName.MESSAGE_NAME_CONSTRAINTS);
        }
        final MovieName name = new MovieName(this.name);

        return new Movie(name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMovie)) {
            return false;
        }

        return name.equals(((XmlAdaptedMovie) other).name);
    }
}
