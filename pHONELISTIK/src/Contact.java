import java.util.ArrayList;
import java.util.List;

class Contact {
    private final String firstName;
    private final String lastName;
    private List<String> phoneNumbers;

    public Contact(String firstName, String lastName, List<String> phoneNumbers) {
        this.firstName = firstName;
        this.lastName = lastName;
        setPhoneNumbers(phoneNumbers);
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public List<String> getPhoneNumbers() { return phoneNumbers; }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = new ArrayList<>(phoneNumbers.subList(0, Math.min(phoneNumbers.size(), 3)));
    }

    @Override
    public String toString() {
        return String.format("%s %s: %s", firstName, lastName, String.join(", ", phoneNumbers));
    }
}
