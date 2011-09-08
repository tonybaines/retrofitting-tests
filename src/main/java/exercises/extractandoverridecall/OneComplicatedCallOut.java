package exercises.extractandoverridecall;

import java.util.Date;

public class OneComplicatedCallOut {

  public boolean isThisMyLuckyDay(String name, Date dob) {
    Date today = new Date();
    if (dob.equals(today)) {
      return true;
    }
    else {
      return AstrologicalCalculator.isThisALuckyDay(today, name, dob);
    }
  }
}
