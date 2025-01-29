package ru.gpb.crules.businessModel;

public class Borrower {
    private double age;
    private boolean salaryClient;
    private BorrowerType borrowerType;

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public boolean isSalaryClient() {
        return salaryClient;
    }

    public void setSalaryClient(boolean salaryClient) {
        this.salaryClient = salaryClient;
    }

    public BorrowerType getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(BorrowerType borrowerType) {
        this.borrowerType = borrowerType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Borrower{");
        sb.append("age=").append(age);
        sb.append(", salaryClient=").append(salaryClient);
        sb.append(", borrowerType=").append(borrowerType);
        sb.append('}');
        return sb.toString();
    }
}
