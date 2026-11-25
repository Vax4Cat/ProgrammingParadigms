package paradigm.functional;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 函数式编程范式的主程序。
 * 使用不可变数据、纯函数和Stream API完成业务计算。
 */
public class Main {

    /**
     * 程序主入口。
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

        // 执行五项业务计算，无外部副作用
        List<String> gradeRanks = getGradeRanks(records);
        List<String> failedRecords = getFailedRecords(records);
        List<String> courseAverages = getCourseWeightedAverages(records);
        String topStudent = getTopStudent(students, records);
        List<String> sortedRecords = getSortedPassingRecords(records);

        // Side effect: 所有控制台输出集中在最终输出阶段
        System.out.println("=== Task C: Functional Programming ===");
        System.out.println("Students: " + students.size());
        System.out.println("Courses: " + courses.size());
        System.out.println("Grade Records: " + records.size());
        System.out.println("\n=== Task 1: Grade Ranks ===");
        gradeRanks.forEach(System.out::println);
        System.out.println("\n=== Task 2: Failed Records ===");
        failedRecords.forEach(System.out::println);
        System.out.println("\n=== Task 3: Course Weighted Averages ===");
        courseAverages.forEach(System.out::println);
        System.out.println("\n=== Task 4: Top Student ===");
        System.out.println(topStudent);
        System.out.println("\n=== Task 5: Sorted Passing Records ===");
        sortedRecords.forEach(System.out::println);
    }

    /**
     * Task 1: 将每条成绩记录转换为等级。
     *
     * @param records 成绩记录列表
     * @return 包含成绩等级的格式化字符串列表
     */
    public static List<String> getGradeRanks(List<GradeRecord> records) {
        // Pure computation: 不修改原始数据
        return records.stream()
                .map(record ->
                        record.student().sid() + " | " +
                                record.course().cid() + " | " +
                                record.rawScore() + " | " +
                                GradeRank.getGradeRank(record.rawScore()))
                .toList();
    }

    /**
     * Task 2: 筛选所有不及格成绩。
     *
     * @param records 成绩记录列表
     * @return 不及格成绩的格式化字符串列表
     */
    public static List<String> getFailedRecords(List<GradeRecord> records) {

        // 按是否及格进行分区
        Map<Boolean, List<GradeRecord>> partitioned = records.stream()
                .collect(Collectors.partitioningBy(
                        record -> !GradeRank.getGradeRank(
                                record.rawScore()).equals("D")
                ));

        // false对应不及格记录
        return partitioned.get(false).stream()
                .map(record ->
                        record.student().sid() + " | " +
                                record.course().cid() + " | " +
                                record.rawScore() + " | " +
                                GradeRank.getGradeRank(record.rawScore()))
                .toList();
    }

    /**
     * Task 3: 按课程计算及格成绩的学分加权平均分。
     *
     * @param records 成绩记录列表
     * @return 各课程加权平均分的格式化字符串列表
     */
    public static List<String> getCourseWeightedAverages(
            List<GradeRecord> records) {
        // 筛选及格成绩，然后按课程分组
        Map<Course, List<GradeRecord>> grouped = records.stream()
                .filter(record -> !GradeRank.getGradeRank(
                        record.rawScore()).equals("D"))
                .collect(Collectors.groupingBy(
                        GradeRecord::course,
                        () -> new TreeMap<>(
                                Comparator.comparing(Course::cid)),
                        Collectors.toList()
                ));
        return grouped.entrySet().stream()
                .map(entry -> {
                    Course course = entry.getKey();
                    List<GradeRecord> courseRecords = entry.getValue();

                    // Pure computation: 使用reduce代替可变累加变量
                    double weightedSum = courseRecords.stream()
                            .map(record -> (double) record.rawScore()
                                    * record.course().credit())
                            .reduce(0.0, Double::sum);

                    int totalWeight = courseRecords.stream()
                            .map(record -> record.course().credit())
                            .reduce(0, Integer::sum);

                    double average = weightedSum / totalWeight;

                    return String.format(
                            Locale.ROOT,
                            "%s | Weighted Average: %.2f",
                            course.name(),
                            average
                    );
                })
                .toList();
    }

    /**
     * Task 4: 找出所有课程总分最高的学生。
     *
     * @param students 学生列表
     * @param records 成绩记录列表
     * @return 总分最高学生的格式化字符串
     */
    public static String getTopStudent(
            List<Student> students,
            List<GradeRecord> records) {
        // 按学生ID分组，并计算每个学生的成绩总和
        Map<String, Integer> studentTotals = records.stream()
                .collect(Collectors.groupingBy(
                        record -> record.student().sid(),
                        Collectors.reducing(
                                0,
                                GradeRecord::rawScore,
                                Integer::sum
                        )
                ));
        return students.stream()
                .max(Comparator.comparingInt(
                        student -> studentTotals.getOrDefault(
                                student.sid(), 0)))
                .map(student ->
                        student.name() + " | Total Score: " +
                                studentTotals.getOrDefault(student.sid(), 0))
                .orElse("");
    }

    /**
     * Task 5: 将所有及格成绩按原始分数降序排序。
     *
     * @param records 成绩记录列表
     * @return 排序后的及格成绩格式化字符串列表
     */
    public static List<String> getSortedPassingRecords(
            List<GradeRecord> records) {

        // Pure computation: 筛选、排序、转换，不修改原始列表
        return records.stream()
                .filter(record -> !GradeRank.getGradeRank(
                        record.rawScore()).equals("D"))
                .sorted(Comparator.comparingInt(
                        GradeRecord::rawScore).reversed())
                .map(record ->
                        record.student().name() + " | " +
                                record.course().name() + " | " +
                                record.rawScore() + " | " +
                                GradeRank.getGradeRank(record.rawScore()))
                .toList();
    }
}