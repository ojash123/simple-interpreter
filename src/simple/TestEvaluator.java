package simple;

import java.util.ArrayList;
import java.util.List;

// =====================================================================
// Main Test Runner Class
// =====================================================================
public class TestEvaluator {
    public static void main(String[] args) {
        System.out.println("--- Running Statement Tests ---");
        testAssignment();
        testIfStatement();
        testLoopStatement();
        testBlockScoping();
    }

    private static void testAssignment() {
        BigStep evaluator = new BigStep();
        Env env = new Env();
        env.declare("x", new IntVal(0));
        Stmt assignStmt = new AssignStmt("x", new IntLiteral(42));
        evaluator.evaluate(assignStmt, env);
        test("Assignment", env.getVal("x").toString(), "42");
    }

    private static void testIfStatement() {
        BigStep evaluator = new BigStep();
        Env env = new Env();
        env.declare("x", new IntVal(0));
        // if (10 > 5) then x := 1 else x := 2
        Stmt ifStmt = new IfStmt(
            new BinaryExpr(new IntLiteral(10), Operator.GT, new IntLiteral(5)),
            new AssignStmt("x", new IntLiteral(1)),
            new AssignStmt("x", new IntLiteral(2))
        );
        evaluator.evaluate(ifStmt, env);
        test("If-Statement (True)", env.getVal("x").toString(), "1");
    }

    private static void testLoopStatement() {
        BigStep evaluator = new BigStep();
        Env env = new Env();
        env.declare("i", new IntVal(0));
        env.declare("sum", new IntVal(0));
        // while (i < 5) { sum := sum + i; i := i + 1; }
        Stmt loopBody = new BlockStmt(new ArrayList<>(), List.of(
            new AssignStmt("sum", new BinaryExpr(new IdExpr("sum"), Operator.ADD, new IdExpr("i"))),
            new AssignStmt("i", new BinaryExpr(new IdExpr("i"), Operator.ADD, new IntLiteral(1)))
        ));
        Stmt loopStmt = new LoopStmt(
            new BinaryExpr(new IdExpr("i"), Operator.LT, new IntLiteral(5)),
            loopBody
        );
        evaluator.evaluate(loopStmt, env);
        test("Loop Statement", env.getVal("sum").toString(), "10");
    }

    private static void testBlockScoping() {
        BigStep evaluator = new BigStep();
        Env env = new Env();
        env.declare("x", new IntVal(100)); // Global x
        // { int x; x := 5; }
        Stmt blockStmt = new BlockStmt(
            List.of(new VarDecl("x", Type.INTEGER)), // Shadow variable
            List.of(new AssignStmt("x", new IntLiteral(5)))
        );
        evaluator.evaluate(blockStmt, env);
        test("Block Scoping", env.getVal("x").toString(), "100");
    }

    private static void test(String testName, String actual, String expected) {
        boolean pass = actual.equals(expected);
        System.out.printf("Test: [%s] - %s -> Expected: %s, Got: %s%n",
            pass ? "PASS" : "FAIL",
            testName,
            expected,
            actual);
    }
    private static void test_expr(){
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
