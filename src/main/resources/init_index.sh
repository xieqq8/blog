curl -X PUT http://localhost:9200/blog -d '{
                                               "settings" : {
                                                   "index" : {
                                                       "number_of_shards" : 3,
                                                       "number_of_replicas" : 0
                                                   }
                                               }
                                           }'

curl -X POST http://localhost:9200/blog/blog/_mapping -d '{
      "properties": {
            "id":    {
                "type": "text",
                "index":"no"
            },
            "title": {
                "type": "text",
                "analyzer": "ik_smart",
                "search_analyzer": "ik_smart"
            },
            "secondTitle":    {
                "type": "text",
                "analyzer": "ik_smart",
                "search_analyzer": "ik_smart"
            },
            "catalog":    {
                "type": "keyword"
            },
            "auther":    {
                "type": "keyword",
                "index":"no"
            },
            "img":    {
                "type": "text",
                "index":"no"
            },
            "md":     {
                "type": "text",
                "analyzer": "ik_smart",
                "search_analyzer": "ik_smart"
            },
            "display":  {
                "type":   "text",
                "index":"no"
            },
            "publishTime":  {
                "type":   "date",
                "format": "strict_date_optional_time||epoch_millis"
            }
      }
}'