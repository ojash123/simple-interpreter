package simple;



// =====================================================================
// Main Test Runner Class
// =====================================================================
public class TestEvaluator {
    public static void main(String[] args) {
        BigStep evaluator = new BigStep();
        Env env = new Env();
        env.declare("x", BigStep.init(Type.INTEGER));
        env.declare("y", BigStep.init(Type.INTEGER));
        env.declare("p", BigStep.init(Type.BOOLEAN));
        env.declare("q", BigStep.init(Type.BOOLEAN));
        env.addVal("x", new IntVal(10));
        env.addVal("y", new IntVal(5));
        env.addVal("p", new BoolVal(true));
        env.addVal("q", new BoolVal(false));

        System.out.println("--- Running Expression Tests ---");

        // Test 1: Simple arithmetic: (10 + 5) * 2
        test(evaluator, new BinaryExpr(
            new BinaryExpr(new IdExpr("x"), Operator.ADD, new IdExpr("y")),
            Operator.MUL,
            new IntLiteral(2)
        ), env, "30");

        // Test 2: Integer comparisons: x > y
        test(evaluator, new BinaryExpr(new IdExpr("x"), Operator.GT, new IdExpr("y")), env, "true");

        // Test 3: Boolean logic: p AND (x > y)
        test(evaluator, new BinaryExpr(
            new IdExpr("p"),
            Operator.AND,
            new BinaryExpr(new IdExpr("x"), Operator.GT, new IdExpr("y"))
        ), env, "true");

        // Test 4: Equality checks: (x - y) == 5
        test(evaluator, new BinaryExpr(
            new BinaryExpr(new IdExpr("x"), Operator.SUB, new IdExpr("y")),
            Operator.EQ,
            new IntLiteral(5)
        ), env, "true");
        
        // Test 5: Boolean equality: q == true
        test(evaluator, new BinaryExpr(new IdExpr("q"), Operator.EQ, new BoolLiteral(true)), env, "false");

        // Test 6: Boolean OR P || Q 
        test(evaluator, new BinaryExpr(new IdExpr("p"), Operator.OR, new IdExpr("q")), env, "true");
    }

    private static void test(BigStep evaluator, Expr expression, Env env, String expected) {
        try {
            EnvItem result = evaluator.evaluate(expression, env);
            boolean pass = result.toString().equals(expected);
            System.out.printf("Test: [%s] -> Expected: %s, Got: %s%n",
                pass ? "PASS" : "FAIL",
                expected,
                result);
        } catch (Exception e) {
            System.out.printf("Test: [ERROR] -> Threw exception: %s%n", e.getMessage());
        }
    }
}
