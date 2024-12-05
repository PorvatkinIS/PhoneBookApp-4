import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class PhoneBookApp extends JFrame implements ActionListener {
    private final PhoneBook phoneBook;

    // Компоненты GUI
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField phonesField;
    private final JTextField searchPhoneField; // Поле для поиска по телефону
    private final JTextArea outputArea;

    public PhoneBookApp() {
        phoneBook = new PhoneBook();

        // Настройка окна
        setTitle("Телефонная книга");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель для ввода данных
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2)); // Используем GridLayout для упорядочивания

        firstNameField = new JTextField(10);
        lastNameField = new JTextField(10);
        phonesField = new JTextField(20);
        searchPhoneField = new JTextField(15); // Поле для поиска по телефону

        JButton addButton = new JButton("Добавить контакт");
        JButton removeButton = new JButton("Удалить контакт");
        JButton editButton = new JButton("Редактировать контакт");
        JButton searchButton = new JButton("Поиск по фамилии");
        JButton searchByPhoneButton = new JButton("Поиск по телефону"); // Кнопка для поиска по телефону
        JButton showButton = new JButton("Показать все контакты");

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        editButton.addActionListener(this);
        searchButton.addActionListener(this);
        searchByPhoneButton.addActionListener(this); // Добавляем обработчик
        showButton.addActionListener(this);

        panel.add(new JLabel("Имя:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Фамилия:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Телефоны (через запятую):"));
        panel.add(phonesField);
        panel.add(new JLabel("Поиск по телефону:"));
        panel.add(searchPhoneField); // Поле для поиска по телефону

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(editButton);
        panel.add(searchButton);
        panel.add(searchByPhoneButton); // Кнопка для поиска по телефону
        panel.add(showButton);

        add(panel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Добавить контакт":
                addContact();
                break;
            case "Удалить контакт":
                removeContact();
                break;
            case "Редактировать контакт":
                editContact();
                break;
            case "Поиск по фамилии":
                searchByLastName();
                break;
            case "Поиск по телефону": // Обработка поиска по телефону
                searchByPhone();
                break;
            case "Показать все контакты":
                showContacts();
                break;
        }
    }

    private void addContact() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        List<String> phonesToAdd = Arrays.asList(phonesField.getText().split("\\s*,\\s*"));

        if (firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Имя и фамилия не могут быть пустыми.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (phonesToAdd.stream().allMatch(this::isValidPhoneNumber)) {
            phoneBook.addContact(firstName, lastName, phonesToAdd);
            outputArea.append("Контакт добавлен: " + firstName + " " + lastName + "\n");
            clearFields(); // Очистка полей после добавления
        } else {
            JOptionPane.showMessageDialog(this, "Некорректные номера телефонов.", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeContact() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        phoneBook.removeContact(firstName, lastName);
        outputArea.append("Контакт удален: " + firstName + " " + lastName + "\n");
        clearFields(); // Очистка полей после удаления
    }

    private void editContact() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        List<String> newPhones = Arrays.asList(phonesField.getText().split("\\s*,\\s*"));

        if (newPhones.stream().allMatch(this::isValidPhoneNumber)) {
            phoneBook.editContact(firstName, lastName, newPhones);
            outputArea.append("Контакт отредактирован: " + firstName + " " + lastName + "\n");
            clearFields(); // Очистка полей после редактирования
        } else {
            JOptionPane.showMessageDialog(this, "Некорректные номера телефонов.", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchByLastName() {
        String lastName = lastNameField.getText().trim();
        List<Contact> foundContacts = phoneBook.searchByLastName(lastName);

        outputArea.setText(""); // Очистить область вывода перед выводом результатов
        if (foundContacts.isEmpty()) {
            outputArea.append("Контакты не найдены.\n");
        } else {
            foundContacts.forEach(contact -> outputArea.append(contact.toString() + "\n"));
        }
    }

    private void searchByPhone() {
        String phoneNumber = searchPhoneField.getText().trim();
        List<Contact> foundContacts = phoneBook.searchByPhoneNumber(phoneNumber);

        outputArea.setText(""); // Очистить область вывода перед выводом результатов
        if (foundContacts.isEmpty()) {
            outputArea.append("Контакты с номером " + phoneNumber + " не найдены.\n");
        } else {
            foundContacts.forEach(contact -> outputArea.append(contact.toString() + "\n"));
        }
    }

    private void showContacts() {
        phoneBook.displayContacts(outputArea);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\+?\\d{1,15}"); // Пример простой проверки
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        phonesField.setText("");
        searchPhoneField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PhoneBookApp::new); // Запускаем приложение
    }
}
