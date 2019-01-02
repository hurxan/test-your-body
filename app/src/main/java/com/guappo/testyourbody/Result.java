package com.guappo.testyourbody;


public class Result {
    private int id;
    private String type;
    private int result;

    public Result() {

    }

    public Result(String type, int result) {
        super();
        this.type = type;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getResult() {
        return result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", result=" + result +
                '}';
    }
}
