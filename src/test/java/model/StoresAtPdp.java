package model;

public class StoresAtPdp {

    public int storeId;
    public String nameStore;
    public String qntAtStock;
    public String address;
    public String workTime;

    public String getNameStore() {
        return nameStore;
    }

    public StoresAtPdp withNameStore(String nameStore) {
        this.nameStore = nameStore;
        return this;
    }

    public String getQntAtStock() {
        return qntAtStock;
    }

    public StoresAtPdp withQntAtStock(String qntAtStock) {
        this.qntAtStock = qntAtStock;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public StoresAtPdp withAddress(String address) {
        this.address = address;
        return this;
    }

    public String getWorkTime() {
        return workTime;
    }

    public StoresAtPdp withWorkTime(String workTime) {
        this.workTime = workTime;
        return this;
    }

    public int getStoreId() {
        return storeId;
    }

    public StoresAtPdp withStoreId(int storeId) {
        this.storeId = storeId;
        return this;
    }

    @Override
    public String toString() {
        return "StoresAtPdp{" +
                "storeId=" + storeId +
                ", nameStore='" + nameStore + '\'' +
                ", qntAtStock='" + qntAtStock + '\'' +
                ", address='" + address + '\'' +
                ", workTime='" + workTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoresAtPdp)) return false;

        StoresAtPdp that = (StoresAtPdp) o;

        if (getStoreId() != that.getStoreId()) return false;
        if (getNameStore() != null ? !getNameStore().equals(that.getNameStore()) : that.getNameStore() != null)
            return false;
        if (getQntAtStock() != null ? !getQntAtStock().equals(that.getQntAtStock()) : that.getQntAtStock() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        return getWorkTime() != null ? getWorkTime().equals(that.getWorkTime()) : that.getWorkTime() == null;
    }

    @Override
    public int hashCode() {
        int result = getStoreId();
        result = 31 * result + (getNameStore() != null ? getNameStore().hashCode() : 0);
        result = 31 * result + (getQntAtStock() != null ? getQntAtStock().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getWorkTime() != null ? getWorkTime().hashCode() : 0);
        return result;
    }
}
