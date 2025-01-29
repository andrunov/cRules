package ru.gpb.crules.validation;

import ru.gpb.crules.businessModel.CreditProgram;
import ru.gpb.crules.businessModel.CreditRequest;

public class ErrorValidation {

    private final CreditRequest creditRequest;
    private final CreditProgram creditProgram;
    private final StringBuilder sb;

    public ErrorValidation(CreditRequest creditRequest, CreditProgram creditProgram) {
        this.creditRequest = creditRequest;
        this.creditProgram = creditProgram;
        sb = new StringBuilder();
    }

    public String validate() {
        minimumSum();
        maximumSum();
        minimumDuration();
        maximumDuration();
        return sb.toString();
    }

    private void minimumSum() {
        if (creditRequest.getCreditQty() < creditProgram.getMinQuantity()) {
            sb.append("Сумма кредита меньше минимальной!; ");
        }
    }

    private void maximumSum() {
        if (creditRequest.getCreditQty() > creditProgram.getMaxQuantity()) {
            sb.append("Сумма кредита больше максимальной!; ");
        }
    }

    private void minimumDuration() {
        if (creditRequest.getDuration() < creditProgram.getMinDuration()) {
            sb.append("Срок кредита меньше минимального!; ");
        }
    }

    private void maximumDuration() {
        if (creditRequest.getDuration() > creditProgram.getMaxDuration()) {
            sb.append("Срок кредита больше максимального!; ");
        }
    }
}
