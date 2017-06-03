package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael.fccibim on 01/06/2017.
 */

public class SectionsPagerAdapter  extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    List<String> tabNames = new ArrayList<String>();

    public SectionsPagerAdapter(FragmentManager fm, ArrayList<Categoria> categorias) {
        super(fm);
        //Add tabs
        //fragmentList.add(new Tab1());
        fragmentList.add(new ProdutosFragment());
        tabNames.add("Todos");

        if(categorias != null) {
            for (Categoria c : categorias) {
                Bundle bundleCat = new Bundle();
                bundleCat.putInt("categoria", c.getIdCategoria());

                ProdutosFragment pf = new ProdutosFragment();
                pf.setArguments(bundleCat);
                fragmentList.add(pf);
                tabNames.add(c.getNomeCategoria());
            }
        }else{
            System.out.println("CATEGORIA VEIO NULO!");
        }

    }

    @Override
    public Fragment getItem(int position) {
        return (fragmentList.get(position));
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }
}
