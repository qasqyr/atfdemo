package kz.atf.atfdemo.response;

import java.util.LinkedList;
import java.util.List;

public class ListResponse<T> {
    public List<T> data = new LinkedList<>();

    public ListResponse(List<T> data) {
        this.data = data;
    }
}
