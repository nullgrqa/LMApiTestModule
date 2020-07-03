package model;

public class NameCategoryAtPdp {

    String nameCategory;

    public String getNameCategory() {
        return nameCategory;
    }

    public NameCategoryAtPdp withNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
        return this;
    }

    @Override
    public String toString() {
        return "NameCategoryAtPdp{" +
                "nameCategory='" + nameCategory + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameCategoryAtPdp)) return false;

        NameCategoryAtPdp that = (NameCategoryAtPdp) o;

        return getNameCategory() != null ? getNameCategory().equals(that.getNameCategory()) : that.getNameCategory() == null;
    }

    @Override
    public int hashCode() {
        return getNameCategory() != null ? getNameCategory().hashCode() : 0;
    }
}
