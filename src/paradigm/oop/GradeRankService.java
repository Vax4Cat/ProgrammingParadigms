package paradigm.oop;

/**
 * 成绩等级服务ADT，负责将原始成绩转换为对应等级。
 */
public final class GradeRankService {
    private final int aThreshold;
    private final int bThreshold;
    private final int cThreshold;

    /*
     * Abstraction Function (AF):
     * AF(aThreshold, bThreshold, cThreshold) =
     * 根据三个等级分数界限划分A、B、C、D等级的规则。
     *
     * Representation Invariant (RI):
     * 0 < cThreshold < bThreshold < aThreshold <= 100。
     *
     * Safety from Rep Exposure:
     * 所有字段使用private final修饰，
     * 不允许外部修改成绩等级划分规则。
     */

    /**
     * 创建默认成绩等级服务对象。
     */
    public GradeRankService() {
        this(85, 70, 60);
    }

    /**
     * 创建具有指定等级界限的成绩等级服务对象。
     *
     * @param aThreshold A等级最低分
     * @param bThreshold B等级最低分
     * @param cThreshold C等级最低分
     */
    public GradeRankService(int aThreshold, int bThreshold, int cThreshold) {
        this.aThreshold = aThreshold;
        this.bThreshold = bThreshold;
        this.cThreshold = cThreshold;
        checkRep();
    }

    /**
     * 检查表示不变式。
     */
    private void checkRep() {
        if (cThreshold <= 0
                || cThreshold >= bThreshold
                || bThreshold >= aThreshold
                || aThreshold > 100) {
            throw new IllegalStateException("Invalid grade rank thresholds");
        }
    }

    /**
     * 根据原始成绩获取对应等级。
     *
     * @param score 原始成绩（0～100）
     * @return 对应的成绩等级（A/B/C/D）
     */
    public String getGradeRank(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException(
                    "Score must be between 0 and 100");
        }

        if (score >= aThreshold) {
            return "A";
        } else if (score >= bThreshold) {
            return "B";
        } else if (score >= cThreshold) {
            return "C";
        } else {
            return "D";
        }
    }
}
