package object;

/**
 * Created by Sai sreenivas on 10/30/2017.
 */

public class DateAndCount {
    String Date;
    int Count;
    int Shirts;
    int Pants;
    int Inners;

    public DateAndCount() {
    }

    public DateAndCount(String date, int count) {
        Date = date;
        Count = count;
    }

    public DateAndCount(String date, int shirts, int pants, int inners) {
        Date = date;
        Shirts = shirts;
        Pants = pants;
        Inners = inners;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public int getShirts() {
        return Shirts;
    }

    public void setShirts(int shirts) {
        Shirts = shirts;
    }

    public int getPants() {
        return Pants;
    }

    public void setPants(int pants) {
        Pants = pants;
    }

    public int getInners() {
        return Inners;
    }

    public void setInners(int inners) {
        Inners = inners;
    }
}
