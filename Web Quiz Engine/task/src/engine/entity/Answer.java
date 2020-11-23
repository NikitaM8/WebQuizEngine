package engine.entity;

import javax.validation.constraints.NotNull;

public class Answer {

    public Answer() {}

    @NotNull
    private int[] answer;

    public int[] getAnswer() {
        return this.answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
