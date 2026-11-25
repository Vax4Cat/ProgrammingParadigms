package paradigm.procedural;

import java.util.Locale;

/**
 * 过程式编程范式的主程序。
 */
public class Main {
    /**
     * 程序主入口，初始化数据并执行五项业务功能
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String[] studentIds = {"S001", "S002", "S003", "S004", "S005"};
        String[] studentNames = {"Alice", "Bob", "Charlie", "David", "Eve"};
        String[] courseIds = {"C001", "C002"};
        String[] courseNames = {"Software Construction", "Algorithm"};
        int[] courseCredits = {3, 4};
        String[] recordSid = {
                "S001", "S001",
                "S002", "S002",
                "S003", "S003",
                "S004", "S004",
                "S005", "S005"
        };
        String[] recordCid = {
                "C001", "C002",
                "C001", "C002",
                "C001", "C002",
                "C001", "C002",
                "C001", "C002"
        };
        int[] recordScores = {
                88, 76,
                55, 62,
                92, 81,
                48, 73,
                66, 59
        };
        validateIds(studentIds, courseIds);
        validateIds(recordSid, recordCid);
        // Side effect: 向控制台输出程序运行信息
        System.out.println("=== Task A: Procedural Programming ===");
        System.out.println("Students: " + studentIds.length);
        System.out.println("Courses: " + courseIds.length);
        System.out.println("Grade Records: " + recordScores.length);

        System.out.println("\n=== Task 1: Grade Ranks ===");
        for (int i = 0; i < recordScores.length; i++) {
            String rank = getGradeRank(recordScores[i]);
            System.out.println(
                    recordSid[i] + " | " +
                            recordCid[i] + " | " +
                            recordScores[i] + " | " +
                            rank);
        }
        showFailedRecords(recordSid, recordCid, recordScores);
        showCourseWeightedAverages(
                courseIds,
                courseNames,
                courseCredits,
                recordCid,
                recordScores
        );
        showTopStudent(
                studentIds,
                studentNames,
                recordSid,
                recordScores
        );
        showSortedPassingRecords(
                studentIds,
                studentNames,
                courseIds,
                courseNames,
                recordSid,
                recordCid,
                recordScores
        );
    }

    /**
     * 将每条成绩记录转换为等级
     * @param score 原始成绩（0～100）
     * @return 对应的成绩等级（A/B/C/D）
     */
    public static String getGradeRank(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }

