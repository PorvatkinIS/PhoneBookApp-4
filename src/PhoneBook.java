import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

class PhoneBook {
    private final List<Contact> contacts;

    public PhoneBook() {
        this.contacts = new ArrayList<>();
    }

    public void addContact(String firstName, String lastName, List<String> phoneNumbers) {
        contacts.add(new Contact(firstName, lastName, phoneNumbers));
        sortContacts();
    }

    public void removeContact(String firstName, String lastName) {
        contacts.removeIf(contact -> contact.getFirstName().equalsIgnoreCase(firstName) && contact.getLastName().equalsIgnoreCase(lastName));
    }

    public void editContact(String firstName, String lastName, List<String> newPhoneNumbers) {
        Optional<Contact> contactOpt = contacts.stream()
                .filter(contact -> contact.getFirstName().equalsIgnoreCase(firstName) && contact.getLastName().equalsIgnoreCase(lastName))
                .findFirst();

        contactOpt.ifPresent(contact -> contact.setPhoneNumbers(newPhoneNumbers));
    }

    public List<Contact> searchByLastName(String lastName) {
        return contacts.stream()
                .filter(contact -> contact.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    public List<Contact> searchByPhoneNumber(String phoneNumber) {
        return contacts.stream()
                .filter(contact -> contact.getPhoneNumbers().contains(phoneNumber))
                .collect(Collectors.toList());
    }

    public void displayContacts(JTextArea textArea) {
        textArea.setText(""); // Очистить текстовое поле
        contacts.forEach(contact -> textArea.append(contact.toString() + "\n"));
    }

    private void sortContacts() {
        contacts.sort(Comparator.comparing(Contact::getLastName)
                .thenComparing(Contact::getFirstName));
    }
}
