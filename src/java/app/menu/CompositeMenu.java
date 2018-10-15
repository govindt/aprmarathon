package app.menu;

import java.util.Vector;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class CompositeMenu extends Menu
{
    private Vector<Menu> list = new Vector<Menu>();

    /** @link aggregationByValue */
    /*# Menu lnkMenu; */

    public CompositeMenu(String menuId, String menuName)
    {
        super(menuId, menuName);
    }


    public CompositeMenu(String menuId, String menuName,String url)
    {
        super(menuId, menuName,url);
    }

    /**
     * Returns the list of child menus
     * @return collection of child menus
    */
    public Vector<Menu> listChildMenus()
    {
        return list;
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
     * Renders the section menus
     * @return String containing the section menus.
    */
    public String render()
    {
        StringBuffer sb = new StringBuffer();


        /*sb.append("addmenuitem(");
        sb.append("\"" + getLevelCoord() + "\",");
        sb.append("\"" + getMenuName() + "\",");
        if (null == getUrl())
            sb.append("null" + ",");
         else
	 sb.append("\"" + getUrl() + "\",");*/

	if ( list != null ) {
	    if ( list.size() == 0 ) {
			sb.append("<li><a href=\"" + core.util.Util.trim(getUrl()) + "\">" + getMenuName() + "</a></li>\n");
	    } else {
			sb.append("<li><span class=\"dir\">" + getMenuName() + "</span>\n\t<ul>\n");
	    }
	}


	

        /*sb.append("\"black\",\"996600\",\"white\",\"#E0DBBB\",\"white\",\"#E0DBBB\",\"font-family:Tahoma, Verdana, Arial; font-size:12px;font-weight:normal;text-decoration:none;padding: 4px\");");
	  sb.append("\n");*/


        int i=1;
	for ( int j = 0; j < list.size(); j++) {
            Menu menu = list.elementAt(j);

            //menu.setLevelCoord(getLevelCoord()+ i);
	    /*            menu.setLevelCoord(getLevelCoord() + "," + i); */
            sb.append(menu.render());
            i++;
        }
	if ( list != null ) {
	    if ( list.size() > 0 ) {
		sb.append("\t</ul>\n</li>\n");
	    }
	}

        return sb.toString();
    }//~public String render()...

    /**
     * Adds the menu to the list
     * @param Menu object
     * @return boolean value for success or failure.
     * @exception NoSuchElementException
    */
    public  boolean add(Menu menu) throws NoSuchElementException
    {
        list.add(menu);
        return true;
    }

  /**
     * Removes the menu from the list
     * @param menu object
     * @exception NoSuchElementException
    */
    public void remove(Menu menu) throws NoSuchElementException
    {
        list.remove(menu);
    }


}//~public class CompositeMe...

