package simple;


import java.io.FileReader;


public class Evaluate {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java simple.Evaluate <test-case-file>");
            System.exit(1);
        }

        try {
            // 1. Create the lexer by reading from the input file
            SimpleLexer lexer = new SimpleLexer(new FileReader(args[0]));

            // 2. Create the parser, feeding it the lexer
            parser cupParser = new parser(lexer);

            // 3. Run the parser. The .parse() method returns a Symbol whose 'value'
            // field contains the root of our AST (the ProgramNode).
            java_cup.runtime.Symbol result = cupParser.parse();
            ProgramNode program = (ProgramNode) result.value;

            if (program != null) {
                Env finalEnv = BigStep.evaluateProgram(program);
                System.out.println("--- Program Finished ---");
                System.out.println("Final Environment State:");
                System.out.println(finalEnv);
            } else {
                System.err.println("Parsing failed, AST is null.");
            }

        } catch (Exception e) {
            System.err.println("An error occurred:");
            e.printStackTrace();
        }
    }
}

