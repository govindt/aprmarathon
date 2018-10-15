package app.menu;

import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.Collection;

/**
 * Class that extends menu and implements its abstract method for menu functions.
 *
*/
public class SimpleMenu extends Menu
{
    public SimpleMenu( String menuId, String menuName,String url)
    {
        super(menuId, menuName,url);
        isLeaf=true;
    }

    /**
     * Returns the list of child menus
     * @return collection of child menus
    */
    public Vector<Menu> listChildMenus() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("This mehtod Cannot be invoked for simple menu item");
    }

    /**
     * Returns the child
     * @param functionid as string
     * @return the child
    */
    public Menu getChild(String s)
    {
        return null;
    }

    /**
     * Renders the function menus for the immediate section
     * @return String containing the function menus.
    */
    public String render()
    {
        StringBuffer sb = new StringBuffer();

        /*sb.append("addmenuitem(");
        sb.append("\"" + getLevelCoord() + "\",");
        sb.append("\"" + getMenuName() + "\",");
        sb.append("\"" + getUrl() + "\",");
        sb.append("\"black\",\"#996600\",\"white\",\"#E0DBBB\",\"white\",\"#E0DBBB\",\"font-family:Tahoma, Verdana, Arial; font-size:12px;font-weight:normal;text-decoration:none;padding: 4px\");");

        sb.append("\n");*/
	sb.append("<li><a href=\"" + core.util.Util.trim(getUrl()) + "\">" + getMenuName() + "</a></li>\n");

        return sb.toString();
    }//~public String render()...

  /**
     * Adds the function menu to the list
     * @param Menu object
     * @return boolean value for success or failure.
     * @exception NoSuchElementException
    */
    public  boolean add(Menu menu) throws NoSuchElementException, UnsupportedOperationException
    {
        // list.add(menu);
        throw new UnsupportedOperationException("This mehtod Cannot be invoked for simple menu item");
    }

    /**
     * Removes the function menu from the list
     * @param Menu object
     * @exception NoSuchElementException
    */
    public void remove(Menu menu) throws NoSuchElementException, UnsupportedOperationException
    {
        throw new UnsupportedOperationException("This mehtod Cannot be invoked for simple menu item");
    }
}//~public class SimpleMenu ...

