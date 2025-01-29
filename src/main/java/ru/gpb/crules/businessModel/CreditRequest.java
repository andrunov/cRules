package ru.gpb.crules.businessModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreditRequest {

    private CreditType creditType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Calendar applicDate;
    private String programCode;
    private double creditQty;
    private double duration;
    private double prepayPercent;
    private Borrower borrower;
    private MarketType marketType;

    public CreditRequest(String programCode, int creditQty) {
        this.programCode = programCode;
        this.creditQty = creditQty;
    }

    public boolean isComplete() {
        if (creditType == null) {
            return false;
        } else if (creditType == CreditType.CONSUMER) {
            return this.programCode != null
                    && !this.programCode.isEmpty()
                    && this.creditQty != 0
                    && borrower.getBorrowerType() != null;
        } else if (creditType == CreditType.MORTGAGE) {
            return this.programCode != null
                    && !this.programCode.isEmpty()
                    && this.creditQty != 0
                    && this.prepayPercent != -1
                    && this.marketType != null
                    && borrower.getBorrowerType() != null;
        }
        return false;
    }

    public CreditRequest() {
    }

    public Calendar getApplicDate() {
        return applicDate;
    }

    public void setApplicDate(Calendar applicDate) {
        this.applicDate = applicDate;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public double getCreditQty() {
        return creditQty;
    }

    public void setCreditQty(double creditQty) {
        this.creditQty = creditQty;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public MarketType getMarketType() {
        return marketType;
    }

    public void setMarketType(MarketType marketType) {
        this.marketType = marketType;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    public double getPrepayPercent() {
        return prepayPercent;
    }

    public void setPrepayPercent(double prepayPercent) {
        this.prepayPercent = prepayPercent;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(applicDate.getTime());
        final StringBuilder sb = new StringBuilder("CreditRequest{");
        sb.append("applicDate=").append(strDate);
        sb.append(", programCode='").append(programCode).append('\'');
        if (creditType == CreditType.MORTGAGE) {
            sb.append(", marketType=").append(marketType);
        }
        sb.append(", creditQty=").append(creditQty);
        if (creditType == CreditType.MORTGAGE) {
            sb.append(String.format(", prepayPercent=%.1f", prepayPercent));
        }
        sb.append(String.format(", duration=%.0f", duration));
        sb.append(", borrower=").append(borrower);
        sb.append('}');
        return sb.toString();
    }
}
