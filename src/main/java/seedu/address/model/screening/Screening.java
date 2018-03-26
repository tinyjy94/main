package seedu.address.model.screening;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.address.model.cinema.Theater;
import seedu.address.model.movie.Movie;

/**
 * Represents a movie screening in a cinema theater
 */
public class Screening {

    private final Movie movie;
    private final Theater theater;
    private final LocalDateTime screeningDateTime;
    private final LocalDateTime screeningEndDateTime;

    public Screening(Movie movie, Theater theater, LocalDateTime screeningDateTime,
                     LocalDateTime screeningEndDateTime) {
        this.movie = movie;
        this.theater = theater;
        this.screeningDateTime = screeningDateTime;
        this.screeningEndDateTime = screeningEndDateTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public LocalDateTime getScreeningDateTime() {
        return screeningDateTime;
    }

    public LocalDateTime getScreeningEndDateTime() {
        return screeningEndDateTime;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Movie: ")
                .append(movie.getName())
                .append(" Theater: ")
                .append(theater.getTheaterNumber())
                .append(" Date: ")
                .append(screeningDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Screening)) {
            return false;
        }

        Screening otherScreening = (Screening) other;
        return otherScreening.getMovie().equals(this.getMovie())
                && otherScreening.getTheater().equals(this.getTheater())
                && otherScreening.getScreeningDateTime().equals(this.getScreeningDateTime())
                && otherScreening.getScreeningEndDateTime().equals(this.getScreeningEndDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(movie, theater, screeningDateTime, screeningEndDateTime);
    }
}
