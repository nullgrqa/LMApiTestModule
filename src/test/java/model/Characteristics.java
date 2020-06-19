package model;

import java.util.ArrayList;
import java.util.List;

public class Characteristics {

    String description;
    String value;

    public String getDescription() {
        return description;
    }

    public Characteristics withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Characteristics withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "Characteristics{" +
                "description='" + description + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Characteristics)) return false;

        Characteristics that = (Characteristics) o;

        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = getDescription() != null ? getDescription().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}

