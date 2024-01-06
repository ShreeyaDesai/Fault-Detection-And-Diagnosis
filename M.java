package com.example.priya.reciever;

/*A code from www.bipinrupadiya.com*/
public class M
{
    public static char[] p = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    public static char[] ch = {'Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M'};
    public static  String doEncryption(String s)
    {



        char c[]=new char[(s.length())];
        for(int i=0;i<s.length();i++)
        {
            for(int j=0;j<26;j++)
            {
                if(p[j]==s.charAt(i))
                {
                    c[i]=ch[j];
                    break;
                }
            }
            System.out.println(s.charAt(i) +" "+c[i]);
        }
        System.out.println("Cipher Text : "+c);
        return(new String(c));
    }
    public static String doDecryption(String s)
    {
        System.out.println(s +" Len Of s :"+s.length());
        char p1[]=new char[(s.length())];
        for(int i=0;i<s.length();i++)
        {
            for(int j=0;j<26;j++)
            {
                if(ch[j]==s.charAt(i))
                {
                    p1[i]=p[j];
                    break;
                }
            }
        }
        System.out.println("Plain Text : "+new String(p1));
        return(new String(p1));
    }
}