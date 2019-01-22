package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.IdWorker;

/**
 * @Description TODO
 * @Author "zhouhai"
 * @Date2019/1/2123:12
 **/
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    public void save(Article article) {

        article.setId(idWorker.nextId()+"");

        articleDao.save(article);

    }

    public Page<Article> findByKey(String key, int page, int size) {

        PageRequest pageable = PageRequest.of(page - 1, size);
        return                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              articleDao.findByTitleOrContentLike(key, key, pageable);

    }
}
