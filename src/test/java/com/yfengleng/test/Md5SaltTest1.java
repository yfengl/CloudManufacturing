package com.yfengleng.test;

import com.yfengleng.util.Md5SaltTool;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Md5SaltTest1 {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String[] password1={"testConsignee","testCOnsignee2","testConsignee3","admin"};
        String[] password2={"test4","myfactory","123456","admin"};
        String[] password3={"test1","test2","test1","test2"};
        for(int i=0;i<password1.length;i++){
            System.out.println(password1[i]+": "+Md5SaltTool.getEncryptedPwd(password1[i]));
        }
        for(int i=0;i<password2.length;i++){
            System.out.println(password2[i]+": "+Md5SaltTool.getEncryptedPwd(password2[i]));
        }
        for(int i=0;i<password3.length;i++){
            System.out.println(password3[i]+": "+Md5SaltTool.getEncryptedPwd(password3[i]));
        }
        if(Md5SaltTool.validPassword("123456","9A88F27A862F7B44D66C47CC8258002C01C7F4B21EC7A39E64AF30CF")){
            System.out.println("ok");
        }
        else
            System.out.println("false");
    }
}
