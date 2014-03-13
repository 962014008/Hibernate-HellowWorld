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
    }//���캯����Ӧ�ò�����

    public void insert(User user) {
        session.getTransaction().begin();
        this.session.save(user);
        session.getTransaction().commit();
        //session.close();                                                               
    	}
        
    //����User���ݺ���
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
    }//�����ֲ�������
    
    public void delete(String name){
        String hql="delete User where name=?";
        Query q=this.session.createQuery(hql);
        q.setString(0, name);
        session.getTransaction().begin();
        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }//ɾ������
    
    @SuppressWarnings("unchecked")
    public List<User> queryAll(){
        List<User> list=null;
        String hql="from User";
        Query q=this.session.createQuery(hql);
        list=q.list();
        return list;
    }//��������
    
    @SuppressWarnings("unchecked")
    public List<User> queryNameLike(String content){
        List<User> list=null;
        String hql="from User where name like ?";
        Query q=this.session.createQuery(hql);
        q.setString(0, "%"+content+"%");
        list=q.list();
        return list;
    }//ģ����ѯ
    
    public boolean canLogin(String name,String password){
        boolean flag=false;
        User userGet=this.queryByName(name);
        if(userGet!=null){
           // password=password;
            if(userGet.getPassword().equals(password))
                flag=true;
            else
                System.out.println("�������");
        }
        else{
            System.out.println("û�и��û�����");
        }
        return flag;
    }//�Լ�д���ж��ܷ��¼�ĺ������������ζ�password���ܣ������ж���ȡ����������������password��һ����
}