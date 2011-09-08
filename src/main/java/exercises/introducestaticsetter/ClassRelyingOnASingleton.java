package exercises.introducestaticsetter;

public class ClassRelyingOnASingleton {
  public String greet(String name) {
    return Singleton.getInstance().getGreeting() + " " + name;
  }

}
