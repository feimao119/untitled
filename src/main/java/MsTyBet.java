import domain.MsTyMatch;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.log4j.PropertyConfigurator;
import java.util.*;

public class MsTyBet {




    private static final PropertyUtil prop = new PropertyUtil("E://work//untitled//src//main//resources//conf.properties");
    private static final int Money = NumberUtils.toInt(prop.getString("Money"),50);
    private static String Cookie = prop.getString("Cookie");
    private static final String TestStr = prop.getString("TestStr");
    private static final String BetUrl = prop.getString("BetUrl");
    private static final String ListUrl = prop.getString("ListUrl");
    private static Date _date =  new Date();

    private static final Logger logger = LoggerFactory.getLogger(MsTyBet.class);
    private static final String Charset = "UTF-8";
    private static Map<Integer,MsTyMatch> msTyMatches = new HashMap<Integer, MsTyMatch>();

    public static void main(String[] args) throws Exception {
       // PropertyConfigurator.configure("log4j.properties");

        logger.info("===============================prop==============================================");
        logger.info("[Money:"+Money +"]");
        logger.info("[Cookie:"+Cookie +"]");
        logger.info("===============================prop end==============================================");
//        String sr="21213333333333333";
//        String cookie1 = StringUtils.substringBeforeLast(Cookie,"sr=");
//        logger.info("[cookie1:"+cookie1 +"]");
//        String cookie2 = StringUtils.substringAfter(StringUtils.substringAfterLast(Cookie,"sr="),";");
//        logger.info("[cookie2:"+cookie2 +"]");
//        String newCookie = cookie1+"sr="+sr+";"+cookie2;
//        logger.info("[newCookie:"+newCookie +"]");

        //获取比赛列表
        //备选条件策略
        //赛事id存入临时list
        //备选赛事入库 + 第一次比赛数据（包括数据id）
        //*每分钟备选赛事数据更新（赔率+数据）
        //1分钟后循环

        //备选赛事观察线程  //先单线程
        //循环获取观察赛事
        //应用投注策略
        //符合策略投注
        //继续观察

        //备选条件策略（领先+赔率0.775以上）

        /*投注策略
        先都5分钟一单，不区分上下半场
        上半场:
           1）每3分钟观察1次（事实获取备选赛事数据（赔率+数据））比分扳平或反超放弃（标注）
           2）观察时符合2倍盈利条件、赔率稳定、数据上升的下注，根据2倍盈利条件数据大小决定注额大小， 分1.5-2.0，2.1-2.8，2.8以上3档
           3）30分钟前最多1注，30分钟后每次观测符合条件的再下1注，42分钟停止投注，上半场最多5注
        下半场：
           1）每5分钟观察1次 （事实获取备选赛事数据（赔率+数据）
           2）观察时符合2倍盈利条件、赔率稳定、数据上升的下注，根据2倍盈利条件数据大小决定注额大小， 分1.5-2.0，2.1-2.8，2.8以上3档
           3）70分钟前最多1注，70分钟后每次观测符合条件的再下1注，90分钟停止投注，下半场最多5注
         */

       MsTyBet myTyBet = new MsTyBet();
        myTyBet.rollMsBetStart();

      //  myTyBet.bet(null);
//        myTyBet.bet();
//        myTyBet.uniqRand();
//

      //  myTyBet.getMatchList(myTyBet.getMatchListStr());
/*        String testString = "<script type=\"text/javascript\">var d=[];d[0]=[[['10022547','0','中国女子足球锦标赛|0','3644573','0','201807051400','Shaanxi Datai Zhicheng (女)','八一惠州瀛峰 (女)','0_2','0','3','32`','0_0','0_0','|0','1|0','Y','1','0','1','-0.2500','0-0.5|',,'0.9100|1.8100','0.7900|1.6900',,,'5','17','2.5000','2.5','1','0.7500|1.6500','0.9500|1.8500','1','0',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'0','0_0__N_90','0'],\n" +
                "[,,,,,,,,,,,,,,,,,,'0','16','-0.5000','0.5|',,'1.2500|2.1100','0.5000|1.4000',,,'5','3','2.7500','2.5-3',,'0.9900|1.8900','0.7100|1.6100',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'0','0_0__N_90','1'],\n" +
                "[,,,'3644574','0','201807051400','长春农商银行 (女)','大连权健 (女)','0_1','3','3','29`','0_0','0_0','|0','1|0','Y','1','0','1','0.2500','|0-0.5',,'0.7500|1.6500','0.9500|1.8500',,,'5','17','4.7500','4.5-5','1','0.8200|1.7200','0.8800|1.7800','0','1',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'0','0_0__Y_90','2'],\n" +
                "[,,,,,,,,,,,,,,,,,,'0','16','0.5000','|0.5','1','0.4500|1.3500','1.3300|2.1700','0','1','5','3','4.5000','4.5','1','0.6900|1.5900','1.0100|1.9100','1','0',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'0','0_0__Y_90','3']\n" +
                "],['40'],['10~ch~AH_Live~Double~999~Hongkong~3~0~AllTime~Normal']];d[1]=[[['12014138','0','新西兰全国男子篮球联赛|0','3644282','0','201807051500','超級城市突击队','南部鲨鱼','1_0_28_12_0_0_0_40_0_0_0_0_0_0_0_0_0|__4_0_','0_27_25_0_0_0_52_0_0_0_0_0_0_0_0_0|','12','03`_T','0_0','0_0','3644282|0','1|0','N','1','0','7','16.5000','|16.5','1','0.9500|','0.8100|','0','1','5','3','210.5000','210.5','1','0.6800|','0.9800|','0','1','3','5',,'0.9100|','0.9700|',,,,,,,,,,,'2','2','12.5000','|12.5','1','0.9500|','0.8100|','0','1',,,,,,,,,,,,,,,,,,,,,,,,,'0','0_0__','0'],\n" +
                "['12011836','0','菲律宾 PBA 发展联赛|0','3644467','0','201807051500','中央大学蝎子','Che Lu SSC-R 雄鹿','1_0_16_13_0_0_0_29_0_0_0_0_0_0_0_0_0|-__2_0_','0_23_21_0_0_0_44_0_0_0_0_0_0_0_0_0|-','12','03`','0_0','0_0','3644467|0','1|0','N','1','0','7','13.5000','|13.5','1','0.7000|','1.0000|','0','1','5','8','186.5000','186.5','1','1.0000|','0.6000|','1','0','3','5',,'0.9100|','0.9100|',,,,,,,,,,,'2','2','14.5000','|14.5','1','1.0500|','0.6500|','1','0',,,,,,,,,,,,,,,,,,,,,,,,,'0','0_0__','1']\n" +
                "],['29'],['12~ch~AH_Live~Double~999~Hongkong~3~0~AllTime~Normal']];parent.main.prepDtMulti(d);</script>";
        myTyBet.getMatchList(testString);*/
    }

    public void rollMsBetStart() throws Exception {
        while (true){
            try {
                logger.info("=================rollMsBetStart===================");
                //Cookie = prop.getString("Cookie");
                String matchesStr = this.getMatchListStr();
                if(matchesStr==null){
                    int random =  RandomUtils.getRandom50000to99999();
                    logger.info("=================没有合适的比赛 sleep ==================="+random+"毫秒");
                    Thread.sleep(random);
                    continue;
                }
                this.getMatchList( matchesStr);
                int random =  RandomUtils.getRandom5000to9999();
                logger.info("================轮询结束 sleep =================="+random+"毫秒");
                Thread.sleep(random*7);

            }  catch (Exception e) {
                e.printStackTrace();
                logger.error("error",e);
                continue;
                //throw e;
            }

        }
    }

