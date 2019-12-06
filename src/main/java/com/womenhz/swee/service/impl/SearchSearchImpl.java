package com.womenhz.swee.service.impl;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.womenhz.swee.indextemplate.UserIndexKey;
import com.womenhz.swee.indextemplate.UserIndexTemplate;
import com.womenhz.swee.model.User;
import com.womenhz.swee.service.SearchService;
import com.womenhz.swee.service.UserService;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SearchSearchImpl implements SearchService {

    public static final String INDEX_NAME = "swee";

    public static final String INDEX_TYPE = "user";

    @Autowired
    private TransportClient esClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    private void createOrUpdateIndex(Long userId) {
        User user = userService.getById(userId);
        UserIndexTemplate userIndexTemplate = UserIndexTemplate.builder().build();
        BeanUtils.copyProperties(user, userIndexTemplate);
        userIndexTemplate.setUserId(userId);
        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME).setTypes(INDEX_TYPE)
                .setQuery(QueryBuilders.termQuery(UserIndexKey.USER_ID, userId));

        SearchResponse searchResponse = requestBuilder.get();
        Long totalHits = searchResponse.getHits().getTotalHits();

        if (totalHits == 0) { 
            create(userIndexTemplate);
        } else if (totalHits == 1) {
            String esId = searchResponse.getHits().getAt(0).getId();
            update(esId, userIndexTemplate);
        } else {
            deleteAndCreate(totalHits, userIndexTemplate);
        }
    }

    private boolean deleteAndCreate(Long totalHits, UserIndexTemplate userIndexTemplate) {
        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(esClient)
                .filter(QueryBuilders.termQuery(UserIndexKey.USER_ID, userIndexTemplate.getUserId()))
                .source(INDEX_NAME);

        log.debug("Delete by query for house: " + builder);

        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();
        if (deleted != totalHits) {
            log.warn("Need delete {}, but {} was deleted!", totalHits, deleted);
            return false;
        } else {
            return create(userIndexTemplate);
        }
    }

    private boolean update(String esId, UserIndexTemplate userIndexTemplate) {
        try {
            UpdateResponse response = this.esClient
                    .prepareUpdate(INDEX_NAME, INDEX_TYPE, esId)
                    .setDoc(objectMapper.writeValueAsBytes(userIndexTemplate), XContentType.JSON).get();

            log.debug("Update index with house: " + userIndexTemplate.getUserId());
            if (response.status() == RestStatus.OK) {
                return true;
            } else {
                return false;
            }
        } catch (JsonProcessingException e) {
            log.error("Error to index house " + userIndexTemplate.getUserId(), e);
            return false;
        }
    }

    private boolean create(UserIndexTemplate userIndexTemplate) {
        try{
            IndexResponse response = this.esClient.prepareIndex(INDEX_NAME, INDEX_TYPE)
                    .setSource(objectMapper.writeValueAsBytes(userIndexTemplate), XContentType.JSON).get();

            log.debug("Create index with house: " + userIndexTemplate.getUserId());
            if (response.status() == RestStatus.CREATED) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            log.info("e= "+e.getMessage());
        }
        return false;
    }

    @Override
    public void index(Long userId) {
        createOrUpdateIndex(userId);
    }

    @Override
    public void remove(Long userId) {

    }
}
