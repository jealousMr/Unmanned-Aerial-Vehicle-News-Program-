package weprosever.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class IdTool {

    public String getUserId(String code){
        String AppId="wxf17e681fda3c8379";
        String AppSercet="d7e5fa9bff577bda1223a422c4a14df5";
        String URLs="https://api.weixin.qq.com/sns/jscode2session";
        BufferedReader in =null;
        String result="";
        try{
            String urlName=URLs+"?appid="+AppId+"&js_code="+code+"&secret="+AppSercet+"&grant_type=authorization_code";
           // System.out.println(urlName);
            URL realUrl=new URL(urlName);
            URLConnection connection=realUrl.openConnection();
            connection.connect();
            Map<String, List<String>> map=connection.getHeaderFields();
            in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line=in.readLine())!=null){
                result+=line;
            }
            //System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String getArticleId(String userID,String articleTitle){
        int x=(int)(Math.random()*10000);
        int len=articleTitle.length()>10?10:articleTitle.length();
        return userID+articleTitle.substring(0,len)+x;
    }
    public String getVideoId(String userID,String title){
        int x=(int)(Math.random()*10000);
        int len=title.length()>10?10:title.length();
        return userID+title.substring(0,len)+x;
    }
    public String getNowTime(){
        int y,m,d;
        Calendar cal=Calendar.getInstance();
        y=cal.get(Calendar.YEAR);
        m=cal.get(Calendar.MONTH);
        d=cal.get(Calendar.DATE);
        String s=y+"/"+(m+1)+"/"+d;
         return s;
    }
    public int[] getFourRand(int max){
        int[] a={-1,-1,-1,-1};
        Random random=new Random();
       for (int i=0;i<a.length;i++){
           int temp;
           while (true){
               temp=random.nextInt(max)+1;
               if(temp!=a[0]&&temp!=a[1]&&temp!=a[2]&&temp!=a[3]){
                   a[i]=temp;
                   break;
               }
           }
       }
       return a;
    }
    public static int getMax(int a,int b,int c,int d){
        int i=a;
        if(i<b)
            i=b;
        if(i<c)
            i=c;
        if(i<d)
            i=d;
        return i;
    }
}
