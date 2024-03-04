import java.sql.*;
import java.util.Scanner;
public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String username = "root";
    private static final String password = "@Rushi31";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return; // Exit the program if driver not found
        }

        try {
            //transaction cotroling
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            String debitq="update money set balence=balence - ? where id = ?";
            String creditq="update money set balence=balence + ? where id = ?";
            PreparedStatement debitpreparedStatement=connection.prepareStatement(debitq);
            PreparedStatement creditpreparedStatement= connection.prepareStatement(creditq);
            Scanner sc=new Scanner(System.in);
            System.out.print("Enter your account number : ");
            int acc1=sc.nextInt();
            System.out.print("Enter sender account number : ");
            int acc2=sc.nextInt();
            System.out.print("Enter ammount to send sender : ");
            double ammount=sc.nextDouble();
            debitpreparedStatement.setDouble(1,ammount);
            debitpreparedStatement.setInt(2,acc1);
            creditpreparedStatement.setDouble(1,ammount);
            creditpreparedStatement.setDouble(2,acc2);
            debitpreparedStatement.executeLargeUpdate();
            creditpreparedStatement.executeUpdate();
            if(checkbalence(connection,acc1,ammount)){
                connection.commit();
                System.out.println("Transaction Successful !!");
            }else {
                connection.rollback();
                System.out.println("Transaction Failed !!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    static boolean checkbalence(Connection connection,int acc1,double ammount )
    {
        try {
            String  query="select balence from money where id = ?";
            PreparedStatement preparedStatement= connection.prepareStatement(query);

            preparedStatement.setInt(1,acc1);

            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next())
            {
                double cur_bal=resultSet.getDouble("balence");
                if(ammount>cur_bal)
                {
                    return  false;
                }
                else {
                    return true;
                }
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return false;

    }
}
//1.
/*
       try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            //single row insert
            //String query=String.format("insert into students(name,age,marks) values('%s',%d,%f)","Rahul",16,96.8);
            //rows show
                String query4 = "select * from students where id = 6";
                //retrieving data
                ResultSet resultSet = statement.executeQuery(query4);

                System.out.println("ID\tNAME\t\tAGE\t\tMARKS");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    double marks = resultSet.getDouble("marks");
                    System.out.println(id+"\t"+name+"\t\t"+age+"\t\t"+marks);
                }
                 } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//2
//simple statement program
        try {
                Connection connection = DriverManager.getConnection(url, username, password);
                //multiple data insert

                Statement statement = connection.createStatement();
                int i,n;
                Scanner sc=new Scanner(System.in);
                while(true)
                {
                    String n1;
                    int a1;
                    double m1;
                    System.out.println("Enter your Name: ");
                    n1=sc.next();
                    System.out.println("Enter your Age: ");
                    a1=sc.nextInt();
                    System.out.println("Enter your Marks: ");
                    m1= sc.nextDouble();
                    String query =String.format("insert into students(name,age,marks) values('%s',%d,%f)",n1,a1,m1);
                    int row=statement.executeUpdate(query);
                    if(row>0){
                        System.out.println("Data Inserted Successfully");
                    }
                    else {
                        System.out.println("Data Insertion Failed");
                    }
                    System.out.println("You want to add more details then press 1/0 : ");
                    n=sc.nextInt();

                    if(n==0)
                    {
                    System.exit(0);
                    }
              }
                 //rows show
                String query4 = "select * from students where id = 6";
                //retrieving data
                ResultSet resultSet = statement.executeQuery(query4);

                System.out.println("ID\tNAME\t\tAGE\t\tMARKS");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    double marks = resultSet.getDouble("marks");
                    System.out.println(id+"\t"+name+"\t\t"+age+"\t\t"+marks);
                }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



//3.

             try {
                Connection connection = DriverManager.getConnection(url, username, password);
                //multiple data insert

                Statement statement = connection.createStatement();

             // update value
            String query2=String.format("update students set name= '%s' where id= %d","Ramraj",6);
            int row1=statement.executeUpdate(query2);
            if(row1>0){
                System.out.println("Data Updated Successfully");
            }
            else {
                System.out.println("Data Updation Failed");
            }
            }catch (SQLException e) {
            System.out.println(e.getMessage());
        }


//4.

         try {
                Connection connection = DriverManager.getConnection(url, username, password);
                //multiple data insert

                Statement statement = connection.createStatement();


            //delete row
            String query3="delete from students where id =4";
            int row2=statement.executeUpdate(query3);
            if(row2>0){
                System.out.println("Data Delete Successfully");
            }
            else {
                System.out.println("Data Deletion Failed");
            }
          }catch (SQLException e) {
            System.out.println(e.getMessage());
        }




//5

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            //*******IMP--> prepared statement
            String q="insert into students(name,age,marks) values(?, ?, ?)";//only one time execute
            PreparedStatement preparedStatement= connection.prepareStatement(q);
            preparedStatement.setString(1,"Ankita");
            preparedStatement.setInt(2,22);
            preparedStatement.setDouble(3,90.0);
            int row3=preparedStatement.executeUpdate();
            if(row3>0){
                System.out.println("Data insert Successfully");
            }
            else {
                System.out.println("Data insertion Failed");
            }
            }catch (SQLException e) {
            System.out.println(e.getMessage());
        }


//6.

 try {
            Connection connection = DriverManager.getConnection(url, username, password);
            //*******IMP--> prepared statement
            String q="update students set marks=? where id=?";//only one time execute
            PreparedStatement preparedStatement= connection.prepareStatement(q);
            preparedStatement.setDouble(1,99.0);
            preparedStatement.setInt(2,2);

            int row3=preparedStatement.executeUpdate();
            if(row3>0){
                System.out.println("Data update Successfully");
            }
            else {
                System.out.println("Data updation Failed");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//7.
  try {
            //batch wise data enter
            Connection connection = DriverManager.getConnection(url, username, password);
            String query ="insert into students(name,age,marks)values(?, ?, ?)";
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            Scanner sc=new Scanner(System.in);
            while (true)
            {
                String name;
                int age;
                double marks;
                try {

                    System.out.println("Enter your Name: ");
                    name = sc.next();
                    System.out.println("Enter your Age: ");
                    age = sc.nextInt();
                    System.out.println("Enter your Marks: ");
                    marks = sc.nextDouble();
                    preparedStatement.setString(1,name);
                    preparedStatement.setInt(2,age);
                    preparedStatement.setDouble(3,marks);
                    preparedStatement.addBatch();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                System.out.print("you have add more data (Y/N) : ");
                String ch=sc.next();
                if(ch.toUpperCase().equals("N")){
                    break;
                }
            }
            int []arr= preparedStatement.executeBatch();
            for(int i=0;i<arr.length;i++){
                if(arr[i]==0){
                    System.out.println("Query : "+i+"th not execute successfully");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


 */