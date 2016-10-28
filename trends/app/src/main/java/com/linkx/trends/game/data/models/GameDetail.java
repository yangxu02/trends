package com.linkx.trends.game.data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by ulyx.yang on 2016/9/15.
 */
@AutoValue
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GameDetail extends Model {
    @JsonProperty("taptap_id")
    public abstract String id();
    @JsonProperty("rank")
    public abstract String rank();
    @JsonProperty("icon")
    public abstract String icon();
    @JsonProperty("title")
    public abstract String title();
    @JsonProperty("description")
    public abstract String desc();
    @JsonProperty("category")
    public abstract String category();
    @JsonProperty("rating")
    public abstract String rating();
    @JsonProperty("img")
    public abstract String clip();
    @JsonProperty("img_orientation")
    public abstract Orientation clip_orientation();

    @Override
    public String identity() throws MethodNotOverrideException {
        return id();
    }

    @JsonCreator
    public static GameDetail create(@JsonProperty("taptap_id") String id,
                                    @JsonProperty("rank") String rank,
                                    @JsonProperty("icon") String icon,
                                    @JsonProperty("title") String title,
                                    @JsonProperty("description") String desc,
                                    @JsonProperty("category") String category,
                                    @JsonProperty("rating") String rating,
                                    @JsonProperty("img") String clip,
                                    @JsonProperty("img_orientation") Orientation clip_orientation
    ) {
        return new AutoValue_GameDetail(id, rank, icon,
            StringEscapeUtils.unescapeJava(title),
            StringEscapeUtils.unescapeJava(desc),
            StringEscapeUtils.unescapeJava(category),
            rating, clip, clip_orientation);
    }

}
