package utils;
/** 
 * @类名:FilterStr 
 * @描述:正则表达式过滤数字、字母和中文 
 * @Author：游海东 
 * @date: 2014年3月12日 下午7:18:20 
 */  
public class FilterStr   
{  
 /** 
 *  
 * @Title : filterNumber 
 * @Type : FilterStr 
 * @date : 2014年3月12日 下午7:23:03 
 * @Description : 过滤出数字 
 * @param str 
 * @return 
 */  
 public static String filterNumber(String number)  
 {  
 number = number.replaceAll("[^(0-9)]", "");  
 return number;  
 }  
    
 /** 
 *  
 * @Title : filterAlphabet 
 * @Type : FilterStr 
 * @date : 2014年3月12日 下午7:28:54 
 * @Description : 过滤出字母 
 * @param alph 
 * @return 
 */  
 public static String filterAlphabet(String alph)  
 {  
 alph = alph.replaceAll("[^(A-Za-z)]", "");  
 return alph;  
 }  
    
 /** 
 *  
 * @Title : filterChinese 
 * @Type : FilterStr 
 * @date : 2014年3月12日 下午9:12:37 
 * @Description : 过滤出中文 
 * @param chin 
 * @return 
 */  
 public static String filterChinese(String chin)  
 {  
 chin = chin.replaceAll("[^(\\u4e00-\\u9fa5)]", "");  
 return chin;  
 }  
    
 /** 
 *  
 * @Title : filter 
 * @Type : FilterStr 
 * @date : 2014年3月12日 下午9:17:22 
 * @Description : 过滤出字母、数字和中文 
 * @param character 
 * @return 
 */  
 public static String filter(String character)  
 {  
 character = character.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", "");  
 return character;  
 }  
    
 /** 
 * @Title : main 
 * @Type : FilterStr 
 * @date : 2014年3月12日 下午7:18:22 
 * @Description :  
 * @param args 
 */  
 public static void main(String[] args)   
 {  
 /** 
  * 声明字符串you 
  */  
 String you = "^&^&^you123$%$%你好";  
 /** 
  * 调用过滤出数字的方法 
  */  
 you = filterNumber(you);  
 /** 
  * 打印结果 
  */  
 System.out.println("过滤出数字：" + you);  
    
 /** 
  * 声明字符串hai 
  */  
 String hai = "￥%……4556ahihdjsadhj$%$%你好吗wewewe";  
 /** 
  * 调用过滤出字母的方法 
  */  
 hai = filterAlphabet(hai);  
 /** 
  * 打印结果 
  */  
 System.out.println("过滤出字母：" + hai);  
    
 /** 
  * 声明字符串dong 
  */  
 String dong = "$%$%$张三34584yuojk李四@#￥#%%￥……%&";  
 /** 
  * 调用过滤出中文的方法 
  */  
 dong = filterChinese(dong);  
 /** 
  * 打印结果 
  */  
 System.out.println("过滤出中文：" + dong);  
    
 /** 
  * 声明字符串str 
  */  
 String str = "$%$%$张三34584yuojk李四@#￥#%%￥……%&";  
 /** 
  * 调用过滤出字母、数字和中文的方法 
  */  
 str = filter(str);  
 /** 
  * 打印结果 
  */  
 System.out.println("过滤出字母、数字和中文：" + str);  
    
 }  
   
}  