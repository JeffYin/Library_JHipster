package com.dream.books.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bibliograph.
 */
@Entity
@Table(name = "T_BIBLIOGRAPH")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

public class Bibliograph implements Serializable {
	private static final long serialVersionUID = -4300729851675884342L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "intro")
    private String intro;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "call_number")
    private String callNumber;

    @Column(name = "due_days")
    private Integer dueDays;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "type")
    private Integer type;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tag> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public Integer getDueDays() {
        return dueDays;
    }

    public void setDueDays(Integer dueDays) {
        this.dueDays = dueDays;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bibliograph bibliograph = (Bibliograph) o;

        if (id != null ? !id.equals(bibliograph.id) : bibliograph.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Bibliograph{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", author='" + author + "'" +
                ", intro='" + intro + "'" +
                ", publisher='" + publisher + "'" +
                ", callNumber='" + callNumber + "'" +
                ", dueDays='" + dueDays + "'" +
                ", imageUrl='" + imageUrl + "'" +
                ", type='" + type + "'" +
                '}';
    }
}
