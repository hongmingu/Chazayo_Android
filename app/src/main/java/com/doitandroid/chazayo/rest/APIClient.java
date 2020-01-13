package com.doitandroid.chazayo.rest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit;
    private static String base_url = ConstantREST.URL_HOME;
    // ConstantREST 에 일정값 넣어서 썼었음.

    // getCLient 함수가 스트링 토큰을 받는 버전이랑 안 받는 버전이 있다.
    // 원리는 똑같은거같음 필요없는 코드 수정해야할거같긴한데 귀찮음..
    public static Retrofit getClient(final String token) {


        // 장고와 토큰으로 통신을 하게 된다면 post에서 request의 body가 아니라 header에 토큰을 넣어서 통신을 하게 된다.
        // requestbody를 만들어서 쓰는 방법으로는 header에 토큰값을 넣기 힘드므로 Intercepter를 쓴다.
        // 아마 okhttp3에 있는 기능으로 기억한다. 아님말고

        Interceptor token_interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String auth_token = "Token "+ token ;
                // 장고에선 토큰값을 "Token 그리고 토큰일련랜덤스트링값" 으로 표현하므로 Token 바로 다음에 띄어쓰기를 넣어준 것.

                Request request;
                request = chain.request().newBuilder().addHeader("Authorization", auth_token).build();
                // 리퀘스트 빌드를 토큰을 오쏘라이재이션 헤더에 넣어주면서 만들어준다.

                // 참고: https://wkdtjsgur100.github.io/django-token-auth/

                return chain.proceed(request);
            }
        };

        HttpLoggingInterceptor log_interceptor = new HttpLoggingInterceptor();
        log_interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // 이건 왜 쓰는건지 기억이 안남 필요없을수도??

        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(log_interceptor)
                .addInterceptor(token_interceptor)
                .build();

        // 토큰넣어준 것을 적용되게끔 클라이언트를 만들어줌.
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        // 이건 레트로핏 구현방식.

        return retrofit;
    }

    public static Retrofit getClient() {

        Interceptor token_interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request;
                request = chain.request().newBuilder().addHeader("Authorization", "no_token").build();

                return chain.proceed(request);
            }
        };

        HttpLoggingInterceptor log_interceptor = new HttpLoggingInterceptor();
        log_interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(log_interceptor)
                .addInterceptor(token_interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}