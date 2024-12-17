import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class StringProcessor {

    public static void main(String[] args) {
        try {
            BufferedReader inp = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt"), "UTF-8"));
            String lexstr = inp.readLine();
            String delstr = inp.readLine();
            inp.close();

            if (delstr == null || delstr.isEmpty()) {
                return;
            }

            String delreg = "[" + Pattern.quote(delstr) + "]+";
            String[] tokens = lexstr.split(delreg);
            List<String> lexemes = new ArrayList<>();
            List<Double> numbers = new ArrayList<>();
            List<String> times = new ArrayList<>();
            Random rand = new Random();

            for (String token : tokens) {
                try {
                    double number = Double.parseDouble(token);
                    numbers.add(number);  
                } catch (NumberFormatException e) {
                    if (token.matches("([01]?[0-9]|2[0-3])-[0-5][0-9]")) {
                        times.add(token);  
                    } else {
                        lexemes.add(token); 
                    }
                }
            }

            for (int i = 0; i < numbers.size(); i++) {
                int randnum = rand.nextInt(91) + 10;
                String numberStr = Double.toString(numbers.get(i));
                String randStr = Integer.toString(randnum);
                String combinedStr = numberStr + randStr; 
                double newnum = Double.parseDouble(combinedStr); 
                numbers.set(i, newnum);
            }
            

            String srtststr = null;
            for (String l : lexemes) {
                if (srtststr == null || l.length() < srtststr.length()) {
                    srtststr = l; 
                }
            }

            if (srtststr != null) {
                lexemes.remove(srtststr);  
            }

            times.sort(Comparator.comparing(String::toString));
            NumberFormat pf = NumberFormat.getPercentInstance(Locale.US);
            DecimalFormat cf = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.US);
            BufferedWriter outp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            sb.append("Вещественные числа с добавленным случайным числом (денежный и процентный форматы):\n");
            for (double n : numbers) {
                sb.append(cf.format(n)).append(" ").append(pf.format(n)).append(" ").append(n).append("\n");
            }
            sb.append("\nВремя (сорт):\n");
            for (String t : times) {
                sb.append(t).append("\n");
            }
            sb.append("\nЛексемы без самой короткой подстроки:\n");
            for (String l : lexemes) {
                StringBuilder temp = new StringBuilder(l);
                temp.insert(0, "[");
                temp.append("]");
                temp.delete(1, 2); 
                sb.append(temp.reverse()).append("\n");
            }

            outp.write(sb.toString());
            outp.close();

        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }
}
