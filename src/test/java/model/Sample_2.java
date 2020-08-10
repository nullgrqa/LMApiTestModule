package model;

public class Sample_2 {
    int id;
    String describe;

    public int getId() {
        return id;
    }

    public Sample_2 withId(int id) {
        this.id = id;
        return this;
    }

    public String getDescribe() {
        return describe;
    }

    public Sample_2 withDescribe(String describe) {
        this.describe = describe;
        return this;
    }

    @Override
    public String toString() {
        return "Sample_2{" +
                "id=" + id +
                ", describe='" + describe + '\'' +
                '}';
    }
}
