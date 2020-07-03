package model;

public class AdditionalItemsAtPdp {
    String name;
    String mainPrice;

    public String getName() {
        return name;
    }

    public AdditionalItemsAtPdp withName(String name) {
        this.name = name;
        return this;
    }

    public String getMainPrice() {
        return mainPrice;
    }

    public AdditionalItemsAtPdp withMainPrice(String mainPrice) {
        this.mainPrice = mainPrice;
        return this;
    }

    @Override
    public String toString() {
        return "AdditionalItemsAtPdp{" +
                "name='" + name + '\'' +
                ", mainPrice='" + mainPrice + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdditionalItemsAtPdp)) return false;

        AdditionalItemsAtPdp that = (AdditionalItemsAtPdp) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getMainPrice() != null ? getMainPrice().equals(that.getMainPrice()) : that.getMainPrice() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getMainPrice() != null ? getMainPrice().hashCode() : 0);
        return result;
    }
}
