package seedu.address.ui;

import java.util.Objects;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;

/**
 * A small presentation-model for PersonCard UI logic that can be tested without JavaFX.
 *
 * Responsible only for producing strings and visibility flags for optional fields (address, email, company)
 * and for producing a sorted tag summary string.
 */
public class PersonCardViewModel {

    private final String idText;
    private final String nameText;
    private final String phoneText;

    private final String addressText;
    private final boolean addressVisible;

    private final String emailText;
    private final boolean emailVisible;

    private final String companyText;
    private final boolean companyVisible;

    private final String tagsText;

    /**
     * Constructs a {@code PersonCardViewModel} with information extracted from a {@code Person}.
     *
     * @param person The person to extract data from.
     * @param displayedIndex The index of the person in the displayed list.
     */
    public PersonCardViewModel(Person person, int displayedIndex) {
        Objects.requireNonNull(person);
        this.idText = displayedIndex + ". ";
        this.nameText = person.getName().fullName;
        this.phoneText = person.getPhone().value;

        if (person.getAddress() != null) {
            this.addressText = person.getAddress().value;
            this.addressVisible = true;
        } else {
            this.addressText = "";
            this.addressVisible = false;
        }

        if (person.getEmail() != null) {
            this.emailText = person.getEmail().value;
            this.emailVisible = true;
        } else {
            this.emailText = "";
            this.emailVisible = false;
        }

        if (person.getCompany() != null) {
            this.companyText = person.getCompany().value;
            this.companyVisible = true;
        } else {
            this.companyText = "";
            this.companyVisible = false;
        }

        this.tagsText = person.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.joining(", "));
    }

    // Getters for tests to assert values

    public String getIdText() {
        return idText;
    }

    public String getNameText() {
        return nameText;
    }

    public String getPhoneText() {
        return phoneText;
    }

    public String getAddressText() {
        return addressText;
    }

    public boolean isAddressVisible() {
        return addressVisible;
    }

    public String getEmailText() {
        return emailText;
    }

    public boolean isEmailVisible() {
        return emailVisible;
    }

    public String getCompanyText() {
        return companyText;
    }

    public boolean isCompanyVisible() {
        return companyVisible;
    }

    public String getTagsText() {
        return tagsText;
    }
}
