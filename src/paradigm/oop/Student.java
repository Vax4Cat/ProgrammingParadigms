package paradigm.oop;

/**
 * 学生ADT，表示具有唯一标识和姓名的学生。
 */
public final class Student {
    private final String sid;
    private final String name;

    /*
     * Abstraction Function (AF):
     * AF(sid, name) = 学号为sid、姓名为name的学生。
     *
     * Representation Invariant (RI):
     * sid不能为null或空白字符串。
     * name不能为null或空白字符串。
     *
     * Safety from Rep Exposure:
     * 字段使用private final修饰，String为不可变类型，
     * 不提供setter方法，防止外部修改内部状态。
     */

    /**
     * 创建学生对象。
     *
     * @param sid 学生ID
     * @param name 学生姓名
     */
    public Student(String sid, String name) {
        this.sid = sid;
        this.name = name;
        checkRep();
    }

    /**
     * 检查表示不变式。
     */
    private void checkRep() {
        if (sid == null || sid.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be blank");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student name cannot be blank");
        }
    }

    /**
     * 获取学生ID。
     *
     * @return 学生ID
     */
    public String getSid() {
        return sid;
    }

    /**
     * 获取学生姓名。
     *
     * @return 学生姓名
     */
    public String getName() {
        return name;
    }
}
