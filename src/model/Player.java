package model;

public class Player {
    private String name;
    private int age;
    private String skill;
    private Score score;

    public Player(String name, int age, String skill) {
        this.name = name;
        this.age = age;
        this.skill = skill;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSkill() {
        return skill;
    }

    public Score getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
