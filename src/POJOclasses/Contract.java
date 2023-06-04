package POJOclasses;

public class Contract {

  private String contractid;
  private String sid;
  private int contractSalary;
  private int contractStartYear;
  private int contractStartMonth;
  private int contractEndYear;
  private int contractEndMonth;


  public String getContractid() {
    return contractid;
  }

  public void setContractid(String contractid) {
    this.contractid = contractid;
  }


  public String getSid() {
    return sid;
  }

  public void setSid(String sid) {
    this.sid = sid;
  }


  public int getContractSalary() {
    return contractSalary;
  }

  public void setContractSalary(int contractSalary) {
    this.contractSalary = contractSalary;
  }


  public int getContractStartYear() {
    return contractStartYear;
  }

  public void setContractStartYear(int contractStartYear) {
    this.contractStartYear = contractStartYear;
  }


  public int getContractStartMonth() {
    return contractStartMonth;
  }

  public void setContractStartMonth(int contractStartMonth) {
    this.contractStartMonth = contractStartMonth;
  }


  public int getContractEndYear() {
    return contractEndYear;
  }

  public void setContractEndYear(int contractEndYear) {
    this.contractEndYear = contractEndYear;
  }


  public int getContractEndMonth() {
    return contractEndMonth;
  }

  public void setContractEndMonth(int contractEndMonth) {
    this.contractEndMonth = contractEndMonth;
  }

}
