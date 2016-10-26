package regex.example;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

// http://www.vogella.com/tutorials/JavaRegularExpressions/article.html
// http://credemol.blogspot.kr/2010/07/regular-expression-java.html
public class RegExpTest {
	public String EXAMPLE_TEST;
	
	@Before
	public void setup(){
		EXAMPLE_TEST = "This is my small example "
                + "string which I'm going to " + "use for pattern matching.";
	}
	
	@Ignore
	@Test
	public void test() {
		System.out.println(EXAMPLE_TEST.matches("\\w.*"));
        String[] splitString = (EXAMPLE_TEST.split("\\s+"));
        System.out.println(splitString.length);// should be 14
        for (String string : splitString) {
                System.out.println(string);
        }
        // replace all whitespace with tabs
        System.out.println(EXAMPLE_TEST.replaceAll("\\s+", "\t"));
	}
	
	@Ignore
	@Test
	public void testMacher() {
		Pattern pattern = Pattern.compile("\\w+");
        // in case you would like to ignore case sensitivity,
        // you could use this statement:
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(EXAMPLE_TEST);
        // check all occurance
        while (matcher.find()) {
                System.out.print("Start index: " + matcher.start());
                System.out.print(" End index: " + matcher.end() + " ");
                System.out.println(matcher.group());
        }
        // now create a new pattern and matcher to replace whitespace with tabs
        Pattern replace = Pattern.compile("\\s+");
        Matcher matcher2 = replace.matcher(EXAMPLE_TEST);
        System.out.println(matcher2.replaceAll("\t"));
	}
	
	@Ignore
	@Test
	public void testOr() {
		String s = "humbapumpa jim";
        assertTrue(s.matches(".*(jim|joe).*"));
        s = "humbapumpa jom";
        assertFalse(s.matches(".*(jim|joe).*"));
        s = "humbaPumpa joe";
        assertTrue(s.matches(".*(jim|joe).*"));
        s = "humbapumpa joe jim";
        assertTrue(s.matches(".*(jim|joe).*"));
	}

	/**
	 * @�ۼ��� : 2016. 10. 25.
	 * @�ۼ��� : ���μ�
	 * @Method ���� : sql injection �� ���ϱ� ���� ����� �� Ư������ üũ
	 * ��ҹ��� ������ �ʰ�, ���๮�� �־ ó����.
	 */
	@Test
	public void testSQLInjection() {
		String reg = ".*(select|insert|update|delete|drop|alter|table|having|union|\\'|\\`|\\>|\\<|\\=|\\;).*";
		/*
		(.*) ǥ������ ����� ��� ���๮�ڰ� ���ԵǾ� ������ ��Ī���� �ʴ´�.
		�׷��� ���๮�ڸ� �����Ͽ� ��Ī�ϰ��� �ϸ� Pattern �ɼǿ� Pattern.DOTALL �÷��׸� �����ϸ� ���๮�ڸ� �����Ͽ� ��Ī�Ѵ�.
		ǥ���� �� �κп� (?s)�� ���Խ�Ű�� DOTALL �÷��׸� �����ϴ� �Ͱ� �����ϴ�.
		*/
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		String s1 = "'";
		result(pattern, s1);
		String s2 = "select";
		result(pattern, s2);
		String s3 = "update";
		result(pattern, s3);
		String s4 = "`";
		result(pattern, s4);
		String s5 = ">";
		result(pattern, s5);
		String s6 = "<";
		result(pattern, s6);
		String s7 = "=";
		result(pattern, s7);
		String s8 = "SELECT";
		result(pattern, s8);
		String s9 = "';";
		result(pattern, s9);
		String s10 = "";
		result(pattern, s10);
		String s11 = "selECt";
		result(pattern, s11);
		
		String s20 = "'1'; dRop table";
		result(pattern, s20);
		String s21 = "aaa \n" +"Table";
		result(pattern, s21);
	}
	
	private void result(Pattern pattern, String s){
		boolean result = true;
		System.out.print("data:" + s +"\n");
		Matcher matcher = pattern.matcher(s);
		
		if(matcher.matches()){
			System.out.println(matcher.group() + " ");
		} else {
			result = false;
		}
		System.out.println(result +"\n\r");
	}
}
