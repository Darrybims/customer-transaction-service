package customer_tranx_service.utils;

public class GenerateAccount {

    public static String generateRandom(int size){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int digit = (int)(Math.random() * 10);
            sb.append(digit);
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static String createAccountNumber(){
        return generateRandom(10);
    }

}
