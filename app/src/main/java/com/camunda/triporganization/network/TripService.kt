package com.camunda.triporganization.network

import com.camunda.triporganization.model.BasicTaskItem
import com.camunda.triporganization.model.CitiesData
import com.camunda.triporganization.model.CityResponse
import com.camunda.triporganization.model.PartnerOfferResponse
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.model.TripItinerary
import com.camunda.triporganization.model.UserLogInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TripService {

    @POST("/user/login")
    suspend fun userLogIn(@Body user: UserLogInResponse): Response<Boolean>

    @POST("/trip/create")
    suspend fun createTripInstance(@Query("coordinatorId") userId: String): Response<Long>

    @GET("/trip/{processInstanceKey}")
    suspend fun getTripInformation(@Path("processInstanceKey") processKey: Long): Response<Trip>

    @POST("/trip/{processInstanceKey}/fillTripData")
    suspend fun fillTripCreationData(@Path("processInstanceKey") processKey: Long, @Body tripRequest: Trip)

    @POST("/trip/{processInstanceKey}/saveTripData")
    suspend fun saveTripCreationData(@Path("processInstanceKey") processKey: Long, @Body tripRequest: Trip)

    @GET("/user/{userId}/tasks/all")
    suspend fun getActiveTripsForUser(@Path("userId") userId: String): Map<String, List<BasicTaskItem>>

    @GET("/offer/trip/{processKey}")
    suspend fun getOffersForTrip(@Path("processKey") processKey: Long): PartnerOfferResponse

    @POST("/offer/trip/{processKey}/accept")
    suspend fun acceptOffersForTrip(@Path("processKey") processKey: Long, @Query("transportPartnerIds") transportPartnerId: List<Long>, @Query("accommodationPartnerIds") accommodationPartnerId: List<Long>)

    @POST("/offer/trip/{processKey}/rejectAll")
    suspend fun rejectOffersForTrip(@Path("processKey") processKey: Long)

    @GET("/user/availableGuides")
    suspend fun getAvailableGuides(@Query("processKey") processKey: Long): List<String>

    @POST("/trip/{processKey}/assignTourGuide")
    suspend fun assignTourGuide(@Path("processKey") processKey: Long, @Query("tourGuide") tourGuide: String)

    @GET("/trip/{processKey}/tripItinerary")
    suspend fun getTripItinerary(@Path("processKey") processKey: Long): Response<TripItinerary>

    @POST("/trip/{processKey}/reviewTripItinerary")
    suspend fun reviewTripItinerary(@Path("processKey") processKey: Long, @Query("price") price: Double?, @Query("note") note: String?)

    @POST("/trip/{processInstanceKey}/fillTripPlan")
    suspend fun fillTripItinerary(@Path("processInstanceKey") processKey: Long, @Body tripItinerary: List<CitiesData>)

    @GET("/city/all")
    suspend fun getCities(): List<CityResponse>

}