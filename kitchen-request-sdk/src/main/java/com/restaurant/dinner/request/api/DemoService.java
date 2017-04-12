package com.restaurant.dinner.request.api;

import com.restaurant.dinner.request.domain.DemoDomain;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 请求服务接口定义（示例）
 *
 * @date 2017-04-11
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public interface DemoService {

    @GET("demo/{id}")
    Call<DemoDomain> test(@Path("id") String id);

}
