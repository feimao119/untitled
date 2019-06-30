import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: heroc
 * Date: 19-6-13
 * Time: 下午3:44
 * To change this template use File | Settings | File Templates.
 */
public class MsRepoParse {
    private static final Logger logger = LoggerFactory.getLogger(MsRepoParse.class);
    private static final PropertyUtil prop = new PropertyUtil("E://work//untitled//src//main//resources//conf.properties");
    private static String Cookie = prop.getString("Cookie");
    private static final String MsHost = prop.getString("MsHost");

    private static final String Charset = "UTF-8";

    private String getRepoData(String frdate,String todate)  {

        // PostMethod method = new PostMethod("https://nss.m88ms.com/nss/TabBetSlipFrame.aspx");
        PostMethod method = new PostMethod("https://www."+MsHost+".com/Main/Sports/mSports/nss/RepoData.aspx");

        //method.setRequestHeader("DNT", "1");
        method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        method.setRequestHeader("Accept-Encoding", "gzip, deflate, br");
        method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        method.setRequestHeader("Cache-Control", "max-age=0");
        method.setRequestHeader("Connection", "keep-alive");
        method.setRequestHeader("Content-Length", "85");
        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
       // method.setRequestHeader("Cookie","ASP.NET_SessionId=aqjd2q1otxrbpsj0hhqzzb2h; incap_ses_1044_1721050=8vsjWng6vzQekziC7Al9DozyAV0AAAAANa5G+zMbL2+oaGBw76xJCw==; _ga=GA1.2.1977389373.1560408649; _gid=GA1.2.1328375733.1560408649; selectedLanguage=zh-CN; u=lzqiang2019; iovc=3875682; M88-A4=close; memLastLog=; lastdt=10~Soccer~2; category=0; t=VElDS0VUX0lEPTI4MjgyODQ3MSZTRVNTSU9OX0lEPTlGRURBRjUxRENERDRBQ0M2NTVBMkMxNTVBNUM2RDg5MzIzNkZBRUEyRTY3NDhGRDgxQUZEQTIwMjk1NjAzMURGNkU4QTg4MTFEN0IzOTgxMUU5QzdGMkQ4NjBDQ0ZFOTQyOTdFQTZCRDBFRDI3NDZFOTQ4MjExOURERUZGNkRDNUVCNUYyMzMzMTE1Rjk0MEFCNkMwMUQ4REY1QkIzNzY4MkMxQzk0MUVBQzQ5MEFBMzUxNUJDOTVBNUJBQjNCNUNFMDk5QTY5QkJCMUI2NkY3NUJGMDRFQzQxMzZBQTU0MjEwNjgzREUzNUU5MDczMjY5RTUyOTc4MERCRjE0RkQyNjdBOTgzNDlDQTdDQkIxNzZDODgxNTNBOEE4QTkxOTQyMEMxMTNBMDJCOURDMTZBRDQyODAyNTFCNkJBQzc0NjUwNkY1RjVFNEY4MzEyQjBCMkFFN0M4QjQwNjA1MUEwNUVCNkJBRTQ1NUFFMERDMUYyNzY1MkY3QjNDNThENA%3d%3d; puid=lzqiang2019; ot=2; curr1=RMB; OddsGroup=3; vp7=0; vp26=0; onHashID=; MPL=-99^5.0000^2000.0000^200000.0000|1^5.0000^25000.0000^200000.0000|2^5.0000^15000.0000^200000.0000|3^5.0000^3000.0000^200000.0000|90^5.0000^5000.0000^200000.0000|; curr=RMB; __utma=243143454.1977389373.1560408649.1560408725.1560408725.1; __utmc=243143454; __utmz=243143454.1560408725.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); lgnum_13=10_999; categoryMenu=0; dtC=2; lgnum_21=999; lgnum_12=10_999; srttyp=0; ms_session=%7B%22brand%22%3A%22M88%22%2C%22channel%22%3A%7B%22trafficSource%22%3A%22DIRECT%22%2C%22trafficReferral%22%3A%22%22%2C%22referralURL%22%3A%22%22%2C%22referralFullURL%22%3A%22%22%2C%22registerFrom%22%3A%22www.ms88ki.com%22%2C%22platform%22%3A%22Desktop%22%2C%22custom_data%22%3A%7B%7D%7D%7D; ms_traffic_source=DIRECT; userIPCountry=CN; visid_incap_1721050=2cUG8Xo8TOOiL31J/E12lovyAV0AAAAAQUIPAAAAAAAtC7LeK9nwei4POdYx95kH; incap_ses_628_1721050=hq0SJt+0Q3oMWAo25Bu3CKUPAl0AAAAACLjTkLbuV7tCEqh22IQuAg==; sr=10F002E6106E9EF2FBDFF741792A3E74681D7B71529B5D87F064CDDE26C891F6251004B11BD2D69FE2DA9F0909A35099438CADE8DE686B79862AEC7DB2F215D9220909942937CF48AB94F7A0E52193D2724321E38DB77171AF37192FB333E561D33719A6CB04D00404286DBAB5A167B336C3114B2AADBB466407E1C4A20AF04CD52811296C94AF2891F4CD6103BEDFEFE9AF5A03765B3C7E50E24E33D3BEE218077285765539EEAC2430B3803E0DFDDEEA29ED365B7B5A5E32F8F8E790CF2ACA55F10839DB6908E04C1944992F23F8B7B93EA14E20F8FC04D4F427B26388C317959236E2D69C47DA54EEFF13AA422FBF7843653132344F4FE7BCA251E7F06C68C89D4063B4DF6ABA59BDA64E005C080651A52EB4233EA6A62B28AD2E8D76C747AE03AB149823A89865BC67333D1197193439ECE0FBE8D0B41A1F10D79148C6406E749DF93ADB1D9D8FCDD2EADE7F05C0DE403057B288EA861E3E40416C54F36DDB3A9DEC9367B4D627B52ECF8B0B7A598CE7F01A0D2DE06BF51522A64A1CE0CC1AB064C9C09A000C85FA83580F75C42FA2C19DAD0837D5C2ED2025AF095CB4DAE0953A275F0EA1B2352595DA32D0580B03F046A7625CA23BC72DDDDB114ABE8D3047A6F0BF96AB1B599B7CFE97C4A9628140371DB45AAD85A2F7571F51AF70FA3CD5F9EF4BD9E2744F1D439514B4AC816176D73AB2D43775BD0D78A0AF223643500AB0E1A4134279330232948D2C9D8BC02629EA75B7BBF1439CEB07474B5CA109F48EB16119CB0B588102CF9487095E7A96F999E0954CCCE46508C572F9CEC85E35634BF7B870E1E9BD700C5F59EE8EB5BCBC29F5332C6C7212787E34B0F750D78096EB5A89947B7537F14559A40670F198203BBDD90787B1CAF364193DFCE331A4BE66D580EEDD835FD11BC0D058EDCF0866DC8AD77DB3B5D1B073FE1C42EA6F8ADF912E93EFA5D9AA881D8390BBA9DCEE90135AF58DAB3A31F878154A731AF8E074C7F6EB6082E970093068E3E6DF60DC8C510F2FBF2B73DE7C6269477AC844F5D44D4123AEF0FC340690B22463948CC98A728E5E6D091DE7FBFE515A6BB0908689053B28EDE5; Elapse=1; Lspid=10_12_11_16_52; s=1186BA69-7D1C-4294-91CA-EAC73053524C; g=1186BA69-7D1C-4294-91CA-EAC73053524C; OddsType=2; M88_COOKIE=!y9Lj09pKC1jgFbg0nKMQGzfoNpOTs2oOPP4Bi9itftMdlz0RZNUZZBx2nC6NFsXXwdOS36X7ZiuQ8Jo=; M88_NSS=!enJHHFIm0uoCzs40nKMQGzfoNpOTs4LoVraTmx6FEZflBrALkMt0nijYgIKQY5vYxSxXXuJj7WVqfA==; m88_cookie2=3087275436.20480.0000");
       // method.setRequestHeader("Cookie","ASP.NET_SessionId=aqjd2q1otxrbpsj0hhqzzb2h; incap_ses_1044_1721050=8vsjWng6vzQekziC7Al9DozyAV0AAAAANa5G+zMbL2+oaGBw76xJCw==; _ga=GA1.2.1977389373.1560408649; _gid=GA1.2.1328375733.1560408649; selectedLanguage=zh-CN; u=lzqiang2019; iovc=3875682; M88-A4=close; memLastLog=; lastdt=10~Soccer~2; category=0; t=VElDS0VUX0lEPTI4MjgyODQ3MSZTRVNTSU9OX0lEPTlGRURBRjUxRENERDRBQ0M2NTVBMkMxNTVBNUM2RDg5MzIzNkZBRUEyRTY3NDhGRDgxQUZEQTIwMjk1NjAzMURGNkU4QTg4MTFEN0IzOTgxMUU5QzdGMkQ4NjBDQ0ZFOTQyOTdFQTZCRDBFRDI3NDZFOTQ4MjExOURERUZGNkRDNUVCNUYyMzMzMTE1Rjk0MEFCNkMwMUQ4REY1QkIzNzY4MkMxQzk0MUVBQzQ5MEFBMzUxNUJDOTVBNUJBQjNCNUNFMDk5QTY5QkJCMUI2NkY3NUJGMDRFQzQxMzZBQTU0MjEwNjgzREUzNUU5MDczMjY5RTUyOTc4MERCRjE0RkQyNjdBOTgzNDlDQTdDQkIxNzZDODgxNTNBOEE4QTkxOTQyMEMxMTNBMDJCOURDMTZBRDQyODAyNTFCNkJBQzc0NjUwNkY1RjVFNEY4MzEyQjBCMkFFN0M4QjQwNjA1MUEwNUVCNkJBRTQ1NUFFMERDMUYyNzY1MkY3QjNDNThENA%3d%3d; puid=lzqiang2019; ot=2; curr1=RMB; OddsGroup=3; vp7=0; vp26=0; onHashID=; MPL=-99^5.0000^2000.0000^200000.0000|1^5.0000^25000.0000^200000.0000|2^5.0000^15000.0000^200000.0000|3^5.0000^3000.0000^200000.0000|90^5.0000^5000.0000^200000.0000|; curr=RMB; __utma=243143454.1977389373.1560408649.1560408725.1560408725.1; __utmc=243143454; __utmz=243143454.1560408725.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); lgnum_13=10_999; categoryMenu=0; dtC=2; lgnum_21=999; lgnum_12=10_999; srttyp=0; ms_session=%7B%22brand%22%3A%22M88%22%2C%22channel%22%3A%7B%22trafficSource%22%3A%22DIRECT%22%2C%22trafficReferral%22%3A%22%22%2C%22referralURL%22%3A%22%22%2C%22referralFullURL%22%3A%22%22%2C%22registerFrom%22%3A%22www.ms88ki.com%22%2C%22platform%22%3A%22Desktop%22%2C%22custom_data%22%3A%7B%7D%7D%7D; ms_traffic_source=DIRECT; userIPCountry=CN; visid_incap_1721050=2cUG8Xo8TOOiL31J/E12lovyAV0AAAAAQUIPAAAAAAAtC7LeK9nwei4POdYx95kH; incap_ses_628_1721050=hq0SJt+0Q3oMWAo25Bu3CKUPAl0AAAAACLjTkLbuV7tCEqh22IQuAg==; Elapse=3; Lspid=10_12_11_16_52; s=1186BA69-7D1C-4294-91CA-EAC73053524C; g=1186BA69-7D1C-4294-91CA-EAC73053524C; OddsType=2; M88_COOKIE=!Ho2o5wzUoSy44Vk0nKMQGzfoNpOTs1MBRC2Y23QNaaZ8F3Jfmlu/3VeAhGN8h703TP2YIze4u6YAVkg=; sr=10F002E6106E9EF2FBDFF741792A3E74681D7B71529B5D87F064CDDE26C891F6251004B11BD2D69FE2DA9F0909A35099438CADE8DE686B79862AEC7DB2F215D9220909942937CF48AB94F7A0E52193D2724321E38DB77171AF37192FB333E561D33719A6CB04D00404286DBAB5A167B336C3114B2AADBB466407E1C4A20AF04CD52811296C94AF2891F4CD6103BEDFEFE9AF5A03765B3C7E50E24E33D3BEE218077285765539EEAC2430B3803E0DFDDEEA29ED365B7B5A5E32F8F8E790CF2ACA55F10839DB6908E04C1944992F23F8B7B93EA14E20F8FC04D4F427B26388C317959236E2D69C47DA54EEFF13AA422FBF7843653132344F4FE7BCA251E7F06C68C89D4063B4DF6ABA59BDA64E005C080651A52EB4233EA6A62B28AD2E8D76C747AE03AB149823A89865BC67333D1197193439ECE0FBE8D0B41A1F10D79148C6406E749DF93ADB1D9D8FCDD2EADE7F05C0DE403057B288EA861E3E40416C54F36DDB3A9DEC9367B4D627B52ECF8B0B7A598CE7F01A0D2DE06BF51522A64A1CE0CC1AB064C9C09A000C85FA83580F75C42FA2C19DAD0837D5C2ED2025AF095CB4DAE0953A275F0EA1B2352595DA32D0580B03F046A7625CA23BC72DDDDB114ABE8D3047A6F0BF96AB1B599B7CFE97C4A9628140371DB45AAD85A2F7571F51AF70FA3CD5F9EF4BD9E2744F1D439514B4AC816176D73AB2D43775BD0D78A0AF223643500AB0E1A4134279330232948D2C9D8BC02629EA75B7BBF1439CEB07474B5CA109F48EB16119CB0B588102CF9487095E7A96F999E0954CCCE46508C572F9CEC85E35634BF7B870E1E9BD700C5F59EE8EB5BCBC29F5332C6C7212787E34B0F750D78096EB5A89947B7537F14559A40670F198203BBDD90787B1CAF364193DFCE331A4BE66D580EEDD835FD11BC0D058EDCF0866DC8AD77DB3B5D1B073FE1C42EA6F8ADF912E93EFA5D9AA881D8390BBA9DCEE90135AF58DAB3A31F878154A731AF8E074C7F6EB6082E970093068E3E6DF60DC8C510F2FBF2B73DE7C6269477AC844F5D44D4123AEF0FC340690B22463948CC98A728E5E6D091DE7FBFE515A6BB0D89D0AC83F3019F7; M88_NSS=!WmTLhKTji917BQg0nKMQGzfoNpOTs2XjX6RGPbm7ZiWBq6YJEVNQ0hSmZQ/EMRns+83ePU89R23Uxw==; m88_cookie2=3087275436.20480.0000");
       // method.setRequestHeader("Cookie","Cookie:ASP.NET_SessionId=n4zjuelmp5bnfcdxe44botgr; incap_ses_1044_1721050=vU3hFY4d/SbcArmD7Al9Dho+B10AAAAAcEhjFjDQmHRnI0h9nyL0xQ==; u=lzqiang2019; iovc=3875682; M88-A4=close; memLastLog=; _ga=GA1.2.1977389373.1560408649; _gid=GA1.2.565045862.1560755713; curr=RMB; lgnum_13=10_999; lgnum_21=999; lgnum_12=10_999; m=C7E4540E-DEC9-4FEF-AE76-62D5E7C70BBE; selectedLanguage=zh-CN; incap_ses_1203_1721050=WsruGyMukUcieLPjTuqxECxzB10AAAAAMVJdHX+cIY+s/zyRJgVuBg==; incap_ses_795_1721050=Iv8MA7nGzwNPl+eZFmkICwp6B10AAAAA1a/ALfxWNPLg5Kxbtuvgmg==; incap_ses_627_1721050=S+9AQCFdsSbOd2DYS42zCEd6B10AAAAAaPQjvF7Jt07eRZDiGixP7w==; incap_ses_628_1721050=6eSLYW+ArBHGXTuJ5hu3CKt3B10AAAAA5aK8JqGF7c00SayIfruavA==; visid_incap_1721050=2cUG8Xo8TOOiL31J/E12lovyAV0AAAAAQUIPAAAAAAAtC7LeK9nwei4POdYx95kH; incap_ses_797_1721050=ItJ/doqyYEV+U8uDJoQPC/yhB10AAAAAbsS606zGNVaa8m4kqEbA8Q==; userIPCountry=CN; ms_session=%7B%22brand%22%3A%22M88%22%2C%22channel%22%3A%7B%22trafficSource%22%3A%22DIRECT%22%2C%22trafficReferral%22%3A%22%22%2C%22referralURL%22%3A%22%22%2C%22referralFullURL%22%3A%22%22%2C%22registerFrom%22%3A%22www.ms88ki.com%22%2C%22platform%22%3A%22Desktop%22%2C%22custom_data%22%3A%7B%7D%7D%7D; ms_traffic_source=DIRECT; _pk_id.1.0e5b=c29c67c7f266e933.1560586268.4.1560781243.1560781240.; _pk_ses.1.0e5b=1; category=0; lastdt=10~Soccer~2; t=VElDS0VUX0lEPTI4MzIyOTk4NiZTRVNTSU9OX0lEPUI5NjREOTlGQjAwRkQ2NTJBQTdDMDAxOUU3QThDODVCRkY3NjcxOTgwNzQ3NUUzOUM5NzRCNjM4NjYwQjBGOEVERDE4NDQxNTdFRTUwMjk2QjUwNkE3RDU5MjdFMzE1MTlDRUI0MkY3RUJFMzNEMjZCNEMxMzBDRkNDQkFENTIwMjhGQkM2QjM3NDhDQzk3MTE0NjVBQUJEQUE1QThCMTMwMzQ2NjhDN0E3M0YxNkMxQTY1RjZDREM5NTgxNUFDOUQxNENGOTVBMUFFNUU4QUY5NDAwNDM0MjQ4MDYwRDdDRTU1MTExQzYwMUM4QUY2Q0NENzg0MTRBMDMxQTlEQTQ3NUY4NzRDQzc0N0MzM0Y1RTBEMzkwN0ZCNzJEQzM2OTBBNjQ3QUU1RkIwMTc1RjZBQTg5NjdDMzYwRUY0QjBENTk3NzU4NjNFQjQwRUQ1REZDNDE5MDNEMURFRkFGMDBEMkRCMjMxNEUzN0UxQTJENDMzMTE0QzI4NTg4OUY4MQ%3d%3d; puid=lzqiang2019; ot=2; curr1=RMB; OddsGroup=3; vp7=0; vp26=0; onHashID=; MPL=-99^5.0000^2000.0000^200000.0000|1^5.0000^25000.0000^200000.0000|2^5.0000^15000.0000^200000.0000|3^5.0000^3000.0000^200000.0000|90^5.0000^5000.0000^200000.0000|; categoryMenu=0; dtC=2; __utma=243143454.1977389373.1560408649.1560755714.1560781246.3; __utmb=243143454.1.10.1560781246; __utmc=243143454; __utmz=243143454.1560408725.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); srttyp=0; Lspid=10_12_11_52; Elapse=3; s=697C19AD-5BE4-42DA-AB67-29F5A4019C0E; g=697C19AD-5BE4-42DA-AB67-29F5A4019C0E; OddsType=2; M88_COOKIE=!v2+iYHPXSqBqNCQ0nKMQGzfoNpOTszfRkWxafKOF+KS3oYVoI7gK+2DK0ur0XdTWzXyAQWPJnSUyBv8=; sr=10F002E6106E9EF2FBDFF741792A3E74681D7B71529B5D87F064CDDE26C891F6DFE1A4684A0AC83070E1125EEEB41FFD438CADE8DE686B79862AEC7DB2F215D96DFD8A75C6153FEB8547248CA86E5CB9BEF98B757110758F5331BCA9871FFC5768597A60F2AFEF4F4F1CDB1E87400EB34995D6042B52F819F429B89738B00201B9CA5E42005F1F63DD8037973A2C47B710BD9C7F9D661EA8EE657FC352B59FFB1E894A9092804C1DAC13DE5B346338281EB6718FA14970BB3575B8AD2ED590988AD368FDE8906D248C9A618CBF210768E0BEB776B3A2318D1FEFE09B557D61D97179107834DE5696E32A789C26E6572F5A9209DA7B987D4EF9DD64EF89D117EAE46C89B54EDC3DCBE1BB3254F1B446ECD777C3C0FA9A8262107A170B50BD920818262F262C6CF11C6DCFC25444DB762D80C8CABB5F3601221AAC35BC5986286E73D893C7153F5A82195AC628AB6335D90779B5D3E57FB63668E39704751C8BFF25021CB228860C9D042CB5ABB08ACCC92BE00019ABF4C65C92BD53BFA87B019AC79C182535449A84518D27D39C4DB8DAFC524E11715D49F56AAD225A8384497A33C0373DC5F718B9BABC0ACB3A94B66B06F7C6899B4F3E6AD9B9B47440EA7C54E81D236C59DB8F7954C92EA4CD1A7FE1627D46DCAD9C5194838ACF3B8EC303FFD64AFBC2558AD85D1723C57D7CE78AB6F3C371C3C657E4C755C2D8A4E89F273D7940A46FF23A3637A05CE5DC113BD7F7FBF590A68405FD715FA09520F117C5E61A88623E08763077CF6E0A7A9597329DDCEA6443BF39BD42FAA2AAC8F8D2EFEB07CCAB974866E359FA723B20644DBF6C1ED4A85C6F8FEC9196A4C14009CD2184B0EDADC6D705236BD3440EE161C895C828CD77939FC34CD04CBD601B14975420D72F1439975FFAB4A30A4A86B463DD1B2668C6E948DE2ABFE2F8E254F30471C4F1DD560D1CC2B39BA05596BF0B6E97D1803ECB0B7E0FBEFCC72211D3B86ACB7ECAB7AEBA3D93BFB251511E9E0879ED30601F469A89C6261D10B5B04013B30059EFAB14D0E93D855DEED324ACCBE2E6B48CC98A728E5E6D091DE7FBFE515A6BB08958EB133BE6DFB2; M88_NSS=!jWcflQbwtHpi5bY0nKMQGzfoNpOTs8zJfgGWa4tr4Qbv7Pj0xjuGP6I6eTSyHcQ1nN8AYN80tnFs0w==; m88_cookie2=3087275436.20480.0000");
        method.setRequestHeader("Cookie",prop.getString("Cookie"));
        method.setRequestHeader("Host", "www."+MsHost+".com");
        method.setRequestHeader("Origin", "https://www."+MsHost+".com");
       // method.setRequestHeader("Referer", "https://www."+MsHost+".com/Main/Sports/mSports/nss/repo.aspx?mode=4&ReportDate=6/12/2019%2012:00:00%20AM");
        method.setRequestHeader("Referer",  "https://www.ms88ki.com/Main/Sports/mSports/nss/repo.aspx?mode=4&ReportDate=6/23/2019%2012:00:00%20AM");
        method.setRequestHeader("Upgrade-Insecure-Requests", "1");
        method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0");

//        STR:M_0_1H_3657302_30_0.950000_2_-0.2500_001_000_0_
        //    M_0_上半场_比赛编号_赔率前缀_赔率_2_平手盘_主队进球_客队进球_金额_
//        IsParlay:false
//        Mode:1
//        betLog:

        int month = NumberUtils.toInt(frdate.substring(4,6));
        int day = NumberUtils.toInt(frdate.substring(6,8));
        String frdateStr =  month+"/"+day+"/"+frdate.substring(0,4);
        String todateStr =  todate.substring(6,8)+"/"+todate.substring(4,6)+"/"+todate.substring(0,4);

        System.out.println(frdateStr+":"+todateStr);

        Map matchMap = new HashMap<String, String>();
        matchMap.put("mode","4");
        matchMap.put("lid","ch");
        matchMap.put("spid","");
        matchMap.put("lgnum","");
/*        matchMap.put("frdate","6/12/2019 12:00:00 AM");
        matchMap.put("todate","13/06/2019");*/
        matchMap.put("frdate",frdateStr+" 12:00:00 AM");
        matchMap.put("todate",todateStr);


        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, Charset);
        method.setRequestBody(buildNameValuePair(matchMap));

