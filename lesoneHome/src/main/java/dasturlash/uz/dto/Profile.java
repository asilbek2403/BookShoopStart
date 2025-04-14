package dasturlash.uz.dto;

import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;

import java.time.LocalDateTime;

public class Profile implements Comparable<Profile> {
    private Integer id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String phone;
    private ProfileStatus status;
    private ProfileRole role;
    private LocalDateTime createdDate;

    public String toWrite() {
        return id + "#" + name + "#" + surname + "#" + login + "#" + password + "#" + phone + "#" + status + "#" + role + "#" + createdDate;
    }

    public String getDetailAsString() {
        return id + " " + name + " " + surname + " " + login + " " + phone + " " + status + " " + role + " " + createdDate;
    }

    public String getStudentDetailAsString() {
        return id + " " + name + " " + surname + " " + login + " " + phone + " " + status + " " + createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ProfileStatus getStatus() {
        return status;
    }

    public void setStatus(ProfileStatus status) {
        this.status = status;
    }

    public ProfileRole getRole() {
        return role;
    }

    public void setRole(ProfileRole role) {
        this.role = role;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public int compareTo(Profile o) {
        return this.createdDate.compareTo(o.createdDate);

    }
}
