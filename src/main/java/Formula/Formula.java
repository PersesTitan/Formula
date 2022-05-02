package Formula;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Formula implements Check, Count{

    public String plus = "+";
    public String multiple = "*";
    public String minus = "-";
    public String division = "/";
    static Formula formula = new Formula();

    public static void formula(String plus, String minus, String multiple, String division) {
        formula.plus = plus;
        formula.minus = minus;
        formula.multiple = multiple;
        formula.division = division;
    }

    //괄호 연산하는 메소드
    public static double formula(String line, boolean operation) {
        if (count(line, ')') != count(line, '('))
            System.err.println("괄호의 짝이 일치하지 않습니다.");

        int firstPosition = line.lastIndexOf("(");
        int secondPosition = line.indexOf(")")+1;

        while(check(line)) {
            String Line = line.substring(firstPosition, secondPosition);
            String inLine = line.substring(firstPosition+1, secondPosition-1);
            String outLine = String.valueOf(form(inLine, operation));
            line = line.replace(Line, outLine);
        }

        line = String.valueOf(form(line, operation));
        return Double.parseDouble(line);
    }

    private static double form(String lines, boolean operation) {
        String multiple;
        String plus;
        if (formula.multiple.equals("*")) multiple = "\\*";
        else multiple = formula.multiple;

        if (formula.plus.equals("+")) plus = "\\+";
        else plus = formula.plus;

        String minus = formula.minus;
        String division = formula.division;

        lines = lines.replace(" ", "");
        lines = lines.replace(minus, " "+minus);
        String[] numbers = lines.split(plus+"|"+multiple+"|"+division+"|"+" ");
        String[] sings = lines.replace(minus, formula.plus).split("[0-9|^.]");
        multiple = formula.multiple;

        List<String> numberList = new ArrayList<>(Arrays.asList(numbers));
        List<String> singList = new ArrayList<>(Arrays.asList(sings));
        numberList.removeAll(Collections.singletonList(""));
        numberList.removeAll(Collections.singletonList(null));
        singList.removeAll(Collections.singletonList(""));
        singList.removeAll(Collections.singletonList(null));
        assert numbers.length == sings.length+1;

        //디버깅용
//        System.out.println(numberList);
//        System.out.println(singList);

        while (singList.size()>0) {
            System.out.println(numberList);
            System.out.println(singList);
            if (operation) {
                if (formula.check(singList) && singList.size()>1) {
                    int position = (int) formula.count(singList);
                    double totalNumber = Double.parseDouble(numberList.get(position));

                    String sing = singList.get(position);

                    if (multiple.equals(sing)) totalNumber *= Double.parseDouble(numberList.get(position+1));
                    else if (division.equals(sing)) totalNumber /= Double.parseDouble(numberList.get(position+1));
                    else System.err.println("예기치 못한 에러가 발생하였습니다. - Formula");

                    numberList.set(position, Double.toString(totalNumber));
                    numberList.remove(position+1);
                    singList.remove(position);

                } else calculation(numberList, singList);
            } else calculation(numberList, singList);
        }
        return Double.parseDouble(numberList.get(0));
    }

    /**
     * @param numberList 숫자를 저장하는 리스트
     * @param singList 기호를 저장하는 리스트
     */

    private static void calculation(List<String> numberList, List<String> singList) {
        double total = Double.parseDouble(numberList.get(0));
        for (int i = 0; i<singList.size(); i++) {
            String sing = singList.get(i);
            sing = formula.changeSing(sing).strip();

            System.out.println(Double.parseDouble(numberList.get(i + 1)));

            if (sing.equals(formula.plus)) total += Double.parseDouble(numberList.get(i + 1));
            if (sing.equals(formula.minus)) total -= Double.parseDouble(numberList.get(i + 1));
            if (sing.equals(formula.multiple)) total *= Double.parseDouble(numberList.get(i + 1));
            if (sing.equals(formula.division)) total /= Double.parseDouble(numberList.get(i + 1));
        }

        numberList.set(0, String.valueOf(total));
        singList.clear();
    }

    private String changeSing(String line) {
        line = line.replace("++", "+");
        line = line.replace("+/", "/");
        line = line.replace("+*", "*");
        line = line.replace("+-", "-");

        line = line.replace("-+", "-");
        line = line.replace("--", "+");

        line = line.replace("-/", "");
        return line;
    }

    @Override
    public boolean check(List<String> list) {
        return list.contains(multiple) || list.contains(division);
    }

    @Override
    public long count(List<String> list) {
        return list.stream().filter(s -> s.equals(multiple)||s.equals(division)).count();
    }

    private static boolean check(String line) {
        return line.contains(")") || line.contains("(");
    }

    private static long count(String line, char c) {
        return line.chars().filter(ch -> ch==c).count();
    }

}