    public String preBet(MsTyMatch msTyMatch)  {

       // PostMethod method = new PostMethod("https://nss.m88ms.com/nss/TabBetSlipFrame.aspx");
        PostMethod method = new PostMethod(BetUrl);

        method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        method.setRequestHeader(" Accept-Encoding", "gzip, deflate, br");
        method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        method.setRequestHeader("Cache-Control", "max-age=0");
      method.setRequestHeader("Connection", "keep-alive");
        method.setRequestHeader("Content-Length", "81");
        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        // method.setRequestHeader("Cookie", ":KeepOdds-31232766=no; MiniKey=max; dss=0%7C0; __utma=206572623.769014943.1530774657.1530774657.1530781566.2; __utmz=206572623.1530774657.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); IsBDLogin=false; LangKey=cs; DispVer=3; OddsType_MS88RMB01626539=2; _ga=GA1.2.769014943.1530774657; _gid=GA1.2.1537746327.1530949111; selectedLanguage=zh-CN; OddsType_SPONUUS01445=4; switchViewSkinType_M88=0; visid_incap_1349630=XM2LCFOjSB2k2Y3E3jshU1rEPVsAAAAAQUIPAAAAAACUyRPfc3+WSJc70qQGISTS; incap_ses_627_1349630=DK/4E3Fcnlsd7DeG5Y2zCPV/QVsAAAAAulgXqZGLPuFXrczOKAl2+g==; ASP.NET_SessionId=2xpvy355klmdcobxc1n15qa2; OddsGroup=3; curr1=; category=0; lgnum_21=999; lgnum_12=10_999; lgnum_13=10_999; lgnum_11=999; userIPCountry=CN; ms_session=%7B%22brand%22%3A%22M88%22%2C%22channel%22%3A%7B%22trafficSource%22%3A%22DIRECT%22%2C%22trafficReferral%22%3A%22%22%2C%22referralURL%22%3A%22%22%2C%22referralFullURL%22%3A%22%22%2C%22registerFrom%22%3A%22www.m88ms.com%22%2C%22platform%22%3A%22Desktop%22%2C%22custom_data%22%3A%7B%7D%7D%7D; ms_traffic_source=DIRECT; categoryMenu=0; dtC=2; Lspid=10_12_11_18; srttyp=0; lastdt=10%7E%u8DB3%u7403%7E2; m88_cookie=2281855242.20480.0000; srttyp=0");
//        method.setRequestHeader("Cookie","incap_ses_809_1349630=g+jEbCREC2m7cdB3rSU6CzA/S1sAAAAA+dLF0pxm/9WlzvH3hbPhcQ==; u=cbz20018; iovc=3558655; ms_session=%7B%22brand%22%3A%22M88%22%2C%22channel%22%3A%7B%22trafficSource%22%3A%22DIRECT%22%2C%22trafficReferral%22%3A%22%22%2C%22referralURL%22%3A%22%22%2C%22referralFullURL%22%3A%22%22%2C%22registerFrom%22%3A%22www.m88ms.com%22%2C%22platform%22%3A%22Desktop%22%2C%22custom_data%22%3A%7B%7D%7D%7D; ms_traffic_source=DIRECT; memLastLog=; ASP.NET_SessionId=syjak045iwps4lad0mumds55; category=0; t=U0VTU0lPTl9JRD1BN0QzMjlCMkMzQjVBNTU2OUMwRDQ5MUJBMDk5REMzNzlDMkE3NTNBQ0MyNzM2Q0NEN0FGQzg3NUU0RDkyNDI5RkMxNzExNTNGNkQ5RjRBMjlDNjE0NUJFOEUxOTUwQ0NCOEQ0Q0MwQ0M2QTEwMTBFMzcyQTUyOTg5MEJFQjJCNEVDQkFEODYyNDRCOEY1NkREQjE0ODE5QTE4OUVFRjQwRTdBNTYyOTVFNTYzNjA1NzZDOUNBREYzNzM5QUExQkY2REM4NTQ0NkI3MkVBQjkxNTRCMTAwMEI0Qzc4MkI5QzgwMzRDNkFGOUVBMkM3MjI3RTVBREQ1MjZDRDI3OTAyRkQxQzAyRTA1MTk5MzQzM0RGOUU4NkY0MDRBNEVEQ0U5RTA2NjUxMENBOThDNkRFOUQ0MkQ0NDQ0NDk1RDIzRUIzMTgyMUQ4NkYyMzcyNEImVElDS0VUX0lEPTI0NTIwOTYwMQ%3d%3d; puid=cbz20018; ot=2; curr1=RMB; OddsGroup=3; vp7=0; vp26=0; onHashID=; MPL=-99^5.0000^2000.0000^200000.0000|1^5.0000^20000.0000^200000.0000|2^5.0000^10000.0000^200000.0000|3^5.0000^3000.0000^200000.0000|90^5.0000^5000.0000^200000.0000|; IsBDLogin=false; curr=RMB; LangKey=cs; switchViewSkinType_M88=0; KeepOdds-31232766=no; selectedLanguage=zh-CN; MiniKey=max; lgnum_13=10_999; __utma=206572623.705112580.1531658063.1531659170.1531659170.1; __utmb=206572623.1.10.1531659170; __utmc=206572623; __utmz=206572623.1531659170.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); lgnum_21=999; lgnum_12=10_999; m=FF2961B9-95E4-41C9-80CF-C95587ED8876; _ga=GA1.2.705112580.1531658063; _gid=GA1.2.221429814.1531658063; DispVer=3; OddsType_MS88RMB01626539=2; dss=0%7C0; lgnum_11=999; incap_ses_625_1349630=Ri9WAbq5/0mNIIA66HKsCERHS1sAAAAAm0LvvnmOamFJxK3JXxnmxA==; userIPCountry=CN; visid_incap_1349630=+QPpWZs9RjCA8D2aQwpKuy4/S1sAAAAAQUIPAAAAAABPdO/lsirVlPC0FYv4AEUM; incap_ses_572_1349630=yhnWNt5hVGvy5ouz1yfwB4VHS1sAAAAAhGAQoi+sHk49t9xxpHkaiw==; Elapse=2; s=3D679580-A449-4E69-9DB6-81F6026F6F0F; g=3D679580-A449-4E69-9DB6-81F6026F6F0F; OddsType=2; categoryMenu=0; dtC=1; Lspid=10_12_11_52; lastdt=10%7E%u8DB3%u7403%7E1; srttyp=0; sr=10F002E6106E9EF2E7C0C580C500E5E3721E1C77EB2F0E42F3F9E147F887F3B88253AE885E5D32E66E80821626B6A68DB4C854C4969D23050E3BF07ADFA3D474632A339866E7007F1F757CF31068B1D98D77D3EF5DE4887966B4AF661E25107C0C3BD18A150ED954EFE661F2D7EB778D47388F2EE3E0CBBCEEA21DA0441B231CC8920563227A8FE0793143B02C7BB077E9E5D298C17AB7A7648FB1F23B8A053B1E84D9DCFED17285B93F02B369D30F93EFF231DEE7F719F05AD356EFE268FE1469CDF20B37C344338188EA337874DDFC2A9F2D7642D42F7AD2DF426CED356FDE05CE5461D0CC2F1BE2273C60139B81EA5907ABAD736AAE969AED1241FC998036D78D6B3C3B65BB2E3DFF690F35CF79ED348C7321131C1B536E7B9378F514AB5B02F788B80664CB83F0390C9B8CF053F7035C1D814967662D22F46D443038CB8453939BBB1F81CAEC8002421F999990AC31F2EFA715BD11213DAD2752773821393BCA1E603639B6F85B13BE6289BAE48AE01D61E0313D4505BE79AC301264204FEDB08C245E86CA5DABA65A17075ED3D771E28942835F8C1D3744A2FDE4921484391F38C8048ECAB6E99B3D69450C2439BE54BE6C93FE80D17566A2D2092FCEBE15EB5E411D0F020F1A20C0B6BC1501AD8444C95A9FCE5609BA42DE6FF5BAB22C36FA8C6E2B97D5A5DFB9399B226F7A4C6D91EEF0AD8E22F6F4B5676F4AABB6C9C1899E1B7F62186B1374699B3DD980E07AD1AC83472503AC83A457DE1D4D1C87E61A28475D56F3580029D708E526B88A8E13EA16C8EAFD289E385C4918EFD6CE8CE41F5CFB92D83782DD3A2D942E9ED984FB2EF66C1BBE228602868AC0BDE18BB8BC729F142AA725C1DE8044A4E61562764A4B4641E7F5F908E0422A913F148FFD3B0347AC7526EB1988E15A556BCAAE5C78C58A0EDA6F7E6A3B6312D34786E6B2E701D3F4084EA6303F83649A33C4EACB7EF888EC1B0600; m88_cookie=1711429898.20480.0000");
       // method.setRequestHeader("Cookie", "iovc=3558655; KeepOdds-31232766=no; MiniKey=max; dss=1%7C5000; incap_ses_809_1349630=ZdvTQaH/6Q5r6a94rSU6C9miTFsAAAAAoGSNkGtYfP+1MvF+33o2SA==; ASP.NET_SessionId=wihlaxn3jfd1p055lo0zfof4; lgnum_13=10_999; u=cbz20018; memLastLog=; t=U0VTU0lPTl9JRD02NTE3N0MyMTUzNUYzM0Y1RTQ2MUIyRTk2QkU1MURCOUMzQjQ4RjI1RjdCRDQzMTEwN0VCMDY2NDBBQjM1RDlENEVGREZENzdDOTEwOTUzMzU0Qjg5NDNFMjc3OTRDMzhBQkY1N0NGMTg3MTc0Qjc1NEFBMkI1NzEzQ0QwQThFQzVCRURBMkZCNjFBMzIyN0MxRUVGQUIyRjJCNEVGRTg5RTlERTg3NTE2MjE3RDRERTMxQkQ2NzIwMTcxNEIzMkRGRjhEQ0UyNDJFMjYwNkMyNDQzMzMwMjdDMjA4ODRCNzdDMDRCREY2M0Q4QTkyMEExQ0ZGRTEzMTgxODEyNUZBMDcwRDU5QkUxN0JBMUNDRkIxODA4MDhGQjlBRDJEMDEyRTU1MUVGRjUwNkVBRTg0NkNBNUM1MkFBMkQzODdFRTFBREQxQzUwQTJDMjY5OUMmVElDS0VUX0lEPTI0NTUyNzE2Mw%3d%3d; puid=cbz20018; ot=2; curr=RMB; lgnum_21=999; lgnum_12=10_999; m=0C5628D9-5009-4474-A53A-9879B90E93A4; userIPCountry=CN; selectedLanguage=zh-CN; IsBDLogin=false; LangKey=cs; switchViewSkinType_M88=0; DispVer=3; _ga=GA1.2.705112580.1531658063; _gid=GA1.2.221429814.1531658063; OddsType_MS88RMB01626539=2; incap_ses_1044_1349630=ifMDNWpj7DonOsEceAl9DiSpTFsAAAAAhVTlMwUKlTpQ3Mqx0pPXGg==; visid_incap_1349630=+QPpWZs9RjCA8D2aQwpKuy4/S1sAAAAAQUIPAAAAAABPdO/lsirVlPC0FYv4AEUM; incap_ses_795_1349630=hks2ON9iUhZQFD0mt2gIC1OpTFsAAAAA7cXHuDF6sVzCUoKYJJtP3A==; lgnum_11=999; ms_session=%7B%22brand%22%3A%22M88%22%2C%22channel%22%3A%7B%22trafficSource%22%3A%22DIRECT%22%2C%22trafficReferral%22%3A%22%22%2C%22referralURL%22%3A%22%22%2C%22referralFullURL%22%3A%22%22%2C%22registerFrom%22%3A%22www.m88ms.com%22%2C%22platform%22%3A%22Desktop%22%2C%22custom_data%22%3A%7B%7D%7D%7D; ms_traffic_source=DIRECT; __utmt=1; s=13E83051-87B8-497C-911F-1EF28568ACD8; g=13E83051-87B8-497C-911F-1EF28568ACD8; OddsType=2; category=0; lastdt=10~Soccer~2; curr1=RMB; OddsGroup=3; vp7=0; vp26=0; onHashID=; MPL=-99^5.0000^2000.0000^200000.0000|1^5.0000^20000.0000^200000.0000|2^5.0000^10000.0000^200000.0000|3^5.0000^3000.0000^200000.0000|90^5.0000^5000.0000^200000.0000|; __utma=206572623.705112580.1531658063.1531663013.1531749143.3; __utmb=206572623.7.10.1531749143; __utmc=206572623; __utmz=206572623.1531659170.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Elapse=3; categoryMenu=0; dtC=2; sr=10F002E6106E9EF2E7C0C580C500E5E3721E1C77EB2F0E422B1C213425ECC8A4E213EDDAC9619DCDF590D8BA72FCB2A4C8DC6C470881C527D7F82FFDE392C0B2B79A843C9B62B9E4F3A6C42294AF5D3CAD03BD6039BC32558D0D3193919CF32615A784E64EE3701A090676C7B0CD8200495121F89A9E7494FF10A53692914E95568E2091FD691DDA06C66176AFE1C29A60DD5BED5626E8C5FA1352B3B58C6D00DE185AA7F40AC90419A0FCE6C04C80A91B6F9E85BF085A27625A49CBE7B18767A2BBDFBCDA728D06D50DE9F6BCD42EEDB4EFB228F0723772D73F3B808D73FFC55C2F46578737967BFB344A105FB366589D287E7DBA7D4A4FA4BD1845EA250AC33F5FC1443D6757ED066E4A55D5E1485C7662579E41A926B2D2D63335CEAE09F55BAD71953BD118184633BA3796D7BF8B2135A08E67AED5B7C41949818CA0F021F74F08F81BACF3A64F7439E2ABF206814138E823C45B2951FCB2212AEDD3089BE476B8A3C53CE04895773E4816D337ADAC2DA14F077932CC6687FA034DAFEA7CC5C23E3940A1A303FB1480BEA3DFC6EFFDF0223AB24D8A13208A00568DFE81C1E6C3BD62FA4B1DCC6958B2F5E25A372D45B7FCCDBE8733039F46D50749F30D04C9ED3E6AD7C9279B2E899DB57D100529D579BBEFECAFC85D820954B1F21E1FBF52FF53EAC6165549F5130563A56FB0D170398AE8892474D948B5C6B293E6FB7D2CD7746B4236950FDAF69CB6D9D628BDA973CAF303EBDAFB6035E0E9AE44C2C0D77BF9DDDEE4EC55DB5D17768C5FC697C33D0AA2D2050D439D9C536F38D368E68EE060EE5598E4D1862AD007716B219FEE3B2628E64914566B8B3C9728B4672E69A8B65B34E5B3EB95AEDB7CC75F617166E5E5EEABF287DE08E0422A913F148FFD3B0347AC7526EBAE424B2BE097197E0C3B8DCAF94D05D16A3B6312D34786E6B2E701D3F4084EA611716B51993B6939CB7EF888EC1B0600; srttyp=0; m88_cookie=1778538762.20480.0000; Lspid=10_12_11_52");
        method.setRequestHeader("Cookie",Cookie);
        method.setRequestHeader("Host", "nss.m88ms.com");
        method.setRequestHeader("Origin", "https://nss.m88ms.com");
        method.setRequestHeader("Referer", "https://nss.m88ms.com/nss/tabmenu.aspx?lid=zh-CN");
        method.setRequestHeader("Upgrade-Insecure-Requests", "1");
        method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0");

//        STR:M_0_1H_3657302_30_0.950000_2_-0.2500_001_000_0_
      //    M_0_上半场_比赛编号_赔率前缀_赔率_2_平手盘_主队进球_客队进球_金额_
//        IsParlay:false
//        Mode:1
//        betLog:

        Map matchMap = new HashMap<String, String>();
        matchMap.put("STR", MatchDataUtils.getBetStr(msTyMatch,true));
        //登录 Lspid:10_12_11_52
        // matchMap.put("Lspid", "10_12_11_52");
        matchMap.put("IsParlay", "false");
        matchMap.put("Mode", "1");  //21正在滚球 2是未开始
        // matchMap.put("dt", "11");
        matchMap.put("betLog","");


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

            String result = method.getResponseBodyAsString();
            logger.info("=================== preBet http start======================");
            logger.info("preBet result:" + result);
            logger.info("========================= preBet http end=========================");
            if(result==null || result.length()<100){
                return null;
            } else {
                return result;
            }

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

    public String bet(MsTyMatch msTyMatch)  {

       // PostMethod method = new PostMethod("https://nss.m88ms.com/nss/TabBetSlipFrame.aspx");
        PostMethod method = new PostMethod(BetUrl);

        method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        method.setRequestHeader(" Accept-Encoding", "gzip, deflate, br");
        method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        method.setRequestHeader("Cache-Control", "max-age=0");
      method.setRequestHeader("Connection", "keep-alive");

        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        method.setRequestHeader("Cookie",Cookie);
        method.setRequestHeader("Host", "nss.m88ms.com");
        method.setRequestHeader("Origin", "https://nss.m88ms.com");
        method.setRequestHeader("Referer", "https://nss.m88ms.com/nss/tabmenu.aspx?lid=zh-CN");
        method.setRequestHeader("Upgrade-Insecure-Requests", "1");
        method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0");

        int money = msTyMatch.getMoney();

        if(money<100){
            method.setRequestHeader("Content-Length", "111");
        }else if(money>=100&&money<1000){
            method.setRequestHeader("Content-Length", "112");
        }else if(money>=1000&&money<10000){
            method.setRequestHeader("Content-Length", "113");
        }else if(money>=10000){
            method.setRequestHeader("Content-Length", "114");
        }

//        STR:M_0_2H_3657433_28_0.520000_2_0.00000_003_002_100_     M_0_上半场_比赛编号_？_赔率_2_平手盘_主队进球_客队进球_金额_
//        IsParlay:false
//        Mode:2
//        betLog:20180719122236612_R7GsHvqps00

        Map matchMap = new HashMap<String, String>();
        matchMap.put("STR", MatchDataUtils.getBetStr(msTyMatch,false));

       // matchMap.put("STR","M_0_1H_3660907_02_0.830000_2_0.00000_000_000_30_");
        //登录 Lspid:10_12_11_52
        // matchMap.put("Lspid", "10_12_11_52");
        matchMap.put("IsParlay", "false");
        matchMap.put("Mode", "2");  //21正在滚球 2是未开始
        // matchMap.put("dt", "11");
        matchMap.put("betLog",MatchDataUtils.uniqRand());




        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, Charset);
        method.setRequestBody(buildNameValuePair(matchMap));

        long startTime = System.currentTimeMillis();
        HttpClient client = new HttpClient();

        client.getHttpConnectionManager().getParams().setConnectionTimeout(100000);
        client.getHttpConnectionManager().getParams().setSoTimeout(100000);

        int statusCode = HttpStatus.SC_OK;
        long elapsedTime = 0;


        try {
            client.getParams().setCookiePolicy(
                    CookiePolicy.BROWSER_COMPATIBILITY);

            statusCode = client.executeMethod(method);

            String sessionId = null;
            String sr = null;
            Cookie[] cookies = client.getState().getCookies();
            StringBuffer tmpcookies = new StringBuffer();

            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
                if(c.getName().equalsIgnoreCase("ASP.NET_SessionId")){
                    sessionId = c.getValue();
                }
                if(c.getName().equalsIgnoreCase("sr")){
                    sr = c.getValue();
                }
                logger.info("-||"+c.toString()+"|");
            }

            if(sessionId!=null){
                String cookie1 = StringUtils.substringBefore(Cookie,"ASP.NET_SessionId=");
                String cookie2 = StringUtils.substringAfter(StringUtils.substringAfter(Cookie,"ASP.NET_SessionId="),";");
                String newCookie = cookie1+"ASP.NET_SessionId="+sessionId+";"+cookie2;
                logger.info("Cookie 变化 sessionId：");
                logger.info("老："+Cookie);
                logger.info("新："+newCookie);
                Cookie = newCookie;
            }
//            if(sr!=null){
//                String cookie1 = StringUtils.substringBeforeLast(Cookie,"sr=");
//                String cookie2 = StringUtils.substringAfter(StringUtils.substringAfterLast(Cookie,"sr="),";");
//                String newCookie = cookie1+"sr="+sr+";"+cookie2;
////                logger.info("Cookie 变化 sr ：");
////                logger.info("老："+Cookie);
////                logger.info("新："+newCookie);
//                Cookie = newCookie;
//            }


            elapsedTime = System.currentTimeMillis() - startTime;

            logger.info("调用http请求成功: " + method.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " + statusCode);

            String result = method.getResponseBodyAsString();
            logger.info("===================bet http start======================");
            logger.info("bet result:" + result+"|||msTyMatch:"+msTyMatch);
            logger.info("=========================bet http end=========================");

            if(result == null){
                return null;
            } else {
                return result;
            }

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


    public String getMatchListStr()  {

        //PostMethod method = new PostMethod("https://nss.m88ms.com/nss/Main2Data.aspx");
        PostMethod method = new PostMethod(ListUrl);
       // PostMethod method = new PostMethod("https://nss.m88ms.com/nss/Main1Data.aspx");

        method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        method.setRequestHeader(" Accept-Encoding", "gzip, deflate, br");
        method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        method.setRequestHeader("Cache-Control", "max-age=0");
      method.setRequestHeader("Connection", "keep-alive");
        method.setRequestHeader("Content-Length", "115");
        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
       // method.setRequestHeader("Cookie","iovc=3558655; KeepOdds-31232766=no; MiniKey=max; dss=1%7C10; ASP.NET_SessionId=fq2x0x3wouyxwu55osmgt455; curr=RMB; lgnum_13=10_999; lgnum_21=999; lgnum_12=10_999; u=cbz20018; memLastLog=; m=672B18CB-00F8-4AA9-A914-854A8923A58B; ms_session=%7B%22brand%22%3A%22M88%22%2C%22channel%22%3A%7B%22trafficSource%22%3A%22DIRECT%22%2C%22trafficReferral%22%3A%22%22%2C%22referralURL%22%3A%22%22%2C%22referralFullURL%22%3A%22%22%2C%22registerFrom%22%3A%22www.m88ms.com%22%2C%22platform%22%3A%22Desktop%22%2C%22custom_data%22%3A%7B%7D%7D%7D; ms_traffic_source=DIRECT; visid_incap_1349630=+QPpWZs9RjCA8D2aQwpKuy4/S1sAAAAAQUIPAAAAAABPdO/lsirVlPC0FYv4AEUM; incap_ses_809_1349630=bQZ6Qn5EED1rmlrKryU6C/etTlsAAAAAZP0Lf37D/XcSomtTfk1nqg==; IsBDLogin=false; LangKey=cs; switchViewSkinType_M88=0; DispVer=3; t=U0VTU0lPTl9JRD0zNkRGQjBFMDc0RTkwMThDNjQ4QzdGODg2NjJDRkFDMzdBQUZBODYzRjMwRTg0RTA5MzM0ODA2MEYyQjhGMzkwMEZBNTdCMzdCNUJDRjk3MkM2REE4OEMyMUVBQzEwQjI1QkQ4MzgyNzMzRDRDN0U1M0Y0M0U5NTVCRTdFRkY1Q0E1NkQ5ODMzNTU5N0VFOEVFQkVEQjlEMTE5OTVFRjVDNDVGODI0NDdFMTEwNDczNTJFQjhGMUJBNjUyQzNDRTI3NTgxRDU0NTY0OEI4RUQ1MDRBODFGN0ExRUQ3NEVFNzRDQ0ZDQTFGRkY0MDQ4Q0ZFQUYwRkM1NzE1NDFCMTYzMjRENDE5QzBDQzUyQzYxNENEOUJDQzQ0MjUzRUYwOEFFQ0UyMjQyNTM5NUIxMDgzNkQ0RkYwODNFNUFBNTIzQ0NCNzJDQjk2NDA2QjJFQTYmVElDS0VUX0lEPTI0NTgyNzY1NQ%3d%3d; puid=cbz20018; ot=2; _ga=GA1.2.705112580.1531658063; _gid=GA1.2.221429814.1531658063; OddsType_MS88RMB01626539=2; userIPCountry=CN; OddsType=2; s=18138360-BA2E-4CC8-A1FE-3880D1FC794A; g=18138360-BA2E-4CC8-A1FE-3880D1FC794A; selectedLanguage=zh-CN; category=0; lastdt=10~Soccer~2; curr1=RMB; OddsGroup=3; vp7=0; vp26=0; onHashID=; MPL=-99^5.0000^2000.0000^200000.0000|1^5.0000^20000.0000^200000.0000|2^5.0000^10000.0000^200000.0000|3^5.0000^3000.0000^200000.0000|90^5.0000^5000.0000^200000.0000|; categoryMenu=0; dtC=2; Elapse=2; __utmt=1; __utma=206572623.705112580.1531658063.1531902766.1531906876.11; __utmb=206572623.1.10.1531906876; __utmc=206572623; __utmz=206572623.1531659170.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Lspid=10_12_11_32_16_52; sr=10F002E6106E9EF2E7C0C580C500E5E3721E1C77EB2F0E4255058924E7463B18D59CF37E1779168065F1173A6C8A4B9D324C7175DDD3D66DA225D813908189CC021921E073E7EEF04D44C4722BC09A2B4279ED5E557C48F0C56FA4130FDEDEABF43F3AEA9C7976DF806D3B13B65296FCB6CB2BB2E4B97A9B82FF52AE3581F70E4EDC87CFDF648BE05588C4AAB5F4669F7359EEB4E740AAFF77FCCE17F921D33C4EF43ADCCC0F9867D4D3DFE3DCF8977C5CF872723546ED5C0AA7BD830F1DE8C3CE4DC47217FBB3ABFA568B371B9F7C110126B6D78CBCD068E35167AB548EB4B22B5E89E8BB5233B21D506EF287B6620577D4D2B7376FF6EBDAF761BBB1D57B620DB35C8C403E84856CFF3BCF3328B3EB733897388B9EC5909E6F2A6935D0A10EFF7B45132D47F5AD43A1B427780AF4F3D01728CB7D090B898BB9B038564BB4FC0E361370E0F8E5CB0D9A2C404D2CCCE4CF38DCDF8AD4A90BA1E56A2A7487EED2D66BCED38117706B48907CE9C14A76C84743422FCEFF1787E212D18C03912CB34FD0126A59EBA34F2DC2F78E7FA597D94343A972CD6361CE3553534117021DEFA59552A07AC58FD797000D3063B38A70764B701A0199E59CC740F97C9706012979742FBBD33F7CD8FF20A874825EC22021C73E80928603E53179E5CF5533E80C85C1CB2BEC0CD256F93A893546E23603E353E57C1578A4B58B8556D225C0018A121B2BB12C2F413002A5A91566EDBEA88D5CAA6085012D3EAC625A1809489D2E7464B21B76A553728DDF249E0B924EAAA3FF6DAD347E4F77FE005FBE9CB410B92FCF631FEB6D40741ED65E6A831EF51C8E4BBA32B2EC53DB50FA175FAF5D1E47EF6259E9EAEC6115901337BCADE3AC63D29D6373C560104708E0422A913F148FFD3B0347AC7526EB0FA1D06C2F46187FD02504F0096C4BDF6A3B6312D34786E6B2E701D3F4084EA66C9171427EBCED67FEAE17536F0848A5; m88_cookie=2181191946.20480.0000; srttyp=0");
        method.setRequestHeader("Cookie",Cookie);
        method.setRequestHeader("Host", "nss.m88ms.com");
        method.setRequestHeader("Origin", "https://nss.m88ms.com");

        method.setRequestHeader("Referer", "https://nss.m88ms.com/nss/main2.aspx?lid=zh-CN&spid=10&spname=Soccer&dt=2");
      //  method.setRequestHeader("Referer", "https://nss.m88ms.com/nss/main1.aspx?lid=zh-CN&spid=10&spname=%E8%B6%B3%E7%90%83&dt=1");

        method.setRequestHeader("Upgrade-Insecure-Requests", "1");
       // method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4620.400 QQBrowser/9.7.13014.400");
        method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0");

        Map matchMap = new HashMap<String, String>();
        matchMap.put("spid", "10");
        //登录 Lspid:10_12_11_52
       // matchMap.put("Lspid", "10_12_11_52");
        matchMap.put("ot", "2");
        matchMap.put("dt", "21");  //21正在滚球 2是未开始
       // matchMap.put("dt", "11");
        matchMap.put("vt", "0");
        matchMap.put("vs", "0");
        matchMap.put("tf", "0");
        matchMap.put("vd", "0");
        matchMap.put("lid", "ch");
        matchMap.put("lgnum", "999");
        matchMap.put("reqpart", "0");
        matchMap.put("verpart", "undefined");
        matchMap.put("prevParams", "undefined");
        matchMap.put("verpartLA", "");

        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, Charset);
        method.setRequestBody(buildNameValuePair(matchMap));

        long startTime = System.currentTimeMillis();
        HttpClient client = new HttpClient();

        client.getHttpConnectionManager().getParams().setConnectionTimeout(100000);
        client.getHttpConnectionManager().getParams().setSoTimeout(100000);

        int statusCode = HttpStatus.SC_OK;
        long elapsedTime = 0;

        try {

            client.getParams().setCookiePolicy(
                    CookiePolicy.BROWSER_COMPATIBILITY);

            statusCode = client.executeMethod(method);

            String sessionId = null;
            String sr = null;
            Cookie[] cookies = client.getState().getCookies();
            StringBuffer tmpcookies = new StringBuffer();

            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
                if(c.getName().equalsIgnoreCase("ASP.NET_SessionId")){
                    sessionId = c.getValue();
                }
                if(c.getName().equalsIgnoreCase("sr")){
                    sr = c.getValue();
                }
                logger.info("-||"+c.toString()+"|");
            }

            if(sessionId!=null){
                String cookie1 = StringUtils.substringBefore(Cookie,"ASP.NET_SessionId=");
                String cookie2 = StringUtils.substringAfter(StringUtils.substringAfter(Cookie,"ASP.NET_SessionId="),";");
                String newCookie = cookie1+"ASP.NET_SessionId="+sessionId+cookie2;
                logger.info("Cookie 变化 sessionId：");
                logger.info("老："+Cookie);
                logger.info("新："+newCookie);
                Cookie = newCookie;
            }
            if(sr!=null){
                String cookie1 = StringUtils.substringBeforeLast(Cookie,"sr=");
                String cookie2 = StringUtils.substringAfter(StringUtils.substringAfterLast(Cookie,"sr="),";");
                String newCookie = cookie1+"sr="+sr+";"+cookie2;
//                logger.info("Cookie 变化 sr ：");
//                logger.info("老："+Cookie);
//                logger.info("新："+newCookie);
                Cookie = newCookie;
            }


            elapsedTime = System.currentTimeMillis() - startTime;

            logger.info("调用http请求成功: " + method.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " + statusCode);

            String result = method.getResponseBodyAsString();
            logger.info("===================getMatchListStr http start======================");
            logger.info("getMatchListStr result Length:" + result.length());
            logger.info("=========================getMatchListStr http end=========================");
            if(result==null || result.length()<100){
                return null;
            } else {
                return result;
            }

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

    public void getMatchList(String matchListStr){
        logger.info("=========================getMatchList start=========================");
        logger.info(matchListStr.length()+"");
        String substring = StringUtils.substringBetween(matchListStr,"[[[","]\n],");
        if(StringUtils.isBlank(substring)){
            logger.info("*****==无比赛列表=*****");
            return;
        }

      //  String[] matchArray = StringUtils.split(substring,"],\n[");
        String[] matchArray = substring.split("],\n\\[");
        logger.info("==msTyMatches==:"+msTyMatches.size()+"|"+msTyMatches.toString());

        /*
        /0       ,1 ,2          ,3        ,4 , 5     , 6     , 7     , 8       , 9       ,10,11             ,12  ,13  , 14,15, 16    ,17,18,19,20  , 21      ,22,23             ,24             ,25,26,27,28,29,
        [联赛编号，0，联赛名称|0，赛事编号，0，时间戳，主队名，客队名，主队得分，客队得分，1，比赛已进行时间，0_0，0_0, |0,1|0,N（Y）, 1 ,0, 1,盘口，显示盘口，，全场上盘赔率|？，全场下盘赔率|?， ， ，5，3，全场大小盘盘口，
        全场大小盘显示盘口，1，大球赔率|？，小球赔率|？,,,,,,,,,,'1','7','1',胜赔|，平赔|，负赔|,'1|-1',,'2','2','0.0000','0|',,上半场胜赔率，上半场负赔率，
        ,,'6','4',上半场大小盘盘口，上半场大小盘显示盘口，，上半场大赔率|，半场小赔率|,,,,,,,,,,,,,,,,,,'1','0_0__N_90','16'],

         /0       ,  1 ,            2          ,3        ,4 , 5            , 6                 , 7             , 8   , 9 ,10,  11  ,12   ,13   , 14 ,15   , 16,17 ,18, 19  , 20     , 21    ,22,23             ,24            ,25,26,27,28,29,
        ['10022547','0','中国女子足球锦标赛|0','3644665','0','201807051600','河北华夏幸福 (女)','河南徽商 (女)','0_1','1','1','41`','0_0','0_0','|0','1|0','Y','1','0','16','0.2500','|0-0.5',,'0.7700|1.6700','0.9300|1.8300',  ,  ,'5','3','3.7500',
        30    ,31,32            , 33            ,34,35,36,37,38,39,40,41,42,43 ,44 ,45 ,46       ,47       ,48       ,49    ,50,51 ,52 ,53      ,54 ,55,56             ,57             ,
        '3.5-4',,'0.6700|1.5700','1.0300|1.9300',  ,  ,  ,  ,  ,  ,  ,  ,  ,'1','7','1','3.2000|','2.8500|','2.2000|','1|-1',  ,'2','2','0.0000','0|', ,'1.0400|1.9400','0.6600|1.5600',
        58,59 ,60 ,61      ,62   ,63, 64            ,65             ,
        , ,'6','4','2.5000','2.5',  ,'2.1700|2.7800','0.1600|1.0600',,,,,,,,,,,,,,,,,,'1','0_0__N_90','16'],

        [,,,,,,,,,,,,,,,,,,'0','21','2.2500','|2-2.5',,'1.2900|2.0600','0.6100|1.4400',,,'5','3','4.0000','4',,'1.0400|1.8800','0.7800|1.6200',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'8','0_0__Y_90','2'],
        [,,,'3644638','0','201807051630','泰国 U19','新加坡 U19','0_3','0','1','36`','0_0','0_0','|0','1|0','N','1','0','21','-1.5000','1.5|','1','0.8100|1.7600','1.0300|1.9800','0','1','5','22','5.5000','5.5','1','0.8200|1.7700','1.0000|1.9500','1','0',,,,,,,,,,,,,,,,'2','2','-0.2500','0-0.5|','1','1.2000|2.1300','0.6700|1.6200','1','0','6','4','3.5000','3.5','1','1.1900|2.1200','0.6600|1.6100','0','1',,,,,,,,,,,,,,,,'8','0_0__Y_90','3'],

        ['40'],['10~ch~AH_Live~Double~999~Hongkong~3~0~AllTime~Normal']];
         */
        MsTyMatch prevMsTyMatch = null;
        for( int i = 0;i < matchArray.length;i++){
            //logger.info("matchArray["+i+"]:"+matchArray[i]);

            //去引号
            String matchArrayStr = StringUtils.remove(StringUtils.remove(matchArray[i],"'"),"`");

            String[]  matchProps = matchArrayStr.split(",");

            if(StringUtils.isNotBlank(matchProps[1])&&!matchProps[1].equals("0")){
               // logger.info("--------非联赛跳过--matchProps[1]:"+matchProps[1]);
                continue;//非联赛跳过
            }
            if(StringUtils.isBlank(matchProps[10])){//只赔率数组特殊处理  //只更新prevMsTyMatch;

                //logger.info("-多个赔率处理--");
                /*
                只赔率数组特殊处理
                 */
                if(NumberUtils.toFloat(matchProps[20])==0 ){
                    //    ||(NumberUtils.toFloat(matchProps[20])>0&&NumberUtils.toFloat(matchProps[20])<prevMsTyMatch.getHalfHandicap())){
                    prevMsTyMatch.setHandicap(NumberUtils.toFloat(matchProps[20]));
                    prevMsTyMatch.setUpperOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[23],"|")));
                    prevMsTyMatch.setDownOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[24],"|")));
                }

