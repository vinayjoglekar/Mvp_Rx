package com.jovinz.mvp_rx.presenter

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jovinz.mvp_rx.model.MovieListModel
import com.jovinz.mvp_rx.model.Result
import com.jovinz.mvp_rx.network.ApiInterface
import com.jovinz.mvp_rx.testutil.TestSchedulerProvider
import com.jovinz.mvp_rx.utils.ApiKey
import com.jovinz.mvp_rx.utils.FilterData
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class MovieListPresenterTest {

    @Mock
    lateinit var mockView: MovieListView

    lateinit var mockPresenter: MovieListPresenter

    lateinit var testSchedulerProvider: TestSchedulerProvider

    private val mockWebServer = MockWebServer()

    @Mock
    private lateinit var mockMovies: MovieListModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testSchedulerProvider = TestSchedulerProvider()


        mockMovies = MovieListModel(
            Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        )
    }

    @Test
    fun testGetMoviesBlankResponse() {
        val testSubscriber: TestObserver<MutableList<Result>> = TestObserver()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("")
        mockWebServer.enqueue(response)

        mockMovies.execute(
            ApiKey(""), 1, FilterData("kk")
        )?.subscribe(testSubscriber)
        testSubscriber.errors()
    }


    @Test
    fun testGetMoviesEmptyResponse() {
        val testSubscriber: TestObserver<MutableList<Result>> = TestObserver()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{ \"page\": 1, \"total_results\": 10000, \"total_pages\": 500, \"results\": []}")
        mockWebServer.enqueue(response)

        mockMovies.execute(
            ApiKey("0ba1ead43a849d61114e60ff7322b791"), 1, FilterData("kk")
        )?.subscribe(testSubscriber)
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()
        testSubscriber.assertValue {
            it.size == 0
        }

    }

    @Test
    fun testGetMoviesSizeOne() {
        var temp = this.javaClass.classLoader!!.getResource("./movies.json").readText()
        val testObserver: TestObserver<MutableList<Result>> = TestObserver()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(temp)
        mockWebServer.enqueue(response)
        mockMovies.execute(
            ApiKey("0ba1ead43a849d61114e60ff7322b791"), 1, FilterData("kk")
        )?.test()
        testObserver.awaitTerminalEvent(5, TimeUnit.SECONDS)
        testObserver
            .assertNoErrors().assertValue { it.size == 0 }

    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    fun <T> check(consumer: Consumer<T>): Predicate<T>? {
        return Predicate<T> { t ->
            consumer.accept(t)
            true
        }
    }
}