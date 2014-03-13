package com.pojo;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UserOperate {

    /**This Class is the tool class for Class User
     * @param null
     */
    private Session session = null;

    public UserOperate() {
        File file = new File("hibernate.cfg.xml");
        if (file.exists()) {
            Configuration config = new Configuration().configure(file);
            SessionFactory factory = config.buildSessionFactory();
            this.session = factory.openSession();
        } else {
            System.out.println("error");
            System.exit(0);
        }
    }//构造函数，应该不会错吧

    public void insert(User user) {
        session.getTransaction().begin();
        this.session.save(user);
        session.getTransaction().commit();
        //session.close();                                                               
    	}
        
    //插入User数据函数
    @SuppressWarnings("unchecked")
    public User queryByName(String name){
        User user=null;
        String hql="FROM User as p where p.name=?";
        Query q=this.session.createQuery(hql);
        q.setString(0,name);
        List<User> userList=q.list();
        Iterator<User> iter=userList.iterator();
        if(iter.hasNext()){
            user=iter.next();
        }
        return user;
    }//按名字查找数据
    
    public void delete(String name){
        String hql="delete User where name=?";
        Query q=this.session.createQuery(hql);
        q.setString(0, name);
        session.getTransaction().begin();
        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }//删除数据
    
    @SuppressWarnings("unchecked")
    public List<User> queryAll(){
        List<User> list=null;
        String hql="from User";
        Query q=this.session.createQuery(hql);
        list=q.list();
        return list;
    }//查找所有
    
    @SuppressWarnings("unchecked")
    public List<User> queryNameLike(String content){
        List<User> list=null;
        String hql="from User where name like ?";
        Query q=this.session.createQuery(hql);
        q.setString(0, "%"+content+"%");
        list=q.list();
        return list;
    }//模糊查询
    
    public boolean canLogin(String name,String password){
        boolean flag=false;
        User userGet=this.queryByName(name);
        if(userGet!=null){
           // password=password;
            if(userGet.getPassword().equals(password))
                flag=true;
            else
                System.out.println("密码错误");
        }
        else{
            System.out.println("没有该用户存在");
        }
        return flag;
    }//自己写的判断能否登录的函数，其中两次对password加密，可以判断提取出来的密码跟输入的password的一致性
}