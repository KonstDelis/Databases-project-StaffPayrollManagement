package POJOclasses;

public class SalaryTotals extends Staff {

  private String staffType;
  private int dateMonth;
  private int dateYear;
  private int salary;


  public String getStaffType() {
    return staffType;
  }

  public void setStaffType(String staffType) {
    this.staffType = staffType;
  }


  public int getDateMonth() {
    return dateMonth;
  }

  public void setDateMonth(int dateMonth) {
    this.dateMonth = dateMonth;
  }


  public int getDateYear() {
    return dateYear;
  }

  public void setDateYear(int dateYear) {
    this.dateYear = dateYear;
  }


  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

}
