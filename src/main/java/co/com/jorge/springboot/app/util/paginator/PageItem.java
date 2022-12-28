package co.com.jorge.springboot.app.util.paginator;

public class PageItem {

    private int number;

    private boolean actual;

    public PageItem() {
    }

    public PageItem(int number, boolean actual) {
        this.number = number;
        this.actual = actual;
    }

    public int getNumber() {
        return number;
    }

    public boolean isActual() {
        return actual;
    }
}
