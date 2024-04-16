import java.sql.*;
import java.util.Scanner;

public class SQLQuery {
    public static void main(String[] args)  {
        String user="root";
        String password="yz20040813";
        String url="jdbc:mysql://localhost:3306/movies?useUnicode=true&characterEncoding=utf8";
        try { // 加载数据库驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try { // 通过访问数据库的URL获取数据库连接对象
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("数据库movies连接成功");
            Scanner scanner = new Scanner(System.in);
            int queryNumber;
            while(true) {
                System.out.println("请输入查询编号（1-9），输入0退出：");
                queryNumber = scanner.nextInt();

                if (queryNumber == 0) {
                    System.out.println("程序已退出。");
                    break;
                }
                // 根据查询编号初始化 SQL 查询语句
                String SQLquery;
                switch (queryNumber) {
                    case 1:
                        SQLquery = "select count(title) as number from movies;";
                        break;
                    case 2:
                        SQLquery = "select title from movies;";
                        break;
                    case 3:
                        SQLquery = "select title, year from movies where year=(select min(year) from movies);";
                        break;
                    case 4:
                        SQLquery = "select count(*) as number from actors;";
                        break;
                    case 5:
                        SQLquery = "select * from actors where familyName='Zeta-Jones';";
                        break;
                    case 6:
                        SQLquery = "select distinct genre from belongsto;";
                        break;
                    case 7:
                        SQLquery = "select title, year from Movies where id IN (select movie from Directs where director = (select id from Directors where familyName = 'Spielberg'));";
                        break;
                    case 8:
                        SQLquery = "select familyName, givenNames from Actors where id IN (select actor from AppearsIn group BY actor having count(movie) = (select count(*) from Movies));";
                        break;
                    case 9:
                        SQLquery = "select familyName, givenNames from Directors where id NOT IN (select distinct director from Directs);";
                        break;
                    default:
                        System.out.println("Invalid query number.");
                        continue;

                }

                // 创建声明并执行查询
                Statement statement = con.createStatement();
                long startTime = System.currentTimeMillis();
                ResultSet rs = statement.executeQuery(SQLquery);
                long endTime = System.currentTimeMillis();

                // 处理并打印查询结果
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rsmd.getColumnLabel(i) + " ");
                }
                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(rs.getString(i) + " ");
                    }
                    System.out.println();
                }

                // 关闭资源
                statement.close();
                rs.close();
                // 打印执行时间
                System.out.println("执行时间: " + (endTime - startTime) + " 毫秒");


                }

            con.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
//测试变化
}
