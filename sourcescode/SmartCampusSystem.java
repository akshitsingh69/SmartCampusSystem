import java.util.*;
import java.time.LocalDateTime;

/*
 * ============================================================
 * SMART CAMPUS RESOURCE MANAGEMENT SYSTEM
 * ============================================================
 *
 * Single-file Java project for VITyarthi
 *
 * Major Modules:
 * 1. Student Management
 * 2. Resource & Booking Management
 * 3. Analytics & Multithreading
 *
 * Java Concepts:
 * OOP, Collections, Generics, Exception Handling,
 * Interfaces, Abstract Classes, Inheritance,
 * Polymorphism, Multithreading, Synchronization,
 * Comparable, Lambda, static, final, etc.
 *
 * ============================================================
 */

public class SmartCampusSystem {

    // =========================================================
    // 1. INTERFACE - ABSTRACTION
    // =========================================================

    interface Displayable {
        void display();
    }


    // =========================================================
    // 2. ABSTRACT CLASS - ABSTRACTION + ENCAPSULATION
    // =========================================================

    static abstract class Person implements Displayable {

        private int id;
        private String name;
        private String email;

        // Constructor
        Person(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        // Setters
        public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        // Abstract method
        abstract String getRole();

        // Method Overriding
        @Override
        public void display() {
            System.out.println(
                    "ID: " + id +
                    " | Name: " + name +
                    " | Email: " + email +
                    " | Role: " + getRole()
            );
        }
    }


    // =========================================================
    // 3. INHERITANCE
    // Student IS-A Person
    // =========================================================

    static class Student extends Person implements Comparable<Student> {

        private String course;
        private int year;

        Student(int id, String name, String email,
                String course, int year) {

            super(id, name, email);

            this.course = course;
            this.year = year;
        }

        public String getCourse() {
            return course;
        }

        public int getYear() {
            return year;
        }

        // Method Overriding
        @Override
        String getRole() {
            return "Student";
        }

        // Method Overriding
        @Override
        public void display() {
            System.out.println(
                    "Student ID: " + getId() +
                    " | Name: " + getName() +
                    " | Course: " + course +
                    " | Year: " + year
            );
        }

        // Comparable for sorting
        @Override
        public int compareTo(Student other) {
            return this.getName().compareToIgnoreCase(other.getName());
        }
    }


    // =========================================================
    // 4. ANOTHER CHILD CLASS - INHERITANCE + POLYMORPHISM
    // =========================================================

    static class Faculty extends Person {

        private String department;

        Faculty(int id, String name, String email,
                String department) {

            super(id, name, email);
            this.department = department;
        }

        @Override
        String getRole() {
            return "Faculty";
        }

        @Override
        public void display() {
            System.out.println(
                    "Faculty ID: " + getId() +
                    " | Name: " + getName() +
                    " | Department: " + department
            );
        }
    }


    // =========================================================
    // 5. RESOURCE CLASS
    // =========================================================

    static class Resource implements Displayable {

        private int resourceId;
        private String resourceName;
        private String type;
        private boolean available;

        Resource(int resourceId, String resourceName, String type) {

            this.resourceId = resourceId;
            this.resourceName = resourceName;
            this.type = type;

            // Initially resource is available
            this.available = true;
        }

        public int getResourceId() {
            return resourceId;
        }

        public String getResourceName() {
            return resourceName;
        }

        public String getType() {
            return type;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        @Override
        public void display() {

            System.out.println(
                    "Resource ID: " + resourceId +
                    " | Name: " + resourceName +
                    " | Type: " + type +
                    " | Status: " +
                    (available ? "Available" : "Booked")
            );
        }
    }


    // =========================================================
    // 6. BOOKING CLASS
    // =========================================================

    static class Booking {

        private static int bookingCounter = 1000;

        private int bookingId;
        private Student student;
        private Resource resource;
        private LocalDateTime bookingTime;

        Booking(Student student, Resource resource) {

            // static variable
            bookingId = ++bookingCounter;

            this.student = student;
            this.resource = resource;
            this.bookingTime = LocalDateTime.now();
        }

        public void display() {

            System.out.println(
                    "Booking ID: " + bookingId +
                    " | Student: " + student.getName() +
                    " | Resource: " + resource.getResourceName() +
                    " | Time: " + bookingTime
            );
        }

        public Resource getResource() {
            return resource;
        }

        public Student getStudent() {
            return student;
        }
    }


    // =========================================================
    // 7. CUSTOM EXCEPTION
    // =========================================================

    static class ResourceNotAvailableException extends Exception {

        ResourceNotAvailableException(String message) {
            super(message);
        }
    }


    // =========================================================
    // 8. MANAGER CLASS
    // =========================================================

