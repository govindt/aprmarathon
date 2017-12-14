package app.menu;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.sql.*;

import org.w3c.dom.*;

public class JDBCMenuBuilder implements MenuBuilder
{
    private int coord=0;
    private String driver;
    private String url;
    private String username;
    private String password;
    private ArrayList<CompositeMenu> topMenus = new ArrayList<CompositeMenu>();


    public JDBCMenuBuilder(String driver, String url, String username, String password)
    {
           this.driver = driver;
           this.url = url;
           this.username = username;
           this.password = password;


    }

    public String renderMenu() throws MenuException
    {
            String str = buildTopMenu();

            return str;
    }

    private String buildTopMenu() throws MenuException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try{
                Class.forName(driver);
                con = DriverManager.getConnection(url, username, password);
                ps = con.prepareStatement("select menuid, menuname, url from HierarchyMenu where parentmenuid is null order by menuord");
                rs = ps.executeQuery();
                while(rs.next()){
                     CompositeMenu aTopMenu = new CompositeMenu(rs.getString(1), rs.getString(2), rs.getString(3));
                     topMenus.add(aTopMenu);
                }
        }
        catch(Exception ex)
        {
                throw new MenuException("Unable to get root menu from the database", ex);
        }
        finally
        {
             try{
             if (rs != null){
                rs.close();
                rs=null;
             }
             if (ps != null){
                ps.close();
                ps=null;
             }
             if (con != null){
                con.close();
                con=null;
             }
             }catch(Exception e){}
        }

        StringBuffer sb = new StringBuffer();
        int j=1;
        for (int i=0;i<topMenus.size();i++)
        {
         CompositeMenu menu = topMenus.get(i);
         menu.setLevelCoord(Integer.toString(j));
         j++;
         buildMenu(menu.getMenuId(), menu);
         sb.append(menu.render());
     }
        //System.out.println(sb.toString());
        return sb.toString();
    }

    private boolean isLeaf(String menuId)throws MenuException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        //System.out.println(menuId);
        boolean isLeaf = false;
        try{
                //Class.forName(driver);
                con = DriverManager.getConnection(url, username, password);
                ps = con.prepareStatement("select count(menuid) from HierarchyMenu where parentmenuid =?");
                ps.setString(1, menuId.trim());
                rs = ps.executeQuery();
                if (rs.next()){
                        //leaf node
                        int i = rs.getInt(1);
                        if (i < 1)
                           isLeaf = true;
                }
                return isLeaf;
        }
        catch(Exception ex)
        {
                throw new MenuException("Unable to get data from the database", ex);
        }
        finally
        {
             try{
             if (rs != null){
                rs.close();
                rs=null;
             }
             if (ps != null){
                ps.close();
                ps=null;
             }
             if (con != null){
                con.close();
                con=null;
             }
           }catch(Exception e){}
        }

    }

    private void buildMenu(String menuId, CompositeMenu comSrc) throws MenuException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        //System.out.println(menuId);
        try{
                //Class.forName(driver);
                con = DriverManager.getConnection(url, username, password);
                ps = con.prepareStatement("select menuid, menuname, url, parentmenuid from HierarchyMenu where parentmenuid =? order by menuord");
                ps.setString(1, menuId.trim());
                rs = ps.executeQuery();
                while(rs.next()){
                     String childMenuId = rs.getString(1);
                     String menuName = rs.getString(2);
                     String href = rs.getString(3);
                     String parentId = rs.getString(4);
                     //System.out.println(childMenuId + " " + menuName + " " + parentId + " " + href);
                     if (isLeaf(childMenuId)) //simple menu
                     {
                            SimpleMenu sm = new SimpleMenu(childMenuId , menuName, href);
                            comSrc.add(sm);
                     }
                     else
                     {
                        //System.out.println("inside submenu" + childMenuId);
                        CompositeMenu aParentMenu = new CompositeMenu(childMenuId, menuName);
                        comSrc.add(aParentMenu);
                        buildMenu(childMenuId, aParentMenu);
                     }
                }
        }
        catch(Exception ex)
        {
                throw new MenuException("Unable to get data from the database", ex);
        }
        finally
        {
             try{
             if (rs != null){
                rs.close();
                rs=null;
             }
             if (ps != null){
                ps.close();
                ps=null;
             }
             if (con != null){
                con.close();
                con=null;
             }
           }catch(Exception e){}
        }
    }



    public static void main(String argv[]) throws Exception
    {
        JDBCMenuBuilder builder = new JDBCMenuBuilder("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@fxs04ntdev:1521:fccd", "fxapp2", "fxapp2");
        builder.renderMenu();
    }

}
