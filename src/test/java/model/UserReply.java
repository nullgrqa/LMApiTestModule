package model;

public class UserReply {
    String rating;
    String body;
    String pros;
    String cons;
    String location_name;
    String author_name;
    String created_at;
    String label; // Срок использования
    String value; // больше года
    Boolean recommended;

    public String getRating() {
        return rating;
    }

    public UserReply withRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getBody() {
        return body;
    }

    public UserReply withBody(String body) {
        this.body = body;
        return this;
    }

    public String getPros() {
        return pros;
    }

    public UserReply withPros(String pros) {
        this.pros = pros;
        return this;
    }

    public String getCons() {
        return cons;
    }

    public UserReply withCons(String cons) {
        this.cons = cons;
        return this;
    }

    public String getLocation_name() {
        return location_name;
    }

    public UserReply withLocation_name(String location_name) {
        this.location_name = location_name;
        return this;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public UserReply withAuthor_name(String author_name) {
        this.author_name = author_name;
        return this;
    }

    public String getCreated_at() {
        return created_at;
    }

    public UserReply withCreated_at(String created_at) {
        this.created_at = created_at;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public UserReply withLabel(String label) {
        this.label = label;
        return this;
    }

    public String getValue() {
        return value;
    }

    public UserReply withValue(String value) {
        this.value = value;
        return this;
    }

    public Boolean getRecommended() {
        return recommended;
    }

    public UserReply withRecommended(Boolean recommended) {
        this.recommended = recommended;
        return this;
    }

    @Override
    public String toString() {
        return "UserReply{" +
                "rating='" + rating + '\'' +
                ", body='" + body + '\'' +
                ", pros='" + pros + '\'' +
                ", cons='" + cons + '\'' +
                ", location_name='" + location_name + '\'' +
                ", author_name='" + author_name + '\'' +
                ", created_at='" + created_at + '\'' +
                ", label='" + label + '\'' +
                ", value='" + value + '\'' +
                ", recommended=" + recommended +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserReply)) return false;

        UserReply userReply = (UserReply) o;

        if (getRating() != null ? !getRating().equals(userReply.getRating()) : userReply.getRating() != null)
            return false;
        if (getBody() != null ? !getBody().equals(userReply.getBody()) : userReply.getBody() != null) return false;
        if (getPros() != null ? !getPros().equals(userReply.getPros()) : userReply.getPros() != null) return false;
        if (getCons() != null ? !getCons().equals(userReply.getCons()) : userReply.getCons() != null) return false;
        if (getLocation_name() != null ? !getLocation_name().equals(userReply.getLocation_name()) : userReply.getLocation_name() != null)
            return false;
        if (getAuthor_name() != null ? !getAuthor_name().equals(userReply.getAuthor_name()) : userReply.getAuthor_name() != null)
            return false;
        if (getCreated_at() != null ? !getCreated_at().equals(userReply.getCreated_at()) : userReply.getCreated_at() != null)
            return false;
        if (getLabel() != null ? !getLabel().equals(userReply.getLabel()) : userReply.getLabel() != null) return false;
        if (getValue() != null ? !getValue().equals(userReply.getValue()) : userReply.getValue() != null) return false;
        return getRecommended() != null ? getRecommended().equals(userReply.getRecommended()) : userReply.getRecommended() == null;
    }

    @Override
    public int hashCode() {
        int result = getRating() != null ? getRating().hashCode() : 0;
        result = 31 * result + (getBody() != null ? getBody().hashCode() : 0);
        result = 31 * result + (getPros() != null ? getPros().hashCode() : 0);
        result = 31 * result + (getCons() != null ? getCons().hashCode() : 0);
        result = 31 * result + (getLocation_name() != null ? getLocation_name().hashCode() : 0);
        result = 31 * result + (getAuthor_name() != null ? getAuthor_name().hashCode() : 0);
        result = 31 * result + (getCreated_at() != null ? getCreated_at().hashCode() : 0);
        result = 31 * result + (getLabel() != null ? getLabel().hashCode() : 0);
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        result = 31 * result + (getRecommended() != null ? getRecommended().hashCode() : 0);
        return result;
    }
}