    static class CampusManager {

        /*
         * Generic Collections
         */

        private ArrayList<Student> students =
                new ArrayList<>();

        private ArrayList<Resource> resources =
                new ArrayList<>();

        private ArrayList<Booking> bookings =
                new ArrayList<>();

        private HashMap<Integer, Student> studentMap =
                new HashMap<>();


        // =====================================================
        // STUDENT MANAGEMENT
        // =====================================================

        public void addStudent(Student student) {

            students.add(student);

            studentMap.put(
                    student.getId(),
                    student
            );

            System.out.println(
                    "Student added successfully."
            );
        }


        public void displayStudents() {

            if (students.isEmpty()) {
                System.out.println("No students found.");
                return;
            }

            System.out.println("\n----- STUDENT LIST -----");

            for (Student student : students) {

                // Runtime polymorphism
                student.display();
            }
        }


        public Student searchStudent(int id) {

            return studentMap.get(id);
        }


        // =====================================================
        // RESOURCE MANAGEMENT
        // =====================================================

        public void addResource(Resource resource) {

            resources.add(resource);

            System.out.println(
                    "Resource added successfully."
            );
        }


        public void displayResources() {

            if (resources.isEmpty()) {
                System.out.println("No resources found.");
                return;
            }

            System.out.println("\n----- RESOURCE LIST -----");

            for (Resource resource : resources) {

                resource.display();
            }
        }


        public Resource findResource(int id) {

            for (Resource resource : resources) {

                if (resource.getResourceId() == id) {
                    return resource;
                }
            }

            return null;
        }


        // =====================================================
        // BOOKING MODULE
        // =====================================================

        /*
         * synchronized method
         *
         * Important for multithreading.
         *
         * Only one thread can execute this method at a time.
         */

        public synchronized void bookResource(
                Student student,
                Resource resource)
                throws ResourceNotAvailableException {

            System.out.println(
                    Thread.currentThread().getName() +
                    " is trying to book " +
                    resource.getResourceName()
            );

            // Simulate processing time
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }


            if (!resource.isAvailable()) {

                throw new ResourceNotAvailableException(
                        "Resource " +
                        resource.getResourceName() +
                        " is already booked."
                );
            }


            resource.setAvailable(false);

            Booking booking =
                    new Booking(student, resource);

            bookings.add(booking);

            System.out.println(
                    "Booking successful for " +
                    student.getName()
            );
        }


        public void displayBookings() {

            if (bookings.isEmpty()) {

                System.out.println(
                        "No bookings found."
                );

                return;
            }

            System.out.println(
                    "\n----- BOOKING LIST -----"
            );

            for (Booking booking : bookings) {

                booking.display();
            }
        }


        // =====================================================
        // ANALYTICS
        // =====================================================

        public void showAnalytics() {

            System.out.println(
                    "\n========== CAMPUS ANALYTICS =========="
            );

            System.out.println(
                    "Total Students: " +
                    students.size()
            );

            System.out.println(
                    "Total Resources: " +
                    resources.size()
            );

            System.out.println(
                    "Total Bookings: " +
                    bookings.size()
            );


            int available = 0;
            int booked = 0;

            for (Resource r : resources) {

                if (r.isAvailable()) {
                    available++;
                }
                else {
                    booked++;
                }
            }

            System.out.println(
                    "Available Resources: " +
                    available
            );

            System.out.println(
                    "Booked Resources: " +
                    booked
            );
        }


        // =====================================================
        // SORT STUDENTS
        // =====================================================

        public void sortStudents() {

            Collections.sort(students);

            System.out.println(
                    "\nStudents sorted alphabetically:"
            );

            displayStudents();
        }
    }


    // =========================================================
    // 9. MULTITHREADING CLASS
    // =========================================================

    static class BookingTask implements Runnable {

        private CampusManager manager;
        private Student student;
        private Resource resource;

        BookingTask(
                CampusManager manager,
                Student student,
                Resource resource) {

            this.manager = manager;
            this.student = student;
            this.resource = resource;
        }


        @Override
        public void run() {

            try {

                manager.bookResource(
                        student,
                        resource
                );

            }
            catch (ResourceNotAvailableException e) {

                System.out.println(
                        Thread.currentThread().getName() +
                        " FAILED: " +
                        e.getMessage()
                );
            }
        }
    }


    // =========================================================
    // 10. METHOD OVERLOADING
    // =========================================================

    static class Notification {

        // Method 1
        public void send(String message) {

            System.out.println(
                    "Notification: " + message
            );
        }

        // Method 2 - overloaded
        public void send(String message, String user) {

            System.out.println(
                    "Notification for " +
                    user + ": " +
                    message
            );
        }
    }


