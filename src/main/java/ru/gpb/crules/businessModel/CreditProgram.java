package ru.gpb.crules.businessModel;

public class CreditProgram {
    private String name;
    private String code;
    private double rate;
    private double minQuantity;
    private double maxQuantity;
    private double minDuration;
    private double maxDuration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(double minQuantity) {
        this.minQuantity = minQuantity;
    }

    public double getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(double maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public double getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(double minDuration) {
        this.minDuration = minDuration;
    }

    public double getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(double maxDuration) {
        this.maxDuration = maxDuration;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreditProgram{");
        sb.append("name='").append(name).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(String.format(", rate=%.1f", rate));
        sb.append(String.format(", minQuantity=%.0f", minQuantity));
        sb.append(String.format(", maxQuantity=%.0f", maxQuantity));
        sb.append(String.format(", minDuration=%.0f", minDuration));
        sb.append(String.format(", maxDuration=%.0f", maxDuration));
        sb.append('}');
        return sb.toString();
    }
}
