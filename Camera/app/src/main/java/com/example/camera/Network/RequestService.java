/**
 * Created by James Coggan on 24/09/2016.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.camera.Network;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RequestService {
//    @GET("usr=user5&pwd=media5&{cmd}")
    @GET("CGIProxy.fcgi")
    Observable<ResponseBody> request(@Query("usr") String user, @Query("pwd") String pwd, @Query("cmd") String path);
}