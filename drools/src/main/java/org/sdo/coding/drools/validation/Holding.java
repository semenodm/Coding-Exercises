package org.sdo.coding.drools.validation;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 11/24/13
 * Time: 1:57 PM
 */
public class Holding {
    private String productCode;
    private String managerId;
    private HoldingType holdingType;

    public Holding(String productCode) {
        this.productCode = productCode;
    }

    public Holding(String productCode, String managerId){
        this(productCode);
        this.managerId =  managerId;
    }

    public String getProductCode() {
        return productCode;
    }

    public HoldingType getHoldingType(){
        return this.holdingType;
    }

    public void setHoldingType(HoldingType holdingType) {
        this.holdingType = holdingType;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
}
