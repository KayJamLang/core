import com.github.kayjam.core.KayJamLexer;
import com.github.kayjam.core.KayJamParser;

public class ParserTest {

    public static void main(String[] args) throws Exception {
        KayJamLexer lexer = new KayJamLexer("@JS(\"test\")\n" +
                "public function test(){\n" +
                "   test(\"test\");\n"+
                "   var test = \"test\";\n" +
                "   test(test);test(test, test);\n" +
                "   test(-> () {\n" +
                "       return concat(\"test\", 123);\n" +
                "   }); \n" +
                "   if(true){" +
                "      echo(test);" +
                "   }" +
                "   class Test {" +
                "      companion object {" +
                "          public var test = 0;" +
                "                              " +
                "          public function test(output){" +
                "              this.test = output;" +
                "          }" +
                "      }" +
                "   }"+
                "}");
        KayJamParser parser = new KayJamParser(lexer);
        System.out.println(parser.readExpression());
    }
}
