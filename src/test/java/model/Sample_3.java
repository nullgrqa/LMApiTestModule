package model;

public class Sample_3 {
    int id;
    String name;
    String describe;

    public int getId() {
        return id;
    }

    public Sample_3 withId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Sample_3 withName(String name) {
        this.name = name;
        return this;
    }

    public String getDescribe() {
        return describe;
    }

    public Sample_3 withDescribe(String describe) {
        this.describe = describe;
        return this;
    }

    @Override
    public String toString() {
        return "Sample_3{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
