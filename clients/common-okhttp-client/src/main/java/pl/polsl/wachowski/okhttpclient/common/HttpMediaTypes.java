package pl.polsl.wachowski.okhttpclient.common;

import okhttp3.MediaType;

public final class HttpMediaTypes {

    private HttpMediaTypes() {
    }

    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");

}
