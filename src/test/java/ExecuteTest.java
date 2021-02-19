import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;
import com.github.kayjam.core.containers.MainContainer;

public class ExecuteTest {

    public static void main(String[] args) throws Exception {
        KayJamLexer lexer = new KayJamLexer("{\n" +
                "var test = -> (test) {\n" +
                "   return concat(test, \"world!\");" +
                "};\n" +
                "return test(\"Hello \");\n" +
                "\n                   " +
                "class Test {\n" +
                "      companion object{\n" +
                "          public var func = \"fail\";\n" +
                "\n                              " +
                "          public function test(output){\n" +
                "             this.func = output(func);\n" +
                "          }\n" +
                "      }\n" +
                "}\n"+
                "}\n");
        KayJamParser parser = new KayJamParser(lexer);
        System.out.println(parser.readExpression().execute(new MainContainer()));
    }
}
