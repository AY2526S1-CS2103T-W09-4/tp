package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label company;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        // Address (optional)
        address.setText(getAddressText(person));
        address.setVisible(isAddressVisible(person));
        address.setManaged(isAddressVisible(person));

        // Email (optional)
        email.setText(getEmailText(person));
        email.setVisible(isEmailVisible(person));
        email.setManaged(isEmailVisible(person));

        // Company (optional)
        company.setText(getCompanyText(person));
        company.setVisible(isCompanyVisible(person));
        company.setManaged(isCompanyVisible(person));

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /*
     * Package-private pure helpers to determine what the UI should display for optional fields.
     * These are intentionally pure and depend only on Person so they can be tested without JavaFX.
     */
    static String getAddressText(Person person) {
        return person.getAddress() != null ? person.getAddress().value : "";
    }

    static boolean isAddressVisible(Person person) {
        return person.getAddress() != null;
    }

    static String getEmailText(Person person) {
        return person.getEmail() != null ? person.getEmail().value : "";
    }

    static boolean isEmailVisible(Person person) {
        return person.getEmail() != null;
    }

    static String getCompanyText(Person person) {
        return person.getCompany() != null ? person.getCompany().value : "";
    }

    static boolean isCompanyVisible(Person person) {
        return person.getCompany() != null;
    }

}
