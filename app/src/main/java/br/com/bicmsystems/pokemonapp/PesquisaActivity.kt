package br.com.bicmsystems.pokemonapp

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.bicmsystems.pokemonapp.loading.ViewDialog
import br.com.bicmsystems.pokemonapp.model.Pokemon
import br.com.bicmsystems.pokemonapp.service.PokemonService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pesquisa.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PesquisaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)
        btnPesquisar.setOnClickListener() {
            pesquisarPokemon()
        }
    }

    private fun pesquisarPokemon() {
        val id = (edtPesquisa.text.let { "${it!!}" } ?: "0").toInt()
        if(id <= 0) {
            Toast.makeText(this, "Informe um código válido! Maior que ZERO", Toast.LENGTH_SHORT).show()
            return
        }
        val viewDialog = ViewDialog(this)
        viewDialog.showDialog()
        val service = PokemonService()
        service.obtemPokemonPorId(id)
                .enqueue(object : Callback<Pokemon>{
                    override fun onFailure(call: Call<Pokemon>?, t: Throwable?) {
                        viewDialog.hideDialog()
                        Toast.makeText(this@PesquisaActivity, "Erro: ${t!!.message}", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<Pokemon>?, response: Response<Pokemon>?) {
                        viewDialog.hideDialog()
                        response?.isSuccessful.let {
                            if(it!!) {
                                val pokemon = response?.body()
                                tvNome.text = pokemon?.nome
                                Picasso.with(this@PesquisaActivity)
                                        .load(pokemon?.imagens?.frontDefault)
                                        .placeholder(R.drawable.pikachu)
                                        .error(R.drawable.pokemon_not_found)
                                        .into(ivPokemon)
                            } else {
                                Toast.makeText(this@PesquisaActivity, "Nenhum Pokemon foi encontrado para esse código", Toast.LENGTH_SHORT).show()
                                Picasso.with(this@PesquisaActivity)
                                        .load(R.drawable.pokemon_not_found)
                                        .placeholder(R.drawable.pikachu)
                                        .into(ivPokemon)
                            }
                        }
                    }
                })
    }

}
