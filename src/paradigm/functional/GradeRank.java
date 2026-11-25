package paradigm.functional;

/**
 * 成绩等级转换工具类，使用纯静态函数实现等级划分。
 */
public final class GradeRank {

    /**
     * 私有构造方法，防止工具类被实例化。
     */
    private GradeRank() {
    }

    /**
     * 根据原始成绩返回对应等级。
     * Pure Function: 相同输入始终产生相同结果。
     * No Mutation: 不修改任何已有数据。
     * No Side Effect: 不产生外部副作用。
     *
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
}