                if(StringUtils.isNotBlank(matchProps[56])) {
                    if(NumberUtils.toFloat(matchProps[53])==0){
                         //   ||(NumberUtils.toFloat(matchProps[53])>0&&NumberUtils.toFloat(matchProps[53])<prevMsTyMatch.getHalfHandicap())){
                        prevMsTyMatch.setHalfHandicap(NumberUtils.toFloat(matchProps[53]));
                        prevMsTyMatch.setHalfUpperOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[56],"|")));
                        prevMsTyMatch.setHalfDownOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[57],"|")));
                    }
                }

                continue;
            }

            /*
             /0       ,1 ,2          ,3        ,4 , 5     , 6     , 7     , 8       , 9       ,10,11             ,12  ,13  , 14,15, 16    ,17,18,19,20  , 21      ,22,23             ,24             ,25,26,27,28,29,
            [联赛编号，0，联赛名称|0，赛事编号，0，时间戳，主队名，客队名，主队得分，客队得分，1，比赛已进行时间，0_0，0_0, |0,1|0,N（Y）, 1 ,0, 1,盘口，显示盘口，，全场上盘赔率|？，全场下盘赔率|?， ， ，5，3，全场大小盘盘口，
            全场大小盘显示盘口，1，大球赔率|？，小球赔率|？,,,,,,,,,,'1','7','1',胜赔|，平赔|，负赔|,'1|-1',,'2','2','0.0000','0|',,上半场胜赔率，上半场负赔率，
            ,,'6','4',上半场大小盘盘口，上半场大小盘显示盘口，，上半场大赔率|，半场小赔率|,,,,,,,,,,,,,,,,,,'1','0_0__N_90','16'],

             /0       ,  1 ,            2          ,3        ,4 , 5            , 6                 , 7             , 8   , 9 ,10,  11  ,12   ,13   , 14 ,15   , 16,17 ,18, 19  , 20     , 21    ,22,23             ,24            ,25,26,27,28,29,
            ['10022547','0','中国女子足球锦标赛|0','3644665','0','201807051600','河北华夏幸福 (女)','河南徽商 (女)','0_1','1','1','41`','0_0','0_0','|0','1|0','Y','1','0','16','0.2500','|0-0.5',,'0.7700|1.6700','0.9300|1.8300',  ,  ,'5','3','3.7500',
            30    ,31,32            , 33            ,34,35,36,37,38,39,40,41,42,43 ,44 ,45 ,46       ,47       ,48       ,49    ,50,51 ,52 ,53      ,54 ,55,56             ,57             ,
            '3.5-4',,'0.6700|1.5700','1.0300|1.9300',  ,  ,  ,  ,  ,  ,  ,  ,  ,'1','7','1','3.2000|','2.8500|','2.2000|','1|-1',  ,'2','2','0.0000','0|', ,'1.0400|1.9400','0.6600|1.5600',
            58,59 ,60 ,61      ,62   ,63, 64            ,65             ,
            , ,'6','4','2.5000','2.5',  ,'2.1700|2.7800','0.1600|1.0600',,,,,,,,,,,,,,,,,,'1','0_0__N_90','16'],

             */
            MsTyMatch msTyMatch = new MsTyMatch();
            if(StringUtils.isNotBlank(matchProps[2])){ //联赛名称
                msTyMatch.setLeagueName(StringUtils.substringBefore(matchProps[2], "|"));
            } else if  (prevMsTyMatch != null){
                msTyMatch.setLeagueName(prevMsTyMatch.getLeagueName());
            }
