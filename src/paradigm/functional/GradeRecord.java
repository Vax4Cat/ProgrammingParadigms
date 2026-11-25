package paradigm.functional;

/**
 * 不可变成绩记录，关联学生、课程和原始成绩。
 *
 * @param student 学生记录
 * @param course 课程记录
 * @param rawScore 原始成绩
 */
public record GradeRecord(Student student, Course course, int rawScore) {

    /**
     * 紧凑构造方法，检查成绩记录的合法性。
     */
    public GradeRecord {
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
}