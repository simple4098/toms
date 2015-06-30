
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
/**
 *
 * <p>Title: JSON-XML转换工具</p>
 * <p>desc:
 * <p>Copyright: Copyright(c)Gb 2012</p>
 * @author http://www.ij2ee.com
 * @time 上午8:20:40
 * @version 1.0
 * @since
 */
public class XmlJSON {
    private static final String STR_JSON = "{\"name\":\"Michael\",\"address\":{\"city\":\"Suzou\",\"street\":\" Changjiang Road \",\"postcode\":100025},\"blog\":\"http://www.ij2ee.com\"}";
    public static String xml2JSON(String xml){
        return new XMLSerializer().read(xml).toString();
    }

    public static String json2XML(String json){
        JSONObject jobj = JSONObject.fromObject(json);
        String xml =  new XMLSerializer().write(jobj);
        return xml;
    }
class A{
    private String k1;
    private String k2;

    public String getK1() {
        return k1;
    }

    public void setK1(String k1) {
        this.k1 = k1;
    }

    public String getK2() {
        return k2;
    }

    public void setK2(String k2) {
        this.k2 = k2;
    }
}
    public static void main(String[] args) {
        /*String xml = json2XML(STR_JSON);
        System.out.println("xml = "+xml);
        String json = xml2JSON(xml);
        System.out.println("json="+json);*/
        JSONObject js = new JSONObject();
        js.put("k1", "v1");
        js.put("k2", "v2");
        System.out.println(js.toString());
        String xml = json2XML(js.toString());
        System.out.println("xml = "+xml);

    }
}