        if (score >= 85) {
            return "A";
        } else if (score >= 70) {
            return "B";
        } else if (score >= 60) {
            return "C";
        } else {
            return "D";
        }
    }

    /**
     * 筛选并输出所有不及格成绩
     * @param recordSid 成绩记录中的学生ID数组
     * @param recordCid 成绩记录中的课程ID数组
     * @param recordScores 原始成绩数组
     */
    public static void showFailedRecords(
            String[] recordSid,
            String[] recordCid,
            int[] recordScores) {
        System.out.println("\n=== Task 2: Failed Records ==="); // Side effect: 控制台输出
        for (int i = 0; i < recordScores.length; i++) {
            String rank = getGradeRank(recordScores[i]);
            if (rank.equals("D")) {
                System.out.println(
                        recordSid[i] + " | " +
                                recordCid[i] + " | " +
                                recordScores[i] + " | " +
                                rank); // Side effect: 控制台输出
            }
        }
    }

    /**
     * 按课程计算及格成绩的加权平均分
     * @param courseIds 课程ID数组
     * @param courseNames 课程名称数组
     * @param courseCredits 课程学分数组
     * @param recordCid 成绩记录中的课程ID数组
     * @param recordScores 原始成绩数组
     */
    public static void showCourseWeightedAverages(
            String[] courseIds,
            String[] courseNames,
            int[] courseCredits,
            String[] recordCid,
            int[] recordScores) {
        System.out.println("\n=== Task 3: Course Weighted Averages ==="); // Side effect

        for (int i = 0; i < courseIds.length; i++) {
            if (courseCredits[i] <= 0) {
                throw new IllegalArgumentException("Credit must be positive");
            }

            double weightedSum = 0;
            int totalWeight = 0;

            for (int j = 0; j < recordScores.length; j++) {
                String rank = getGradeRank(recordScores[j]);

                if (recordCid[j].equals(courseIds[i]) && !rank.equals("D")) {
                    weightedSum += recordScores[j] * courseCredits[i]; // Mutation
                    totalWeight += courseCredits[i]; // Mutation
                }
            }

            if (totalWeight > 0) {
                double average = weightedSum / totalWeight;
                System.out.printf(
                        Locale.ROOT,
                        "%s | Weighted Average: %.2f%n",
                        courseNames[i], average
                ); // Side effect
            }
        }
    }

    /**
     * 找出所有课程总分最高的学生
     * @param studentIds 学生ID数组
     * @param studentNames 学生姓名数组
     * @param recordSid 成绩记录中的学生ID数组
     * @param recordScores 原始成绩数组
     */
    public static void showTopStudent(
            String[] studentIds,
            String[] studentNames,
            String[] recordSid,
            int[] recordScores) {
        System.out.println("\n=== Task 4: Top Student ==="); // Side effect
        int maxTotal = -1;
        String topStudent = "";

        for (int i = 0; i < studentIds.length; i++) {
            int total = 0;
            for (int j = 0; j < recordScores.length; j++) {
                if (recordSid[j].equals(studentIds[i])) {
                    total += recordScores[j]; // Mutation
                }
            }

            if (total > maxTotal) {
                maxTotal = total; // Mutation
                topStudent = studentNames[i]; // Mutation
            }
        }

        System.out.println(topStudent + " | Total Score: " + maxTotal); // Side effect
    }

    /**
     * 将及格成绩按原始分数降序排序并输出
     * @param studentIds 学生ID数组
     * @param studentNames 学生姓名数组
     * @param courseIds 课程ID数组
     * @param courseNames 课程名称数组
     * @param recordSid 成绩记录中的学生ID数组
     * @param recordCid 成绩记录中的课程ID数组
     * @param recordScores 原始成绩数组
     */
    public static void showSortedPassingRecords(
            String[] studentIds,
            String[] studentNames,
            String[] courseIds,
            String[] courseNames,
            String[] recordSid,
            String[] recordCid,
            int[] recordScores) {
        System.out.println("\n=== Task 5: Sorted Passing Records ==="); // Side effect
        int[] passingIndexes = new int[recordScores.length];
        int count = 0;

        for (int i = 0; i < recordScores.length; i++) {
            if (!getGradeRank(recordScores[i]).equals("D")) {
                passingIndexes[count] = i; // Mutation
                count++; // Mutation
            }
        }

        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - 1 - i; j++) {
                if (recordScores[passingIndexes[j]]
                        < recordScores[passingIndexes[j + 1]]) {
                    int temp = passingIndexes[j];
                    passingIndexes[j] = passingIndexes[j + 1]; // Mutation
                    passingIndexes[j + 1] = temp; // Mutation
                }
            }
        }

        for (int i = 0; i < count; i++) {
            int index = passingIndexes[i];
            String studentName = "";
            String courseName = "";

            for (int j = 0; j < studentIds.length; j++) {
                if (studentIds[j].equals(recordSid[index])) {
                    studentName = studentNames[j];
                    break;
                }
            }

            for (int j = 0; j < courseIds.length; j++) {
                if (courseIds[j].equals(recordCid[index])) {
                    courseName = courseNames[j];
                    break;
                }
            }
            System.out.println(
                    studentName + " | " +
                            courseName + " | " +
                            recordScores[index] + " | " +
                            getGradeRank(recordScores[index])
            ); // Side effect
        }
    }

    /**
     * 检查学生ID和课程ID是否合法。
     *
     * @param studentIds 学生ID数组
     * @param courseIds 课程ID数组
     */
    public static void validateIds(
            String[] studentIds,
            String[] courseIds) {
        for (String id : studentIds) {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException(
                        "Student ID cannot be blank");
            }
        }

        for (String id : courseIds) {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException(
                        "Course ID cannot be blank");
            }
        }
    }
}