        long startTime = System.currentTimeMillis();
        HttpClient client = new HttpClient();

        client.getHttpConnectionManager().getParams().setConnectionTimeout(100000);
        client.getHttpConnectionManager().getParams().setSoTimeout(100000);

        int statusCode = HttpStatus.SC_OK;
        long elapsedTime = 0;

        try {
            statusCode = client.executeMethod(method);
            elapsedTime = System.currentTimeMillis() - startTime;

            logger.info("调用http请求成功: " + method.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " + statusCode);

            logger.info("=================== getRepoData http start======================");
            InputStream is = method.getResponseBodyAsStream();
            GZIPInputStream gis = new GZIPInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(gis,Charset));
            StringBuffer stringBuffer = new StringBuffer();
            String str= "";
            while((str = br.readLine()) != null){
                stringBuffer .append(str );
            }
            logger.info("getRepoData result:" + stringBuffer.toString());
            logger.info("========================= getRepoData http end=========================");
            return stringBuffer.toString();

/*            String result = method.getResponseBodyAsString();
            logger.info("=================== getRepoData http start======================");
            logger.info("method.getResponseCharSet():" + method.getResponseCharSet());
            logger.info("getRepoData result:" + result);
            logger.info("========================= getRepoData http end=========================");
            if(result==null || result.length()<100){
                return null;
            } else {
                return result;
            }*/


        } catch (Exception ex) {
            statusCode = 499;
            try {
                logger.info("调用http请求异常: " + method.getURI() + ",耗时：" + elapsedTime
                        + "ms, exception:" + ex.getMessage());
            } catch (URIException uriex) {
                // ignore this exception
            }
            if (ex instanceof HttpInvokeException) {
                throw (HttpInvokeException) ex;
            } else {
                throw new HttpInvokeException(statusCode, ex);
            }
        } finally {
            method.releaseConnection();
        }

    }

    private static NameValuePair[] buildNameValuePair(Map<String, String> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return EMPTY_NAMEVALUE_PAIRS;
        }
        NameValuePair[] nameValuePairs = new NameValuePair[parameters.size()];

        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>(parameters.size());
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            nameValuePairList.add(new NameValuePair(entry.getKey(), entry.getValue()));
        }
        nameValuePairList.toArray(nameValuePairs);
        return nameValuePairs;
    }

    private static final NameValuePair[] EMPTY_NAMEVALUE_PAIRS = new NameValuePair[]{};

    /*
    frdate 20190612
    todate 20190613
     */
    private void buildRepo(String frdate,String todate) throws Exception {

        File file1=new File("E:\\work\\mslog\\"+frdate+"-"+todate);
        if(!file1.exists()){
            boolean b = file1.mkdir();
            if (b==false){
                System.out.print("mkdir error");
                return;
            }
        }

       String repoDataList = this.getRepoData(frdate,todate);
        //String repoDataList = prop.getString("RepoTest");

        int wno1 = 0; //1.jbm test guest shootup
        int wno2 = 0; //2.jbm test master 后10分钟加注
        int wno3 = 0; //3.jbm test guest 后10分钟加注
        int wno4 = 0; //4.合算比赛,后10分钟加注
        int wno5 = 0; //5.jbm test,数据稳定,赔率大幅提升
        int wno6 = 0; //6.最后10分钟,超高赔率,射门上升
        int wno7 = 0; //7.only second,小波攻势
        int wno8 = 0; //8.高赔率比赛,射门上升
        int wno9 = 0; //9.jbm test,master base shootup
        int wno10 = 0; //10.jbm test,master shootup
        int wno11 = 0; //11.only second,base shoot up
        int wno12 = 0; //12.only second,shoot up

        int lno1 = 0; //1.jbm test guest shootup
        int lno2 = 0; //2.jbm test master 后10分钟加注
        int lno3 = 0; //3.jbm test guest 后10分钟加注
        int lno4 = 0; //4.合算比赛,后10分钟加注
        int lno5 = 0; //5.jbm test,数据稳定,赔率大幅提升
        int lno6 = 0; //6.最后10分钟,超高赔率,射门上升
        int lno7 = 0; //7.only second,小波攻势
        int lno8 = 0; //8.高赔率比赛,射门上升
        int lno9 = 0; //9.jbm test,master base shootup
        int lno10 = 0; //10.jbm test,master shootup
        int lno11 = 0; //11.only second,base shoot up
        int lno12 = 0; //12.only second,shoot up


        String substring = StringUtils.substringBetween(repoDataList, "d=[", "\"];");
        String[] repoDataArr = substring.split("U\\^");
        for (int i=1; i<repoDataArr.length;i++){
            String line1= repoDataArr[i];// 单号；比赛id；胜负
            System.out.println("============"+i+"============");
            System.out.println(line1);

            String[] s1 = line1.split("\\^");

            String isWin = s1[22];
            if(isWin.equals("D"))continue;

            String dateStr = s1[2].substring(0,8);
            String dateStrOv = dateStr.substring(0,4)+"-"+ dateStr.substring(4,6)+"-"+dateStr.substring(6,8);
            System.out.println("dateStrOv:"+dateStrOv);

            String betNo = s1[0].split("\\|")[0];
            String matchNo = s1[30];
            String leagueName = s1[3];
           // String isWin = s1[22];
            String betMoney =  s1[7];
            String winMoney =  s1[20];
            String isHalf =  s1[4];
            String betScore = s1[11]+"-"+s1[12];
            String halfScore = s1[23]+"-"+s1[24];
            String overScore = s1[25]+"-"+s1[26];

            String repoStr =  betNo + "|" + matchNo + "|" + leagueName + "|" + isHalf + "|" + betScore + "|" + halfScore + "|" + overScore + "|" + betMoney + "|" + winMoney + "|" + isWin ;
            System.out.println(repoStr);

            Process proc = null;
            String[] cmd = { "cmd", "/c", "grep "+matchNo+" E:\\work\\mslog\\msbet.log."+dateStrOv+" > E:\\work\\mslog\\"+frdate+"-"+todate+"\\"+matchNo+".log" };
            proc = Runtime.getRuntime().exec(cmd);
            Thread.sleep(2000);
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "grep " + betNo + " E:\\work\\mslog\\"+frdate+"-"+todate+"\\"+matchNo+".log > E:\\work\\mslog\\tmp88.log"});
            Thread.sleep(2000);
            BufferedReader in = new BufferedReader(new FileReader("E:\\work\\mslog\\tmp88.log"));
            String tmp = in.readLine();
            in.close();
            System.out.println(tmp);
            String tmp1 = tmp.split("\\|\\|")[1];
            System.out.println(tmp1);
            int noInt = NumberUtils.toInt(tmp1.split("\\.")[0],0);
            System.out.println(noInt);

            switch(noInt){
                case 1:
                    int ii1 = isWin.equals("W")? wno1++ : lno1++;
                    break;
                case 2:
                    int ii2 = isWin.equals("W")? wno2++ : lno2++;
                    break;
                case 3:
                    int ii3 = isWin.equals("W")? wno3++ : lno3++;
                    break;
                case 4:
                    int ii4 = isWin.equals("W")? wno4++ : lno4++;
                    break;
                case 5:
                    int ii5 = isWin.equals("W")? wno5++ : lno5++;
                    break;
                case 6:
                    int ii6 = isWin.equals("W")? wno6++ : lno6++;
                    break;
                case 7:
                    int ii7 = isWin.equals("W")? wno7++ : lno7++;
                    break;
                case 8:
                    int ii8 = isWin.equals("W")? wno8++ : lno8++;
                    break;
                case 9:
                    int ii9 = isWin.equals("W")? wno9++ : lno9++;
                    break;
                case 10:
                    int ii10 = isWin.equals("W")? wno10++ : lno10++;
                    break;
                case 11:
                    int ii11 = isWin.equals("W")? wno11++ : lno11++;
                    break;
                case 12:
                    int ii12 = isWin.equals("W")? wno12++ : lno12++;
                    break;
                default:
                    System.out.println("error noInt:"+noInt);break;
            }
