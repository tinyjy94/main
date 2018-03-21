package seedu.address.model.screening;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Theater;
import seedu.address.model.movie.Movie;

/**
 * Represents a movie screening in a cinema theater
 */
public class Screening {

    // Constants for calculations
    private static final int PREPARATION_DELAY = 15;
    private static final int MINUTES_INTERVAL = 5;
    private static final int ENSURE_MINUTES_POSITIVE = 65;

    private final Movie movie;
    private final Cinema cinema;
    private final Theater theater;
    private final LocalDateTime screeningDateTime;
    private LocalDateTime screeningEndDateTime;

    public Screening(Movie movie, Cinema cinema, Theater theater, LocalDateTime screeningDateTime) {
        this.movie = movie;
        this.cinema = cinema;
        this.theater = theater;
        this.screeningDateTime = screeningDateTime;
        this.screeningEndDateTime = getEndTime(movie);
    }

    /**
     * Calculates the time needed to screen a movie.
     * Elements used in calculations are movie's duration, preparation delay and rounding off to nearest 5 minutes
     * @param movie
     * @return endTime time where the screening will end
     */
    private LocalDateTime getEndTime(Movie movie) {
        int movieDuration = Integer.parseInt(movie.getDuration().toString());
        LocalDateTime endTime = this.screeningDateTime.plusMinutes(movieDuration).plusMinutes(PREPARATION_DELAY);

        if (endTime.getMinute() % MINUTES_INTERVAL != 0) {
            LocalDateTime roundedTime = endTime;
            roundedTime = roundedTime.withSecond(0).withNano(0).plusMinutes((
                    ENSURE_MINUTES_POSITIVE - roundedTime.getMinute()) % MINUTES_INTERVAL);
            return roundedTime;
        }
        return endTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public Cinema getCinema() {
        return cinema;
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
                .append(" Cinema: ")
                .append(cinema.getName())
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
                && otherScreening.getCinema().equals(this.getCinema())
                && otherScreening.getTheater().equals(this.getTheater())
                && otherScreening.getScreeningDateTime().equals(this.getScreeningDateTime())
                && otherScreening.getScreeningEndDateTime().equals(this.getScreeningEndDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(movie, cinema, theater, screeningDateTime, screeningEndDateTime);
    }
}
