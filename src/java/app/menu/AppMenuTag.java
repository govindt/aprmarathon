package app.menu;

public class AppMenuTag extends MenuTag
{
    private static final long serialVersionUID = 7526472295622776147L;
    private int usersId;
    public AppMenuTag() {
    }

    public int getUsersId() {
            return usersId;
    }

    public void setUsersId(int usersId) {
            this.usersId = usersId;
    }

    protected String getMenu() throws MenuException{
            MenuBuilder builder = new AppMenuBuilder(usersId);
            return builder.renderMenu();
    }

}
