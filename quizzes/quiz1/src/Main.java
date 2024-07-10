import java.lang.String;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static java.lang.String.valueOf;

public class Main {
    public static void main(String[] args) {
        FileOutput.writeToFile("output.txt","",false,false);
        String file_name = args[0];
        String[] input = FileInput.readFile(file_name, true, true);
        for (int num = 0; num < input.length; num++) {
            String line = input[num];

            if (line.contains("Armstrong")) {
                List<Integer> arms_nums = new ArrayList<>();
                int number_1 = Integer.parseInt(input[num + 1]) + 1;
                for (int i = 0; i < number_1; i++ ) {
                    int length = (int) (Math.log10(i) + 1);
                    int sum_dig = 0;
                    for(int a = 0; a < length; a++) {
                        char currentDigit = String.valueOf(i).charAt(a);
                        int currentNumber = currentDigit - '0';
                        sum_dig += Math.pow(currentNumber, length);
                    }
                    if (sum_dig == i) arms_nums.add(i);
                }
                StringBuilder sb = new StringBuilder();
                for (Integer s : arms_nums) {
                    sb.append(s);
                    sb.append(" ");
                }
                String string_output = sb.toString();
                FileOutput.writeToFile("output.txt", "Armstrong numbers up to " +
                        (number_1 - 1) + ":\n" + string_output.substring(0, string_output.length()-1) + "\n", true, true);
            }
            else if (line.contains("Emirp")) {
                int number2 = Integer.parseInt(input[num + 1]) + 1;
                List<Integer> emir_nums = new ArrayList<>();
                for (int value = 13; value < number2; value++) {
                    Boolean is_prime = true;
                    for (int nums = 2; nums < value ; nums++ ) {
                        if (value % nums == 0) {
                            is_prime = false;
                        }
                    }
                    if (is_prime) {
                        int digit_num = (int) (Math.log10(value) + 1);
                        String reverse_num = "";
                        for (int s = digit_num -1; s > -1; s--) {
                            reverse_num += valueOf(value).charAt(s);
                        }
                        int reverse_number = Integer.parseInt(reverse_num);
                        if (reverse_number == value) {
                            continue;
                        }

                        Boolean is_Prime = true;
                        for (int nums = 2; nums < reverse_number ; nums++ ) {
                            if (reverse_number % nums == 0) {
                                is_Prime = false;
                            }
                        }
                        if (is_Prime) {
                            emir_nums.add(value);
                        }
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (Integer s : emir_nums) {
                    sb.append(s);
                    sb.append(" ");
                }
                String string_output1 = sb.toString();
                FileOutput.writeToFile("output.txt", "Emirp numbers up to "
                        + (number2 - 1) + ":\n"+ string_output1.substring(0, string_output1.length()-1) + "\n", true, true);
            }
            else if (line.contains("Abundant")) {
                int number3 = Integer.parseInt(input[num + 1]) + 1;
                List<Integer> abun_nums = new ArrayList<>();
                for (int value2 = 1; value2 < number3; value2++) {
                    int sum_divisors = 0;
                    for (int m = 1; m < value2; m++) {
                        if (value2 % m == 0) sum_divisors += m;
                    }
                    if (sum_divisors > value2) abun_nums.add(value2);
                }
                StringBuilder sb = new StringBuilder();
                for (Integer s : abun_nums) {
                    sb.append(s);
                    sb.append(" ");
                }
                String string_output2 = sb.toString();
                FileOutput.writeToFile("output.txt", "Abundant numbers up to " +
                        (number3 - 1) + ":\n"+ string_output2.substring(0, string_output2.length()-1) + "\n", true, true);
            }
            else if (line.contains("Ascending")) {
                List<Integer> numbers_1 = new ArrayList<>();
                int num_at_1 = num + 1;
                FileOutput.writeToFile("output.txt", "Ascending order sorting:",true, true);
                while (!input[num_at_1].equals("-1")) {
                    numbers_1.add(Integer.parseInt(input[num_at_1]));
                    numbers_1.sort(Comparator.naturalOrder());
                    StringBuilder sb = new StringBuilder();
                    for (Integer s : numbers_1) {
                        sb.append(s);
                        sb.append(" ");
                    }
                    String string_output3 = sb.toString();
                    FileOutput.writeToFile("output.txt",
                            string_output3.substring(0, string_output3.length()-1), true, true);
                    num_at_1 += 1;
                }
                FileOutput.writeToFile("output.txt", "\n",true, false);
            }
            else if (line.contains("Descending")) {
                List<Integer> numbers_2 = new ArrayList<>();
                int num_at_2 = num + 1;
                FileOutput.writeToFile("output.txt", "Descending order sorting:",true, true);
                while (!input[num_at_2].equals("-1")) {
                    numbers_2.add(Integer.parseInt(input[num_at_2]));
                    numbers_2.sort(Comparator.reverseOrder());
                    StringBuilder sb = new StringBuilder();
                    for (Integer s : numbers_2) {
                        sb.append(s);
                        sb.append(" ");
                    }
                    String string_output4 = sb.toString();
                    FileOutput.writeToFile("output.txt",
                            string_output4.substring(0, string_output4.length()-1), true, true);
                    num_at_2 += 1;
                }
                FileOutput.writeToFile("output.txt", "\n",true, false);
            }
            if (line.contains("Exit")) FileOutput.writeToFile("output.txt", "Finished...",true, true);
        }
    }
}