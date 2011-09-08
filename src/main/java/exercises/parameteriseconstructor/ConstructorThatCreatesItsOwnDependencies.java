package exercises.parameteriseconstructor;

public class ConstructorThatCreatesItsOwnDependencies {
  private Database database;

  public ConstructorThatCreatesItsOwnDependencies() {
    this.database = new Database();
    // ... other constructive work here
  }
  
  public String messageOfTheDay() {
    return database.getMotd();
  }
}
