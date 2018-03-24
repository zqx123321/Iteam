package cn.ouctechnology.domain;


import javax.persistence.*;

@Entity
public class Application {
    private Long id;
    private String clubName;
    private String clubCreate;
    private String clubOwner;
    private String clubPhone;
    private String clubEmail;
    private String intro;
    private Long token;
    private String submit;
    private String type;
    private String deal;
    private Long agreeAccount;
    private Long deptId;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String name) {
        this.clubName = name;
    }

    public String getClubCreate() {
        return clubCreate;
    }

    public void setClubCreate(String create) {
        this.clubCreate = create;
    }

    public String getClubOwner() {
        return clubOwner;
    }

    public void setClubOwner(String owner) {
        this.clubOwner = owner;
    }

    public String getClubPhone() {
        return clubPhone;
    }

    public void setClubPhone(String phone) {
        this.clubPhone = phone;
    }

    public String getClubEmail() {
        return clubEmail;
    }

    public void setClubEmail(String email) {
        this.clubEmail = email;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getToken() {
        return token;
    }

    public void setToken(Long token) {
        this.token = token;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    public Long getAgreeAccount() {
        return agreeAccount;
    }

    public void setAgreeAccount(Long agreeAccount) {
        this.agreeAccount = agreeAccount;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", clubName='" + clubName + '\'' +
                ", clubCreate='" + clubCreate + '\'' +
                ", clubOwner='" + clubOwner + '\'' +
                ", clubPhone='" + clubPhone + '\'' +
                ", clubEmail='" + clubEmail + '\'' +
                ", intro='" + intro + '\'' +
                ", token='" + token + '\'' +
                ", dept=" + deptId +
                '}';
    }
}
