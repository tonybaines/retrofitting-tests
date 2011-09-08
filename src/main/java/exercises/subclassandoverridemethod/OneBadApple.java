package exercises.subclassandoverridemethod;

import java.util.Random;

public class OneBadApple {
  
  public String roll() {
    int rawValue = getRandomNumber();
    if (rawValue > 5 ) return "Six";
    if (rawValue > 4 ) return "Five";
    if (rawValue > 3 ) return "Four";
    if (rawValue > 2 ) return "Three";
    if (rawValue > 1 ) return "Two";
    return "One";
  }

  protected int getRandomNumber() {
    return new Random().nextInt(6);
  }

}
