package POJOclasses;

public class ContractorTeachingSalaryTotals extends SalaryTotals{
  private int contractSalary;
  private int familySupport;
  private int libraryGrant;


  public int getContractSalary() {
    return contractSalary;
  }

  public void setContractSalary(int contractSalary) {
    this.contractSalary = contractSalary;
  }


  public int getFamilySupport() {
    return familySupport;
  }

  public void setFamilySupport(int familySupport) {
    this.familySupport = familySupport;
  }


  public int getLibraryGrant() {
    return libraryGrant;
  }

  public void setLibraryGrant(int libraryGrant) {
    this.libraryGrant = libraryGrant;
  }

}
