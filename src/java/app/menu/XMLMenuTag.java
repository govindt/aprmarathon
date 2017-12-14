package app.menu;

public class XMLMenuTag extends MenuTag
{
    private static final long serialVersionUID = 7526472295622776147L;
    private String xmlfile;
    public XMLMenuTag(){
    }

    public String getXmlFilename(){
            return xmlfile;
    }

    public void setXmlFilename(String xmlfile){
            this.xmlfile = xmlfile;
    }

    protected String getMenu() throws MenuException{
            XMLMenuBuilder builder = new XMLMenuBuilder(xmlfile);
            return builder.renderMenu();
    }

}
