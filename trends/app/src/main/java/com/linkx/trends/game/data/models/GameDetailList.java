package com.linkx.trends.game.data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import java.util.List;

/**
 * Created by ulyx.yang on 2016/9/15.
 */
@AutoValue
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GameDetailList extends Model {
    @JsonProperty("id")
    public abstract String id();
    @JsonProperty("details")
    public abstract List<GameDetail> details();

    @Override
    public String identity() throws MethodNotOverrideException {
        return id();
    }

    @JsonCreator
    public static GameDetailList create(@JsonProperty("id") String id,
                                        @JsonProperty("details") List<GameDetail> details
                                        ) {
        return new AutoValue_GameDetailList(id, details);
    }

}
