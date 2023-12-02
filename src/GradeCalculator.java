import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
*  This program is a simple grade calculator I made to know how badly I might fail linear algebra
*  In the future, I'd like to add the feature to edit existing grades with new information
*  which would require me to store the weight and number of assignments so I can recalculate the average and weight it.
* */
public class GradeCalculator {
    public static void main(String args[]) throws IOException {
        GradeCalculator g = new GradeCalculator();
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Welcome to the Grade Calculator. How can I help you?\n1. Find new grade\n2. View existing grades\n3. Return to menu\n0. Quit");
            int response = sc.nextInt();

            switch (response) {
                case 1:
                    System.out.println("You have chosen to find a new grade.");
                    g.findNewGrade(sc);
                    continue;
                case 2:
                    System.out.println("You have chosen to view existing grades. Enter class name: ");
                    String fileName = sc.next() + ".txt";
                    System.out.println("\n");
                    g.viewExistingGrade(fileName);
                    continue;
                case 3:
                    continue;

                case 0:
                    return;
            }

        }
    }
    void findNewGrade(Scanner sc) throws IOException {
        class AssignmentType {
            public String assignmentName;
            public float weight;
            public ArrayList<Float> grades = new ArrayList<Float>();
        }
        System.out.println("What is the name of your class?");
        String className = sc.next();
        File classFile = new File(className + ".txt");
        FileWriter writer = new FileWriter(classFile, true);
        float totalGrade = 0;
        classFile.createNewFile();
        System.out.println("File created: " + classFile.getName());
            // begin with calculating the grade. each one should have a name, an array of grades, and a weight percentage.
        while (true) {
            AssignmentType currentAssignment = new AssignmentType();
            System.out.println("Type 'quit' to finish calculating grades.\n");
            System.out.println("Now beginning with calculating grade.\nEnter the name of this grade type (i.e. Project, Homework, etc): ");
            currentAssignment.assignmentName = sc.next();
            if (currentAssignment.assignmentName.equals("quit") || currentAssignment.assignmentName.equals("Quit")) {
                break;
            }
            System.out.println("Enter the total weight (in decimal) of " + currentAssignment.assignmentName);
            currentAssignment.weight = sc.nextFloat();
            System.out.println("Enter individual grade for each " + currentAssignment.assignmentName + " and hit enter.\nType negative value to finish inputting.");
            boolean inputtingGrades = true;
            while(inputtingGrades) {
                float grade = sc.nextFloat();
                if (grade < 0) {
                    inputtingGrades = false;
                    break;
                }
                currentAssignment.grades.add(grade);
            }
            float weightedGrade = 0;
            for (int i = 0; i < currentAssignment.grades.size(); i++) {
                weightedGrade += currentAssignment.grades.get(i);
            }
            weightedGrade = weightedGrade / currentAssignment.grades.size();
            weightedGrade = weightedGrade * currentAssignment.weight;
            totalGrade += weightedGrade;
            writer.write(currentAssignment.assignmentName + " weighted grade: " + weightedGrade + "\n");
        }
        writer.write("Total grade: " + totalGrade + "\n");
        writer.close();
    }

    void viewExistingGrade(String fileName) throws IOException {
        System.out.println("Reading " + fileName + "...\n");
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        System.out.println("-------------------------------");
        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            System.out.println(data);
        }
        System.out.println("-------------------------------");
    }
}
