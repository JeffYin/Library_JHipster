package com.dream.books.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Reader.
 */
@Entity
@Table(name = "T_READER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
    @NamedQuery(
	    name = "Reader.getByNameLike",
	    query= "from Reader where name like ?"
   )
})
public class Reader implements Serializable {
	private static final long serialVersionUID = -5478768464972594604L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "permanent_no")
    private String permanentNo;

    @Column(name = "name")
    private String name;

    @Column(name = "home_phone")
    private String homePhone;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "post_code")
    private String postCode;

    @OneToMany(mappedBy = "reader")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReaderCard> readerCards = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermanentNo() {
        return permanentNo;
    }

    public void setPermanentNo(String permanentNo) {
        this.permanentNo = permanentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Set<ReaderCard> getReaderCards() {
        return readerCards;
    }

    public void setReaderCards(Set<ReaderCard> readerCards) {
        this.readerCards = readerCards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Reader reader = (Reader) o;

        if (id != null ? !id.equals(reader.id) : reader.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", permanentNo='" + permanentNo + "'" +
                ", name='" + name + "'" +
                ", homePhone='" + homePhone + "'" +
                ", mobilePhone='" + mobilePhone + "'" +
                ", email='" + email + "'" +
                ", address='" + address + "'" +
                ", postCode='" + postCode + "'" +
                '}';
    }
}
