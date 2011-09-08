package exercises.exposestaticmethod;

public class HardToInstantiateClass {

  private final Database db;
  private final Configuration config;
  private final Socket socket;
  public HardToInstantiateClass(Database db, Configuration config, Socket socket) {
    this.db = db;
    this.config = config;
    this.socket = socket;
  }

  public String translate(String statusCode) {
    if(statusCode.equals("ORA-00001")) return "Unique constraint violated. (Invalid data has been rejected)";
    if(statusCode.equals("ORA-00600")) return "Internal error (contact support)";
    if(statusCode.equals("ORA-03113")) return "End-of-file on communication channel (Network connection lost)";
    if(statusCode.equals("ORA-03114")) return "Not connected to ORACLE"; 
    if(statusCode.equals("ORA-00942")) return "Table or view does not exist"; 
    if(statusCode.equals("ORA-01017")) return "Invalid Username/Password";
    if(statusCode.equals("ORA-01031")) return "Insufficient privileges";
    if(statusCode.equals("ORA-01034")) return "Oracle not available (the database is down)";
    if(statusCode.equals("ORA-01403")) return "No data found";
    if(statusCode.equals("ORA-01555")) return "Snapshot too old (Rollback has been overwritten)";
    throw new IllegalArgumentException("Unknown status code " + statusCode);
  }

  
  public interface Database { /*lots of dependencies and methods*/ }
  public interface Configuration { /*lots of dependencies and methods*/ }
  public interface Socket { /*expensive*/ }

}
