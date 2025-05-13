import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class Employee {
    int id;
    String name;
    String dept;
    double salary;

    Employee(int id, String name, String dept, double salary) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.salary = salary;
    }

    public String toString() {
        return id + " - " + name + " (" + dept + ") : ₹" + salary;
    }

    public double getSalary() { return salary; }
    public String getDept() { return dept; }
    public String getName() { return name; }
}

interface Greeting {
    default void sayHello() {
        System.out.println("Hello from default method!");
    }

    void sayBye(); // abstract method
}

class GreetingImpl implements Greeting {
    public void sayBye() {
        System.out.println("Bye from overridden method!");
    }
}

public class Main {
    public static void main(String[] args) {

        // ✅ Lambda Expression
        Runnable task = () -> System.out.println("Lambda says Hello!");
        task.run();

        // ✅ Functional Interface
        Predicate<String> isLong = s -> s.length() > 4;
        System.out.println("Is 'Rohit' long? " + isLong.test("Rohit"));

        // ✅ Optional
        Optional<String> maybeName = Optional.of("Anurag");
        maybeName.ifPresent(n -> System.out.println("Name in Optional: " + n));

        // ✅ Method Reference
        List<String> sample = Arrays.asList("one", "two", "three");
        sample.forEach(System.out::println);

        // ✅ Default Method in Interface
        Greeting g = new GreetingImpl();
        g.sayHello();
        g.sayBye();

        // ✅ Date and Time API
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        System.out.println("Today: " + date + ", Time: " + time);


        List<Employee> employees = Arrays.asList(
                new Employee(1, "Rohit", "IT", 50000),
                new Employee(2, "Ankit", "HR", 40000),
                new Employee(3, "Amit", "IT", 60000),
                new Employee(4, "Sneha", "Finance", 45000),
                new Employee(5, "Nina", "IT", 75000),
                new Employee(6, "John", "HR", 40000)
        );

        // 1. Filter
        System.out.println("\nHigh earners:");
        employees.stream()
                .filter(e -> e.getSalary() > 45000)
                .forEach(System.out::println);

        // 2. Map
        System.out.println("\nEmployee Names:");
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

        // 3. Sorted
        System.out.println("\nSorted by salary:");
        employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .forEach(System.out::println);

        // 4. Limit & Skip
        System.out.println("\nTop 2 Salaries:");
        employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(2)
                .forEach(System.out::println);

        System.out.println("\nSkip Top 2 Salaries:");
        employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .skip(2)
                .forEach(System.out::println);

        // 5. Collect
        System.out.println("\nList of IT Employees:");
        List<Employee> itEmployees = employees.stream()
                .filter(e -> e.getDept().equals("IT"))
                .collect(Collectors.toList());
        itEmployees.forEach(System.out::println);

        // 6. Grouping By
        System.out.println("\nGroup by Department:");
        Map<String, List<Employee>> groupByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDept));
        groupByDept.forEach((dept, list) -> System.out.println(dept + ": " + list));

        // 7. Average Salary per Department
        System.out.println("\nAverage Salary per Dept:");
        Map<String, Double> avgSalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDept,
                        Collectors.averagingDouble(Employee::getSalary)));
        avgSalary.forEach((dept, avg) -> System.out.println(dept + ": ₹" + avg));

        // 8. Matching
        boolean allHigh = employees.stream().allMatch(e -> e.getSalary() > 30000);
        boolean anyHigh = employees.stream().anyMatch(e -> e.getSalary() > 70000);
        System.out.println("\nAll earn >30k? " + allHigh);
        System.out.println("Any earn >70k? " + anyHigh);

        // 9. Min / Max
        Employee maxSal = employees.stream()
                .max(Comparator.comparing(Employee::getSalary)).get();
        Employee minSal = employees.stream()
                .min(Comparator.comparing(Employee::getSalary)).get();
        System.out.println("\nHighest Paid: " + maxSal);
        System.out.println("Lowest Paid: " + minSal);

        // 10. Reduce - Sum of Salaries
        double totalSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(0.0, Double::sum);
        System.out.println("\nTotal Salary: ₹" + totalSalary);
    }
}

