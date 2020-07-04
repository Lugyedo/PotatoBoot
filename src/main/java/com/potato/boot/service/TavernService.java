package com.potato.boot.service;

import com.potato.boot.pojo.Tavern;
import org.springframework.stereotype.Service;

@Service
public class TavernService {
    //@Autowired
    //private MongoTemplate mongoTemplate;

    public Tavern getTavern() {
        //Query query = Query.query(Criteria.where("tavernGuid").is("5e0324d9-b897-479b-99e3-1f9a461d4938"));
        //return mongoTemplate.findOne(query, Tavern.class);
        return new Tavern();
    }

}
