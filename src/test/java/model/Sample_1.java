package model;

public class Sample_1 {
    int id;
    String name;

    public int getId() {
        return id;
    }

    public Sample_1 withId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Sample_1 withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Sample_1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
