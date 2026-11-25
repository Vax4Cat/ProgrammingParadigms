package paradigm.oop;

/**
 * 课程ADT，表示具有课程编号、课程名称和学分的课程。
 */
public final class Course {
    private final String cid;
    private final String name;
    private final int credit;

    /*
     * Abstraction Function (AF):
     * AF(cid, name, credit) = 编号为cid、名称为name、
     * 学分为credit的课程。
     *
     * Representation Invariant (RI):
     * cid不能为null或空白字符串。
     * name不能为null或空白字符串。
     * credit必须大于0。
     *
     * Safety from Rep Exposure:
     * 所有字段使用private final修饰，
     * 不提供setter方法，防止外部修改内部状态。
     */

    /**
     * 创建课程对象。
     *
     * @param cid 课程ID
     * @param name 课程名称
     * @param credit 课程学分
     */
    public Course(String cid, String name, int credit) {
        this.cid = cid;
        this.name = name;
        this.credit = credit;
        checkRep();
    }

    /**
     * 检查表示不变式。
     */
    private void checkRep() {
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

    /**
     * 获取课程ID。
     *
     * @return 课程ID
     */
    public String getCid() {
        return cid;
    }

    /**
     * 获取课程名称。
     *
     * @return 课程名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取课程学分。
     *
     * @return 课程学分
     */
    public int getCredit() {
        return credit;
    }
}
