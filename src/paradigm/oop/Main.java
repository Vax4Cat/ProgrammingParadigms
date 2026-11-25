package paradigm.oop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * 面向对象编程范式的主程序。
 */
public class Main {

    /**
     * 程序主入口，初始化对象并执行五项业务功能。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        Student s1 = new Student("S001", "Alice");
        Student s2 = new Student("S002", "Bob");
        Student s3 = new Student("S003", "Charlie");
        Student s4 = new Student("S004", "David");
        Student s5 = new Student("S005", "Eve");
        List<Student> students = List.of(s1, s2, s3, s4, s5);
        Course c1 = new Course("C001", "Software Construction", 3);
        Course c2 = new Course("C002", "Algorithm", 4);
        List<Course> courses = List.of(c1, c2);
        List<GradeRecord> records = List.of(
                new GradeRecord(s1, c1, 88),
                new GradeRecord(s1, c2, 76),
                new GradeRecord(s2, c1, 55),
                new GradeRecord(s2, c2, 62),
                new GradeRecord(s3, c1, 92),
                new GradeRecord(s3, c2, 81),
                new GradeRecord(s4, c1, 48),
                new GradeRecord(s4, c2, 73),
                new GradeRecord(s5, c1, 66),
                new GradeRecord(s5, c2, 59)
        );
        GradeRankService rankService = new GradeRankService();

        // Side effect: 向控制台输出程序运行信息
        System.out.println("=== Task B: Object-Oriented Programming ===");
        System.out.println("Students: " + students.size());
        System.out.println("Courses: " + courses.size());
        System.out.println("Grade Records: " + records.size());

        // 执行五项业务功能
        showGradeRanks(records, rankService);
        showFailedRecords(records, rankService);
        showCourseWeightedAverages(courses, records, rankService);
        showTopStudent(students, records);
        showSortedPassingRecords(records, rankService);
    }

    /**
     * Task 1: 将每条成绩记录转换为等级。
     *
     * @param records 成绩记录列表
     * @param rankService 成绩等级服务对象
     */
    public static void showGradeRanks(
            List<GradeRecord> records,
            GradeRankService rankService) {

        System.out.println("\n=== Task 1: Grade Ranks ==="); // Side effect

        for (GradeRecord record : records) {
            String rank = rankService.getGradeRank(record.getRawScore());

            System.out.println(
                    record.getStudent().getSid() + " | " +
                            record.getCourse().getCid() + " | " +
                            record.getRawScore() + " | " +
                            rank
            ); // Side effect
        }
    }

    /**
     * Task 2: 筛选并输出所有不及格成绩。
     *
     * @param records 成绩记录列表
     * @param rankService 成绩等级服务对象
     */
    public static void showFailedRecords(
            List<GradeRecord> records,
            GradeRankService rankService) {

        System.out.println("\n=== Task 2: Failed Records ==="); // Side effect

        for (GradeRecord record : records) {
            String rank = rankService.getGradeRank(record.getRawScore());

            if (rank.equals("D")) {
                System.out.println(
                        record.getStudent().getSid() + " | " +
                                record.getCourse().getCid() + " | " +
                                record.getRawScore() + " | " +
                                rank
                ); // Side effect
            }
        }
    }

    /**
     * Task 3: 按课程计算及格成绩的加权平均分。
     *
     * @param courses 课程列表
     * @param records 成绩记录列表
     * @param rankService 成绩等级服务对象
     */
    public static void showCourseWeightedAverages(
            List<Course> courses,
            List<GradeRecord> records,
            GradeRankService rankService) {

        System.out.println("\n=== Task 3: Course Weighted Averages ==="); // Side effect

        for (Course course : courses) {
            double weightedSum = 0;
            int totalWeight = 0;

            for (GradeRecord record : records) {
                String rank = rankService.getGradeRank(record.getRawScore());

                if (record.getCourse().getCid().equals(course.getCid())
                        && !rank.equals("D")) {

                    weightedSum += record.getRawScore() * course.getCredit(); // Mutation
                    totalWeight += course.getCredit(); // Mutation
                }
            }

            if (totalWeight > 0) {
                double average = weightedSum / totalWeight;

                System.out.printf(
                        Locale.ROOT,
                        "%s | Weighted Average: %.2f%n",
                        course.getName(), average
                ); // Side effect
            }
        }
    }

    /**
     * Task 4: 找出所有课程总分最高的学生。
     *
     * @param students 学生列表
     * @param records 成绩记录列表
     */
    public static void showTopStudent(
            List<Student> students,
            List<GradeRecord> records) {

        System.out.println("\n=== Task 4: Top Student ==="); // Side effect

        int maxTotal = -1;
        String topStudent = "";

        for (Student student : students) {
            int total = 0;

            for (GradeRecord record : records) {
                if (record.getStudent().getSid().equals(student.getSid())) {
                    total += record.getRawScore(); // Mutation
                }
            }

            if (total > maxTotal) {
                maxTotal = total; // Mutation
                topStudent = student.getName(); // Mutation
            }
        }

        System.out.println(
                topStudent + " | Total Score: " + maxTotal
        ); // Side effect
    }

    /**
     * Task 5: 将所有及格成绩按原始分数降序排序并输出。
     *
     * @param records 成绩记录列表
     * @param rankService 成绩等级服务对象
     */
    public static void showSortedPassingRecords(
            List<GradeRecord> records,
            GradeRankService rankService) {
        System.out.println("\n=== Task 5: Sorted Passing Records ==="); // Side effect
        List<GradeRecord> passingRecords = new ArrayList<>();

        for (GradeRecord record : records) {
            if (!rankService.getGradeRank(record.getRawScore()).equals("D")) {
                passingRecords.add(record); // Mutation
            }
        }

        passingRecords.sort(Comparator.comparingInt(GradeRecord::getRawScore).reversed()); // Mutation

        for (GradeRecord record : passingRecords) {
            System.out.println(
                    record.getStudent().getName() + " | " +
                            record.getCourse().getName() + " | " +
                            record.getRawScore() + " | " +
                            rankService.getGradeRank(record.getRawScore())
            ); // Side effect
        }
    }
}