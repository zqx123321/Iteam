package cn.ouctechnology.domain;

import javax.persistence.*;

@Entity
public class Admin {
    private Long id;
    private String account;
    private String name;
    private String password;
    private String email;
    private int adminClass;
    private Dept dept;

    public Admin() {
    }

    @Id
    //主键生成策略，GenerationType.AUTO表示根据数据库自动选择
    //MYSQL是native，oracle是sequence
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //可以选择添加@Column注解，对应表的列名
    public String getAccount() {
        return account;
    }

    @ManyToOne(targetEntity = Dept.class)
    @JoinColumn(name = "deptId")
    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdminClass() {
        return adminClass;
    }

    public void setAdminClass(int adminClass) {
        this.adminClass = adminClass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", dept=" + dept +
                '}';
    }
}
