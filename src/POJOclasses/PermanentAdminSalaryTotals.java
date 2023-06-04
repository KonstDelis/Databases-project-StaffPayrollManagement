package POJOclasses;

public class PermanentAdminSalaryTotals extends SalaryTotals{
  private int basicSetIncome;
  private int familySupport;
  private int yearsWorkedBonus;



  public int getBasicSetIncome() {
    return basicSetIncome;
  }

  public void setBasicSetIncome(int basicSetIncome) {
    this.basicSetIncome = basicSetIncome;
  }


  public int getFamilySupport() {
    return familySupport;
  }

  public void setFamilySupport(int familySupport) {
    this.familySupport = familySupport;
  }


  public int getYearsWorkedBonus() {
    return yearsWorkedBonus;
  }

  public void setYearsWorkedBonus(int yearsWorkedBonus) {
    this.yearsWorkedBonus = yearsWorkedBonus;
  }

}
