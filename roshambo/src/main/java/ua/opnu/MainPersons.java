package ua.opnu;

public class MainPersons {
    public static void main(String[] args) {
        Person p1 = new Person("Петренко", "Іван", 40);
        Person p2 = new Student("Шевченко", "Ольга", 19, "КН-22", "AB123456");
        Person p3 = new Lecturer("Коваленко", "Тарас", 55, "ПЗАС", 42000.0);
        Student s1 = new Student("Сидоренко", "Марія", 20, "КН-21", "CD987654");

        Person[] people = new Person[] { p1, p2, p3, s1 };
        for (Person p : people) System.out.println(p);
    }
}
