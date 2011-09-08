package exercises.introducestaticsetter;

public class Singleton {
  private static Singleton instance;
  private final Database database;

  private Singleton() {
    // Expensive stuff...
    this.database = new Database();
  }
  
  public String getGreeting() {
    // get appropriate greetings from the database
    return database.getGreetingOfTheDay();
  }
  
  public static Singleton getInstance() {
    if (instance == null) {
      instance = new Singleton();
    }
    return instance;
  }
  
  
  // Let's pretend that there's all sorts of JDBC stuff here
  private class Database {
    String getGreetingOfTheDay() {
      return "hello";
    } 
  }
}
