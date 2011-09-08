package exercises.parameteriseconstructor;

public class Database {
  
  public Database() {
    throw new UnsupportedOperationException("You don't have time for this!");
  }

  // Let's pretend that there's all sorts of JDBC stuff here
  public String getMotd() {
    return "Keep calm and carry on coding";
  }
}