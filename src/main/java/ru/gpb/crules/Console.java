package ru.gpb.crules;

import ru.gpb.crules.businessModel.*;
import ru.gpb.crules.exception.ParseException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Console {


    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchFieldException, ParseException, InvocationTargetException, IllegalAccessException {

    }

    private CreditRequest initRequest() {
        CreditRequest creditRequest = new CreditRequest();
        Calendar calendar = new GregorianCalendar(2024,Calendar.JULY, 1);
        creditRequest.setApplicDate(calendar);
        creditRequest.setPrepayPercent(-1);
        Borrower borrower = new Borrower();
        borrower.setSalaryClient(true);
       // borrower.setBorrowerType(BorrowerType.GAZPROM);
        creditRequest.setBorrower(borrower);
        return creditRequest;
    }


    private void complete(CreditRequest creditRequest) {
        Scanner in = new Scanner(System.in);
        if (creditRequest.getCreditType() == null) {
            System.out.print("Введите тип программы: \n1 - Ипотека\n2 - Потребительский кредит \n");
            int number = in.nextInt();
            if (number == 1) {
                creditRequest.setCreditType(CreditType.MORTGAGE);
            } else if (number == 2) {
                creditRequest.setCreditType(CreditType.CONSUMER);
            }
        } else if (creditRequest.getProgramCode() == null || creditRequest.getProgramCode().isEmpty()) {
            System.out.print("Введите номер программы: \n");
            String prog = in.next();
            creditRequest.setProgramCode(prog);
        } else if (creditRequest.getCreditQty() == 0) {
            System.out.print("Введите сумму кредита: \n");
            int qty = in.nextInt();
            creditRequest.setCreditQty(qty);
        } else if (creditRequest.getBorrower().getBorrowerType() == null) {
            System.out.print("Введите тип заемщика: \n1 - GAZPROM\n2 - GAZPROM_GROUP\n3 - BANK\n4 - OTHER\n5 - OUTER\n");
            int number = in.nextInt();
            setBorrowerType(creditRequest.getBorrower(), number);
        } else if (creditRequest.getMarketType() == null) {
            System.out.print("Введите тип рынка: \n1 - Первичный\n2 - Вторичный\n");
            int number = in.nextInt();
            if (number == 1) {
                creditRequest.setMarketType(MarketType.PRIMARY_MARKET);
            } else if (number == 2) {
                creditRequest.setMarketType(MarketType.SECONDARY_MARKET);
            }
        } else if (creditRequest.getPrepayPercent() == -1) {
            System.out.print("Введите первоначальный (о1т 1 до 100 процентов)\n");
            double number = in.nextDouble();
            creditRequest.setPrepayPercent(number);
        }
    }

    private void setBorrowerType(Borrower borrower, int number) {
        switch (number) {
            case 1 : {
                borrower.setBorrowerType(BorrowerType.GAZPROM);
                return;
            }
            case 2 : {
                borrower.setBorrowerType(BorrowerType.GAZPROM_GROUP);
                return;
            }
            case 3 : {
                borrower.setBorrowerType(BorrowerType.BANK);
                return;
            }
            case 4 : {
                borrower.setBorrowerType(BorrowerType.OTHER);
                return;
            }
            case 5 : {
                borrower.setBorrowerType(BorrowerType.OUTER);
            }
        }
    }

    private void clean(CreditRequest creditRequest) {
        creditRequest.setProgramCode(null);
        creditRequest.setCreditQty(0);
        creditRequest.getBorrower().setBorrowerType(null);
        creditRequest.setCreditType(null);
        creditRequest.setPrepayPercent(-1);
        creditRequest.setMarketType(null);
    }

}
