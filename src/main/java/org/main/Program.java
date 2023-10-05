package org.main;
import org.hibernate.query.Query;
import org.main.dao.ManufactureDAO;
import org.main.dao.PhoneDAO;
import org.main.domain.Manufacture;
import org.main.domain.Phone;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class Program
{
    public static void main(String[] args) {
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);
        initial();

        Scanner scanner = new Scanner(System.in);
        PhoneDAO<Phone, Integer> phoneDAO = new PhoneDAO<>();
        ManufactureDAO<Manufacture, Integer> manufactureDAO = new ManufactureDAO<>();

        int option;
        do {
            System.out.println("Choose an option:");
            System.out.println("1. Display all phones (descending order by ID)");
            System.out.println("2. Remove a phone by ID");
            System.out.println("3. Get a phone by ID");
            System.out.println("4. Update a phone");
            System.out.println("5. Get the highest selling price");
            System.out.println("6. Display phones sorted by country and price");
            System.out.println("7. Check if any phone is priced above a threshold");
            System.out.println("8. Get the first phone matching color and price");
            System.out.println("9. Display all manufacturers (descending order by ID)");
            System.out.println("10. Remove a manufacturer by ID");
            System.out.println("11. Get a manufacturer by ID");
            System.out.println("12. Update a manufacturer");
            System.out.println("13. Check if all manufacturers are above an employee threshold");
            System.out.println("14. Get the total number of employees in all manufacturers");
            System.out.println("15. Get the last manufacturer in the US");
            System.out.println("0. Exit");
            System.out.print("Option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    List<Phone> phones = phoneDAO.getAll();
                    for (Phone phone : phones) {
                        System.out.println(phone);
                    }
                    break;
                case 2:
                    System.out.print("Enter the phone ID to remove: ");
                    int removePhoneId = scanner.nextInt();
                    Phone phoneToRemove = phoneDAO.get(removePhoneId);
                    if (phoneToRemove != null) {
                        phoneDAO.remove(phoneToRemove);
                        System.out.println("Phone removed successfully.");
                    } else {
                        System.out.println("Phone not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter the phone ID to get: ");
                    int getPhoneId = scanner.nextInt();
                    Phone phone = phoneDAO.get(getPhoneId);
                    if (phone != null) {
                        System.out.println(phone);
                    } else {
                        System.out.println("Phone not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter the phone ID to update: ");
                    int updatePhoneId = scanner.nextInt();
                    Phone phoneToUpdate = phoneDAO.get(updatePhoneId);
                    if (phoneToUpdate != null) {
                        System.out.print("Enter the new phone name: ");
                        scanner.nextLine(); // Consume newline
                        String newName = scanner.nextLine();
                        phoneToUpdate.setName(newName);
                        phoneDAO.update(phoneToUpdate);
                        System.out.println("Phone updated successfully.");
                    } else {
                        System.out.println("Phone not found.");
                    }
                    break;
                case 5:
                    double highestSellingPrice = phoneDAO.getHighestSellingPrice();
                    System.out.println("Highest selling price: " + highestSellingPrice);
                    break;
                case 6:
                    List<Phone> phonesSortedByCountryAndPrice = phoneDAO.getPhonesSortedByCountryAndPrice();
                    for (Phone sortedPhone : phonesSortedByCountryAndPrice) {
                        System.out.println(sortedPhone);
                    }
                    break;
                case 7:
                    System.out.print("Enter the price threshold: ");
                    double priceThreshold = scanner.nextDouble();
                    boolean isPricedAboveThreshold = phoneDAO.isPhonePricedAboveThreshold(priceThreshold);
                    if (isPricedAboveThreshold) {
                        System.out.println("Some phones are priced above the threshold.");
                    } else {
                        System.out.println("No phone is priced above the threshold.");
                    }
                    break;
                case 8:
                    System.out.print("Enter the phone color: ");
                    scanner.nextLine(); // Consume newline
                    String color = scanner.nextLine();
                    System.out.print("Enter the price threshold: ");
                    double threshold = scanner.nextDouble();
                    Phone firstMatchingPhone = phoneDAO.getFirstMatchingPhone(color, threshold);
                    if (firstMatchingPhone != null) {
                        System.out.println("First matching phone: " + firstMatchingPhone);
                    } else {
                        System.out.println("No matching phone found.");
                    }
                    break;

                case 9:
                    List<Manufacture> manufacturers = manufactureDAO.getAll();
                    manufacturers.sort((m1, m2) -> m2.getId() - m1.getId());
                    for (Manufacture manufacturer : manufacturers) {
                        System.out.println(manufacturer);
                    }
                    break;

                case 10:
                    System.out.print("Enter the manufacturer ID to remove: ");
                    int removeManufacturerId = scanner.nextInt();
                    Manufacture manufacturerToRemove = manufactureDAO.get(removeManufacturerId);
                    if (manufacturerToRemove != null) {
                        manufactureDAO.remove(manufacturerToRemove);
                        System.out.println("Manufacturer removed successfully.");
                    } else {
                        System.out.println("Manufacturer not found.");
                    }
                    break;

                case 11:
                    System.out.print("Enter the manufacturer ID to get: ");
                    int getManufacturerId = scanner.nextInt();
                    Manufacture manufacturer = manufactureDAO.get(getManufacturerId);
                    if (manufacturer != null) {
                        System.out.println(manufacturer);
                    } else {
                        System.out.println("Manufacturer not found.");
                    }
                    break;

                case 12:
                    System.out.print("Enter the manufacturer ID to update: ");
                    int updateManufacturerId = scanner.nextInt();
                    Manufacture manufacturerToUpdate = manufactureDAO.get(updateManufacturerId);
                    if (manufacturerToUpdate != null) {
                        System.out.print("Enter the new manufacturer name: ");
                        scanner.nextLine(); // Consume newline
                        String newName = scanner.nextLine();
                        manufacturerToUpdate.setName(newName);
                        manufactureDAO.update(manufacturerToUpdate);
                        System.out.println("Manufacturer updated successfully.");
                    } else {
                        System.out.println("Manufacturer not found.");
                    }
                    break;

                case 13:
                    boolean areAllAboveThreshold = manufactureDAO.areAllManufacturersAboveEmployeeThreshold();
                    if (areAllAboveThreshold) {
                        System.out.println("All manufacturers are above the employee threshold.");
                    } else {
                        System.out.println("Not all manufacturers are above the employee threshold.");
                    }
                    break;

                case 14:
                    int totalEmployees = manufactureDAO.getTotalEmployees();
                    System.out.println("Total number of employees in all manufacturers: " + totalEmployees);
                    break;

                case 15:
                    Manufacture lastManufacturerInUS = manufactureDAO.getLastManufacturerInUS();
                    if (lastManufacturerInUS != null) {
                        System.out.println("Last manufacturer in the US: " + lastManufacturerInUS);
                    } else {
                        System.out.println("No manufacturer found in the US.");
                    }
                    break;

                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
                    break;
            }
        } while (option != 6);

        phoneDAO.closeSession();
        manufactureDAO.closeSession();
        scanner.close();
    }

    static final void initial() {
        ManufactureDAO daoObject = new ManufactureDAO<>();
        if(daoObject.isEmptyData()) {
            daoObject.add(new Manufacture("Apple", "Cupertino", 1000));
            daoObject.add(new Manufacture("Samsung", "Seocho-daer", 10000));
            daoObject.add(new Manufacture("Huawei", "Bantian", 5000));
        }
    }
}
