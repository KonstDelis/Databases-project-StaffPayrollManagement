package POJOclasses;

public class SalaryDepositData {

  private String depositid;
  private String staffFirstName;
  private String staffLastName;
  private int dateMonth;
  private int dateYear;
  private int salary;
  private String sid;


  public String getDepositid() {
    return depositid;
  }

  public void setDepositid(String depositid) {
    this.depositid = depositid;
  }


  public String getStaffFirstName() {
    return staffFirstName;
  }

  public void setStaffFirstName(String staffFirstName) {
    this.staffFirstName = staffFirstName;
  }


  public String getStaffLastName() {
    return staffLastName;
  }

  public void setStaffLastName(String staffLastName) {
    this.staffLastName = staffLastName;
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


  public String getSid() {
    return sid;
  }

  public void setSid(String sid) {
    this.sid = sid;
  }

}
