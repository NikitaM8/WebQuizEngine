package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@JsonIgnoreProperties(value = {"user"}, allowSetters = true)
@Entity
public class Completion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @CreationTimestamp
    private Timestamp completedAt;

    @JsonProperty("id")
    private  Long quizId;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    public Completion() {
    }

    public Completion(Long quiz, User user) {
        this.quizId = quiz;
        this.user = user;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
