package deepcopy.entities;

import java.math.BigDecimal;

public enum SomeEnum {
    R,
    V;

    BigDecimal field = BigDecimal.ONE;

    Company company;

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }
}