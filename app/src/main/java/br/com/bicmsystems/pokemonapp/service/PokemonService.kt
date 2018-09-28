package br.com.bicmsystems.pokemonapp.service

import br.com.bicmsystems.pokemonapp.api.PokemonAPI
import br.com.bicmsystems.pokemonapp.model.Pokemon
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonService {

    private val BASE_URL: String get() = "https://pokeapi.co"
    private val API_VERSION: String get() = "/api/v2/"
    private val FINAL_URL = BASE_URL + API_VERSION

    fun buildRetrofit() : PokemonAPI {

        val okhttp = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()

        return Retrofit.Builder()
                .baseUrl(FINAL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp)
                .build()
                .create(PokemonAPI::class.java)
    }

    fun obtemPokemonPorId(id: Int) : Call<Pokemon> = buildRetrofit().obtemPokemonPorId(id)

}