package com.manwal.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestClient {
    private var retrofit: Retrofit? = null
    private val BASE_URL = "http://demo6483760.mockable.io/";
    val instance: Retrofit?
        @Synchronized get() {
            if (retrofit == null) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                val okHttpBuilder = OkHttpClient.Builder()
                okHttpBuilder.connectTimeout(3, TimeUnit.MINUTES)
                okHttpBuilder.readTimeout(3, TimeUnit.MINUTES)
                okHttpBuilder.retryOnConnectionFailure(true)
                okHttpBuilder.addInterceptor(httpLoggingInterceptor)

                okHttpBuilder.addInterceptor(object : Interceptor {

                    override fun intercept(chain: Interceptor.Chain): Response {

                        val requestOriginal = chain.request()
                        val requestBuilder = requestOriginal.newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Accept", "multipart/form-data")
                            .addHeader("Connection", "close")
                            .method(requestOriginal.method, requestOriginal.body)
                        val requestFinal = requestBuilder.build()
                        return chain.proceed(requestFinal)
                    }
                })

                val okHttpClient = okHttpBuilder.build()
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
            }
            return retrofit
        }
}
