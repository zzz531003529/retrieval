package com.retrieval.controllers;

import com.retrieval.models.PublicOpionModel;
import com.retrieval.services.PublicOpionService;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by sks on 2017/9/21.
 */
@RestController
public class PublicOpionController {

    private PublicOpionService solrServer;

    private HttpSolrClient httpSolrServer;


    @RequestMapping(value = "/searching", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
//    public List<PublicOpionModel> searching(@RequestBody(required = false)String navtitle)throws Exception{
    public ResponseEntity<Object> searching(@RequestBody String term,
                                            @RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception {

        System.out.println("====================================");

        System.out.println(page);
        System.out.println(term);

        String serverUrl = "http://10.10.4.48:8983/solr/uradar_article_shard2_replica2";

        HttpSolrClient httpSolrServer = new HttpSolrClient(serverUrl);

        solrServer = new PublicOpionService(httpSolrServer);

        HttpHeaders responseHeaders = new HttpHeaders();

        Object datas = new HashMap();

//        List<PublicOpionModel> models = solrServer.search(navtitle,2,10);
        HashMap res = solrServer.search(term, page, 10);

        responseHeaders.set("X-Total-Count", res.get("X-Total-Count").toString());

        datas = res.get("data");


//        responseHeaders.set("total_count",res.count);
        return new ResponseEntity<Object>(datas, responseHeaders, HttpStatus.CREATED);
    }

}
