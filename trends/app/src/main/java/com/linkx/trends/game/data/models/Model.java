package com.linkx.trends.game.data.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linkx.trends.game.data.SerializerProvider;
import java.io.IOException;

public class Model {
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return SerializerProvider.getInstance().readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson() {
        try {
            return SerializerProvider.getInstance().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String identity() throws MethodNotOverrideException {
        throw new MethodNotOverrideException("child:" + this.getClass().getName() + " must override this");
    }

    public static class MethodNotOverrideException extends Exception {
        public MethodNotOverrideException(String detailMessage) {
            super(detailMessage);
        }
    }
}
