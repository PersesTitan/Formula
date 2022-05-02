import Formula.Formula;

public class Main {
    public static void main(String[] args) {
        String total = "20-5*2";
        String aa = "(19-1)*12";
        double d1 = Formula.formula(total, true);
        double d2 = Formula.formula(total, false);
        double d3 = Formula.formula(aa, true);
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
    }
}
