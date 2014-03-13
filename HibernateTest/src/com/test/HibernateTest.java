package com.test;

import java.util.Iterator;
import java.util.List;

import com.pojo.User;
import com.pojo.UserOperate;

public class HibernateTest {

	public static void main(String[] args) {
        User user=new User();
        UserOperate userop=new UserOperate();
        
        user.setName("Newflypig");
        user.setPassword("8215085");
        
        userop.insert(user);
        
        User[] userArray=new User[10];
        
        for(int i=0;i<10;i++){
            userArray[i]=new User();
            userArray[i].setName("dingding"+String.valueOf(i));
            userArray[i].setPassword("newflypig");
            userop.insert(userArray[i]);
        }
        
        List<User> list=userop.queryAll();
        Iterator<User> iter=list.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next().getName());
        }
        System.out.println(userop.canLogin("dingding0", "newflypig"));
    }
}