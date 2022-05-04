package Formula;

import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Formula implements Check, Count{
    String plus = "+";
    String multiple = "*";
    String minus = "-";
    String division = "/";

    public Formula() {}

    public Formula(@NotNull String plus, @NotNull String minus, @NotNull String multiple, @NotNull String division) throws IOException {
        boolean bool = plus.equals(minus) || plus.equals(multiple) || plus.equals(division);
        bool = bool || minus.equals(multiple) || minus.equals(division) || multiple.equals(division);
        if (bool) throw new IOException("같은 문자는 사용이 불가능합니다.");
        this.plus = plus;
        this.minus = minus;
        this.multiple = multiple;
        this.division = division;
    }

    //괄호 연산하는 메소드
    public double formula(String line, boolean operation) {
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

    private double form(@NonNull String lines, @NonNull boolean operation) {
        String multiple = this.multiple.equals("*") ? "\\*" : this.multiple;;
        String plus = this.plus.equals("+") ? "\\+" : this.plus;
        String minus = this.minus;
        String division = this.division;

        lines = lines.replace(" ", "");
        lines = lines.replace(minus, " "+minus);
        String[] numbers = lines.split(plus+"|"+multiple+"|"+division+"|"+" ");
        String[] sings = lines.replace(minus, this.plus).split("[0-9|^.]");
        multiple = this.multiple;

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
            if (operation) {
                if (this.check(singList) && singList.size()>1) {
                    int position = (int) this.count(singList);
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
    private void calculation(List<String> numberList, List<String> singList) {
        double total = Double.parseDouble(numberList.get(0));
        for (int i = 0; i<singList.size(); i++) {
            String sing = singList.get(i);
            sing = this.changeSing(sing).strip();

            if (sing.equals(this.plus)) total += Double.parseDouble(numberList.get(i + 1));
            if (sing.equals(this.minus)) total -= Double.parseDouble(numberList.get(i + 1));
            if (sing.equals(this.multiple)) total *= Double.parseDouble(numberList.get(i + 1));
            if (sing.equals(this.division)) total /= Double.parseDouble(numberList.get(i + 1));
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
//        line = line.replace("-/", "");
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

    private boolean check(String line) {
        return line.contains(")") || line.contains("(");
    }

    private long count(String line, char c) {
        return line.chars().filter(ch -> ch==c).count();
    }

}
