package co.com.jorge.springboot.app.util.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T> {

    private String url;

    private Page<T> page;

    private int totalPage;

    private int numberItemsByPage;

    private int actualPage;

    private List<PageItem> pages;

    public PageRender() {
    }

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pages = new ArrayList<PageItem>();

        numberItemsByPage = page.getSize();
        totalPage = page.getTotalPages();
        actualPage = page.getNumber() + 1;

        int from, to;
        if (totalPage <= numberItemsByPage){
            from = 1;
            to = totalPage;
        }else {
            if (actualPage <= numberItemsByPage / 2){
                from = 1;
                to = numberItemsByPage;
            }else if(actualPage >= totalPage - numberItemsByPage / 2){
                from = totalPage - numberItemsByPage + 1;
                to = numberItemsByPage;
            }else{
                from = actualPage - numberItemsByPage / 2;
                to = numberItemsByPage;
            }
        }

        for (int i = 0; i < to; i++) {
            pages.add(new PageItem(from + i, actualPage == from + i));
        }
    }

    public String getUrl() {
        return url;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getActualPage() {
        return actualPage;
    }

    public List<PageItem> getPages() {
        return pages;
    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){
        return page.isLast();
    }

    public boolean isHasNext(){
        return page.hasNext();
    }

    public boolean isHasPrevious(){
        return page.hasPrevious();
    }
}
