package huahua.common;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: PageResult
 * @Author: zhangyuyang
 * @Time : 2019/4/10 -下午4:23
 * @Version:1.0
 */
@Data
public class PageResult<T> {

    private Long total;

    private List<T> rows;

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
    }
}
