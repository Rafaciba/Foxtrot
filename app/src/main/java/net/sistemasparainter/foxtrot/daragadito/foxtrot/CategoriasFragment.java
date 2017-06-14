package net.sistemasparainter.foxtrot.daragadito.foxtrot;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriasFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<Categoria> categoriasArrayList = null;

    ShowDialog showDialog = new ShowDialog(getActivity());

    public CategoriasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_categorias, container, false);
        viewPager = (ViewPager) fragmentView.findViewById(R.id.pager);
        tabLayout = (TabLayout) fragmentView.findViewById(R.id.tabsCategorias);

        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Carregando a lista de produtos");
        progress.setCancelable(false);
        progress.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://foxtrotws.azurewebsites.net/g1/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Services service = retrofit.create(Services.class);

        Call<ArrayList<Categoria>> categorias = service.getCategorias();

        try {
            categorias.enqueue(new Callback<ArrayList<Categoria>>() {
                @Override
                public void onResponse(Call<ArrayList<Categoria>> call, Response<ArrayList<Categoria>> response) {
                    if(response.code() == 200) {
                        categoriasArrayList = response.body();

                        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getFragmentManager(), categoriasArrayList);
                        viewPager.setAdapter(adapter);
                        tabLayout.setupWithViewPager(viewPager);
                        progress.dismiss();
                    }else{
                        progress.dismiss();
                        showDialog.showMessage("Não foi possível carregar os produtos.","Ocorreu um erro");
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Categoria>> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    showDialog.showMessage("Não foi possível carregar os produtos.","Ocorreu um erro");
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }


        return fragmentView;
    }

}
