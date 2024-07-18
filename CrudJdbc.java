import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class CrudJdbc {
    public static void printOptions(){
        System.out.println("------  CRUD MENU ------");
        System.out.println("1. Show Data ");
        System.out.println("2. Insert ");
        System.out.println("3. Update with Id");
        System.out.println("4. Update with Name");
        System.out.println("5. Delete with Id");
        System.out.println("6. Delete with Name");
        System.out.println("7. Exit");
    }
    public static void main(String[] args) throws IOException, SQLException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        AllInOne all = new AllInOne();
        while(true){
            printOptions();
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(bf.readLine());

            switch(choice){
                case 1:
                    all.select();
                    break;
                case 2:
                    Student s1 = new Student();
                    System.out.println("Enter id: ");
                    s1.id = Integer.parseInt(bf.readLine());
                    System.out.print("Enter the Name: ");
                    s1.name = bf.readLine();
                    all.insert(s1);
                    break;
                case 3:
                    Student s2 = new Student();
                    System.out.println("Enter id for which name has to be updated :");
                    s2.id = Integer.parseInt(bf.readLine());
                    System.out.println("Enter the updated Name: ");
                    s2.name = bf.readLine();
                    all.updateWithId(s2);
                    break;
                case 4:
                    Student s3 = new Student();
                    System.out.println("Enter the Name for which id has to be updated: ");
                    s3.name = bf.readLine();
                    System.out.println("Enter the updated Id");
                    s3.id = Integer.parseInt(bf.readLine());
                    all.updateWithName(s3);
                    break;
                case 5:
                    System.out.println("Enter the Id which has to be deleted: ");
                    all.deleteWithId(Integer.parseInt(bf.readLine()));
                    break;
                case 6:
                    System.out.println("Enter the Name which has to be deleted: ");
                    all.deleteWithName(bf.readLine());
                    break;
                case 7:
                    all.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please enter valid Input Choice: ");
            }
        }
    }
}

class AllInOne{
    Connection conn=null;
    Statement statement = null;
    PreparedStatement pstatement = null;
    final String URL = "jdbc:mysql://localhost:3306/University";
    final String USERNAME = "root";
    final String PASSWORD = "";

    public AllInOne() throws SQLException {
        conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);

    }
    public void insert(Student s) throws SQLException {
        pstatement = conn.prepareStatement("insert into StudentData values (?,?);");
        pstatement.setInt(1,s.id);
        pstatement.setString(2,s.name);
        pstatement.executeUpdate();
        System.out.println("Insert successfully !!");
    }
    public void deleteWithId(int id) throws SQLException {
        pstatement = conn.prepareStatement("delete from StudentData where id = ?");
        pstatement.setInt(1,id);
        pstatement.executeUpdate();
        System.out.println("Successfully deleted the record with id "+id);
    }

    public void deleteWithName(String name) throws SQLException {
        pstatement = conn.prepareStatement("delete from StudentData where name = ?");
        pstatement.setString(1,name);
        pstatement.executeUpdate();
        System.out.println("Successfully deleted the record with name "+name);
    }

    public void select() throws SQLException{
        statement = conn.createStatement();
        ResultSet res = statement.executeQuery("select * from StudentData");
        while(res.next()){
            String data = res.getInt(1)+" : "+res.getString(2);
            System.out.println(data);
        }
    }

    public void updateWithId(Student s) throws SQLException{
        pstatement = conn.prepareStatement("update StudentData set name = ? where id = ?");
        pstatement.setInt(2,s.id);
        pstatement.setString(1,s.name);
        pstatement.executeUpdate();
        System.out.println("Successfully updated record with id "+s.id);
    }

    public void updateWithName(Student s) throws SQLException{
        pstatement = conn.prepareStatement("update StudentData set id = ? where name = ?");
        pstatement.setInt(1,s.id);
        pstatement.setString(2,s.name);
        pstatement.executeUpdate();
        System.out.println("Successfully updated record with name "+s.name);
    }

    public void close() throws SQLException {
        conn.close();
        statement.close();
        pstatement.close();
    }
}