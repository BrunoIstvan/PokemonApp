package br.com.bicmsystems.pokemonapp.loading

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import android.view.Window.FEATURE_NO_TITLE
import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.ImageView
import br.com.bicmsystems.pokemonapp.R
import com.squareup.picasso.Picasso

class ViewDialog {

    var activity: Activity? = null
    var dialog: Dialog? = null

    constructor(activity: Activity) {
        this.activity = activity
    }

    fun showDialog() {

        dialog = Dialog(activity)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //...set cancelable false so that it's never get hidden
        dialog?.setCancelable(false)
        //...that's the layout i told you will inflate later
        dialog?.setContentView(R.layout.custom_loading_layout)

        //...initialize the imageView form infalted layout
        val gifImageView = dialog?.findViewById(R.id.custom_loading_imageView) as ImageView

        /*
        it was never easy to load gif into an ImageView before Glide or Others library
        and for doing this we need DrawableImageViewTarget to that ImageView
        */
        // Essa é uma outra biblioteca que manipula imagem
       val imageViewTarget = GlideDrawableImageViewTarget(gifImageView)

        //...now load that gif which we put inside the drawble folder here with the help of Glide

        // Essa é uma outra biblioteca que manipula imagem
        Glide.with(activity)
                .load(R.drawable.loading)
                .placeholder(R.drawable.loading)
                .centerCrop()
                .crossFade()
                .into(imageViewTarget)

        //...finaly show it
        dialog?.show()
    }

    //..also create a method which will hide the dialog when some work is done
    fun hideDialog() {
        dialog?.dismiss()
    }


}