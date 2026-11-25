package paradigm.oop;

/**
 * 成绩记录ADT，表示某个学生在某门课程中的成绩。
 */
public final class GradeRecord {
    private final Student student;
    private final Course course;
    private final int rawScore;

    /*
     * Abstraction Function (AF):
     * AF(student, course, rawScore) =
     * 学生student在课程course中取得rawScore分的成绩记录。
     *
     * Representation Invariant (RI):
     * student不能为null。
     * course不能为null。
     * rawScore必须在0～100之间。
     *
     * Safety from Rep Exposure:
     * 所有字段使用private final修饰。
     * Student和Course均为不可变对象。
     * 不提供setter方法，防止外部修改内部状态。
     */

    /**
     * 创建成绩记录对象。
     *
     * @param student 学生对象
     * @param course 课程对象
     * @param rawScore 原始成绩
     */
    public GradeRecord(Student student, Course course, int rawScore) {
        this.student = student;
        this.course = course;
        this.rawScore = rawScore;
        checkRep();
    }

    /**
     * 检查表示不变式。
     */
    private void checkRep() {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        if (rawScore < 0 || rawScore > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
    }

    /**
     * 获取学生对象。
     *
     * @return 学生对象
     */
    public Student getStudent() {
        return student;
    }

    /**
     * 获取课程对象。
     *
     * @return 课程对象
     */
    public Course getCourse() {
        return course;
    }

    /**
     * 获取原始成绩。
     *
     * @return 原始成绩
     */
    public int getRawScore() {
        return rawScore;
    }
}
