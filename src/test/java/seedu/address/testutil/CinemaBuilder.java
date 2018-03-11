package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.cinema.Address;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Email;
import seedu.address.model.cinema.Name;
import seedu.address.model.cinema.Phone;
import seedu.address.model.cinema.Theater;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Cinema objects.
 */
public class CinemaBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_THEATERS = "1";
    public static final String DEFAULT_TAGS = "friends";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Theater> theaters;
    private Set<Tag> tags;

    public CinemaBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        theaters = SampleDataUtil.getTheaterSet(Integer.parseInt(DEFAULT_THEATERS));
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the CinemaBuilder with the data of {@code cinemaToCopy}.
     */
    public CinemaBuilder(Cinema cinemaToCopy) {
        name = cinemaToCopy.getName();
        phone = cinemaToCopy.getPhone();
        email = cinemaToCopy.getEmail();
        address = cinemaToCopy.getAddress();
        theaters = new HashSet<>(cinemaToCopy.getTheaters());
        tags = new HashSet<>(cinemaToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Cinema} that we are building.
     */
    public CinemaBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Cinema} that we are building.
     */
    public CinemaBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public CinemaBuilder withTheaters(int theaters) {
        this.theaters = SampleDataUtil.getTheaterSet(theaters);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Cinema} that we are building.
     */
    public CinemaBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Cinema} that we are building.
     */
    public CinemaBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Cinema} that we are building.
     */
    public CinemaBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Cinema build() {
        return new Cinema(name, phone, email, address, theaters, tags);
    }

}