    // =========================================================
    // 11. DEMONSTRATE POLYMORPHISM
    // =========================================================

    public static void demonstratePolymorphism() {

        System.out.println(
                "\n========== POLYMORPHISM DEMO =========="
        );

        Person person1 =
                new Student(
                        1,
                        "Akshit",
                        "akshit@vit.ac.in",
                        "CSE",
                        2
                );

        Person person2 =
                new Faculty(
                        101,
                        "Dr. Sharma",
                        "sharma@vit.ac.in",
                        "Computer Science"
                );


        // Runtime polymorphism
        person1.display();
        person2.display();
    }


    // =========================================================
    // 12. MULTITHREADING DEMONSTRATION
    // =========================================================

    public static void demonstrateMultithreading(
            CampusManager manager,
            Student s1,
            Student s2,
            Resource resource) {


        System.out.println(
                "\n========== MULTITHREADING DEMO =========="
        );


        /*
         * Two students attempt to book
         * the same resource simultaneously.
         */

        Thread thread1 =
                new Thread(
                        new BookingTask(
                                manager,
                                s1,
                                resource
                        ),
                        "Thread-Student-1"
                );


        Thread thread2 =
                new Thread(
                        new BookingTask(
                                manager,
                                s2,
                                resource
                        ),
                        "Thread-Student-2"
                );


        // Start both threads
        thread1.start();
        thread2.start();


        /*
         * join()
         *
         * Main thread waits until
         * both threads finish.
         */

        try {

            thread1.join();
            thread2.join();

        }
        catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                    "Main thread interrupted."
            );
        }