//            if (msTyMatch.getLeagueName().equals("梦幻对垒")){
//                logger.info("梦幻对垒跳过");
//                continue;
//            }
            msTyMatch.setMatchNo(NumberUtils.toInt(matchProps[3]));
            msTyMatch.setMatchDate(matchProps[5]);
            msTyMatch.setMasterTeamName(MatchDataUtils.cleanTeamName(matchProps[6]));
            msTyMatch.setGuestTeamName(MatchDataUtils.cleanTeamName(matchProps[7]));
            msTyMatch.setMasterGoalNum(NumberUtils.toInt(StringUtils.substringAfter(matchProps[8],"_")));
            msTyMatch.setGuestGoalNum(NumberUtils.toInt(matchProps[9]));
            msTyMatch.setMatchStep(NumberUtils.toInt(matchProps[10]));
            msTyMatch.setMatchTime(NumberUtils.toInt(matchProps[11]));
            msTyMatch.setCapfix(matchProps[19]);
            msTyMatch.setHandicap(NumberUtils.toFloat(matchProps[20]));
            msTyMatch.setUpperOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[23],"|")));
            msTyMatch.setDownOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[24],"|")));
            if(StringUtils.isNotBlank(matchProps[56])) {
                msTyMatch.setHalfCapfix(matchProps[52]);
                msTyMatch.setHalfHandicap(NumberUtils.toFloat(matchProps[53]));
                msTyMatch.setHalfUpperOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[56],"|")));
                msTyMatch.setHalfDownOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[57],"|")));
            }

           // logger.info("msTyMatch:"+msTyMatch);

           //测试
            if(prop.getString("testBet").equals("1")){
                msTyMatch.setBetH(true);
                msTyMatch.setMoney(Money);
               // msTyMatch.setUpperOdds(NumberUtils.toFloat("0.94"));

                String _preResult = this.bet(msTyMatch);
                logger.info("_preResult："+_preResult);
                break;
            }

