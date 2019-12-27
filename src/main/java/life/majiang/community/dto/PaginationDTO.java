package life.majiang.community.dto;

import java.util.ArrayList;
import java.util.List;

//重新封装了一个对象，这个对象包括：分页功能 + 遍历数据，这些都传到前端去
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showFirstPage;
    private boolean showPrevious;
    private boolean showNextPage;
    private boolean showEndPage;
    private List<Integer> pages = new ArrayList<>();
    private Integer page;
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {
                                    //  7                 1
        this.totalPage = totalPage;
        this.page = page;
        pages.add(page);           //先把当前页放进去pages里
        for (int i = 1; i <= 3; i++){    //遍历三次的原因，是因为我们只需要前三页或后三页
            if (page - i > 0){           //当page=4，通过循环page-i，得到3、2、1
                pages.add(0, page - i);    //然后把得到的值不停的放在索引0上，就反过来变成了1、2、3
            }

            if (page + i <= totalPage){  //当page=4，通过循环page+i，得到5、6、7，由于未页就是第7页，所以是小于等于第7页
                pages.add(page + i);     //把5、6、7放到pages里
            }
        }
        //当page=1时，上一页就不显示
        if (page == 1){
            showPrevious = false;
        }else{
            showPrevious = true;
        }
        //当page等于最后一页时，下一页就不显示
        if (page == totalPage){
            showNextPage = false;
        }else{
            showNextPage = true;
        }
        //分页栏里如果还显示第一页，那首页的图标就不显示
        if (pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }
        //分页栏里如果还显示最后一页，那未页的图标就不显示
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }

    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public boolean getShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean getShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean getShowNextPage() {
        return showNextPage;
    }

    public void setShowNextPage(boolean showNextPage) {
        this.showNextPage = showNextPage;
    }

    public boolean getShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "PaginationDTO{" +
                "data=" + data +
                ", showFirstPage=" + showFirstPage +
                ", showPrevious=" + showPrevious +
                ", showNextPage=" + showNextPage +
                ", showEndPage=" + showEndPage +
                ", pages=" + pages +
                ", page=" + page +
                ", totalPage=" + totalPage +
                '}';
    }
}