        System.out.println(
                "Both booking threads completed."
        );
    }


    // =========================================================
    // 13. SAMPLE DATA
    // =========================================================

    public static void loadSampleData(
            CampusManager manager) {


        Student s1 =
                new Student(
                        1,
                        "Akshit Singh",
                        "akshit@vit.ac.in",
                        "CSE",
                        2
                );


        Student s2 =
                new Student(
                        2,
                        "Rahul Raj",
                        "rahul@vit.ac.in",
                        "CSE",
                        2
                );


        Student s3 =
                new Student(
                        3,
                        "Aman Kumar",
                        "aman@vit.ac.in",
                        "ECE",
                        2
                );


        manager.addStudent(s1);
        manager.addStudent(s2);
        manager.addStudent(s3);


        Resource r1 =
                new Resource(
                        101,
                        "Computer Lab 1",
                        "LAB"
                );


        Resource r2 =
                new Resource(
                        102,
                        "Seminar Hall",
                        "HALL"
                );


        Resource r3 =
                new Resource(
                        103,
                        "Projector",
                        "EQUIPMENT"
                );


        manager.addResource(r1);
        manager.addResource(r2);
        manager.addResource(r3);
    }


    // =========================================================
    // 14. MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        Scanner scanner =
                new Scanner(System.in);


        CampusManager manager =
                new CampusManager();


        /*
         * final variable
         *
         * Value cannot be changed.
         */

        final String PROJECT_NAME =
                "SMART CAMPUS RESOURCE MANAGEMENT SYSTEM";


        System.out.println(
                "================================================"
        );

        System.out.println(PROJECT_NAME);

        System.out.println(
                "================================================"
        );


        // Load sample data
        loadSampleData(manager);


        // Demonstrate OOP
        demonstratePolymorphism();


        /*
         * Notification object
         */
        Notification notification =
                new Notification();

        notification.send(
                "Welcome to Smart Campus System!"
        );

        notification.send(
                "Your account is active.",
                "Akshit"
        );


        // =====================================================
        // MENU
        // =====================================================

        int choice;

        do {

            System.out.println(
                    "\n\n========== MAIN MENU =========="
            );

            System.out.println(
                    "1. Add Student"
            );

            System.out.println(
                    "2. View Students"
            );

            System.out.println(
                    "3. Search Student"
            );

            System.out.println(
                    "4. Add Resource"
            );

            System.out.println(
                    "5. View Resources"
            );

            System.out.println(
                    "6. Book Resource"
            );

            System.out.println(
                    "7. View Bookings"
            );

            System.out.println(
                    "8. Sort Students"
            );

            System.out.println(
                    "9. Analytics"
            );

            System.out.println(
                    "10. Multithreading Demo"
            );

            System.out.println(
                    "11. Exit"
            );

            System.out.print(
                    "Enter your choice: "
            );


            try {

                choice =
                        scanner.nextInt();

                scanner.nextLine();

            }
            catch (InputMismatchException e) {

                System.out.println(
                        "Invalid input! Enter a number."
                );

                scanner.nextLine();

                choice = 0;
            }


            switch (choice) {


                // =================================================
                // ADD STUDENT
                // =================================================

                case 1:

                    try {

                        System.out.print(
                                "Enter Student ID: "
                        );

                        int id =
                                scanner.nextInt();

                        scanner.nextLine();


                        System.out.print(
                                "Enter Name: "
                        );

                        String name =
                                scanner.nextLine();


                        System.out.print(
                                "Enter Email: "
                        );

                        String email =
                                scanner.nextLine();


                        System.out.print(
                                "Enter Course: "
                        );

                        String course =
                                scanner.nextLine();


                        System.out.print(
                                "Enter Year: "
                        );

                        int year =
                                scanner.nextInt();


                        Student student =
                                new Student(
                                        id,
                                        name,
                                        email,
                                        course,
                                        year
                                );


                        manager.addStudent(student);

                    }
                    catch (Exception e) {

                        System.out.println(
                                "Error: " +
                                e.getMessage()
                        );

                        scanner.nextLine();
                    }

                    break;


                // =================================================
                // VIEW STUDENTS
                // =================================================

                case 2:

                    manager.displayStudents();

                    break;


                // =================================================
                // SEARCH STUDENT
                // =================================================

                case 3:

                    System.out.print(
                            "Enter Student ID: "
                    );

                    int searchId =
                            scanner.nextInt();


                    Student found =
                            manager.searchStudent(
                                    searchId
                            );


                    if (found != null) {

                        System.out.println(
                                "Student Found:"
                        );

                        found.display();

                    }
                    else {

                        System.out.println(
                                "Student not found."
                        );
                    }

                    break;


                // =================================================
                // ADD RESOURCE
                // =================================================

                case 4:

                    System.out.print(
                            "Enter Resource ID: "
                    );

                    int resourceId =
                            scanner.nextInt();

                    scanner.nextLine();


                    System.out.print(
                            "Enter Resource Name: "
                    );

                    String resourceName =
                            scanner.nextLine();


                    System.out.print(
                            "Enter Resource Type: "
                    );

                    String type =
                            scanner.nextLine();


                    Resource resource =
                            new Resource(
                                    resourceId,
                                    resourceName,
                                    type
                            );


                    manager.addResource(
                            resource
                    );

                    break;


                // =================================================
                // VIEW RESOURCES
                // =================================================

                case 5:

                    manager.displayResources();

                    break;


                // =================================================
                // BOOK RESOURCE
                // =================================================

                case 6:

                    System.out.print(
                            "Enter Student ID: "
                    );

                    int sid =
                            scanner.nextInt();


                    Student student =
                            manager.searchStudent(
                                    sid
                            );


                    if (student == null) {

                        System.out.println(
                                "Student not found."
                        );

                        break;
                    }


                    System.out.print(
                            "Enter Resource ID: "
                    );

                    int rid =
                            scanner.nextInt();


                    Resource res =
                            manager.findResource(
                                    rid
                            );


                    if (res == null) {

                        System.out.println(
                                "Resource not found."
                        );

                        break;
                    }


                    try {

                        manager.bookResource(
                                student,
                                res
                        );

                    }
                    catch (
                            ResourceNotAvailableException e) {

                        System.out.println(
                                "Booking Failed: " +
                                e.getMessage()
                        );
                    }

                    break;


                // =================================================
                // VIEW BOOKINGS
                // =================================================

                case 7:

                    manager.displayBookings();

                    break;


                // =================================================
                // SORT STUDENTS
                // =================================================

                case 8:

                    manager.sortStudents();

                    break;


                // =================================================
                // ANALYTICS
                // =================================================

                case 9:

                    manager.showAnalytics();

                    break;


                // =================================================
                // MULTITHREADING
                // =================================================

                case 10:

                    Student s1 =
                            manager.searchStudent(1);

                    Student s2 =
                            manager.searchStudent(2);

                    Resource multithreadResource =
                            manager.findResource(102);


                    if (s1 != null &&
                        s2 != null &&
                        multithreadResource != null) {

                        /*
                         * If resource 102 was already booked,
                         * create a new resource for demonstration.
                         */

                        if (!multithreadResource.isAvailable()) {

                            multithreadResource =
                                    new Resource(
                                            999,
                                            "Multithreading Lab",
                                            "LAB"
                                    );

                            manager.addResource(
                                    multithreadResource
                            );
                        }


                        demonstrateMultithreading(
                                manager,
                                s1,
                                s2,
                                multithreadResource
                        );

                    }

                    break;


                // =================================================
                // EXIT
                // =================================================

                case 11:

                    System.out.println(
                            "\nThank you for using " +
                            PROJECT_NAME
                    );

                    break;


                default:

                    System.out.println(
                            "Invalid choice!"
                    );
            }

        }
        while (choice != 11);


        scanner.close();
    }
}