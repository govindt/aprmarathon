package app.menu;

public class JDBCMenuTag extends MenuTag
{
    private static final long serialVersionUID = 7526472295622776147L;
    private String driverName;
    private String conurl;
    private String username;
    private String password;
    public JDBCMenuTag(){
    }

    public String getDriverName(){
            return driverName;
    }

    public void setDriverName(String driverName){
            this.driverName = driverName;
    }

    public String getDriverUrl(){
            return conurl;
    }

    public void setDriverUrl(String url){
            this.conurl = url;
    }

    public String getUserName(){
            return username;
    }

    public void setUserName(String username){
            this.username = username;
    }

    public String getPassword(){
            return password;
    }

    public void setPassword(String password){
            this.password = password;
    }

    protected String getMenu() throws MenuException{
            MenuBuilder builder = new JDBCMenuBuilder(driverName, conurl, username, password);
            return builder.renderMenu();
    }

}