/*            for (int j=0; j<s1.length; j++){
                System.out.println(j+":"+s1[j]);
            }*/

        }

        StringBuilder repoOutStr = new StringBuilder();
        repoOutStr.append("1.jbm test guest shootup:").append(wno1).append("-").append(lno1).append("\n");
        repoOutStr.append("2.jbm test master 后10分钟加注:").append(wno2).append("-").append(lno2).append("\n");
        repoOutStr.append("3.jbm test guest 后10分钟加注:").append(wno3).append("-").append(lno3).append("\n");
        repoOutStr.append("4.合算比赛,后10分钟加注:").append(wno4).append("-").append(lno4).append("\n");
        repoOutStr.append("5.jbm test,数据稳定,赔率大幅提升:").append(wno5).append("-").append(lno5).append("\n");
        repoOutStr.append("6.最后10分钟,超高赔率,射门上升:").append(wno6).append("-").append(lno6).append("\n");

        repoOutStr.append("7.only second,小波攻势:").append(wno7).append("-").append(lno7).append("\n");
        repoOutStr.append("8.高赔率比赛,射门上升:").append(wno8).append("-").append(lno8).append("\n");
        repoOutStr.append("9.jbm test,master base shootup:").append(wno9).append("-").append(lno9).append("\n");
        repoOutStr.append("10.jbm test,master shootup:").append(wno10).append("-").append(lno10).append("\n");
        repoOutStr.append("11.only second,base shoot up:").append(wno11).append("-").append(lno11).append("\n");
        repoOutStr.append("12.only second,shoot up:").append(wno12).append("-").append(lno12);

        BufferedWriter out = new BufferedWriter(new FileWriter("E:\\work\\mslog\\"+frdate+"-"+todate+"\\"+frdate+"-"+todate+".log"));
        out.write(repoOutStr.toString());
        out.close();
    }


    public static void main(String[] args) throws Exception {
            /*String frdate = args[0]!=null?args[0]:"6/12/2019";
            String todate = args[1]!=null?args[1]:"6/13/2019";*/

        MsRepoParse msRepoParse = new MsRepoParse();
       // System.out.print(msRepoParse.getRepoData("20190620","20190623"));
        msRepoParse.buildRepo("20190626","20190627");
        msRepoParse.buildRepo("20190627","20190628");
        msRepoParse.buildRepo("20190628","20190629");

    }


}
