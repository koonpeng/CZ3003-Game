package cz3003.pptx.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.*;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * This class handles some SQL Connection
 * @author Wang Bowen-
 *
 */

public class DbConfig {
	private String username;
	private String password;
	private String tablename;
	private LabelStyle style;
		// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	//static final String DB_URL = "jdbc:mysql://192.168.1.172/leaderboard";
	//static final String DB_URL = "jdbc:mysql://10.0.2.2/leaderboard";
	static final String DB_URL = "jdbc:mysql://10.27.14.48:3306/leaderboard";
	static final String URL_http="10.27.14.48";
	//  Database credentials
	static final String USER = "wangbwhz";
	static final String PASS = "123456";	

	public DbConfig(String user, String pass, String tbname) {
		username = user;
		password = pass;
		tablename = tbname;

	}
	public Group getsummery()
	{	
	style = new LabelStyle(CusFontStyle.getBoldFont(), CusFontStyle
				.getTopbarFont().getColor());
	Group group=new Group();
		Connection conn = null;
		Statement stmt =null;
		boolean status=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.print("ffffkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		}
		try {
			Table table = new Table();
			//table.setPosition(380, 700);
			Label lblusername = new Label("Username", style);
			Label lbldugeonid = new Label("Dugeonid", style);
			Label lblsocre = new Label("Score", style);
			table.add(lblusername).width(120).align(Align.center).pad(10);
			table.add(lbldugeonid).width(120);
			table.add(lblsocre).width(120);
//			

			conn = DriverManager.getConnection(DB_URL, username, password);
			stmt = conn.createStatement();
			 String sql = "SELECT * FROM playinghistory";
			//STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(sql);
		      while(rs.next()){
		          //Retrieve user name
		    	  String username  = rs.getString("username");
		          int dugeonid = rs.getInt("dugeonid");
		          int score = rs.getInt("score");
		          //Display values
		          System.out.print("ID: " + username);
		          System.out.print(", Age: " + dugeonid);
		          System.out.print(", First: " + score);
		          
				table.row();
				
					Label labelcount = new Label(username,style);
					Label lbldugeonid2 = new Label(String.valueOf(dugeonid), style);
					Label lblscore2 = new Label(String.valueOf(score), style);
					
					table.add(labelcount).width(120).align(Align.center).pad(10);
					table.add(lbldugeonid2).width(120);
					table.add(lblscore2).width(120);

				}
			group.addActor(table);
		      try {
					stmt.close();
				    conn.close();
				   } catch (SQLException e) {
				    e.printStackTrace();
				   }
		      
		}
	catch (SQLException e) {
		e.printStackTrace();}
		finally
		{
			try {
				stmt.close();
			    conn.close();
			   } catch (SQLException e) {
			    e.printStackTrace();
			   }
		}
	return group;
		
	}
	private List<Object> Search(Object obj,String strwhere) {
		List<Object> list = new ArrayList<Object>();
		Object objectCopy = null;
		Connection conn = null;
		Statement stmt=null;
		
		Field[] fields = obj.getClass().getDeclaredFields();
		String sql = "select ";
		for (int i = 0; i < fields.length; i++) {
			sql = sql + fields[i].getName();
			if (i != fields.length - 1) {
				sql = sql + ",";
			} else {
				sql = sql + " from " + tablename;
				if(strwhere!="")
				{
					sql=sql+" where "+strwhere;
				}
				
			}
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}// 加载驱动程序
		try {
			conn = DriverManager.getConnection(DB_URL, username, password);
			stmt = conn.createStatement();// 创建Statement
			Class<?> classType = obj.getClass();
			ResultSet rs = stmt.executeQuery(sql);// ResultSet类似Cursor

			while (rs.next()) {

				objectCopy = classType.newInstance();
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					String fieldName = field.getName();
					String firstLetter = fieldName.substring(0, 1).toUpperCase();
					String setMethodName = "set" + firstLetter
							+ fieldName.substring(1);
					if (rs.getObject(fieldName) == null) {
						continue;
					}
					Method method1 = classType.getDeclaredMethod(setMethodName,
							new Class[] { field.getType() });
					method1.invoke(objectCopy,
							new Object[] { rs.getObject(fieldName)});

				}
				list.add(objectCopy);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}

		finally {
			try {
				stmt.close();
			    conn.close();
			   } catch (SQLException e) {
			    e.printStackTrace();
			   }
		}

		return list;
	}
	public List<Object> searchSpecific(Object obj,String searchingfiled,String searchingvalue) 
	{
		return Search(obj,searchingfiled+"='"+searchingvalue+"'");
	}
	public List<Object> searchAll(Object obj)
	{
		return Search(obj,"");
	}
	
	public boolean insert(String values) {
		Connection conn = null;
		Statement stmt =null;
		boolean status=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, username, password);
			stmt = conn.createStatement();
			String sql="insert into "+tablename+" values("+values+")";  
			int t = stmt.executeUpdate (sql); 
			if(t>=0)
			{
				status=true;
			}			
		}
	catch (SQLException e) {
		e.printStackTrace();}
		finally
		{
			try {
				stmt.close();
			    conn.close();
			   } catch (SQLException e) {
			    e.printStackTrace();
			   }
		}
		return status;
	}
	public boolean batchinsert(String values[],int datacount) {
		Connection conn = null;
		Statement stmt =null;
		boolean status=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String sql;
			conn = DriverManager.getConnection(DB_URL, username, password);
			stmt = conn.createStatement();
			for(int i=0;i<datacount;i++)
			{
				sql="insert into "+tablename+" values("+values[i]+")"; 
				stmt.addBatch(sql);
			}
			int t[] = stmt.executeBatch();
			status=true;
			for(int j=0;j<t.length;j++)
			{
				if(t[j]<0)
				{
					status=false;
				}
				
			}
		}
	catch (SQLException e) {
		e.printStackTrace();}
		finally
		{
			try {
				stmt.close();
			    conn.close();
			   } catch (SQLException e) {
			    e.printStackTrace();
			   }
		}
		return status;
	}
	public boolean update(String fieldname,String value,String searchingfiled,String searchingvalue) 
	{
		boolean status=false;;
		Connection conn = null;
		Statement stmt=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, username, password);
			stmt = conn.createStatement();
			String sql="update "+tablename+" set "+fieldname+" ='"+value+"' where "+searchingfiled+"="+searchingvalue;  
			int t = stmt.executeUpdate (sql); 
			if(t>=0)
			{
				status=true;
			}
		}
	catch (SQLException e) {
		e.printStackTrace();}
		finally
		{
			try {
				stmt.close();
			    conn.close();
			   } catch (SQLException e) {
			    e.printStackTrace();
			   }
		}
		return status;
	}
}
