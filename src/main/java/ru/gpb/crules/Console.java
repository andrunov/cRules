package ru.gpb.crules;

import org.camunda.bpm.dmn.engine.*;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import ru.gpb.crules.businessModel.*;
import ru.gpb.crules.exception.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Console {


    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchFieldException, ParseException, InvocationTargetException, IllegalAccessException {

        VariableMap variables = prepareVariableMap(initRequest());

        // parse decision from resource input stream
        InputStream inputStream = BeveragesDecider.class.getResourceAsStream("1_23_01.dmn");

        try {
            parseAndEvaluateDecision(variables, inputStream);

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }

    private static CreditRequest initRequest() {
        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setProgramCode("1.23.01");
        creditRequest.setCreditQty(100005);
        Calendar calendar = new GregorianCalendar(2024,Calendar.JANUARY, 1);
        creditRequest.setApplicDate(calendar);
        creditRequest.setPrepayPercent(-1);
        Borrower borrower = new Borrower();
        borrower.setSalaryClient(false);
        borrower.setBorrowerType(BorrowerType.GAZPROM);
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

    protected static VariableMap prepareVariableMap(CreditRequest creditRequest) {

        Date applicDate = creditRequest.getApplicDate().getTime();
        String borrowerType = creditRequest.getBorrower().getBorrowerType().toString();
        boolean salaryClient = creditRequest.getBorrower().isSalaryClient();
        double creditQty = creditRequest.getCreditQty();

        // prepare variables for decision evaluation
        VariableMap variables = Variables
                .putValue("applicDate", applicDate)
                .putValue("borrowerType", borrowerType)
                .putValue("salaryClient", salaryClient)
                .putValue("creditQty", creditQty);

        return variables;
    }

    protected static void parseAndEvaluateDecision(VariableMap variables, InputStream inputStream) {

        // create a new default DMN engine
        DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();

        //DmnDecision decision1 = dmnEngine.parseDecision("rate", inputStream);
        DmnDecision decision2 = dmnEngine.parseDecision("final", inputStream);

        // evaluate decision
        DmnDecisionResult result = dmnEngine.evaluateDecision(decision2, variables);

        // print result
        double rate = result.getSingleEntry();
        System.out.println("Rate is: " + rate);
    }


}
