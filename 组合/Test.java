public class Test {
    private static int i;

    public static void permutation1(String str ,String result ,int len){
        if(result.length()==len){            //表示遍历完了一个全排列结果
            System.out.println(result);
            System.out.println(++i);
        }
        else{
            for(int i=0;i<str.length();i++){
                if(result.indexOf(str.charAt(i))<0){    //返回指定字符在此字符串中第一次出现处的索引。
                    permutation1(str, result+str.charAt(i), len);
                }
            }
        }
    }

    public static void main(String args[]) throws Exception {
        String s = "abc";
        String result = "";
        permutation1(s, result, s.length());
    }
}