//            Date _a = new Date();
//
//            if((_a.getTime() - _date.getTime())/1000>1200-(RandomUtils.getRandom5000to9999())/25){
//                msTyMatch.setBetH(true);
//                msTyMatch.setMoney(Money);
//                String _preResult = this.bet(msTyMatch);
//                if(_preResult==null){
//                    logger.info("定时投注失败_preResult is null",_preResult);
//                }else if(MatchDataUtils.parseBetResultCode(_preResult)==0
//                        || MatchDataUtils.parseBetResultCode(_preResult)==401){
//                    logger.info("定时投注成功_preResult："+_preResult);
//                    _date = _a;
//                }else {
//                    logger.info("定时投注失败_preResult："+_preResult);
//                }
//
//            }



            if (prevMsTyMatch == null) {
                prevMsTyMatch = msTyMatch;
                continue;
            }

            //如果已经入库，则5分钟一次更新数据     上一次数据
            if(msTyMatches.containsKey(prevMsTyMatch.getMatchNo())){
                logger.info("已经入库，prevMsTyMatch处理："+prevMsTyMatch);
                MsTyMatch oldMsTyMatch =  msTyMatches.get(prevMsTyMatch.getMatchNo());
                if(prevMsTyMatch.getMatchTime()-oldMsTyMatch.getMatchTime()>=4){//小于间隔时间不处理
                    logger.info("4分钟观察处理oldMsTyMatch："+oldMsTyMatch);
                    if(oldMsTyMatch.getMasterGoalNum() == prevMsTyMatch.getMasterGoalNum() && oldMsTyMatch.getGuestGoalNum() == prevMsTyMatch.getGuestGoalNum()){

                        String matchDataContain = MatchDataUtils.getMatchData(prevMsTyMatch.getMasterTeamName(),prevMsTyMatch.getGuestTeamName());
                        if(StringUtils.isBlank(matchDataContain)){
                            logger.info("4分钟观察处理内比赛数据为空："+oldMsTyMatch);
                            prevMsTyMatch = msTyMatch;
                            continue;
                        }

                        prevMsTyMatch.setMatchData(matchDataContain);

                      //  if(MatchDataUtils.assessMatchData(prevMsTyMatch)>1.00){

                            //根据投注策略投注
                           //最简单策略，用于测试 ，数据赔率双上升          后期加入1）2倍盈利定律金额 2）数据上升 赔率在可接受范围内波动
//                           if(prevMsTyMatch.getMatchStep()==2 || StringUtils.isBlank(prevMsTyMatch.getMatchTime()+"")){
//                               logger.info("############中场休息不处理："+prevMsTyMatch.getMatchData());
//                               continue;
//
//                           } else {
                        logger.info("比分没变化prevMsTyMatch："+prevMsTyMatch);
                               if(MatchDataUtils.isGreaterOOCap(prevMsTyMatch,oldMsTyMatch)         //>00赔率
                                       && MatchDataUtils.isOddsScope(prevMsTyMatch,oldMsTyMatch)  //可接受范围
                                       && MatchDataUtils.assessMatchDataOnlyShoot(prevMsTyMatch)>MatchDataUtils.assessMatchDataOnlyShoot(oldMsTyMatch)){ //数据上升

                                   //测试
                                if(prevMsTyMatch.getMasterGoalNum()>prevMsTyMatch.getGuestGoalNum()){
                                    prevMsTyMatch.setBetH(true);
                                }else {
                                    prevMsTyMatch.setBetH(false);
                                }
                                   double secondPre = MatchDataUtils.assessMatchData(prevMsTyMatch)*MatchDataUtils.getOdds(prevMsTyMatch);
                                   if(secondPre<0.7){
                                       logger.info("secondPre<0.7："+secondPre);
                                       prevMsTyMatch = msTyMatch;
                                       continue;
                                   }
                                   int second =(int)Math.ceil( MatchDataUtils.assessMatchData(prevMsTyMatch)*MatchDataUtils.getOdds(prevMsTyMatch));
                                   logger.info("############second："+second);

                                   prevMsTyMatch.setMoney(Money*second);

                                   String preResult = this.bet(prevMsTyMatch);
                                  // logger.info("preResult："+preResult);
                                   if(preResult==null){
                                       logger.info("preResult is null",preResult);
                                   }else if(MatchDataUtils.parseBetResultCode(preResult)==0){
                                       logger.info("投注成功0："+preResult);
                                   }else if(MatchDataUtils.parseBetResultCode(preResult)==401){
                                       logger.info("投注成功："+preResult);
                                   }else if(MatchDataUtils.parseBetResultCode(preResult)==402){
                                       float odds_ =  MatchDataUtils.parseBetResultOdds(preResult,prevMsTyMatch.getMoney());
                                       float cap_  =  MatchDataUtils.parseBetResultHandicap(preResult,prevMsTyMatch.getMoney());
                                       logger.info("赔率变化402| odds_ |  cap_:"+odds_+"|"+cap_);

                                       if(prevMsTyMatch.getMatchStep()==1){
                                           if(prevMsTyMatch.getHalfHandicap() == cap_){
                                               if(prevMsTyMatch.isBetH()){
                                                   if( odds_ - prevMsTyMatch.getHalfUpperOdds()>=-0.05 ){
                                                       prevMsTyMatch.setHalfUpperOdds(odds_);
                                                       logger.info("赔率变化，重新投注："+this.bet(prevMsTyMatch));
                                                   }
                                               }else {
                                                   if( odds_ - prevMsTyMatch.getHalfDownOdds()>=-0.05 ){
                                                       prevMsTyMatch.setHalfDownOdds(odds_);
                                                       logger.info("赔率变化，重新投注："+this.bet(prevMsTyMatch));
                                                   }
                                               }
                                           }else {
                                               logger.info("水位变化，跳过");
                                           }

                                       }else {
                                           if(prevMsTyMatch.getHandicap() == cap_){
                                               if(prevMsTyMatch.isBetH()){
                                                   if( odds_ - prevMsTyMatch.getUpperOdds()>=-0.05 ){
                                                       prevMsTyMatch.setUpperOdds(odds_);
                                                       logger.info("赔率变化，重新投注："+this.bet(prevMsTyMatch));
                                                   }
                                               }else {
                                                   if( odds_ - prevMsTyMatch.getDownOdds()>=-0.05 ){
                                                       prevMsTyMatch.setDownOdds(odds_);
                                                       logger.info("赔率变化，重新投注："+this.bet(prevMsTyMatch));
                                                   }
                                               }
                                           }else {
                                               logger.info("水位变化，跳过");
                                           }
                                       }

                                   }else {
                                       logger.info("preResult is error",preResult);
                                       prevMsTyMatch = msTyMatch;
                                       continue;
                                   }
                               }
                          // }

//                        } else {
//                            logger.info("############更新数据|不投注|不符合1倍盈利定律:" + prevMsTyMatch.toString());
//                            msTyMatches.remove(prevMsTyMatch.getMatchNo());
//                            msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
//                        }
                        //更新数据
                        logger.info("更新数据"+msTyMatches.remove(prevMsTyMatch.getMatchNo()).toString());
                        msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                    } else {
                        //比分变化 暂策略先去除
                        logger.info("############比分变化，出库："+ prevMsTyMatch.toString());
                        msTyMatches.remove(prevMsTyMatch.getMatchNo());
                       // msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                    }
                }else if (prevMsTyMatch.getMatchTime()-oldMsTyMatch.getMatchTime() < 0 ){
                    logger.info("@@@@@@@@@@@oldMsTyMatch.setMatchTime(0);");
                    oldMsTyMatch.setMatchTime(0);//半场比赛时间归0
                }

            }else {
                //入库策略，先必须仙人指路

                int goalDiff = prevMsTyMatch.getMasterGoalNum()-prevMsTyMatch.getGuestGoalNum();
                if (goalDiff != 0) {//比分

                    if ((goalDiff > 0 && prevMsTyMatch.getHandicap() >= 0 && prevMsTyMatch.getUpperOdds() > 0.700)
                            || (goalDiff < 0 && prevMsTyMatch.getHandicap() <= 0 && prevMsTyMatch.getDownOdds() > 0.700)){

                        if(prevMsTyMatch.getMatchTime()>=10){
                            //入库前获取比赛数据
                            String matchData = MatchDataUtils.getMatchData(prevMsTyMatch.getMasterTeamName(),prevMsTyMatch.getGuestTeamName());
                            if(matchData!=null){
                                //prevMsTyMatch.setMatchData(prevMsTyMatch.getBetData()+"|"+matchData);

                                prevMsTyMatch.setMatchData(matchData);
                                if(prevMsTyMatch.getMatchStep()==2 ) {
                                    logger.info("############中场休息，时间测试getMatchTime:" + prevMsTyMatch.getMatchTime());
                                    prevMsTyMatch.setMatchTime(0);
                                }

                                if(MatchDataUtils.assessMatchData(prevMsTyMatch)>0.6){
                                    prevMsTyMatch.setSaveTime(new Date());
                                    msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                                    //save
                                    logger.info("############入库:" + prevMsTyMatch.toString());
                                 }else {
                                  logger.info("############不入库|绝对劣势:" + prevMsTyMatch.toString());
                                 }

                            }else {
                                logger.info("############无法获取比赛数据，不入库:"+prevMsTyMatch.toString());
                            }
                        }else {
                            logger.info("############比赛时间不足，不入库:"+prevMsTyMatch.toString());
                        }



                    }
                }
            }

            prevMsTyMatch = msTyMatch;

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


}


