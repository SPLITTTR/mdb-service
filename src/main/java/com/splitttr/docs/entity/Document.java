package com.splitttr.docs.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@MongoEntity(collection = "documents")
public class Document {

    @BsonId
    public ObjectId id;

    public String title;
    public String content;
    public String ownerId;

    public Instant createdAt;
    public Instant updatedAt;

    public long version;

    public List<String> activeEditors = new ArrayList<>();
}
