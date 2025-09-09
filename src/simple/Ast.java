package simple;

import java.util.List;

interface AstNode {}

abstract class Stmt implements AstNode {}

abstract class Expr implements AstNode {}

enum Type {INTEGER, BOOLEAN}

enum Operator {ADD, SUB, MUL, DIV, EQ, LT, GT, AND, OR}

class ProgramNode implements AstNode{
    final List<FuncDef> fns;
    final List<VarDecl> globals;
    final List<Stmt> main;
    public ProgramNode(List<FuncDef> fns,List<VarDecl> globals, List<Stmt> main) {
        this.fns = fns;
        this.globals = globals;
        this.main = main;
    }
    
}

class VarDecl extends Stmt{
    final String name;
    final Type type;
    public VarDecl(String name, Type type) {
        this.name = name;
        this.type = type;
    }
    
}
class FuncDef implements AstNode{
    final String name;
    final List<VarDecl> params;
    final Stmt body;
    public FuncDef(String name, List<VarDecl> params, Stmt body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }
    
}

class BlockStmt extends Stmt{
    final List<VarDecl> declarations;
    final List<Stmt> statements;
    public BlockStmt(List<VarDecl> declarations, List<Stmt> statements) {
        this.declarations = declarations;
        this.statements = statements;
    }
    
}

class AssignStmt extends Stmt{
    final String id;
    final Expr expr;
    public AssignStmt(String id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }
    
}

class IfStmt extends Stmt{
    final Expr conditional;
    final Stmt t;
    final Stmt e;
    public IfStmt(Expr conditional, Stmt t, Stmt e) {
        this.conditional = conditional;
        this.t = t;
        this.e = e;
    }
    
}

class LoopStmt extends Stmt{
    final Expr conditional;
    final Stmt body;
    public LoopStmt(Expr conditional, Stmt body) {
        this.conditional = conditional;
        this.body = body;
    }
    
}

class ReturnStmt extends Stmt{
    final Expr expr;

    public ReturnStmt(Expr expr) {
        this.expr = expr;
    }
    
}

class BinaryExpr extends Expr{
    final Expr left;
    final Operator op;
    final Expr right;
    public BinaryExpr(Expr left, Operator op, Expr right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
    
}

class IdExpr extends Expr{
    final String name;

    public IdExpr(String name) {
        this.name = name;
    }
    
}
class IntLiteral extends Expr{
    final int value;

    public IntLiteral(int value) {
        this.value = value;
    }
    
}
class BoolLiteral extends Expr{
    final boolean value;

    public BoolLiteral(boolean value) {
        this.value = value;
    }
    
}
class FuncCall extends Expr{
    final String name;
    final List<Expr> args;
    public FuncCall(String name, List<Expr> args) {
        this.name = name;
        this.args = args;
    }
    
}
