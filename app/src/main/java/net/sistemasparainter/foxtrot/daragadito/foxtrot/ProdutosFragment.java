package net.sistemasparainter.foxtrot.daragadito.foxtrot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProdutosFragment extends Fragment {


    private ViewGroup linearContainer;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public ProdutosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_produtos, container, false);

        linearContainer = (ViewGroup) fragmentView.findViewById(R.id.linearContainer);
        ArrayList<String> urls = new ArrayList<>();
        //urls.add("http://localhost:8080/WSECommerce/rest/imagem/1/200/200");
        urls.add("https://riodegraca.files.wordpress.com/2016/07/casa_suica.jpg");
        urls.add("https://i2.wp.com/oprofessorweb.files.wordpress.com/2017/04/figura-4.jpg");

        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));

        for (int i = 0; i < 2; i++) {

            addCardView(urls.get(i), savedInstanceState);
        }

        // Inflate the layout for this fragment
        return fragmentView;
    }
    private void addCardView(String url, Bundle bundle) {

        CardView cardView = (CardView) getLayoutInflater(bundle).inflate(R.layout.fragment_produtos_cardview, linearContainer, false);
        ImageView verImagem = (ImageView) cardView.findViewById(R.id.imageView);
        //imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
        imageLoader.displayImage(url, verImagem);

        linearContainer.addView(cardView);

    }
}
