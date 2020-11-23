package engine.entity;

public class ResultAnswer {

    boolean success;
    String feedback;

    public ResultAnswer() {
    }

    public ResultAnswer(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }
}
