package paradigm.functional;

/**
 * 不可变学生记录，保存学生ID和姓名。
 *
 * @param sid 学生ID
 * @param name 学生姓名
 */
public record Student(String sid, String name) {

    /**
     * 紧凑构造方法，检查学生信息的合法性。
     */
    public Student {
        if (sid == null || sid.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be blank");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student name cannot be blank");
        }
    }
}
