package paradigm.functional;

/**
 * 不可变课程记录，保存课程ID、名称和学分。
 *
 * @param cid 课程ID
 * @param name 课程名称
 * @param credit 课程学分
 */
public record Course(String cid, String name, int credit) {

    /**
     * 紧凑构造方法，检查课程信息的合法性。
     */
    public Course {
        if (cid == null || cid.isBlank()) {
            throw new IllegalArgumentException("Course ID cannot be blank");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Course name cannot be blank");
        }

        if (credit <= 0) {
            throw new IllegalArgumentException("Credit must be positive");
        }
    }
}