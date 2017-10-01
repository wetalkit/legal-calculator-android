package mk.wetalkit.legalcalculator.api;

import java.util.HashMap;

import mk.wetalkit.legalcalculator.data.ServicesResponse;
import mk.wetalkit.legalcalculator.data.TotalCost;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by nikolaminoski on 10/1/17.
 */

public interface LegalCalcServices {
    @GET("procedures")
    Call<ServicesResponse> getServices();

    @POST("calculate")
    Call<TotalCost> calculate(@Field("procedure_id") int procedureId, @FieldMap HashMap<String, String> params);

}
