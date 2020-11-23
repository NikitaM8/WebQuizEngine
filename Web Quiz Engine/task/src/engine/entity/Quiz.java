package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(value = {"answer", "user"}, allowSetters = true)
@Entity(name = "Quiz")
@DynamicUpdate
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Column
    private String title;

    @NotBlank(message = "Text is mandatory")
    @Column
    private String text;

    @NotNull(message = "Options can't be null")
    @Size(min = 2)
    @Column
    private String[] options;

    @Column
    private int[] answer;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    //@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    //private List<Completion> completions = new ArrayList<>();

    public Quiz() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz(String title, String text, String[] options, int[] answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Long getId() { return this.id; };

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return this.title; }

    public String getText() {
        return this.text;
    }

    public String[] getOptions() {
        return this.options;
    }

    public int[] getAnswer() { return this.answer; }

    public void setAnswer(int[] answer) { this.answer = answer; }
}
