import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created with IntelliJ IDEA.
 * User: chenzx
 * Date: 13-10-18
 * Time: 下午11:21
 * To change this template use File | Settings | File Templates.
 */
public class RandomUtils {
    /**
     * 50000~99999
     * @return
     */
    public static int getRandom50000to99999(){
        int a = NumberUtils.toInt(RandomStringUtils.randomNumeric(5));
        if(a<50000){
            a = a+50000;
        }
        return a;
    }

    public static int getRandom5000to9999(){
        int a = NumberUtils.toInt(RandomStringUtils.randomNumeric(4));
        if(a<5000){
            a = a+5000;
        }
        return a;
    }

    public static int getRandom10to25(){
        int a = NumberUtils.toInt(RandomStringUtils.randomNumeric(2));
        if(a<=50){
            return 10;
        }else if(a>50&&a<=70){
            return 15;
        }else if(a>70&&a<=90){
            return 20;
        }else{
            return 25;
        }
    }

    public static void main(String[] args){
//        for(int i=0; i<100;i++){
//            System.out.println(getRandom50000to99999());
//        }
//
//        for(int i=0; i<100;i++){
//            System.out.println(getRandom5000to9999());
//        }

//        System.out.println(RandomStringUtils.randomNumeric(2));
//        System.out.println(RandomStringUtils.random(2, "123") );
        for (int i=0;i<10;i++){
            System.out.println(RandomStringUtils.randomNumeric(2)+":"+RandomUtils.getRandom10to25());
        }
    }

}
