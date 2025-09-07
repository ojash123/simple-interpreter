package simple;

public class BigStep {
    static EnvItem init(Type t){
        if (t == Type.BOOLEAN) {
            return new BoolVal(false);
        }else if(t == Type.INTEGER){
            return new IntVal(0);
        }
        throw new RuntimeException("Invalid Type");
    }
    EnvItem evaluate(Expr expr, Env env){
        if(expr instanceof BinaryExpr) return evaluate((BinaryExpr)expr, env);
        if(expr instanceof IntLiteral) return evaluate((IntLiteral)expr, env);
        if(expr instanceof BoolLiteral) return evaluate((BoolLiteral)expr, env);
        if(expr instanceof IdExpr) return evaluate((IdExpr)expr, env);
        throw new UnsupportedOperationException("This expression type is not implemented");
    }
    private EnvItem evaluate(IntLiteral expr, Env env){
        return new IntVal(expr.value);
    }
    private EnvItem evaluate(BoolLiteral expr, Env env){
        return new BoolVal(expr.value);
    }
    private EnvItem evaluate(IdExpr expr, Env env){
        return env.getVal(expr.name);
    }
    private EnvItem evaluate(BinaryExpr expr, Env env){
        EnvItem leftItem = evaluate(expr.left, env);
        EnvItem rightItem = evaluate(expr.right, env);
        switch (expr.op) {
            case ADD: return new IntVal(((IntVal)leftItem).value + ((IntVal)rightItem).value);
            case AND: return new BoolVal(((BoolVal)leftItem).value && ((BoolVal)rightItem).value);
            case DIV: return new IntVal(((IntVal)leftItem).value / ((IntVal)rightItem).value);
            case EQ: 
                if(leftItem instanceof BoolVal && rightItem instanceof BoolVal)
                    return new BoolVal(((BoolVal)leftItem).value == ((BoolVal)rightItem).value);
                else if(leftItem instanceof IntVal && rightItem instanceof IntVal)
                    return new BoolVal(((IntVal)leftItem).value == ((IntVal)rightItem).value);
                   
            case GT: return new BoolVal(((IntVal)leftItem).value > ((IntVal)rightItem).value);
            case LT:return new BoolVal(((IntVal)leftItem).value < ((IntVal)rightItem).value);
            case MUL: return new IntVal(((IntVal)leftItem).value * ((IntVal)rightItem).value);
            case OR: return new BoolVal(((BoolVal)leftItem).value || ((BoolVal)rightItem).value);
            case SUB: return new IntVal(((IntVal)leftItem).value - ((IntVal)rightItem).value);
            default:
                throw new UnsupportedOperationException("Binary operator not found");
            
        }
    }
}
