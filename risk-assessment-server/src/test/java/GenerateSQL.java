public class GenerateSQL {
  public static void main(String[] args) {
    int maxRiskId = 167;
    int maxGradeId = 3;
    int startScore = 3;

    StringBuilder sqlBuilder = new StringBuilder();
    for (int riskId = 1; riskId <= maxRiskId; riskId++) {
      for (int gradeId = 1; gradeId <= maxGradeId; gradeId++) {
        sqlBuilder
            .append("INSERT INTO risk_grade (risk_id, grade_id, score) VALUES (")
            .append(riskId)
            .append(",")
            .append(gradeId)
            .append(",")
            .append(startScore)
            .append(");\n");

        // 递减 score
        startScore--;
        if (startScore < 1) {
          startScore = 3; // 重置为初始值 3
        }
      }
    }

    // 打印生成的 SQL 语句
    System.out.println(sqlBuilder.toString());
  }
}
