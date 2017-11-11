package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.AppUtil.PanelChoice;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    public static final Pattern PRINT_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<filename>[^/]+)"); //name of .txt file to be saved as
    public static final String[] SELECT_ARGS_PERSON = {"p", "person", "l", "left"};
    public static final String[] SELECT_ARGS_INSURANCE = {"i", "insurance", "in", "r", "right"};

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }
    //@@ author arnollim
    /**
     * @param args into a String, which will be the filename which the .txt file will be saved as
     * for e.g. if filename was the arg, then the file will be saved as filename.txt
     * Leading and trailing whitespaces will be trimmed.
     * @return "filename" for example.
     * @throws IllegalValueException if there is no specified filepath.
     */

    public static String parseFilePath(String args) throws IllegalValueException {
        final Matcher matcher = PRINT_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return matcher.group("filename");
    }

    //author

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }
    //@@author Pujitha97
    /**
     * Parses a {@code Optional<String> dob} into an {@code Optional<Date of Birth>} if {@code dob} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DateOfBirth> parseDateOfBirth(Optional<String> dob) throws IllegalValueException {
        requireNonNull(dob);
        return dob.isPresent() ? Optional.of(new DateOfBirth(dob.get())) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> gender} into an {@code Optional<Gender>} if {@code gender} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Gender> parseGender(Optional<String> gender) throws IllegalValueException {
        requireNonNull(gender);
        return gender.isPresent() ? Optional.of(new Gender(gender.get())) : Optional.empty();
    }
    //@@author
    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    //@@author OscarWang114
    /**
     * Parses a {@code Optional<String> owner} into an {@code Optional<String>} if {@code owner} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseNameForInsurance(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(name.get()) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> premium} into an {@code Optional<Double>} if {@code premium} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Double> parsePremium(Optional<String> premium) throws IllegalValueException {
        requireNonNull(premium);
        try {
            if (premium.isPresent()) {
                return Optional.of(Double.parseDouble(premium.get()));
            } else {
                return Optional.empty();
            }
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException("Premium input cannot be recognised as a double!");
        }
    }
    /**
     * Parses a {@code Optional<String> contract} into an {@code Optional<String>} if {@code contract} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseContract(Optional<String> contract) throws IllegalValueException {
        requireNonNull(contract);
        return contract.isPresent() ? Optional.of(contract.get()) : Optional.empty();
    }

    //@@author Juxarius

    /**
     * @param input String which indicates the user's choice of panel
     * @return PanelChoice enumerator to indicate to the program the user's choice
     * @throws IllegalValueException
     */
    public static PanelChoice parsePanelChoice(String input) throws IllegalValueException {
        if (Arrays.stream(SELECT_ARGS_INSURANCE).anyMatch(key -> key.equals(input))) {
            return PanelChoice.INSURANCE;
        } else if (Arrays.stream(SELECT_ARGS_PERSON).anyMatch(key -> key.equals(input))) {
            return PanelChoice.PERSON;
        } else {
            throw new IllegalValueException("Invalid panel choice");
        }
    }
    //@@author
}
